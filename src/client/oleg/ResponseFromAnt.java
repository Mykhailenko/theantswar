package client.oleg;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ResponseFromAnt implements Serializable{
	private String inMessage;
	private Map<String,Object> answer = new HashMap<String,Object>();
	
	public ResponseFromAnt(String inMessage) {
		super();
		this.inMessage = inMessage;
	}

	public void addAnswer(String key, Object value){
		answer.put(key, value);
	}
	
	public Object getAnswer(String key){
		return answer.get(key);
	}
	
	public Map<String, Object> getAnswer() {
		return answer;
	}

	public void setAnswer(Map<String, Object> answer) {
		this.answer = answer;
	}

	public String getInMessage() {
		return inMessage;
	}

	public void setInMessage(String inMessage) {
		this.inMessage = inMessage;
	}
	
}
