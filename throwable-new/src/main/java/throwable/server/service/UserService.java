package throwable.server.service;

import java.util.List;
import java.util.Map;

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
		log.info(username + ":  " + sql.getSourceSql());
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
		log.info(sql.getSourceSql());
		List<User> list = sql.getList(User.class);
		if(list == null || list.size() == 0) return null;
		return list.get(0);
	}
	
	/**
	 * user 查询所有用户
	 * @return
	 */
	public List<User> queryAllUser(){
		Sql sql = dao.sqls().create("user_queryAllUser");
		sql.setCallback(Sqls.callback.entities());
		sql.setEntity(dao.getEntity(User.class));
		dao.execute(sql);
		log.info(sql.getSourceSql());
		List<User> list = sql.getList(User.class);
		if(list == null || list.size() == 0) return null;
		return list;
	}
	
	/**
	 * user 根据用户id查询用户
	 * @param userId
	 * @return
	 */
	public User queryUserById(int userId){
		Sql sql = dao.sqls().create("user_queryUserById");
		sql.params().set("id", userId);
		sql.setCallback(Sqls.callback.entities());
		sql.setEntity(dao.getEntity(User.class));
		dao.execute(sql);
		log.info(sql.getSourceSql());
		List<User> list = sql.getList(User.class);
		if(list == null || list.size() == 0) return null;
		return list.get(0);
	}
	
	/**
	 * user 根据用户id查询用户部分信息
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Map queryPartUserInfoById(int userId){
		Sql sql = dao.sqls().create("user_queryPartUserInfoById");
		sql.params().set("id", userId);
		sql.setCallback(Sqls.callback.maps());
		dao.execute(sql);
		log.info(sql.getSourceSql());
		List<Map> list = sql.getList(Map.class);
		if(list == null || list.size() == 0) return null;
		return list.get(0);
	}
	
	/**
	 * 根据用户名查询用户部分信息
	 * @param username
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Map queryPartUserInfoByUserName(String username){
		Sql sql = dao.sqls().create("user_queryPartUserInfoByUserName");
		sql.params().set("username", username);
		sql.setCallback(Sqls.callback.maps());
		dao.execute(sql);
		log.info(sql.getSourceSql());
		List<Map> list = sql.getList(Map.class);
		if(list == null || list.size() == 0) return null;
		return list.get(0);
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
		sql.params().set("phone", user.phone);
		sql.params().set("qq", user.qq);
		sql.params().set("score", user.score);
		sql.params().set("user_state", user.user_state);
		sql.params().set("create_time", user.create_time);
		sql.params().set("last_update_time", user.last_update_time);
		sql.params().set("last_active_time", user.last_active_time);
		sql.params().set("last_active_area", user.last_active_area);
		sql.params().set("last_active_ip", user.last_active_ip);
		sql.params().set("last_forgetpassword_time", user.last_forgetpassword_time);
		sql.params().set("last_mark_time", user.last_mark_time);
		sql.params().set("last_mark_ip", user.last_mark_ip);
		dao.execute(sql);
		log.info(sql.getSourceSql());
	}
	
	/**
	 * 更新用户基本信息
	 * @param id    用户id
	 * @param user  用户基本信息
	 */
	public void updateUserInfo(int id, User user){
		Sql sql = dao.sqls().create("user_updateUserInfo");
		sql.params().set("nickname", user.nickname);
		sql.params().set("phone", user.phone);
		sql.params().set("qq", user.qq);
		sql.params().set("last_update_time", user.last_update_time);
		sql.params().set("id", id);
		dao.execute(sql);
		log.info(sql.getSourceSql());
	}
	
	/**
	 * 登陆成功 更新部分日志信息
	 * @param id   用户id
	 * @param map  封装日志信息
	 */
	public void updateLoginSuccess(int id, Map<String, Object> map){
		Sql sql = dao.sqls().create("user_updateUserTimeInfoSuccess");
		sql.params().set("last_active_time", map.get("last_active_time"));
		sql.params().set("last_active_area", map.get("last_active_area"));
		sql.params().set("last_active_ip", map.get("last_active_ip"));
		sql.params().set("id", id);
		dao.execute(sql);
		log.info(sql.getSourceSql());
	}
	
	/**
	 * 更新忘记密码的日志信息
	 * @param id    用户id
	 * @param map   忘记密码时间信息
	 */
	public void updateForgetPW(int id, Map<String, Object> map){
		Sql sql = dao.sqls().create("user_updateUserTimeInfoForget");
		sql.params().set("user_state", (int)map.get("user_state"));
		sql.params().set("last_forgetpassword_time", map.get("last_forgetpassword_time"));
		sql.params().set("id", id);
		dao.execute(sql);
		log.info(sql.getSourceSql());
	}
	
	/**
	 * 异常用户的日志更新
	 * @param id   用户id
	 * @param map  异常的时间  ip信息
	 */
	public void updateExceptionUser(int id, Map<String, Object> map){
		Sql sql = dao.sqls().create("user_updateUserTimeInfoException");
		sql.params().set("user_state", (int)map.get("user_state"));
		sql.params().set("last_mark_time", map.get("last_mark_time"));
		sql.params().set("last_mark_ip", map.get("last_mark_ip"));
		sql.params().set("id", id);
		dao.execute(sql);
		log.info(sql.getSourceSql());
	}
}
