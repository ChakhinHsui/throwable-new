/**
 * Studia Seava
 * 
 * created in 2015年3月11日
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

import throwable.server.bean.Label;
import throwable.server.utils.StringTool;

/**
 * @author WaterHsu
 *
 */
@IocBean
public class LabelService extends BaseService {

	/**
	 * 增加label
	 * @param label
	 * @return
	 */
	public boolean addLabel(Label label) {
		Sql sql = dao.sqls().create("label_addLabel");
		sql.params().set("name", label.name);
		sql.params().set("type", label.type);
		sql.params().set("time", label.time);
		dao.execute(sql);
		return 1 == sql.getUpdateCount();
	}
	
	/**
	 * 增加用户label信息
	 * @param userId   用户id
	 * @param labelId  labelid
	 * @return
	 */
	public boolean addUserLabel(int userId, int labelId) {
		Sql sql = dao.sqls().create("label_addUserLabel");
		sql.params().set("userId", userId);
		sql.params().set("labelId", labelId);
		dao.execute(sql);
		return 1 == sql.getUpdateCount();
	}
	
	/**
	 * 增加问题label信息
	 * @param questionId  问题id
	 * @param labelId     标签id
	 * @return
	 */
	public boolean addQuestionLable(int questionId, int labelId) {
		Sql sql = dao.sqls().create("label_addQuestionLabel");
		sql.params().set("questionId", questionId);
		sql.params().set("labelId", labelId);
		dao.execute(sql);
		return 1 == sql.getUpdateCount();
	}
	
	/**
	 * 根据标签名查找标签
	 * @param labelName
	 * @return
	 */
	public Label queryLabelByName(String labelName) {
		Sql sql = dao.sqls().create("label_queryLabelByName");
		sql.params().set("name", labelName);
		sql.setCallback(Sqls.callback.entities());
		sql.setEntity(dao.getEntity(Label.class));
		dao.execute(sql);
		List<Label> list = sql.getList(Label.class);
		return StringTool.isEmpty(list) ? null : list.get(0);
	}
	
	/**
	 * 根据标签id查找标签
	 * @param labelId
	 * @return
	 */
	public Label queryLabelById(int labelId) {
		Sql sql = dao.sqls().create("label_queryLabelById");
		sql.params().set("id", labelId);
		sql.setCallback(Sqls.callback.entities());
		sql.setEntity(dao.getEntity(Label.class));
		dao.execute(sql);
		List<Label> list = sql.getList(Label.class);
		return StringTool.isEmpty(list) ? null : list.get(0);
	}
	
	/**
	 * 根据用户id查询label信息
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> queryLabelsByUserId(int userId) {
		Sql sql = dao.sqls().create("label_queryLabelByUserId");
		sql.params().set("userId", userId);
		sql.setCallback(Sqls.callback.maps());
		dao.execute(sql);
		return sql.getList(Map.class);
	}
	
	/**
	 * 查询所有的标签
	 * @return
	 */
	public List<Label> queryAllLabels() {
		Sql sql = dao.sqls().create("label_queryAllLabel");
		sql.setCallback(Sqls.callback.entities());
		sql.setEntity(dao.getEntity(Label.class));
		dao.execute(sql);
		return sql.getList(Label.class);
	}
	
	/**
	 * 根据问题id查询标签
	 * @param questionId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> queryLabelsByQuestionId(int questionId) {
		Sql sql = dao.sqls().create("label_queryLabelByQuestionId");
		sql.params().set("questionId", questionId);
		sql.setCallback(Sqls.callback.maps());
		dao.execute(sql);
		return sql.getList(Map.class);
	} 
	
	/**
	 * 查询user_label表中是否存在相同的数据
	 * @param userId   用户id
	 * @param labelId  标签id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> queryExists(int userId, int labelId) {
		Sql sql = dao.sqls().create("label_queryExists");
		sql.params().set("userId", userId);
		sql.params().set("labelId", labelId);
		sql.setCallback(Sqls.callback.maps());
		dao.execute(sql);
		return sql.getList(Map.class);
	}
}
