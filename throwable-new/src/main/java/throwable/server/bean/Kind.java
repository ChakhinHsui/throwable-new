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
}
