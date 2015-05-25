package throwable.server.handler.user;

import java.util.Map;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

import throwable.server.ThrowableConf;
import throwable.server.bean.User;
import throwable.server.bean.UserExtend;
import throwable.server.utils.BackTool;
import throwable.server.utils.StringTool;

/**
 * 用户处理类
 * 
 * @author WaterHsu@xiu8.com
 * @version 2014年10月10日
 */
@At("/user")
@IocBean
public class UserController {

	@Inject
	private UserServer userServer;
	
	/**
	 * 用户注册
	 * @param user
	 * @return
	 */
	@At("/register")
	public Map<String, Object> addOneUser(@Param("..") User user){
		if(StringTool.isEmpty(user)){
			return BackTool.errorInfo("010108", ThrowableConf.errorMsg);
		}
		if(StringTool.isEmpty(user.username)){
			return BackTool.errorInfo("010101", ThrowableConf.errorMsg);
		}
		if(StringTool.isEmpty(user.password)){
			return BackTool.errorInfo("010102", ThrowableConf.errorMsg);
		}
		if(StringTool.isEmpty(user.nickname)){
			return BackTool.errorInfo("010103", ThrowableConf.errorMsg);
		}
		if(StringTool.isEmpty(user.email)){
			return BackTool.errorInfo("010104", ThrowableConf.errorMsg);
		}
		if(StringTool.isEmpty(user.last_active_ip)){
			return BackTool.errorInfo("010112", ThrowableConf.errorMsg);
		}
		if(!StringTool.isIp(user.last_active_ip)){
			return BackTool.errorInfo("010113", ThrowableConf.errorMsg);
		}
		if(!StringTool.checkEmail(user.email)){
			return BackTool.errorInfo("010111", ThrowableConf.errorMsg);
		}
		if(StringTool.isNumber(user.username)){
			return BackTool.errorInfo("010110", ThrowableConf.errorMsg);
		}
		if(!StringTool.userNameCheck(user.username)){
			return BackTool.errorInfo("010109", ThrowableConf.errorMsg);
		}
		return userServer.addOneUser(user);
	}
	
	/**
	 * 用户登陆
	 * @param username   用户名  
	 * @param password   密码
	 * @param ip         登陆ip
	 * @return
	 */
	@At("/login")
	public Map<String, Object> login(@Param("username") String username, @Param("password") String password, @Param("ip") String ip){
		if(StringTool.isEmpty(username)){
			return BackTool.errorInfo("010201", ThrowableConf.errorMsg);
		}
		if(StringTool.isEmpty(password)){
			return BackTool.errorInfo("010202", ThrowableConf.errorMsg);
		}
		if(StringTool.isEmpty(ip)){
			return BackTool.errorInfo("010210", ThrowableConf.errorMsg);
		}
		if(!StringTool.isIp(ip)){
			return BackTool.errorInfo("010211", ThrowableConf.errorMsg);
		}
		return userServer.login(username, password, ip);
	}
	
	/**
	 * 激活用户发送邮件
	 * @param userId   用户id
	 * @param url      激活用户的地址链接
	 * @return
	 */
	@At("/activeUserEmail")
	public Map<String, Object> activeUserEmail(@Param("userId") int userId, @Param("url") String url) {
		if (userId < 1) {
			return BackTool.errorInfo("010403", ThrowableConf.errorMsg);
		}
		if (StringTool.isEmpty(url)) {
			return BackTool.errorInfo("010404", ThrowableConf.errorMsg);
		}
		if (!url.endsWith("?key=")) {
			return BackTool.errorInfo("010405", ThrowableConf.errorMsg);
		}
		return userServer.activeUserEmail(userId, url);
	}
	
	/**
	 * 激活账号
	 * @param key   激活的key
	 * @return
	 */
	@At("/activeUser")
	public Map<String, Object> activeUser(@Param("key") String key) {
		if (StringTool.isEmpty(key)) {
			return BackTool.errorInfo("010506", ThrowableConf.errorMsg);
		}
		return userServer.activeUser(key);
	} 
	
	/**
	 * 添加用户扩展信息
	 * @param userId               用户id
	 * @param live_address         住址
	 * @param now_job              当前工作 职位
	 * @param graduate_school      毕业院校
	 * @param motto                座右铭
	 * @param interest             兴趣爱好
	 * @param goodAt               擅长
	 */
	@At("/addUserExtend")
	public Map<String, Object> addUserExtendInfo(@Param("..") UserExtend userInfo) {
		if(StringTool.isEmpty(userInfo)) {
			return BackTool.errorInfo("010702");
		}
		if(userInfo.user_id < 1) {
			return BackTool.errorInfo("010701");
		}
		userServer.saveUserExtendInfo(userInfo);
		return BackTool.successInfo();
	}
	
	/**
	 * 查询用户信息
	 * @param userId  用户id
	 * @return
	 */
	@At("/queryUserInfo")
	public User queryUserInfo(long userId) {
		if(userId < 1) {
			BackTool.errorInfo("070104");
		}
		return userServer.queryUserInfo(userId);
	}
}
