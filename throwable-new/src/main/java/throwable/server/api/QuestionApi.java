package throwable.server.api;

import java.util.HashMap;
import java.util.Map;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

import throwable.server.ThrowableConf;
import throwable.server.service.QuestionService;
import throwable.server.utils.BackTool;
import throwable.server.utils.StringTool;

/**
 * @author WaterHsu@xiu8.com
 * @version 2014年11月8日
 */
@At("/questionApi")
@IocBean
public class QuestionApi {
	
	@Inject
	private QuestionService questionService;
	
	@SuppressWarnings({"rawtypes", "unchecked"})
	@At("/getPublicQs")
	public Map queryPublicQuestions(@Param("type") int type){
		if(StringTool.isEmpty(type)){
			return BackTool.errorInfo("020201", ThrowableConf.errorMsg);
		}
		Map map = new HashMap();
		map.put("questions", questionService.queryQuestionByType(type));
		return map;
	}
	
}
