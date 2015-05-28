package throwable.server.consumer;

import java.util.Map;
import java.util.UUID;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;
import org.nutz.lang.Lang;

import seava.tools.ProxyChannel;
import seava.tools.RedisTool;
import seava.tools.StringTool;
import seava.tools.rabbitmq.MQWorker;
import throwable.server.bean.Notice;
import throwable.server.bean.User;
import throwable.server.handler.user.UserServer;
import throwable.server.service.QuestionService;
import throwable.server.service.UserService;

/**
 * @author WaterHsu@xiu8.com
 * @version 2015年5月24日
 */
@IocBean
public class AnswerWorker implements MQWorker {

	@Inject
	private QuestionService questionService;
	@Inject
	private UserService userService;
	@Inject
	private RedisTool redisTool;
	
	@SuppressWarnings("unchecked")
	@Override
	public void work(String message) {
		if(StringTool.isEmpty(message)) {
			return;
		}
		Map<String, Object> map = Json.fromJson(Map.class, message);
		if(StringTool.isEmpty(map)) {
			return;
		}
		Map<String, Object> questionMap = questionService.queryQuestionByQuestionId(Integer.parseInt(map.get("questionId").toString()));
		User answerUser = userService.queryUserById(Integer.parseInt(map.get("userId").toString()));
		Notice notice = new Notice();
		notice.userId = Long.parseLong(questionMap.get("user_id").toString());
		notice.nonticeText = answerUser.username + "在" + questionMap.get("question_name").toString() + "中回答了你的问题";
		notice.noticeUrl = "detail.html?qid=" + questionMap.get("id");
		notice.readOrNo = 1;
		notice.type = 2;
		notice.time = Long.parseLong(map.get("time").toString());
		notice.uuid = UUID.randomUUID().toString();
		ProxyChannel.user(notice.userId, Lang.obj2map(notice), redisTool);
		userService.insertNotice(notice);
	}

}
