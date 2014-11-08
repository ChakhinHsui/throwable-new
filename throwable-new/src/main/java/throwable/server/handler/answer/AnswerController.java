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
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

import throwable.server.ThrowableConf;
import throwable.server.bean.Answer;
import throwable.server.enums.CorrectType;
import throwable.server.utils.BackTool;
import throwable.server.utils.StringTool;

/**
 * @author WaterHsu
 *
 */
@At("/answer")
@IocBean
public class AnswerController {

	@Inject
	private AnswerServer answerServer;
	
	@At("/addAnswer")
	public Map<String, Object> addAnswer(@Param("..") Answer answer){
		if(StringTool.isEmpty(answer.answer_abstract)){
			return BackTool.errorInfo("030101", ThrowableConf.errorMsg);
		}
		if(StringTool.isEmpty(answer.answer_description)){
			return BackTool.errorInfo("030102", ThrowableConf.errorMsg);
		}
		if(StringTool.isEmpty(answer.correct_type)){
			return BackTool.errorInfo("030103", ThrowableConf.errorMsg);
		}
		if(StringTool.isEmpty(answer.question_id)){
			return BackTool.errorInfo("030104", ThrowableConf.errorMsg);
		}
		if(StringTool.isEmpty(answer.user_id)){
			return BackTool.errorInfo("030105", ThrowableConf.errorMsg);
		}
		if(CorrectType.correct.getValue() != answer.correct_type && CorrectType.part_correct.getValue() != answer.correct_type && CorrectType.not_sure.getValue() != answer.correct_type && CorrectType.wrong.getValue() != answer.correct_type && CorrectType.illegal.getValue() != answer.correct_type){
			return BackTool.errorInfo("030101", ThrowableConf.errorMsg);
		}
		return answerServer.addAnswer(answer);
	}
}
