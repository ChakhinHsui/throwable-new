package throwable.server.bean;

/**
 * @author WaterHsu@xiu8.com
 * @version 2014年10月13日
 */
public class Answer {

	/**
	 * 答案id
	 */
	public int id;
	
	/**
	 * 答案描述
	 */
	public String answer_description;
	
	/**
	 * 答案符合度
	 */
	public int correct_type;
	
	/**
	 * 答案赞同数
	 */
	public int agrees;
	
	/**
	 * 答案访问数
	 */
	public int viewers;
	
	/**
	 * 答案创建时间
	 */
	public long answer_time;
}
