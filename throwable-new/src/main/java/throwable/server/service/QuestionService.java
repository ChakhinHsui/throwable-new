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
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import throwable.server.bean.Question;

/**
 * @author WaterHsu
 *
 */
@IocBean
public class QuestionService extends BaseService {
	
	private Log log = Logs.get();
	
	/**
	 * 问题-添加问题
	 * @param question  问题的pojo
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
		log.info(sql.getSourceSql());
	}
	
	/**
	 * 问题-根据问题id查询问题
	 * @param id         问题id
	 * @return
	 */
	public Question queryQuestionByQuestionId(int id){
		Sql sql = dao.sqls().create("question_queryQuestionByQuestionId");
		sql.params().set("id", id);
		sql.setCallback(Sqls.callback.entity());
		sql.setEntity(dao.getEntity(Question.class));
		dao.execute(sql);
		log.info(sql.getSourceSql());
		Question question = (Question)sql.getEntity();
		return question;
	}
	
	/**
	 * 问题-根据分类id查询问题
	 * @param kindId          分类id
	 * @return
	 */
	public List<Question> queryQuestionByKindId(int kindId){
		Sql sql = dao.sqls().create("question_queryQuestionByKindId");
		sql.params().set("kind_id", kindId);
		sql.setCallback(Sqls.callback.entities());
		sql.setEntity(dao.getEntity(Question.class));
		dao.execute(sql);
		log.info(sql.getSourceSql());
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
		log.info(sql.getSourceSql());
		List<Question> list = sql.getList(Question.class);
		if(list == null || list.size() < 1){
			return null;
		}
		return list;
	}
	
	/**
	 * 问题-根据用户id查询问题
	 * @param userId        用户id    
	 * @return
	 */
	public List<Question> queryQuestionByUserId(int userId){
		Sql sql = dao.sqls().create("question_queryQuestionByUserId");
		sql.params().set("user_id", userId);
		sql.setCallback(Sqls.callback.entities());
		sql.setEntity(dao.getEntity(Question.class));
		dao.execute(sql);
		log.info(sql.getSourceSql());
		List<Question> list = sql.getList(Question.class);
		if(list == null || list.size() < 1){
			return null;
		}
		return list;
	}
	
	/**
	 * 问题-根据用户id分页查询问题
	 * @param userId       用户id
	 * @param page         查询起始位置
	 * @param count        每页显示条数
	 * @return
	 */
	public List<Question> queryQusetionByUserIdPage(int userId, int page, int count){
		Sql sql = dao.sqls().create("question_queryQuestionByUserIdPage");
		sql.params().set("userId", userId);
		sql.params().set("page", page);
		sql.params().set("count", count);
		sql.setCallback(Sqls.callback.entities());
		sql.setEntity(dao.getEntity(Question.class));
		dao.execute(sql);
		log.info(sql.getSourceSql());
		List<Question> list = sql.getList(Question.class);
		if(list == null || list.size() < 1){
			return null;
		}
		return list;
	}
	
	/**
	 * 根据时间段查询问题
	 * @param minTime      时间段开始时间
	 * @param maxTime      时间段结束时间
	 * @return
	 */
	public List<Question> queryQuestionByTime(long minTime, long maxTime){
		Sql sql = dao.sqls().create("question_queryQuestionByTime");
		sql.params().set("minTime", minTime);
		sql.params().set("maxTime", maxTime);
		sql.setCallback(Sqls.callback.entities());
		sql.setEntity(dao.getEntity(Question.class));
		dao.execute(sql);
		log.info(sql.getSourceSql());
		List<Question> list = sql.getList(Question.class);
		if(list == null || list.size() < 1){
			return null;
		}
		return list;
	}
	
	/**
	 * 根据时间段分页查询问题
	 * @param minTime     时间段开始时间
	 * @param maxTime     时间段结束时间
	 * @param page        查询开始位置
	 * @param count       显示条数
	 * @return
	 */
	public List<Question> queryQuestionByTimePage(long minTime, long maxTime, int page, int count){
		Sql sql = dao.sqls().create("question_queryQuestionByTimePage");
		sql.params().set("minTime", minTime);
		sql.params().set("maxTime", maxTime);
		sql.params().set("page", page);
		sql.params().set("count", count);
		sql.setCallback(Sqls.callback.entities());
		sql.setEntity(dao.getEntity(Question.class));
		dao.execute(sql);
		log.info(sql.getSourceSql());
		List<Question> list = sql.getList(Question.class);
		if(list == null || list.size() < 1){
			return null;
		}
		return list;
	}
}
