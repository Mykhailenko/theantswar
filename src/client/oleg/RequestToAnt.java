package client.oleg;

import java.io.Serializable;

/**
 *  ак бы это уже дело муравь€. 
 * в смысле € буду обениватс€ между своими тем чем мне будет удобно.
 * это к базовому функционалу не относитс€
 * 
 * @author RedFox
 *
 */
//  ласс запроса муравь€ к муравью . 
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
