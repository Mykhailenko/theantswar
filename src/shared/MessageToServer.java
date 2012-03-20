package shared;

import java.io.Serializable;

public interface MessageToServer extends Serializable{
	public enum Type {
		STEP, REQUEST;
	}
	public Type getType();
}
