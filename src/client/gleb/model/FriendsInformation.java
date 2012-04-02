package client.gleb.model;

import java.io.Serializable;

public class FriendsInformation implements Serializable{
	private String antName;
	private int currX;
	private int currY;
	public String getAntName() {
		return antName;
	}

	public void setAntName(String antName) {
		this.antName = antName;
	}

	public int getCurrX() {
		return currX;
	}

	public void setCurrX(int currX) {
		this.currX = currX;
	}

	public int getCurrY() {
		return currY;
	}

	public void setCurrY(int currY) {
		this.currY = currY;
	}
	
}
