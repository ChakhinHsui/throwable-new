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
import throwable.server.bean.Question;
import throwable.server.utils.StringTool;

/**
 * 
 * 问题相关的操作数据库的类
 * @author WaterHsu
 */
@IocBean
public class QuestionService extends BaseService {
	
	/**
	 * 问题-添加问题
	 * @param question  问题的pojo
	 */
	public void addQuestion(Question question){
		Sql sql = dao.sqls().create("question_insertOneQuestion");
		sql.params().set("question_name", question.question_name);
		sql.params().set("question_description", question.question_description);
		sql.params().set("question_type", question.question_type);
		sql.params().set("viewers", question.viewers);
		sql.params().set("agrees", question.agrees);
		sql.params().set("degrees", question.degrees);
		sql.params().set("answers", question.answers);
		sql.params().set("focuses", question.focuses);
		sql.params().set("collections", question.collections);
		sql.params().set("solved", question.solved);
		sql.params().set("create_time", question.create_time);
		sql.params().set("kind_id", question.kind_id);
		sql.params().set("user_id", question.user_id);
		dao.execute(sql);
	}
	
	/**
	 * 更新问题描述
	 * @param id                     问题id
	 * @param question_description   问题的描述
	 */
	public void updateQuestionDesc(int id, String question_description){
		Sql sql = dao.sqls().create("question_addQuestionDesc");
		sql.params().set("question_description", question_description);
		sql.params().set("id", id);
		dao.execute(sql);
	}
	
	/**
	 * 更新访问数
	 * @param id        问题id
	 * @param viewers   新增的访问数
	 */
	public void updateViewers(int id, int viewers){
		Sql sql = dao.sqls().create("question_updateViewers");
		sql.params().set("viewers", viewers);
		sql.params().set("id", id);
		dao.execute(sql);
	}
	
	/**
	 * 更新赞同数
	 * @param id        问题id
	 * @param agrees    新增的赞同数
	 */
	public void updateAgrees(int id, int agrees){
		Sql sql = dao.sqls().create("question_updateAgrees");
		sql.params().set("agrees", agrees);
		sql.params().set("id", id);
		dao.execute(sql);
	}
	
	/**
	 * 更新问题类型 只能从私有问题变为公开问题  反之被禁止
	 * @param id                 问题id
	 * @param question_type      问题类型
	 */
	public void updateQuestionType(int id, int question_type){
		Sql sql = dao.sqls().create("question_publicQuestion");
		sql.params().set("question_type", question_type);
		sql.params().set("id", id);
		dao.execute(sql);
	}
	
	/**
	 * 问题-根据问题id查询问题
	 * @param id         问题id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Map queryQuestionByQuestionId(int id){
		Sql sql = dao.sqls().create("question_queryOneQuestionInfo");
		sql.params().set("id", id);
		sql.setCallback(Sqls.callback.maps());
		dao.execute(sql);
		List<Map> list = sql.getList(Map.class);
		return StringTool.isEmpty(list) ? null : list.get(0);
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
		return sql.getList(Question.class);
	}
	
	/**
	 * 问题-根据分类id分页查询问题
	 * @param kindId 分类id
	 * @param page   开始位置 即offset
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
		return sql.getList(Question.class);
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
		return sql.getList(Question.class);
	}
	
	/**
	 * 问题-根据用户id分页查询问题
	 * @param userId       用户id
	 * @param page         开始位置 即offset
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
		return sql.getList(Question.class);
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
		return sql.getList(Question.class);
	}
	
	/**
	 * 根据时间段分页查询问题
	 * @param minTime     时间段开始时间
	 * @param maxTime     时间段结束时间
	 * @param page        开始位置 即offset
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
		return sql.getList(Question.class);
	}
	
	/**
	 * 根据问题类型查询问题
	 * @param type  类型 1 公开  2 私有
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> queryQuestionByType(int type){
		Sql sql = dao.sqls().create("question_queryAllQuestionByType");
		sql.params().set("question_type", type);
		sql.setCallback(Sqls.callback.maps());
		dao.execute(sql);
		return sql.getList(Map.class);
	}
	
	/**
	 * 查询最热问题  访问最多问题
	 * @param type   问题类型
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> queryHotQuestionByType(int type) {
		Sql sql = dao.sqls().create("question_queryAllHotQuestionByType");
		sql.params().set("question_type", type);
		sql.setCallback(Sqls.callback.maps());
		dao.execute(sql);
		return sql.getList(Map.class);
	}
	
	/**
	 * 查询关注最多的问题
	 * @param type 问题类型
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> queryMostFocusedQuestion(int type) {
		Sql sql = dao.sqls().create("question_queryAllMostFocusQuestionByType");
		sql.params().set("question_type", type);
		sql.setCallback(Sqls.callback.maps());
		dao.execute(sql);
		return sql.getList(Map.class);
	}
	
	/**
	 * 查询最新的回答最多的问题
	 * @param type   问题类型
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> queryNewMostAnswerQuestion(int type) {
		Sql sql = dao.sqls().create("question_queryNewMostAnswerQuestionByType");
		sql.params().set("question_type", type);
		sql.setCallback(Sqls.callback.maps());
		dao.execute(sql);
		return sql.getList(Map.class);
	}
	
	/**
	 * 查询用户是否已经关注过该问题
	 * @param user_id            用户id
	 * @param question_id        问题id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public boolean queryHaveFocused(int user_id, int question_id){
		Sql sql = dao.sqls().create("focus_haveFocused");
		sql.params().set("user_id", user_id);
		sql.params().set("question_id", question_id);
		sql.setCallback(Sqls.callback.maps());
		dao.execute(sql);
		List<Map> list = sql.getList(Map.class);
		return StringTool.isEmpty(list);
	}
	
	/**
	 * 添加关注
	 * @param user_id      用户id
	 * @param question_id  问题id
	 * @param time         关注时间
	 */
	public void addFocus(int user_id, int question_id, long time){
		Sql sql = dao.sqls().create("focus_addFocus");
		sql.params().set("user_id", user_id);
		sql.params().set("question_id", question_id);
		sql.params().set("create_time", time);
		dao.execute(sql);
	}
	
	/**
	 * 删除关注
	 * @param user_id          用户id
	 * @param question_id      问题id
	 */
	public void deleteFocus(int user_id, int question_id){
		Sql sql = dao.sqls().create("focus_deleteFocus");
		sql.params().set("user_id", user_id);
		sql.params().set("question_id", question_id);
		dao.execute(sql);
	}
	
	/**
	 * 查询用户是否已经收藏过问题
	 * @param user_id          用户id
	 * @param question_id      问题id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public boolean haveCollected(int user_id, int question_id){
		Sql sql = dao.sqls().create("collection_haveCollected");
		sql.params().set("user_id", user_id);
		sql.params().set("question_id", question_id);
		sql.setCallback(Sqls.callback.maps());
		dao.execute(sql);
		List<Map> list = sql.getList(Map.class);
		return StringTool.isEmpty(list);
	}
	
	/**
	 * 用户添加收藏
	 * @param user_id         用户id
	 * @param question_id     问题id
	 * @param time            收藏时间
	 * @param mark            标注
	 */
	public void addCollection(int user_id, int question_id, long time, String mark){
		Sql sql = dao.sqls().create("collection_addCollection");
		sql.params().set("user_id", user_id);
		sql.params().set("question_id", question_id);
		sql.params().set("create_time", time);
		sql.params().set("collection_mark", mark);
		dao.execute(sql);
	}
	
	/**
	 * 用户取消收藏问题
	 * @param user_id        用户id
	 * @param question_id    问题id
	 */
	public void deleteCollection(int user_id, int question_id){
		Sql sql = dao.sqls().create("collection_deleteCollection");
		sql.params().set("user_id", user_id);
		sql.params().set("question_id", question_id);
		dao.execute(sql);
	}
	
	public int agreeQuestion(int questionId){
		Sql sql = dao.sqls().create("question_agreeQuestion");
		sql.params().set("agrees", 1);
		dao.execute(sql);
		return 1;
	}
	
	/**
	 * 根据用户id查询问题的部分信息
	 * @param userId   用户id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> queryPartQuestionInfoByUserId(int userId) {
		Sql sql = dao.sqls().create("question_queryPartQByUserId");
		sql.params().set("user_id", userId);
		sql.setCallback(Sqls.callback.maps());
		dao.execute(sql);
		return sql.getList(Map.class);
	}
	
	/**
	 * 查询用户关注的问题
	 * @param userId  用户id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> queryUserFocus(int userId) {
		Sql sql = dao.sqls().create("focus_queryUserFocused");
		sql.params().set("user_id", userId);
		sql.setCallback(Sqls.callback.maps());
		dao.execute(sql);
		return sql.getList(Map.class);
	}
}
