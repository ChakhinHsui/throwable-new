package throwable.server.enums;

/**
 * @author WaterHsu@xiu8.com
 * @version 2015年5月24日
 */
public enum NoticeRead {

	no_read("未被查看", 1),
	has_read("已被查看", 2);
	
	private String text;
	private int value;
	
	private NoticeRead(String text, int value) {
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
