/**
 * Studia Seava
 * 
 * created in 2014年10月14日
 *
 * author by WaterHsu
 * 
 */
package throwable.server.handler.question;

import java.util.HashMap;
import java.util.Map;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.trans.Atom;
import org.nutz.trans.Trans;

import seava.tools.rabbitmq.RabbitMqTool;
import throwable.server.ThrowableConf;
import throwable.server.bean.Question;
import throwable.server.bean.User;
import throwable.server.bean.UserStatistic;
import throwable.server.enums.AgreeOrDisagree;
import throwable.server.enums.LabelType;
import throwable.server.enums.QuestionOrAnswer;
import throwable.server.enums.Right;
import throwable.server.enums.State;
import throwable.server.handler.label.LabelServer;
import throwable.server.handler.user.UserServer;
import throwable.server.service.LabelService;
import throwable.server.service.QuestionService;
import throwable.server.service.UserService;
import throwable.server.utils.BackTool;
import throwable.server.utils.StringTool;

/**
 * @author WaterHsu
 *
 */
@IocBean
public class QuestionServer {

	private Log log = Logs.get();
	@Inject
	private QuestionService questionService;
	@Inject
	private LabelServer labelServer;
	@Inject
	private LabelService labelService;
	@Inject
	private UserServer userServer;
	@Inject
	private UserService userService;
	@Inject
	private RabbitMqTool rabbitMqTool;
	
	/**
	 * 添加问题
	 * @param question  问题的pojo
	 * @return
	 */
	public Map<String, Object> addQuestion(final Question question, final String label_names){
		User user = userServer.queryUserInfo(question.user_id);
		if(null == user) {
			BackTool.errorInfo("050304", "用户不存在");
		}
		if(!user.rights.equals(Right.general.getValue()) && user.rights.equals(Right.context.getValue()) && !user.rights.equals(Right.superU.getValue())) {
			BackTool.errorInfo("050305", "用户权限不够, 不能提问题");
		}
		if(user.user_state == State.no_active.getValue()) {
			BackTool.errorInfo("020306", "用户未激活，不能提问题");
		}
		if(user.user_state != State.user_nomal.getValue()) {
			BackTool.errorInfo("020306", "用户异常，不能提问题，请联系网站管理员");
		}
		question.agrees = 0;
		question.degrees = 0;
		question.viewers = 0;
		question.answers = 0;
		question.focuses = 0;
		question.collections = 0;
		question.solved = 0;
		question.create_time = System.currentTimeMillis();
		Trans.exec(new Atom() {
			
			@Override
			public void run() {
				//添加问题
				questionService.addQuestion(question);
				if(!StringTool.isEmpty(label_names)) {
					//查询问题id
					int id = questionService.queryQuestionId(question);
					String[] labels = label_names.split(";");
					//添加label 以及用户lable
					for(String labelName : labels) {
						labelServer.addLabel(question.user_id, labelName, LabelType.web_own_label.getName());
					}
					//添加问题label
					for(String labelName : labels) {
						int labelId = labelService.queryLabelByName(labelName).id;
						labelService.addQuestionLable(id, labelId);
					}
				}
				//增加用户提问数统计
				userServer.addUserStatistics(UserStatistic.updateAsks(1));
			}
		});
		log.info("添加一个问题: " + question.user_id);
		return BackTool.successInfo();
	}
	
	/**
	 * 添加关注
	 * @param userId
	 * @param questionId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Map<String, Object> addFocus(int userId, int questionId){
		Map question = questionService.queryQuestionByQuestionId(questionId);
		if(Integer.parseInt(question.get("solved").toString()) != 0){
			return BackTool.errorInfo("020304", ThrowableConf.errorMsg);
		}
		if(questionService.queryHaveFocused(userId, questionId)){
			return BackTool.errorInfo("020303", ThrowableConf.errorMsg);
		}
		questionService.addFocus(userId, questionId, System.currentTimeMillis());
		return BackTool.successInfo();
	}
	
	/**
	 * 取消关注
	 * @param userId         用户id
	 * @param questionId     问题id
	 * @return
	 */
	public Map<String, Object> deleteFocus(int userId, int questionId){
		if(!questionService.queryHaveFocused(userId, questionId)){
			return BackTool.errorInfo("020403", ThrowableConf.errorMsg);
		}
		questionService.deleteFocus(userId, questionId);
		return BackTool.successInfo();
	}
	
	/**
	 * 增加收藏
	 * @param userId        用户id
	 * @param questionId    问题id
	 * @param mark          问题标注
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Map<String, Object> addCollection(int userId, int questionId, String mark){
		Map question = questionService.queryQuestionByQuestionId(questionId);
		if(Integer.parseInt(question.get("solved").toString()) == 0){
			return BackTool.errorInfo("020503", ThrowableConf.errorMsg);
		}
		if(questionService.haveCollected(userId, questionId)){
			return BackTool.errorInfo("020504", ThrowableConf.errorMsg);
		}
		questionService.addCollection(userId, questionId, System.currentTimeMillis(), mark);
		return BackTool.successInfo();
	}
	
	/**
	 * 取消收藏
	 * @param userId       用户id 
	 * @param questionId   问题id
	 * @return
	 */
	public Map<String, Object> deleteCollection(int userId, int questionId){
		if(!questionService.haveCollected(userId, questionId)){
			BackTool.errorInfo("020603", ThrowableConf.errorMsg);
		}
		questionService.deleteCollection(userId, questionId);
		return BackTool.successInfo();
	}
	
	/**
	 * 顶问题
	 * @param questionId
	 * @return
	 */
	public Map<String, Object> agreeQuestion(final long questionId, final long userId){
		return agreeDisagreeQuestion(questionId, userId, AgreeOrDisagree.agree.getValue());
	}
	
	/**
	 * 踩问题
	 * @param questionId
	 * @return
	 */
	public Map<String, Object> disagreeQuestion(long questionId, long userId){
		return agreeDisagreeQuestion(questionId, userId, AgreeOrDisagree.disagree.getValue());
	}
	
	
	/**
	 * 赞同或不赞同问题
	 * @param questionId       问题id
	 * @param userId           用户id
	 * @param agreeOrDisagree  1 赞同   2不赞同
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Map<String, Object> agreeDisagreeQuestion(final long questionId, final long userId, final int agreeOrDisagree) {
		Map map = userService.queryAgreeDisAgreeRecord(userId, questionId, QuestionOrAnswer.question.getValue());
		if(!StringTool.isEmpty(map)) {
			BackTool.errorInfo("020607", "你已经赞同或反对过该问题了");
		}
		Trans.exec(new Atom() {

			@Override
			public void run() {
				//更新问题的赞同数
				if(agreeOrDisagree == 1) {
					questionService.agreeQuestion(questionId);
				} else {
					questionService.disagreeQuestion(questionId);
				}
				//插入用户赞同问题的记录
				userService.insertAgreeRecord(userId, questionId, QuestionOrAnswer.question.getValue(), System.currentTimeMillis(), agreeOrDisagree);
				UserStatistic userstat = agreeOrDisagree == 1 ? UserStatistic.updateAgrees(1) : UserStatistic.updateDisAgrees(1);
				//增加用户赞同数的统计
				userService.updateUserStatistics(userstat);
				if(agreeOrDisagree == 1) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("userId", userId);
					map.put("questionId", questionId);
					map.put("time", System.currentTimeMillis());
					rabbitMqTool.sendMessageToExchange("Notice", "agreeQuestion", Json.toJson(map));
				}
			}
		});
		return BackTool.successInfo();
	}
	
	/**
	 * 添加问题的访问数
	 * @param questionId
	 * @return
	 */
	public Map<String, Object> addViewer(int questionId){
		questionService.addViewer(questionId);
		return BackTool.successInfo();
	}
	
}
