package throwable.server.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

import throwable.server.ThrowableConf;
import throwable.server.enums.CorrectType;
import throwable.server.service.AnswerService;
import throwable.server.utils.BackTool;
import throwable.server.utils.DateUtils;
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
	
	@SuppressWarnings({"rawtypes", "unchecked"})
	@At("/getAnswerByQId")
	public Map queryAnswer(@Param("questionId") int questionId){
		if(StringTool.isEmpty(questionId)){
			return BackTool.errorInfo("030201", ThrowableConf.errorMsg);
		}
		List<Map> list = answerService.queryAnswerByQuestionId(questionId);
		Map map = new HashMap();
		if(list != null){
			for(Map mm : list){
				mm.put("answer_time", DateUtils.getNewTime(Long.parseLong(mm.get("answer_time").toString()), 10));
			}
			map.put("answers", list);
		}else{
			map.put("answers", "");
		}
		return map;
	}
	
	/**
	 * 根据用户id活得用户的回答和回答对应的问题信息
	 * @param userId
	 * @return
	 */
	@SuppressWarnings({"rawtypes", "unchecked"})
	public Map queryAnswerByUId(@Param("questionId") String userId) {
		if(StringTool.isEmpty(userId)) {
			return BackTool.errorInfo("030202");
		}
		if(!StringTool.isNumber(userId)) {
			return BackTool.errorInfo("030203");
		}
		List<Map> list = answerService.queryAnswerQuestionByUId(Integer.parseInt(userId));
		Map map = new HashMap();
		if(list != null){
			for(Map mm : list){
				mm.put("answer_time", DateUtils.getNewTime(Long.parseLong(mm.get("answer_time").toString()), 10));
				mm.put("correct_type", CorrectType.getName(Integer.parseInt(mm.get("correct_type").toString())));
			}
			map.put("answers", list);
		}else{
			map.put("answers", "");
		}
		return map;
	}
}
