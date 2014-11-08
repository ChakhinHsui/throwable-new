
/**
 * Studia Seava
 * 
 * created in 2014年10月14日
 *
 * author by WaterHsu
 * 
 */
package throwable.server.bean;

/**
 * @author WaterHsu
 *
 */
public class Answer {
	
	/**
	 * 回答id
	 */
	public int id;
	
	/**
	 * 回答摘要
	 */
	public String answer_abstract;
	
	/**
	 * 回答具体描述
	 */
	public String answer_description;
	
	/**
	 * 正确类型   1:正确   2:部分正确  3:未确认  4:错误  5:非法
	 * 默认为未确认
	 */
	public int correct_type;
	
	/**
	 * 赞同数
	 */
	public int agrees;
	
	/**
	 * 访问数
	 */
	public int viewers;
	
	/**
	 * 回答时间
	 */
	public long answer_time;
	
	/**
	 * 所属问题的id
	 */
	public int question_id;
	
	/**
	 * 所属用户的id
	 */
	public int user_id;
}
