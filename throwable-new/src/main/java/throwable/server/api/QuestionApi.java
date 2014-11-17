package throwable.server.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

import throwable.server.ThrowableConf;
import throwable.server.service.QuestionService;
import throwable.server.utils.BackTool;
import throwable.server.utils.DateUtils;
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
	
	/**
	 * 查询公开的问题 用于首页显示
	 * @param question_type  问题类型
	 * @return
	 */
	@SuppressWarnings({"rawtypes", "unchecked"})
	@At("/getPublicQs")
	public Map queryPublicQuestions(@Param("question_type") int question_type){
		if(StringTool.isEmpty(question_type)){
			return BackTool.errorInfo("020201", ThrowableConf.errorMsg);
		}
		Map map = new HashMap();
		List<Map> list = questionService.queryQuestionByType(question_type);
		for(Map mm : list){
			mm.put("create_time", DateUtils.getNewTime(Long.parseLong(mm.get("create_time").toString()), 10));
		}
		map.put("questions", list);
		return map;
	}
	
	/**
	 * 根据id获得问题  用于详细页显示
	 * @param id
	 * @return
	 */
	@SuppressWarnings({"rawtypes", "unchecked"})
	@At("/getOneQ")
	public Map queryOneQuestion(@Param("id") int id){
		if(id < 0){
			return BackTool.errorInfo("020202", ThrowableConf.errorMsg);
		}
		Map map = questionService.queryQuestionByQuestionId(id);
		map.put("create_time", DateUtils.getNewTime(Long.parseLong(map.get("create_time").toString()), 10));
		return map;
	}
	
}
