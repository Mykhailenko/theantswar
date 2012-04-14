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
			case FOOD:
				return "<food>";
			case HILL:
				return "<hill>";
			default:
				return "<else>";
			}
			
		};
	}
	private String antName;
	private String playerName;
	private String hillName;
	private Type type;
	public Cell copy() {
		Cell cpy = new Cell();
		cpy.setAntName(antName);
		cpy.setPlayerName(playerName);
		cpy.setType(type);
		return cpy;
	}
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
		if(type.equals(Type.FOOD) ||
				type.equals(Type.FREE) ||
				type.equals(Type.WALL) ){
			this.antName = null;
			this.playerName = null;
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
