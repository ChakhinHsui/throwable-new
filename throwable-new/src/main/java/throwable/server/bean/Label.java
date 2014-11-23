package throwable.server.bean;

/**
 * 标签的pojo
 * @author WaterHsu@xiu8.com
 * @version 2014年11月17日
 */
public class Label {

	/**
	 * 标签id
	 */
	public int id;
	
	/**
	 * 标签名
	 */
	public String name;
	
	/**
	 * 标签类型  问题有私有和公有  标签也是   1网站公有   0用户私有
	 */
	public int type;
	
	/**
	 * 标签创建时间
	 */
	public long time;
}
