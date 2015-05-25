package throwable.server.bean;

/**
 * mq消费者的bean
 * @author WaterHsu@xiu8.com
 * @version 2015年5月24日
 */
public class Worker {

	/**
	 * mq消费者id
	 */
	public int id;
	/**
	 * 工作队列
	 */
	public String workQueue;
	/**
	 * 消费者名
	 */
	public String workName;
	/**
	 * 错误队列
	 */
	public String errorQueue;
	/**
	 * 初始化消费者个数
	 */
	public int initNum;
	/**
	 * 消费者启动级别
	 */
	public int startLevel;
	/**
	 * 备注
	 */
	public String mark;
}
