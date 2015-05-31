package throwable.server.enums;

/**
 * @author WaterHsu@xiu8.com
 * @version 2014年11月8日
 */
public enum AgreeOrDisagree {
	agree("agree", 1),
	disagree("disagree", 2);
	
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

	private AgreeOrDisagree(String name, int value){
		this.name = name;
		this.value = value;
	}
	
	public static String getName(int value){
		for(AgreeOrDisagree q : AgreeOrDisagree.values()){
			if(q.getValue() == value){
				return q.getName();
			}
		}
		return null;
	}
}
