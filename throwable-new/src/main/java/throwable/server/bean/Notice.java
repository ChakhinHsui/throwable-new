package throwable.server.bean;

/**
 * 通知消息
 * @author WaterHsu@xiu8.com
 * @version 2015年5月24日
 */
public class Notice {
	
	/**
	 * 通知的id
	 */
	public long id;
	/**
	 * 用户id
	 */
	public long userId;
	/**
	 * 通知内容
	 */
	public String nonticeText;
	/**
	 * 通知内容所指的url
	 */
	public String noticeUrl;
	/**
	 * 通知产生的时间
	 */
	public long time;
	/**
	 * 通知是否已经被查看   1未被查看   2被查看
	 */
	public int readOrNo;
	/**
	 *标识这个通知的唯一uuid 
	 */
	public String uuid;
	/**
	 * 通知消息 类型    1系统消息   2用户之间消息
	 */
	public int type;
}
