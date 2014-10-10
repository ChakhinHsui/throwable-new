
package throwable.server;

import org.nutz.dao.Dao;
import org.nutz.ioc.Ioc;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.NutConfig;
import org.nutz.mvc.Setup;
import org.nutz.mvc.annotation.ChainBy;
import org.nutz.mvc.annotation.IocBy;
import org.nutz.mvc.annotation.Modules;
import org.nutz.mvc.annotation.SetupBy;
import org.nutz.mvc.ioc.provider.ComboIocProvider;

import throwable.server.conf.ThrowableConf;





/**
 * 主模块声明
 * @author Gongqin
 */
@Modules(scanPackage = true)
@IocBy(type = ComboIocProvider.class, args = {	"*org.nutz.ioc.loader.json.JsonLoader",
												"application.js",
												"*org.nutz.ioc.loader.annotation.AnnotationIocLoader",
												"throwable.server"
})
@SetupBy(MainModule.class)
@ChainBy(args={"throwable/server/framework/mvc/mvc-chains.js"})
public class MainModule implements Setup, Runnable {

	private Log log = Logs.getLog("MainModule");
	private Ioc ioc;
	
	/**
	 * 框架启动时，初始化入口
	 */
	@Override
	public void init(NutConfig config) {
		ioc = config.getIoc();
		//启动时初始化一下数据库连接池
		Thread t = new Thread(this);
		//最高优先级
		t.setPriority(Thread.MAX_PRIORITY);
		t.start();
	} 
	
	@Override
	public void destroy(NutConfig config) {
	}

	/**
	 * 通过线程方式，初始化一下数据库连接池。减少系统的启动时间
	 */
	@Override
	public void run() {
		try {
			ioc.get(Dao.class, "dao");
			log.debug("数据库初始化成功!");
			//配置文件初始化启动
			ioc.get(ThrowableConf.class).run(ioc);
		}
		catch (Exception e) {
			log.fatal("数据库连接初始化失败!!!", e);
		}
	}
}
