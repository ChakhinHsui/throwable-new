package throwable.server.service;

import java.util.List;

import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import throwable.server.bean.Kind;

/**
 * @author WaterHsu@xiu8.com
 * @version 2014年10月15日
 */
public class KindService extends BaseService {

	private Log log = Logs.get();
	
	/**
	 * 根据kindId查询分类
	 * @param id
	 * @return
	 */
	public Kind queryKindById(int id){
		Sql sql = dao.sqls().create("kind_queryKindByKindId");
		sql.params().set("id", id);
		sql.setCallback(Sqls.callback.entities());
		sql.setEntity(dao.getEntity(Kind.class));
		dao.execute(sql);
		log.info(sql.getSourceSql());
		List<Kind> list = sql.getList(Kind.class);
		if(list == null || list.size() < 1) return null;
		return list.get(0);
	}
	
	/**
	 * 根据kind_name 查询分类
	 * @param kindName
	 * @return
	 */
	public Kind queryKindByKindName(String kindName){
		Sql sql = dao.sqls().create("kind_queryKindByKindName");
		sql.params().set("kind_name", kindName);
		sql.setCallback(Sqls.callback.entities());
		sql.setEntity(dao.getEntity(Kind.class));
		dao.execute(sql);
		log.info(sql.getSourceSql());
		List<Kind> list = sql.getList(Kind.class);
		if(list == null || list.size() < 1) return null;
		return list.get(0);
	}
	
	/**
	 * 根据kindId 查询分类名
	 * @param id
	 * @return
	 */
	public String queryKindNameByKindId(int id){
		Sql sql = dao.sqls().create("kind_queryKindNameByKindId");
		sql.params().set("id", id);
		sql.setCallback(Sqls.callback.str());
		dao.execute(sql);
		log.info(sql.getSourceSql());
		String kind_name = sql.getString();
		return kind_name;
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
		log.info(sql.getSourceSql());
	}
}
