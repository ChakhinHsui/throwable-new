package throwable.server.enums;

/**
 * @author WaterHsu@xiu8.com
 * @version 2015年5月24日
 */
public enum NoticeType {
	
	system_notice("系统消息", 1),
	user_notice("用户消息", 2);
	
	private String text;
	private int value;
	
	private NoticeType(String text, int value) {
		this.text = text;
		this.value = value;
	}
	
	public String getText() {
		return this.text;
	}
	
	public int getValue() {
		return this.value;
	}
}
