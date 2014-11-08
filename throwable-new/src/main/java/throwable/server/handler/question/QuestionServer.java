/**
 * Studia Seava
 * 
 * created in 2014年10月14日
 *
 * author by WaterHsu
 * 
 */
package throwable.server.handler.question;

import java.util.Map;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import throwable.server.bean.Question;
import throwable.server.service.QuestionService;
import throwable.server.utils.BackTool;

/**
 * @author WaterHsu
 *
 */
@IocBean
public class QuestionServer {

	private Log log = Logs.get();
	@Inject
	private QuestionService questionService;
	
	/**
	 * 添加问题
	 * @param question  问题的pojo
	 * @return
	 */
	public Map<String, Object> addQuestion(Question question){
		question.agrees = 0;
		question.viewers = 0;
		question.create_time = System.currentTimeMillis();
		questionService.addQuestion(question);
		log.info("添加一个问题: " + question.user_id);
		return BackTool.successInfo();
	}
}
