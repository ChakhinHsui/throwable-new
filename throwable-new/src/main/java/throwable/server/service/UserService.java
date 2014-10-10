package throwable.server.service;

import java.util.Date;
import java.util.List;

import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import throwable.server.bean.User;

/**
 * @author WaterHsu@xiu8.com
 * @version 2014年10月10日
 */
@IocBean
public class UserService extends BaseService {

	private Log log = Logs.get();
	
	/**
	 * user 根据username查询用户信息
	 * @param username
	 * @return
	 */
	public User queryUserByUserName(String username){
		Sql sql = dao.sqls().create("user_queryUserByUserName");
		sql.params().set("username", username);
		sql.setCallback(Sqls.callback.entities());
		sql.setEntity(dao.getEntity(User.class));
		dao.execute(sql);
		List<User> list = sql.getList(User.class);
		if(list == null || list.size() == 0) return null;
		return list.get(0);
	}
	
	/**
	 * user 根据email查询用户信息
	 * @param email
	 * @return
	 */
	public User queryUserByEmail(String email){
		Sql sql = dao.sqls().create("user_queryUserByEmail");
		sql.params().set("email", email);
		sql.setCallback(Sqls.callback.entities());
		sql.setEntity(dao.getEntity(User.class));
		dao.execute(sql);
		List<User> list = sql.getList(User.class);
		if(list == null || list.size() == 0) return null;
		return list.get(0);
	}
	
	
	/**
	 * user 查询所有用户信息
	 * @return
	 */
	public List<User> queryAllUser(){
		Sql sql = dao.sqls().create("user_queryAllUser");
		sql.setCallback(Sqls.callback.entities());
		sql.setEntity(dao.getEntity(User.class));
		List<User> list = sql.getList(User.class);
		if(list == null || list.size() == 0) return null;
		return list;
	}
	
	/**
	 * user 插入一个用户
	 * @param user
	 */
	public void insertOneUser(User user){
		Sql sql = dao.sqls().create("user_insertOneUser");
		sql.params().set("username", user.username);
		sql.params().set("password", user.password);
		sql.params().set("rights", user.rights);
		sql.params().set("email", user.email);
		sql.params().set("nickname", user.nickname);
		sql.params().set("phone", "");
		sql.params().set("qq", "");
		sql.params().set("score", 0);
		sql.params().set("user_state", 1);
		sql.params().set("create_time", System.currentTimeMillis());
		sql.params().set("last_update_time", System.currentTimeMillis());
		sql.params().set("last_active_time", System.currentTimeMillis());
		sql.params().set("last_active_area", "");
		sql.params().set("last_active_ip", "");
		sql.params().set("last_forgetpassword_time", 0);
		sql.params().set("last_mark_time", 0);
		sql.params().set("last_mark_ip", "");
		
		dao.execute(sql);
	}
}
