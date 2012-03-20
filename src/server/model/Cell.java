package server.model;

public class Cell {
	public enum Type{ 
		FREE, WALL, FOOD, ANT, HILL, ANT_HILL;
		public String toString() {
			switch (this) {
			case FREE:
				return "<free>";
			case WALL:
				return "<wall>";
			case ANT:
				return "<ant>";
			default:
				return "<else>";
			}
			
		};
	}
	private String antName;
	private String playerName;
	private Type type;
	@Override
	public String toString() {
		return type.toString();
	}
	public Cell() {
		type = Type.FREE;
	}
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
		this.type = type;
	}
	
}
