package throwable.server.api;

import java.util.Map;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

import throwable.server.ThrowableConf;
import throwable.server.service.AnswerService;
import throwable.server.utils.BackTool;
import throwable.server.utils.StringTool;

/**
 * @author WaterHsu@xiu8.com
 * @version 2014年11月8日
 */
@At("/answerApi")
@IocBean
public class AnswerApi {

	@Inject
	private AnswerService answerService;
	
	@SuppressWarnings("rawtypes")
	@At("/getAnswerByQId")
	public Map queryAnswer(@Param("..") int questionId){
		if(StringTool.isEmpty(questionId)){
			return BackTool.errorInfo("030201", ThrowableConf.errorMsg);
		}
		return Lang.obj2map(answerService.queryAnswerByQuestionId(questionId));
	}
}
