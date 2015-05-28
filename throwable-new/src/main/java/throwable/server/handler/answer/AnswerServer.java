/**
 * Studia Seava
 * 
 * created in 2014年10月14日
 *
 * author by WaterHsu
 * 
 */
package throwable.server.handler.answer;

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
import throwable.server.bean.Answer;
import throwable.server.bean.User;
import throwable.server.bean.UserStatistic;
import throwable.server.enums.CorrectType;
import throwable.server.enums.QuestionSolved;
import throwable.server.enums.Right;
import throwable.server.enums.State;
import throwable.server.handler.user.UserServer;
import throwable.server.service.AnswerService;
import throwable.server.service.QuestionService;
import throwable.server.utils.BackTool;

/**
 * @author WaterHsu
 *
 */
@IocBean
public class AnswerServer {

	private Log log = Logs.getLog(AnswerServer.class);
	
	@Inject
	private AnswerService answerService;
	@Inject
	private QuestionService questionService;
	@Inject
	private UserServer userServer;
	@Inject
	private RabbitMqTool rabbitMqTool;
	
	/**
	 * 添加问题
	 * @param answer
	 * @return
	 */
	public Map<String, Object> addAnswer(final Answer answer){
		User user = userServer.queryUserInfo(answer.user_id);
		if(null == user) {
			BackTool.errorInfo("050304", "用户不存在");
		}
		if(!user.rights.equals(Right.general.getValue()) && !user.rights.equals(Right.context.getValue()) && !user.rights.equals(Right.superU.getValue())) {
			BackTool.errorInfo("050305", "用户权限不够,不能回答问题");
		}
		if(user.user_state == State.no_active.getValue()) {
			BackTool.errorInfo("020306", "用户未激活，不能回答问题");
		}
		if(user.user_state != State.user_nomal.getValue()) {
			BackTool.errorInfo("020306", "用户异常，不能回答问题，请联系网站管理员");
		}
		answer.agrees = 0;
		answer.viewers = 0;
		answer.disagrees = 0;
		answer.correct_type = answer.correct_type == 0 ? 3 : answer.correct_type;
		answer.answer_time = System.currentTimeMillis();
		Trans.exec(new Atom() {
			
			@Override
			public void run() {
				answerService.insertData(answer);
				//增加用户回答数数统计
				UserStatistic userStat = new UserStatistic();
				userStat.user_id = answer.user_id;
				userStat.answers = 1;
				userServer.addUserStatistics(userStat);
			}
		});
		//将消息发送到mq中  异步处理
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", answer.user_id);
		map.put("questionId", answer.question_id);
		map.put("time", answer.answer_time);
		rabbitMqTool.sendMessageToExchange("Notice", "AddAnswer", Json.toJson(map));
		log.info("问题添加成功");
		return BackTool.successInfo();
	}
	
	/**
	 * 顶问题  赞同问题
	 * @param answerId
	 */
	public void agreeAnswer(long answerId) {
		answerService.agreeAnswer(answerId);
	}
	
	
	/**
	 * 踩问题
	 * @param answerId
	 */
	public void disagreeAnswer(long answerId) {
		answerService.disagreeAnswer(answerId);
	}
	
	/**
	 * 接受答案
	 * @param questionId  问题id
	 * @param answerId    答案id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public int acceptAnswer(final long questionId, final long answerId, long userId) {
		User user = userServer.queryUserInfo(userId);
		if(null == user) {
			BackTool.errorInfo("050304", "用户不存在");
		}
		if(!user.rights.equals(Right.general.getValue()) && user.rights.equals(Right.context.getValue()) && !user.rights.equals(Right.superU.getValue())) {
			BackTool.errorInfo("050305", "用户权限不够,不能接受问题答案");
		}
		if(user.user_state == State.no_active.getValue()) {
			BackTool.errorInfo("020306", "用户未激活，不能接受问题答案");
		}
		if(user.user_state != State.user_nomal.getValue()) {
			BackTool.errorInfo("020306", "用户异常，不能接受问题答案，请联系网站管理员");
		}
		Map map = questionService.queryQuestionByQuestionId((int)questionId);
		if(userId != Long.parseLong(map.get("user_id").toString())) {
			BackTool.errorInfo("050305", "不是该问题的拥有者不能接受答案");
		}
		try {
			Trans.exec(new Atom() {
				
				@Override
				public void run() {
					questionService.solveQuestion(questionId, QuestionSolved.solved.getValue());
					answerService.acceptAnswer(answerId, CorrectType.correct.getValue());
				}
			});
		} catch(Exception e) {
			log.error("接受答案出错 " + e);
			throw new RuntimeException("接受答案出错 " + e);
		}
		return 1;
	}
}
