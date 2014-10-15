package throwable.server.bean;

/**
 * @author WaterHsu@xiu8.com
 * @version 2014年10月13日
 */
public class Question {
	
	/**
	 * 问题的id
	 */
	public int id;
	
	/**
	 * 问题的名称
	 */
	public String question_name;
	
	/**
	 * 问题的具体描述
	 */
	public String question_description;
	
	/**
	 * 问题的访问数
	 */
	public int viewers;
	
	/**
	 * 问题的赞数
	 */
	public int agrees;
	
	/**
	 * 问题产生时间
	 */
	public long time;
}
