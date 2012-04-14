package shared;

import java.io.Serializable;

public class Cell implements Serializable {
	private static final long serialVersionUID = -2778203217581423616L;
	public enum Type {
		UNVISIBLE, FREE, WALL, FOOD, ENEMY_ANT, FRIEND_ANT, FRIEND_HILL, ENEMY_HILL, ENEMY_ANT_HILL, FRIEND_ANT_HILL;
	}
	private String antName;
	private String playerName;
	private String hillName;
	private Type type;
	public String getAntName() {
		return antName;
	}
	public void setAntName(String antName) {
		this.antName = antName;
	}
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		if(type.equals(Type.UNVISIBLE) || type.equals(Type.ENEMY_ANT) || type.equals(Type.ENEMY_ANT_HILL) || type.equals(Type.ENEMY_ANT_HILL)){
			antName = null;
			playerName = null;
		}
		this.type = type;
	}
	public String getHillName() {
		return hillName;
	}
	public void setHillName(String hillName) {
		this.hillName = hillName;
	}
	
}
