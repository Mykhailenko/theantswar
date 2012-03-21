package client.oleg;

import java.io.Serializable;

// Класс запроса муравья к муравью
public class RequestToAnt implements Serializable{
	private String message;

	public RequestToAnt(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}	
}
