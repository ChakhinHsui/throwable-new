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
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

import throwable.server.ThrowableConf;
import throwable.server.bean.Question;
import throwable.server.enums.QuestionType;
import throwable.server.utils.BackTool;
import throwable.server.utils.StringTool;

/**
 * @author WaterHsu
 *
 */
@At("/question")
@IocBean
public class QuestionController {

	@Inject
	private QuestionServer questionServer;
	
	/**
	 * 添加问题
	 * @param question   封装问题参数
	 * @return
	 */
	@At("/addQuestion")
	public Map<String, Object> addQuestion(@Param("..") Question question){
		if(StringTool.isEmpty(question.question_name)){
			return BackTool.errorInfo("020101", ThrowableConf.errorMsg);
		}
		if(StringTool.isEmpty(question.question_type)){
			return BackTool.errorInfo("020102", ThrowableConf.errorMsg);
		}
		if(StringTool.isEmpty(question.kind_id)){
			return BackTool.errorInfo("020103", ThrowableConf.errorMsg);
		}
		if(StringTool.isEmpty(question.user_id)){
			return BackTool.errorInfo("020104", ThrowableConf.errorMsg);
		}
		if(QuestionType.not_public.getValue() != question.question_type && QuestionType.can_public.getValue() != question.question_type){
			return BackTool.errorInfo("020105", ThrowableConf.errorMsg);
		}
		return questionServer.addQuestion(question);
	}
	
	/**
	 * 添加关注
	 * @param userId       用户id
	 * @param questionId   问题id
	 * @return
	 */
	@At("/addFocus")
	public Map<String, Object> addFocus(@Param("userId") int userId, @Param("questionId") int questionId){
		if(userId <= 0){
			return BackTool.errorInfo("020301", ThrowableConf.errorMsg);
		}
		if(questionId <= 0){
			return BackTool.errorInfo("020302", ThrowableConf.errorMsg);
		}
		return questionServer.addFocus(userId, questionId);
	}
	
	/**
	 * 取消关注
	 * @param userId       用户id
	 * @param questionId   问题id
	 * @return
	 */
	@At("/deleteFocus")
	public Map<String, Object> deleteFocus(@Param("userId") int userId, @Param("questionId") int questionId){
		if(userId <= 0){
			return BackTool.errorInfo("020401", ThrowableConf.errorMsg);
		}
		if(questionId <= 0){
			return BackTool.errorInfo("020402", ThrowableConf.errorMsg);
		}
		return questionServer.deleteFocus(userId, questionId);
	}
	
	/**
	 * 收藏问题
	 * @param userId       用户id
	 * @param questionId   问题id
	 * @param mark         问题标注
	 * @return
	 */
	@At("/addCollection")
	public Map<String, Object> addCollection(@Param("userId") int userId, @Param("questionId") int questionId, @Param("mark") String mark){
		if(userId <= 0){
			return BackTool.errorInfo("020501", ThrowableConf.errorMsg);
		}
		if(questionId <= 0){
			return BackTool.errorInfo("020502", ThrowableConf.errorMsg);
		}
		return questionServer.addCollection(userId, questionId, mark);
	}
	
	/**
	 * 删除收藏
	 * @param userId        用户id
	 * @param questionId    问题id
	 * @return
	 */
	@At("/deleteCollection")
	public Map<String, Object> deleteCollection(@Param("userId") int userId, @Param("questionId") int questionId){
		if(userId <= 0){
			return BackTool.errorInfo("020601", ThrowableConf.errorMsg);
		}
		if(questionId <= 0){
			return BackTool.errorInfo("020602", ThrowableConf.errorMsg);
		}
		return questionServer.deleteCollection(userId, questionId);
	}
	
	/**
	 * 顶问题
	 * @param questionId  问题id
	 * @return
	 */
	@At("/agreeQuestion")
	public Map<String, Object> agreeQuestion(@Param("questionId") int questionId){
		if(questionId <= 0){
			return BackTool.errorInfo("020701", ThrowableConf.errorMsg);
		}
		return questionServer.agreeQuestion(questionId);
	}
	
	/**
	 * 踩问题
	 * @param questionId  问题id
	 * @return
	 */
	@At("/disagreeQuestion")
	public Map<String, Object> disagreeQuestion(@Param("questionId") int questionId){
		if(questionId <= 0){
			return BackTool.errorInfo("020801", ThrowableConf.errorMsg);
		}
		return questionServer.disagreeQuestion(questionId);
	}
	
	/**
	 * 增加访问人数
	 * @param questionId
	 * @return
	 */
	@At("/addViewer")
	public Map<String, Object> addViewers(@Param("questionId") int questionId) {
		if(questionId < 1) {
			return BackTool.errorInfo("020801", ThrowableConf.errorMsg);
		}
		return questionServer.addViewer(questionId);
	}
}
