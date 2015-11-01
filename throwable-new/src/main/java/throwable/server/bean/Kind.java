package throwable.server.bean;

/**
 * @author WaterHsu@xiu8.com
 * @version 2014年10月13日
 */
public class Kind {
	
	/**
	 * 分类id
	 */
	public int id;
	
	/**
	 * 分类名称
	 */
	public String kind_name;
	
	/**
	 * 分类的上层分类
	 */
	public Kind parent_kind;
	
	/**
	 * 分类创建时间
	 */
	public long time;
	
	/**
	 * 分类/房间的描述
	 */
	public String description;
	
	/**
	 * 问题所属用户id
	 */
	public int user_id;
}
