package throwable.server.handler.user;

import java.util.Map;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

import throwable.server.bean.User;
import throwable.server.conf.ThrowableConf;
import throwable.server.utils.BackTool;

/**
 * @author WaterHsu@xiu8.com
 * @version 2014年10月10日
 */
@At("/user")
public class UserController {

	@Inject
	private UserServer userServer;
	
	@At("/register")
	public Map<String, Object> addOneUser(@Param("..") User user){
		if(null == user.username || user.username.trim().equals("")){
			return BackTool.errorInfo("010101", ThrowableConf.errorMsg);
		}
		if(null == user.password || user.password.trim().equals("")){
			return BackTool.errorInfo("010102", ThrowableConf.errorMsg);
		}
		if(null == user.nickname || user.nickname.trim().equals("")){
			return BackTool.errorInfo("010103", ThrowableConf.errorMsg);
		}
		if(null == user.email || user.email.trim().equals("")){
			return BackTool.errorInfo("010104", ThrowableConf.errorMsg);
		}
		return userServer.addOneUser(user);
	}
}
