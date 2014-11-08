package throwable.server.handler.user;

import java.util.HashMap;
import java.util.Map;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import throwable.server.ThrowableConf;
import throwable.server.bean.User;
import throwable.server.enums.State;
import throwable.server.service.UserService;
import throwable.server.utils.BackTool;
import throwable.server.utils.MD5;

/**
 * @author WaterHsu@xiu8.com
 * @version 2014年10月10日
 */
@IocBean
public class UserServer {

	private Log log = Logs.get();
	
	@Inject
	private UserService userService;
	
	/**
	 * 用户注册
	 * @param user
	 * @return
	 */
	@SuppressWarnings("unchecked")
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
		return userService.queryPartUserInfoByUserName(user.username);
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
}
