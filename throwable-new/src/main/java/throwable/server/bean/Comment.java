package throwable.server.bean;

/**
 * 评论Bean
 * 
 * @author WaterHsu@xiu8.com
 * @version 2015年3月22日
 */
public class Comment {

	/**
	 * 评论id
	 */
	public int id;
	
	/**
	 * 评论所属id(问题或答案的id) 可以对问题和答案进行评论
	 */
	public int belongId;
	
	/**
	 * 评论所属类型   0问题  1答案  默认为0
	 */
	public int belongType;
	
	/**
	 * 评论@的那个人的id
	 */
	public int toUserId;
	
	/**
	 * 评论@的那个人的用户名  
	 */
	public String toUserName;
	
	/**
	 * 评论的人的id
	 */
	public int fromUserId;
	
	/**
	 * 评论的人的用户名
	 */
	public String fromUserName;
	
	/**
	 * 评论内容
	 */
	public String comment;
	
	/**
	 * 评论时间
	 */
	public long time;
}
