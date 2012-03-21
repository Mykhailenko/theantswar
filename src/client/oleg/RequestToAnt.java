package client.oleg;

import java.io.Serializable;

/**
 * ��� �� ��� ��� ���� �������. 
 * � ������ � ���� ���������� ����� ������ ��� ��� ��� ����� ������.
 * ��� � �������� ����������� �� ���������
 * 
 * @author RedFox
 *
 */
// ����� ������� ������� � ������� . 
public class RequestToAnt implements Serializable{
	private static final long serialVersionUID = 8566215524829631394L;
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
