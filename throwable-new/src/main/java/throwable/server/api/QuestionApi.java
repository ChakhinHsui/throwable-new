package throwable.server.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

import throwable.server.ThrowableConf;
import throwable.server.enums.QuestionType;
import throwable.server.service.QuestionService;
import throwable.server.utils.BackTool;
import throwable.server.utils.DateUtils;

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
	 * 查询公开的问题 用于首页显示 最新的
	 * @param question_type  问题类型
	 * @return
	 */
	@SuppressWarnings({"rawtypes", "unchecked"})
	@At("/getPublicQs")
	public Map queryPublicQuestions(){
		Map map = new HashMap();
		List<Map> list = questionService.queryQuestionByType(QuestionType.can_public.getValue());
		for(Map mm : list){
			mm.put("create_time", DateUtils.getNewTime(Long.parseLong(mm.get("create_time").toString()), 10));
		}
		map.put("questions", list);
		return map;
	}
	
	/**
	 * 查询公开的最热问题 访问数最多
	 * @param question_type
	 * @return
	 */
	@SuppressWarnings({"rawtypes", "unchecked"})
	@At("/getPublicHotQs")
	public Map queryPublicHotQuestions() {
		Map map = new HashMap();
		List<Map> list = questionService.queryHotQuestionByType(QuestionType.can_public.getValue());
		for(Map mm : list){
			mm.put("create_time", DateUtils.getNewTime(Long.parseLong(mm.get("create_time").toString()), 10));
		}
		map.put("questions", list);
		return map;
	}
	
	/**
	 * 查询关注最多的问题  用户首页等你的 的显示
	 * @return
	 */
	@SuppressWarnings({"rawtypes", "unchecked"})
	@At("/getPublicMFQs")
	public Map queryPublicMostFocusQuestions() {
		Map map = new HashMap();
		List<Map> list = questionService.queryMostFocusedQuestion(QuestionType.can_public.getValue());
		for(Map mm : list){
			mm.put("create_time", DateUtils.getNewTime(Long.parseLong(mm.get("create_time").toString()), 10));
		}
		map.put("questions", list);
		return map;
	}
	
	/**
	 * 查询最新最多回答的问题
	 * @return
	 */
	@SuppressWarnings({"rawtypes", "unchecked"})
	@At("/getPublicNMAQs")
	public Map queryPublicNewMostAQuestions() {
		Map map = new HashMap();
		List<Map> list = questionService.queryNewMostAnswerQuestion(QuestionType.can_public.getValue());
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
