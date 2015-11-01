package throwable.server.bean;

/**
 * @author WaterHsu@xiu8.com
 * @version 2015年4月23日
 */
public class ChatInfo {

	/**
	 * 房间id
	 */
	public long roomId;
	
	/**
	 * 说话人id
	 */
	public long fromUserId;
	
	/**
	 * 说话人名字
	 */
	public String fromUserName;
	
	/**
	 * 说话对象id
	 */
	public long toUserId;
	
	/**
	 * 说话对象的名字
	 */
	public String toUserName;
	
	/**
	 * 发言时间
	 */
	public long time;
	
	/**
	 * 具体内容
	 */
	public String content;
}
