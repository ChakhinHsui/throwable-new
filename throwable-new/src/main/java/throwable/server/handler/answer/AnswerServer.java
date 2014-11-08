/**
 * Studia Seava
 * 
 * created in 2014年10月14日
 *
 * author by WaterHsu
 * 
 */
package throwable.server.handler.answer;

import java.util.Map;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import throwable.server.bean.Answer;
import throwable.server.service.AnswerService;
import throwable.server.utils.BackTool;

/**
 * @author WaterHsu
 *
 */
@IocBean
public class AnswerServer {

	private Log log = Logs.get();
	
	@Inject
	private AnswerService answerService;
	
	/**
	 * 添加问题
	 * @param answer
	 * @return
	 */
	public Map<String, Object> addAnswer(Answer answer){
		answer.agrees = 0;
		answer.viewers = 0;
		answer.answer_time = System.currentTimeMillis();
		answerService.insertData(answer);
		log.info("问题添加成功");
		return BackTool.successInfo();
	}
}
