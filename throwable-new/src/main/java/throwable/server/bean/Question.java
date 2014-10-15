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
public class Question {

	/**
	 * 问题id
	 */
	public int id;
	
	/**
	 * 问题名称
	 */
	public String question_name;
	
	/**
	 * 问题描述
	 */
	public String question_description;
	
	/**
	 * 问题访问数
	 */
	public int viewers;
	
	/**
	 * 问题赞同数
	 */
	public int agrees;
	
	/**
	 * 问题创建时间
	 */
	public long time;
	
	/**
	 * 问题所属用户id
	 */
	public int user_id;
	
	/**
	 * 问题所属分类id
	 */
	public int kind_id;
}
