package client.gleb.model;

import java.io.Serializable;

public interface GMessage extends Serializable{
	public enum Type {
		ORDER, HI1, HI2, HI3;
	}
	Type getType();
}
