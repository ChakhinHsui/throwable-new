package throwable.server.enums;

/**
 * @author WaterHsu@xiu8.com
 * @version 2014年11月21日
 */
public enum LabelType {
	web_own_label("网站公有", 1),
	user_own_label("用户私有", 0);
	
	private String name;
	private int value;
	
	private LabelType(String name, int value){
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	public static String getName(int value){
		for(CorrectType c : CorrectType.values()){
			if(c.getValue() == value){
				return c.getName();
			}
		}
		return null;
	}
}
