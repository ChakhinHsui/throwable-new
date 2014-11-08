package throwable.server.api;

import java.util.HashMap;
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
	
	/**
	 * 这是给后台管理用的  加入了权限控制
	 * @param userId   管理员的用户id
	 * @return
	 */
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
	
	/**
	 * 查询用户部分信息
	 * @param userId   要查询的用户id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@At("/queryUserPartInfo")
	public Map queryUserPartInfo(@Param("userId") int userId){
		if(StringTool.isEmpty(userId)){
			return BackTool.errorInfo("010301", ThrowableConf.errorMsg);
		}
		return userService.queryPartUserInfoById(userId);
	}
}
