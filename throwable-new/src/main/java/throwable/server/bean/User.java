package throwable.server.bean;

import org.nutz.ioc.loader.annotation.IocBean;

/**
 * @author WaterHsu@xiu8.com
 * @version 2014年10月10日
 */
@IocBean
public class User {

	/**
	 * 用户id
	 */
	public int id;
	
	/**
	 * 用户名
	 */
	public String username;
	
	/**
	 * 用户密码
	 */
	public String password;
	
	/**
	 * 用户权限  普通用户:general_user  内容管理用户:context_manager  网站管理用户:web_manager 超级用户: root
	 */
	public String rights;
	
	/**
	 * 用户邮箱
	 */
	public String email;
	
	/**
	 * 用户昵称
	 */
	public String nickname;
	
	/**
	 * 用户电话
	 */
	public String phone;
	
	/**
	 * 用户qq
	 */
	public String qq;
	
	 /**
	  * 用户积分
	  */
	public int score;
	
	/**
	 * 用户状态  1: 未激活   2:账号异常  3:账号被冻结  4: 账号已停用  5: 账号正常
	 */
	public int user_state;
	
	/**
	 * 用户创建时间
	 */
	public long create_time;
	
	/**
	 * 用户最后更新时间
	 */
	public long last_update_time;
	
	/**
	 * 用户最后活动时间
	 */
	public long last_active_time;
	
	/**
	 * 用户最后活动地点
	 */
	public String last_active_area;
	
	/**
	 * 用户最后活动ip
	 */
	public String last_active_ip;
	
	/**
	 * 用户最后忘记密码时间
	 */
	public long last_forgetpassword_time;
	
	/**
	 * 用户最后异常时间
	 */
	public long last_mark_time;
	
	/**
	 * 异常登陆的ip
	 */
	public String last_mark_ip;
}
