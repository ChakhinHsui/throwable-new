package throwable.server.enums;

/**
 * @author WaterHsu@xiu8.com
 * @version 2014年11月8日
 */
public enum CorrectType {
	correct("正确", 1),
	part_correct("部分正确", 2),
	not_sure("未确认", 3),
	wrong("错误", 4),
	illegal("非法", 5);
	
	private String name;
	private int value;
	
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

	private CorrectType(String name, int value){
		this.name = name;
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
