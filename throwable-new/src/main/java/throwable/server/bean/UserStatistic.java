package throwable.server.bean;

/**
 * 用户统计信息的bean
 * @author WaterHsu@xiu8.com
 * @version 2015年2月27日
 */
public class UserStatistic {

	/**
	 * 用户id
	 */
	public long user_id;
	/**
	 * 提问数
	 */
	public int asks;
	/**
	 * 回答数
	 */
	public int answers;
	/**
	 * 赞同数
	 */
	public int agrees;
	/**
	 * 不赞同数
	 */
	public int disagrees;
	/**
	 * 收藏数
	 */
	public int collections;
	/**
	 * 关注数
	 */
	public int focuses;
	
	public static UserStatistic updateAgrees(long userId, int agrees) {
		UserStatistic userstat = new UserStatistic();
		userstat.agrees = agrees;
		return userstat;
	}
	
	public static UserStatistic updateDisAgrees(long userId, int disagrees) {
		UserStatistic userstat = new UserStatistic();
		userstat.disagrees = disagrees;
		return userstat;
	}
	
	public static UserStatistic updateAsks(long userId, int asks) {
		UserStatistic userstat = new UserStatistic();
		userstat.asks = asks;
		return userstat;
	}
	
	public static UserStatistic updateAnswers(long userId, int answers) {
		UserStatistic userstat = new UserStatistic();
		userstat.answers = answers;
		return userstat;
	}
}
