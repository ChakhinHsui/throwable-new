package throwable.server.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;

import seava.tools.StringTool;
import throwable.server.ThrowableConf;
import throwable.server.enums.CorrectType;
import throwable.server.enums.QuestionOrAnswer;
import throwable.server.service.AnswerService;
import throwable.server.service.CommentService;
import throwable.server.service.UserService;
import throwable.server.utils.BackTool;
import throwable.server.utils.DateUtils;

/**
 * @author WaterHsu@xiu8.com
 * @version 2014年11月8日
 */
@At("/answerApi")
@IocBean
public class AnswerApi {

	@Inject
	private AnswerService answerService;
	@Inject
	private CommentService commentService;
	@Inject
	private UserService userService;
	
	@SuppressWarnings({"rawtypes", "unchecked"})
	@At("/getAnswerByQId")
	public Map queryAnswer(int questionId, long userId){
		if(questionId < 1){
			return BackTool.errorInfo("030201", ThrowableConf.errorMsg);
		}
		List<Map> list = answerService.queryAnswerByQuestionId(questionId);
		Map map = new HashMap();
		if(list != null){
			for(Map mm : list){
				mm.put("answer_time", DateUtils.getNewTime(Long.parseLong(mm.get("answer_time").toString()), 10));
				List<Map> comments = commentService.queryCommentByBelongIdType(Integer.parseInt(mm.get("id").toString()), 1);
				for(Map comment : comments) {
					comment.put("time", DateUtils.getNewTime(Long.parseLong(comment.get("time").toString()), 10));
				}
				mm.put("comments", comments);
				if(null == mm.get("image")) {
					mm.put("image", "default.jpg");
				}
				//查询用户是否有赞同或反对答案
				if(userId > 0) {
					Map userAnswerMap = userService.queryAgreeDisAgreeRecord(userId, Long.parseLong(mm.get("id").toString()), QuestionOrAnswer.answer.getValue());
					if(!StringTool.isEmpty(userAnswerMap)) {
						mm.put("user_answer_relation", userAnswerMap.get("agreeType"));
					}
				}
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
	@At("/getUserAnswers")
	public Map queryAnswerByUId(int userId) {
		if(userId < 1) {
			BackTool.errorInfo("030202");
		}
		List<Map> list = answerService.queryAnswerQuestionByUId(userId);
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
	
	/**
	 * 查询用户的回答数
	 * @param userId
	 * @return
	 */
	@At("/getUserAnswerNumber")
	public int getUserAnswerNumber(int userId) {
		if(userId < 1) {
			BackTool.errorInfo("030202");
		}
		return answerService.queryUserAnswerNumber(userId);
	}
}
