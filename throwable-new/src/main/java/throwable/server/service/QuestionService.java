/**
 * Studia Seava
 * 
 * created in 2014年10月14日
 *
 * author by WaterHsu
 * 
 */
package throwable.server.service;


import java.util.List;

import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import throwable.server.bean.Question;

/**
 * @author WaterHsu
 *
 */
public class QuestionService extends BaseService {
	
	private Log log = Logs.get();
	
	/**
	 * 问题-添加问题
	 * @param question
	 */
	public void addQuestion(Question question){
		Sql sql = dao.sqls().create("question_insertOneQuestion");
		sql.params().set("question_name", question.question_name);
		sql.params().set("question_description", question.question_description);
		sql.params().set("question_type", question.question_type);
		sql.params().set("viewers", 0);
		sql.params().set("agrees", 0);
		sql.params().set("create_time", System.currentTimeMillis());
		sql.params().set("kind_id", question.kind_id);
		sql.params().set("user_id", question.user_id);
		dao.execute(sql);
	}
	
	/**
	 * 问题-根据问题id查询问题
	 * @param id
	 * @return
	 */
	public Question queryQuestionByQuestionId(int id){
		Sql sql = dao.sqls().create("question_queryQuestionByQuestionId");
		sql.params().set("id", id);
		sql.setCallback(Sqls.callback.entity());
		sql.setEntity(dao.getEntity(Question.class));
		dao.execute(sql);
		Question question = (Question)sql.getEntity();
		return question;
	}
	
	/**
	 * 问题-根据分类id查询问题
	 * @param kindId
	 * @return
	 */
	public List<Question> queryQuestionByKindId(int kindId){
		Sql sql = dao.sqls().create("question_queryQuestionByKindId");
		sql.params().set("kind_id", kindId);
		sql.setCallback(Sqls.callback.entities());
		sql.setEntity(dao.getEntity(Question.class));
		dao.execute(sql);
		List<Question> list = sql.getList(Question.class);
		if(list == null || list.size() < 1){
			return null;
		}
		return list;
	}
	
	/**
	 * 问题-根据分类id分页查询问题
	 * @param kindId 分类id
	 * @param page   开始位置
	 * @param count  条数
	 * @return
	 */
	public List<Question> queryQuestionByKindIdPage(int kindId, int page, int count){
		Sql sql = dao.sqls().create("queryQuestionByKindIdPage");
		sql.params().set("kind_id", kindId);
		sql.params().set("page", page);
		sql.params().set("count", count);
		sql.setCallback(Sqls.callback.entities());
		sql.setEntity(dao.getEntity(Question.class));
		dao.execute(sql);
		List<Question> list = sql.getList(Question.class);
		if(list == null || list.size() < 1){
			return null;
		}
		return list;
	}
	
	/**
	 * 问题-根据用户id查询问题
	 * @param kindId
	 * @return
	 */
	public List<Question> queryQuestionByUserId(int userId){
		Sql sql = dao.sqls().create("question_queryQuestionByUserId");
		sql.params().set("user_id", userId);
		sql.setCallback(Sqls.callback.entities());
		sql.setEntity(dao.getEntity(Question.class));
		dao.execute(sql);
		List<Question> list = sql.getList(Question.class);
		if(list == null || list.size() < 1){
			return null;
		}
		return list;
	}
}
