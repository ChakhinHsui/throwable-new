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

import throwable.server.bean.Answer;

/**
 * @author WaterHsu
 *
 */
public class AnswerService extends BaseService {
	
	private Log log = Logs.get();
	
	/**
	 * 根据问题id查询答案
	 * @param question_id
	 * @return
	 */
	public Answer queryAnswerByQuestionId(int question_id){
		Sql sql = dao.sqls().create("answer_queryAnswerByQuestionId");
		sql.params().set("question_id", question_id);
		sql.setCallback(Sqls.callback.entities());
		sql.setEntity(dao.getEntity(Answer.class));
		dao.execute(sql);
		log.info(sql.getSourceSql());
		List<Answer> list = sql.getList(Answer.class);
		if(list == null || list.size() < 1) return null;
		return list.get(0);
	}
	
	/**
	 * 根据用户id查询答案
	 * @param user_id
	 * @return
	 */
	public List<Answer> queryAnswerByUserId(int user_id){
		Sql sql = dao.sqls().create("answer_queryAnswerByUserId");
		sql.params().set("user_id", user_id);
		sql.setCallback(Sqls.callback.entities());
		sql.setEntity(dao.getEntity(Answer.class));
		dao.execute(sql);
		log.info(sql.getSourceSql());
		List<Answer> list = sql.getList(Answer.class);
		if(list == null || list.size() < 1) return null;
		return list;
	}
	
	/**
	 * 根据答案id查询答案
	 * @param id
	 * @return
	 */
	public Answer queryAnswerByAnswerId(int id){
		Sql sql = dao.sqls().create("answer_queryAnswerByAnswerId");
		sql.params().set("id", id);
		sql.setCallback(Sqls.callback.entities());
		sql.setEntity(dao.getEntity(Answer.class));
		dao.execute(sql);
		log.info(sql.getSourceSql());
		List<Answer> list = sql.getList(Answer.class);
		if(list == null || list.size() < 1) return null;
		return list.get(0);
	}
	
	/**
	 * 插入答案
	 * @param answer
	 */
	public void insertData(Answer answer){
		Sql sql = dao.sqls().create("answer_insertAnswer");
		sql.params().set("answer_abstract", answer.answer_abstract);
		sql.params().set("answer_description", answer.answer_description);
		sql.params().set("correct_type", 0);
		sql.params().set("agrees", 0);
		sql.params().set("viewers", 0);
		sql.params().set("answer_time", System.currentTimeMillis());
		sql.params().set("question_id", answer.question_id);
		sql.params().set("user_id", answer.user_id);
		dao.execute(sql);
		log.info(sql.getSourceSql());
	}
}
