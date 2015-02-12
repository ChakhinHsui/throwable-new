package throwable.server.service;

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
	public Kind queryKindById(int id){
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
	public String queryKindNameByKindId(int id){
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
		dao.execute(sql);
	}
}
