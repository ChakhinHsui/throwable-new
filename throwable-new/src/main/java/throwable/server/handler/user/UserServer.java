package throwable.server.handler.user;

import java.util.Map;

import org.nutz.castor.Castors;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import throwable.server.ThrowableConf;
import throwable.server.bean.User;
import throwable.server.service.UserService;
import throwable.server.utils.BackTool;

/**
 * @author WaterHsu@xiu8.com
 * @version 2014年10月10日
 */
@IocBean
public class UserServer {

	private Log log = Logs.get();
	
	@Inject
	private UserService userService;
	
	public Map<String, Object> addOneUser(User user){
		System.out.println(user.username);
		User user_x = userService.queryUserByUserName(user.username);
		if(null != user_x){
			return BackTool.errorInfo("010105", ThrowableConf.errorMsg);
		}
		user_x = userService.queryUserByEmail(user.email);
		if(null != user_x){
			return BackTool.errorInfo("010106", ThrowableConf.errorMsg);
		}
		user.rights = "general_user";
		userService.insertOneUser(user);
		//return BackTool.successInfo();
		return Lang.obj2map(user);
	}
}
