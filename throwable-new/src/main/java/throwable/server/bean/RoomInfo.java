package throwable.server.bean;

/**
 * @author WaterHsu@xiu8.com
 * @version 2015年4月23日
 */
public class RoomInfo {

	/**
	 * 房间id
	 */
	public long roomId;
	
	/**
	 * 房间名字
	 */
	public String title;
	
	/**
	 * 房间创建者
	 */
	public long creater;
	
	/**
	 * 房间创建时间
	 */
	public long time;
	
	/**
	 * 房间实际人数
	 */
	public long factCount;
	
	/**
	 * 房间总共说话次数
	 */
	public long speakCount;
	
}
