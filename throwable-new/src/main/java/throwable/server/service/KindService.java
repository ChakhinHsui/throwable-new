package throwable.server.service;

import java.util.List;
import java.util.Map;

import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.IocBean;

import throwable.server.bean.Kind;

/**
 * 分类相关的操作数据库的类
 * 
 * @author WaterHsu@xiu8.com
 * @version 2014年10月15日
 */
@IocBean
public class KindService extends BaseService {

	/**
	 * 根据kindId查询分类
	 * @param id
	 * @return
	 */
	public Kind queryKindById(long id){
		Sql sql = dao.sqls().create("kind_queryKindByKindId");
		sql.params().set("id", id);
		sql.setCallback(Sqls.callback.entity());
		sql.setEntity(dao.getEntity(Kind.class));
		dao.execute(sql);
		return sql.getObject(Kind.class);
	}
	
	/**
	 * 根据kind_name 查询分类
	 * @param kindName
	 * @return
	 */
	public Kind queryKindByKindName(String kindName){
		Sql sql = dao.sqls().create("kind_queryKindByKindName");
		sql.params().set("kind_name", kindName);
		sql.setCallback(Sqls.callback.entity());
		sql.setEntity(dao.getEntity(Kind.class));
		dao.execute(sql);
		return sql.getObject(Kind.class);
	}
	
	/**
	 * 根据kindId 查询分类名
	 * @param id   分类id
	 * @return
	 */
	public String queryKindNameByKindId(long id){
		Sql sql = dao.sqls().create("kind_queryKindNameByKindId");
		sql.params().set("id", id);
		sql.setCallback(Sqls.callback.str());
		dao.execute(sql);
		return sql.getString();
	}
	
	/**
	 * 插入分类
	 * @param kind
	 */
	public void insertKind(Kind kind){
		Sql sql = dao.sqls().create("kind_insertOneKind");
		sql.params().set("kind_name", kind.kind_name);
		sql.params().set("kind_parent_id", kind.parent_kind.id);
		sql.params().set("user_id", kind.user_id);
		sql.params().set("time", kind.time);
		dao.execute(sql);
	}
	
	/**
	 * 查询所有的分类及信息
	 * @return
	 */
	public List<Kind> queryAllKinds() {
		Sql sql = dao.sqls().create("kind_queryAllKind");
		sql.setCallback(Sqls.callback.entities());
		sql.setEntity(dao.getEntity(Kind.class));
		dao.execute(sql);
		return sql.getList(Kind.class);
	}
	
	/**
	 * 查询所有分类的部分信息
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> queryAllKindsPart() {
		Sql sql = dao.sqls().create("kind_queryAllKindPartInfo");
		sql.setCallback(Sqls.callback.maps());
		dao.execute(sql);
		return sql.getList(Map.class);
	}
}
