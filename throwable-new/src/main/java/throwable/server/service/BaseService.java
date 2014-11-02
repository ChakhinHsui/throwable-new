package throwable.server.service;

import java.sql.Timestamp;

import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

/**
 * @author WaterHsu@xiu8.com
 * @version 2014年10月10日
 */
@IocBean
public class BaseService {

	@Inject
	public Dao dao;

	/**
	* @Title: queryNowTime
	* @Description: 查询数据库当前时间
	 */
	public Timestamp queryNowTime(){
		Sql sql = dao.sqls().create("base_queryNowTime");
		sql.setCallback(Sqls.callback.timestamp());
		dao.execute(sql);
		return sql.getObject(Timestamp.class);
	}
}
