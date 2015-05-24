package throwable.server.service;

import java.util.List;

import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.IocBean;

import throwable.server.bean.Worker;

/**
 * @author WaterHsu@xiu8.com
 * @version 2015年5月24日
 */
@IocBean
public class WorkerService extends BaseService{

	/**
	 * 查询所有的消费者
	 * @param mark
	 * @return
	 */
	public List<Worker> queryAllWorker(String mark) {
		Sql sql = dao.sqls().create("mqwork_queryAllWorkers");
		sql.params().set("mark", mark);
		sql.setCallback(Sqls.callback.entities());
		sql.setEntity(dao.getEntity(Worker.class));
		dao.execute(sql);
		return sql.getList(Worker.class);
	}
}
