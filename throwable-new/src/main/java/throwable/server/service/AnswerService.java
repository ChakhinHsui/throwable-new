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
import java.util.Map;

import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.trans.Atom;
import org.nutz.trans.Trans;

import throwable.server.bean.Answer;

/**
 * @author WaterHsu
 *
 */
@IocBean
public class AnswerService extends BaseService {
	
	private Log log = Logs.get();
	
	/**
	 * 根据问题id查询答案
	 * @param question_id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> queryAnswerByQuestionId(int question_id){
		Sql sql = dao.sqls().create("answer_queryAnswerByQuestionId");
		sql.params().set("question_id", question_id);
		sql.setCallback(Sqls.callback.maps());
		dao.execute(sql);
		log.info(sql.getSourceSql());
		List<Map> list = sql.getList(Map.class);
		if(list == null || list.size() < 1) return null;
		return list;
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
		final Sql sql = dao.sqls().create("answer_insertAnswer");
		sql.params().set("answer_abstract", answer.answer_abstract);
		sql.params().set("answer_description", answer.answer_description);
		sql.params().set("correct_type", 0);
		sql.params().set("agrees", 0);
		sql.params().set("viewers", 0);
		sql.params().set("answer_time", answer.answer_time);
		sql.params().set("question_id", answer.question_id);
		sql.params().set("user_id", answer.user_id);
		final Sql sql2 = dao.sqls().create("question_addQuestionAnswers");
		sql2.params().set("counts", 1);
		sql2.params().set("id", answer.question_id);
		Trans.exec(new Atom(){
			public void run(){
				dao.execute(sql);
				dao.execute(sql2);
			}
		});
		log.info(sql.getSourceSql());
	}
}
