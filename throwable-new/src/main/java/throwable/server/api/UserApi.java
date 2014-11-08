package throwable.server.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

import throwable.server.ThrowableConf;
import throwable.server.bean.User;
import throwable.server.enums.Right;
import throwable.server.service.UserService;
import throwable.server.utils.BackTool;
import throwable.server.utils.StringTool;

/**
 * @author WaterHsu@xiu8.com
 * @version 2014年11月8日
 */
@At("/userApi")
@IocBean
public class UserApi {

	@Inject
	private UserService userService;
	
	@SuppressWarnings({"unchecked","rawtypes"})
	@At("/queryAllUser")
	public Map queryAllUser(@Param("..")int userId){
		if(StringTool.isEmpty(userId)){
			return BackTool.errorInfo("010301", ThrowableConf.errorMsg);
		}
		User user = userService.queryUserById(userId);
		if(user == null){
			return BackTool.errorInfo("010302", ThrowableConf.errorMsg);
		}
		if(!user.rights.equals(Right.web.getValue()) && !user.rights.equals(Right.superU.getValue())){
			return BackTool.errorInfo("010303", ThrowableConf.errorMsg);
		}
		Map map = new HashMap();
		map.get(userService.queryAllUser());
		map.put("users", userService.queryAllUser());
		return map;
	}
}
