package shared;

import java.io.Serializable;
/**
 * ћурашка на сервер посылает лишь 2 типа сообщений. 
 * @author RedFox
 *
 */
public interface MessageToServer extends Serializable{
	public enum Type {
		STEP, REQUEST;
	}
	public Type getType();
}
