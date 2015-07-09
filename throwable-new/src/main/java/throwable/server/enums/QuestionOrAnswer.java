package throwable.server.enums;

/**
 * @author WaterHsu@xiu8.com
 * @version 2014年11月8日
 */
public enum QuestionOrAnswer {
	question("question", 1),
	answer("answer", 2);
	
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

	private QuestionOrAnswer(String name, int value){
		this.name = name;
		this.value = value;
	}
	
	public static String getName(int value){
		for(QuestionOrAnswer q : QuestionOrAnswer.values()){
			if(q.getValue() == value){
				return q.getName();
			}
		}
		return null;
	}
}
