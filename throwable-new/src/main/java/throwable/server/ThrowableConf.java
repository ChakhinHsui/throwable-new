package throwable.server;

import org.nutz.ioc.Ioc;
import org.nutz.ioc.impl.PropertiesProxy;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import throwable.server.utils.StringTool;


/**
 * 配置相关的类
 * 
 * @author WaterHsu@xiu8.com
 * @version 2014年10月10日
 */
@IocBean
public class ThrowableConf {
	
	private Log log = Logs.get();
	private long sleepTime;//更新配置周期
	public static Ioc ioc;//IOC容器
	public static PropertiesProxy appProp = new PropertiesProxy();
	public static PropertiesProxy errorMsg = new PropertiesProxy();
	public static PropertiesProxy config = new PropertiesProxy();  //读取conf下面的配置文件
	
	/**
	 * 文件配置的信息
	 */
	public void run(Ioc ioc) {
		initPro(ioc);
		while(true){
			init();
			sleep();
		}
	}
	
	/**
	 * 初始化一些读取配置文件的类
	 * @param ioc   Ioc容器
	 */
	private void initPro(Ioc ioc){
		ThrowableConf.ioc = ioc;
		appProp.setPaths(new String[] { "application.properties" });
		errorMsg.setPaths(new String[] { "errorMsg.properties" });
		sleepTime = appProp.getLong("system.sleepTime",120)*1000L;
		config = ioc.get(PropertiesProxy.class, "config");
	}
	
	private void init(){
		
	}

	private void sleep(){
		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
			log.error("配置循环sleep出错。\r\n"+StringTool.getExceptionStack(e));
		}
	}
}
