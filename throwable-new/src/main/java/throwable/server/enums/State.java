package throwable.server.enums;

/**
 * @author WaterHsu@xiu8.com
 * @version 2014年11月8日
 */
public enum State {
	no_active("未激活", 1),
	user_exception("账号异常", 2),
	user_frozen("账号被冻结", 3),
	user_disabled("账号已停用", 4),
	user_nomal("账号正常", 5);
	
	private String name;
	private int value;
	
	private State(String name, int value){
		this.name = name;
		this.value = value;
	}
	
	public static String getName(int value){
		for(State s : State.values()){
			if(s.getValue() == value){
				return s.getName();
			}
		}
		return null;
	}
	
	

	@Override
	public String toString() {
		return this.name;
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
	
	
}
