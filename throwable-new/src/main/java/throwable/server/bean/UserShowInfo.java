package throwable.server.bean;

/**
 * @author WaterHsu@xiu8.com
 * @version 2015年4月23日
 */
public class UserShowInfo {

	/**
	 * 用户id
	 */
	public long uid;
	
	/**
	 * 用户名
	 */
	public String un;
	
	/**
	 * 用户权限
	 */
	public String ur;
	
	/**
	 * 用户昵称
	 */
	public String nn;
	
	/**
	 * 头像地址
	 */
	public String image;
	
	public UserShowInfo() {
	}
	
	public UserShowInfo(User user) {
		initUserShowInfo(user);
	}
	
	public void initUserShowInfo(User user) {
		this.uid = user.id;
		this.un = user.username;
		this.ur = user.rights;
		this.nn = user.nickname;
		this.image = "chat/images/user_b.gif";
	}
	
	public static UserShowInfo getVisitor(long userId) {
		UserShowInfo visitor = new UserShowInfo();
		visitor.uid = userId;
		visitor.un = "游客";
		visitor.nn = "游客";
		visitor.ur = "游客";
		visitor.image = "chat/images/user_b.gif";
		return visitor;
	}
}
