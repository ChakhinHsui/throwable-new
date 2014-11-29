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

import throwable.server.ThrowableConf;
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
		question.degrees = 0;
		question.viewers = 0;
		question.answers = 0;
		question.focuses = 0;
		question.collections = 0;
		question.solved = 0;
		question.create_time = System.currentTimeMillis();
		questionService.addQuestion(question);
		log.info("添加一个问题: " + question.user_id);
		return BackTool.successInfo();
	}
	
	/**
	 * 添加关注
	 * @param userId
	 * @param questionId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Map<String, Object> addFocus(int userId, int questionId){
		Map question = questionService.queryQuestionByQuestionId(questionId);
		if(Integer.parseInt(question.get("solved").toString()) != 0){
			return BackTool.errorInfo("020304", ThrowableConf.errorMsg);
		}
		if(!questionService.queryHaveFocused(userId, questionId)){
			return BackTool.errorInfo("020303", ThrowableConf.errorMsg);
		}
		questionService.addFocus(userId, questionId, System.currentTimeMillis());
		return BackTool.successInfo();
	}
	
	/**
	 * 取消关注
	 * @param userId         用户id
	 * @param questionId     问题id
	 * @return
	 */
	public Map<String, Object> deleteFocus(int userId, int questionId){
		if(questionService.queryHaveFocused(userId, questionId)){
			return BackTool.errorInfo("020403", ThrowableConf.errorMsg);
		}
		questionService.deleteFocus(userId, questionId);
		return BackTool.successInfo();
	}
	
	/**
	 * 增加收藏
	 * @param userId        用户id
	 * @param questionId    问题id
	 * @param mark          问题标注
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Map<String, Object> addCollection(int userId, int questionId, String mark){
		Map question = questionService.queryQuestionByQuestionId(questionId);
		if(Integer.parseInt(question.get("solved").toString()) == 0){
			return BackTool.errorInfo("020503", ThrowableConf.errorMsg);
		}
		if(!questionService.haveCollected(userId, questionId)){
			return BackTool.errorInfo("020504", ThrowableConf.errorMsg);
		}
		questionService.addCollection(userId, questionId, System.currentTimeMillis(), mark);
		return BackTool.successInfo();
	}
	
	/**
	 * 取消收藏
	 * @param userId       用户id 
	 * @param questionId   问题id
	 * @return
	 */
	public Map<String, Object> deleteCollection(int userId, int questionId){
		if(questionService.haveCollected(userId, questionId)){
			return BackTool.errorInfo("020603", ThrowableConf.errorMsg);
		}
		questionService.deleteCollection(userId, questionId);
		return BackTool.successInfo();
	}
	
	public Map<String, Object> agreeQuestion(int questionId){
		return null;
	}
	
	public Map<String, Object> disagreeQuestion(int questionId){
		return null;
	}
}
