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
}
