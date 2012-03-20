package server.model;

public class Cell {
	public enum Type{ 
		FREE, WALL, FOOD, ANT, HILL, ANT_HILL;
	}
	private String entity;
	private Type type;
	public Cell() {
		type = Type.FREE;
	}
	public String getAntName() throws Exception {
		if(this.type != Type.ANT && this.type != Type.ANT_HILL){
			throw new Exception();
		}
		return entity;
	}

	public void setAntName(String antName) throws Exception {
		if(this.type != Type.ANT && this.type != Type.ANT_HILL){
			throw new Exception();
		}
		this.entity = antName;
	}
	public String getHillName() throws Exception{
		if(this.type != Type.HILL && this.type != Type.ANT_HILL){
			throw new Exception();
		}
		return entity;
	}
	public void setHillName(String hillName) throws Exception{
		if(this.type != Type.HILL && this.type != Type.ANT_HILL){
			throw new Exception();
		}
		this.entity = hillName;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	
}
