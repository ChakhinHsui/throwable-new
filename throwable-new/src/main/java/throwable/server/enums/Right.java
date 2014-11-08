package throwable.server.enums;

/**
 * @author WaterHsu@xiu8.com
 * @version 2014年11月8日
 */
public enum Right {
	general("普通用户", "general_user"),
	context("内容管理用户", "context_manager"),
	web("网站管理用户", "web_manager"),
	superU("超级用户", "root");
	
	private String name;
	private String value;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	private Right(String name, String value){
		this.name = name;
		this.value = value;
	}
	
	public static String getName(String value){
		for(Right r : Right.values()){
			if(r.getValue().equals(value)){
				return r.getName();
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return this.value;
	}
}
