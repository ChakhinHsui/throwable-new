package throwable.server.handler.user;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import throwable.server.ThrowableConf;
import throwable.server.bean.User;
import throwable.server.enums.State;
import throwable.server.service.SsdbHandler;
import throwable.server.service.UserService;
import throwable.server.utils.BackTool;
import throwable.server.utils.MD5;
import throwable.server.utils.MailTool;
import throwable.server.utils.Security;
import throwable.server.utils.StringTool;

/**
 * @author WaterHsu@xiu8.com
 * @version 2014年10月10日
 */
@IocBean
public class UserServer {

	private Log log = Logs.getLog("UserServer");
	private String USER_ACTIVE_UUID = "user_active_";
	private String USER_ACTIVE_NUM = "user_active_num";
	private String USER_ACTIVE_NUM_OUT = "user_active_num_out";
	@Inject
	private UserService userService;
	@Inject
	private SsdbHandler ssdbHandler;
	
	/**
	 * 用户注册
	 * @param user
	 * @return
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public Map<String, Object> addOneUser(User user){
		User user_x = userService.queryUserByUserName(user.username);
		if(null != user_x){
			log.error("用户注册:  用户已被注册  " + user.username);
			return BackTool.errorInfo("010105", ThrowableConf.errorMsg);
		}
		user_x = userService.queryUserByEmail(user.email);
		if(null != user_x){
			log.error("用户注册:  邮箱已被注册  " + user.email);
			return BackTool.errorInfo("010106", ThrowableConf.errorMsg);
		}
		//md5加密
		user.password = MD5.md5(user.password);
		user.rights = "general_user";
		user.user_state = 1;
		user.score = 0;
		user.qq = "";
		user.phone = "";
		user.create_time = System.currentTimeMillis();
		user.last_update_time = System.currentTimeMillis();
		user.last_active_time = System.currentTimeMillis();
		user.last_active_area = "";
		user.last_forgetpassword_time = 0;
		user.last_mark_time = 0;
		user.last_mark_ip = "";
		userService.insertOneUser(user);
		log.info("用户注册: 用户注册成功 " + user.username);
		Map map = userService.queryPartUserInfoByUserName(user.username);
		//发送激活邮件
		sendEmail(Integer.parseInt(map.get("id").toString()), null);
		return map;
	}
	
	/**
	 * 用户登陆
	 * @param username  用户名
	 * @param password  密码
	 * @return
	 */
	@SuppressWarnings({"rawtypes", "unchecked"})
	public Map<String, Object> login(String username, String password, String ip){
		Map map = userService.queryPartUserInfoByUserName(username);
		if(map == null){
			log.error("用户登陆: 用户名错误 " + username);
			return BackTool.errorInfo("010203", ThrowableConf.errorMsg);
		}else{
			if(!MD5.md5(password).equals(map.get("password"))){
				log.error("用户登陆: 密码错误 " + username);
				return BackTool.errorInfo("010204", ThrowableConf.errorMsg);
			}else{
				if((int)map.get("user_state") == State.no_active.getValue()){
					log.error("用户登陆: " + State.no_active.getName() + " " + username);
					return BackTool.errorInfo("010205", ThrowableConf.errorMsg);
				}else if((int)map.get("user_state") == State.user_exception.getValue()){
					log.error("用户登陆: " + State.user_exception.getName() + " " + username);
					return BackTool.errorInfo("010207", ThrowableConf.errorMsg);
				}else if((int)map.get("user_state") == State.user_frozen.getValue()){
					log.error("用户登陆: " + State.user_frozen.getName() + " " + username);
					return BackTool.errorInfo("010206", ThrowableConf.errorMsg);
				}else if((int)map.get("user_state") == State.user_disabled.getValue()){
					log.error("用户登陆: " + State.user_disabled.getName() + " " + username);
					return BackTool.errorInfo("010208", ThrowableConf.errorMsg);
				}else if((int)map.get("user_state") == State.user_nomal.getValue()){
					Map<String, Object> mm = new HashMap<String, Object>();
					mm.put("last_active_time", System.currentTimeMillis());
					mm.put("last_active_area", "");
					mm.put("last_active_ip", ip);
					userService.updateLoginSuccess((int)map.get("id"), mm);
					log.info("用户登陆: 登陆成功! " + username);
					return map;
				}else{
					return BackTool.errorInfo("010209", ThrowableConf.errorMsg);
				}
			}
		}
	}
	
	/**
	 * 用户激活发送邮件
	 * 先从ssdb中获取激活次数  如果当天的激活次数超过10次  则不能激活  否则激活
	 * @param map
	 */
	public Map<String, Object> activeUserEmail(int userId, String url) {
		String num = ssdbHandler.hget(USER_ACTIVE_NUM, "" + userId);
		if(!StringTool.isEmpty(num)) {
			if(Integer.parseInt(num) > 10) {
				String num_out = ssdbHandler.hget(USER_ACTIVE_NUM_OUT, "" + userId);
				if(StringTool.isEmpty(num_out)) {
					ssdbHandler.hset(USER_ACTIVE_NUM_OUT, "" + userId, "" + System.currentTimeMillis());
					return BackTool.errorInfo("010401", ThrowableConf.errorMsg);
				}else {
					if(System.currentTimeMillis() - Long.parseLong(num_out) < 1000 * 60 * 60 * 24) {
						return BackTool.errorInfo("010401", ThrowableConf.errorMsg);
					}else {
						ssdbHandler.hset(USER_ACTIVE_NUM, "" + userId, "" + 0);
						ssdbHandler.hdel(USER_ACTIVE_NUM_OUT, "" + userId);
					}
				}
			}
		}
		if (!sendEmail(userId, url)) {
			return BackTool.errorInfo("010402", ThrowableConf.errorMsg);
		}
		return null;
	}
	
	/**
	 * 用户激活账号
	 * @param key  邮件里面的key
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Map<String, Object> activeUser(String key) {
		String str = Security.decryptContent(key, null);
		Map webMap = Json.fromJson(Map.class, str);
		String ssdbJson = ssdbHandler.get(USER_ACTIVE_UUID + webMap.get("uuid").toString());
		if (StringTool.isEmpty(ssdbJson)) {
			log.error("激活链接已失效");
			return BackTool.errorInfo("010501", ThrowableConf.errorMsg);
		}
		Map ssdbMap = Json.fromJson(Map.class, ssdbJson);
		if(webMap.get("userId").toString().equals(ssdbMap.get("userId").toString()) 
				&& webMap.get("username").toString().equals(ssdbMap.get("username").toString())
				&& webMap.get("email").toString().equals(ssdbMap.get("email").toString())
				&& webMap.get("uuid").toString().equals(ssdbMap.get("uuid").toString())) {
			int userId = Integer.parseInt(ssdbMap.get("userId").toString());
			int state = userService.queryUserState(userId);
			if (state == State.user_nomal.getValue()) {
				log.error("用户已激活");
				return BackTool.errorInfo("010503", ThrowableConf.errorMsg);
			}
			if (state == State.user_frozen.getValue()) {
				log.error("用户被冻结不能激活");
				return BackTool.errorInfo("010504", ThrowableConf.errorMsg);
			}
			if (state == State.user_disabled.getValue()) {
				log.error("用户已停用不能激活");
				return BackTool.errorInfo("010505", ThrowableConf.errorMsg);
			}
			userService.updateUserState(userId, State.user_nomal.getValue());
		} else {
			log.error("验证信息失败");
			return BackTool.errorInfo("010502", ThrowableConf.errorMsg);
		}
		return BackTool.successInfo();
	}
	
	/**
	 * 发送邮件的公共方法
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private boolean sendEmail(int userId, String url) {
		Map map = userService.queryPartUserInfoById(userId);
		Map<String, String> ssdbMap = new HashMap<String, String>();
		String email = map.get("email").toString();
		String username = map.get("username").toString();
		ssdbMap.put("userId", map.get("id").toString());
		ssdbMap.put("username", username);
		ssdbMap.put("email", email);
		ssdbMap.put("uuid", UUID.randomUUID().toString());
		String param = null;
		try {
			param = URLEncoder.encode(Security.encryptContent(Json.toJson(ssdbMap)), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		try {
			MailTool.sendMail(email, "Throwable技术问答社区账号激活", StringTool.getRegisterMailString(username, (url == null ? ThrowableConf.appProp.get("url", "http://127.0.0.1/throwable-web/active.html?key=") : url) + param), true);
			ssdbHandler.setx(USER_ACTIVE_UUID + ssdbMap.get("uuid"), Json.toJson(ssdbMap), 24 * 60 * 60);
			int send_num = ssdbHandler.hincr(USER_ACTIVE_NUM, "" + userId, 1);
			if (send_num >= 10) {
				ssdbHandler.hset(USER_ACTIVE_NUM_OUT, "" + userId, "" + System.currentTimeMillis());
			}
		} catch (Exception e) {
			log.equals("发送邮件出错! " + e);
			return false;
		}
		return true;
	}
}
