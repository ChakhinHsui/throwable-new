package throwable.server.consumer;

import org.nutz.ioc.loader.annotation.IocBean;

import seava.tools.rabbitmq.MQWorker;

/**
 * @author WaterHsu@xiu8.com
 * @version 2015年5月24日
 */
@IocBean
public class AnswerWorker implements MQWorker {

	@Override
	public void work(String message) {
		System.out.println("收到来自mq的消息: " + message);
	}

}
