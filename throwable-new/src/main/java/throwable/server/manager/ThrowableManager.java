package throwable.server.manager;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import seava.tools.StringTool;
import seava.tools.rabbitmq.MQWorker;
import seava.tools.rabbitmq.RabbitMqConsumer;
import seava.tools.rabbitmq.RabbitMqTool;
import throwable.server.MainModule;
import throwable.server.bean.Worker;
import throwable.server.service.WorkerService;

/**
 * @author WaterHsu@xiu8.com
 * @version 2015年5月24日
 */
@IocBean(create="init")
public class ThrowableManager {
	
	private static Log log = Logs.getLog(ThrowableManager.class);
	@Inject
	public RabbitMqTool rabbitMqTool;
	
	@Inject
	private WorkerService workerService;
	
	public void init() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true) {
					try {
						//初始化所有消费者
						initAllWorkers();
						//睡眠10分钟
						Thread.sleep(1000 * 60 * 10);
					} catch (Exception e) {
						log.error("初始化出错", e);
					}
				}
			}
		}).start();
	}
	
	/**
	 * 初始化所有的消费者
	 */
	public void initAllWorkers() {
		List<Worker> workerList = workerService.queryAllWorker("throwable");
		if(StringTool.isEmpty(workerList)) {
			log.error("加载所有的消费者出错");
			return;
		}
		MQWorker mqWorker;
		for(Worker worker : workerList) {
			try {
				if(!allWorkers.containsKey(worker.workQueue)) {
					allWorkers.put(worker.workQueue, new CopyOnWriteArrayList<String>());
				}
				int needWorker = worker.initNum - allWorkers.get(worker.workQueue).size();
				if(needWorker <= 0) {
					log.info("队列" + worker.workQueue + " 消费者足够  :" + worker.initNum);
					continue;
				} 
				mqWorker = MainModule.ioc.get(MQWorker.class, worker.workName);
				for(int i = 0; i < needWorker; i++) {
					new Thread(new RabbitMqConsumer(worker.workQueue, worker.errorQueue, mqWorker, rabbitMqTool, allWorkers)).start();
				}
			} catch (Exception e) {
				log.error("初始化worker出错", e);
			}
		}
	}
	
	/**
	 * 所有worker key=队列名称 value=消费者集合
	 */
	private static Map<String, List<String>> allWorkers = new ConcurrentHashMap<String, List<String>>();
}
