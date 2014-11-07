package throwable.server.handler.user;

import java.util.Map;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

import throwable.server.ThrowableConf;
import throwable.server.bean.User;
import throwable.server.utils.BackTool;
import throwable.server.utils.StringTool;

/**
 * @author WaterHsu@xiu8.com
 * @version 2014年10月10日
 */
@At("/user")
@IocBean
public class UserController {

	@Inject
	private UserServer userServer;
	
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
		if(!StringTool.checkEmail(user.email)){
			return BackTool.errorInfo("010111", ThrowableConf.errorMsg);
		}
		if(StringTool.isNumber(user.email)){
			return BackTool.errorInfo("010110", ThrowableConf.errorMsg);
		}
		return userServer.addOneUser(user);
	}
	
	@At("/login")
	public Map<String, Object> login(@Param("username") String username, @Param("password") String password){
		if(StringTool.isEmpty(username)){
			return BackTool.errorInfo("010201", ThrowableConf.errorMsg);
		}
		if(StringTool.isEmpty(password)){
			return BackTool.errorInfo("010202", ThrowableConf.errorMsg);
		}
		
		return userServer.login(username, password);
	}
}
