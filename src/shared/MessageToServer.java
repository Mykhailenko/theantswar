package shared;

import java.io.Serializable;
/**
 * ������� �� ������ �������� ���� 2 ���� ���������. 
 * @author RedFox
 *
 */
public interface MessageToServer extends Serializable{
	public enum Type {
		STEP, REQUEST;
	}
	public Type getType();
	public boolean isStep();
	public boolean isRequest();
}
