package throwable.server.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

import throwable.server.ThrowableConf;
import throwable.server.enums.QuestionSolved;
import throwable.server.enums.QuestionType;
import throwable.server.service.AnswerService;
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
	@Inject
	private AnswerService answerService;
	
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
	
	/**
	 * 根据用户id获取问题 (选取部分信息)
	 * @param userId  用户id
	 * @return
	 */
	@SuppressWarnings({"rawtypes", "unchecked"})
	@At("/getUserQuestions")
	public Map queryUserQuestion(@Param("userId") String userId) {
		if(StringTool.isEmpty(userId)) {
			return BackTool.errorInfo("020203");
		}
		if(!StringTool.isNumber(userId)) {
			return BackTool.errorInfo("020204");
		}
		Map map = new HashMap();
		List<Map> list = questionService.queryPartQuestionInfoByUserId(Integer.parseInt(userId));
		for(Map mm : list){
			if(1 == Integer.parseInt(mm.get("solved").toString())) {
				mm.putAll(answerService.queryCorrectAnswer(Integer.parseInt(mm.get("id").toString())));
			}
			mm.put("create_time", DateUtils.getNewTime(Long.parseLong(mm.get("create_time").toString()), 10));
			mm.put("question_type", QuestionType.getName(Integer.parseInt(mm.get("question_type").toString())));
			mm.put("solved", QuestionSolved.getName(Integer.parseInt(mm.get("solved").toString())));
		}
		map.put("questions", list);
		return map;
	}
	
	/**
	 * 查询用户关注的问题
	 * @param userId
	 * @return
	 */
	@SuppressWarnings({"rawtypes", "unchecked"})
	@At("/getUserFocus")
	public Map queryUserFocusedQuestion(int userId) {
		if(userId < 1) {
			return BackTool.errorInfo("020203");
		}
		Map map = new HashMap();
		List<Map> list = questionService.queryUserFocus(userId);
		if(list != null) {
			for(Map mm : list){
				mm.put("question_time", DateUtils.getNewTime(Long.parseLong(mm.get("question_time").toString()), 10));
				mm.put("focus_time", DateUtils.getNewTime(Long.parseLong(mm.get("focus_time").toString()), 10));
				mm.put("solved", QuestionSolved.getName(Integer.parseInt(mm.get("solved").toString())));
			}
		}
		map.put("questions", null);
		return map;
	}
	
	/**
	 * 查询用户问题个数
	 * @param userId  用户id
	 * @return
	 */
	@At("/getUserQuestionNumber")
	public int getUserQuestionNumber(int userId) {
		if(userId < 1) {
			BackTool.errorInfo("020203");
		}
		return questionService.getUserQuestionNumber(userId);
	}
}
