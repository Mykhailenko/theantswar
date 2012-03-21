package shared;

import java.io.Serializable;

public enum Cell implements Serializable{
	UNVISIBLE, FREE, WALL, FOOD, ENEMY, FRIEND, OWN_HILL, ENEMY_HILL;
	private String entity;
	
	public String getAntName() throws Exception {
		if(this != FRIEND){
			throw new Exception();
		}
		return entity;
	}

	public void setAntName(String antName) throws Exception {
		if(this != FRIEND){
			throw new Exception();
		}
		this.entity = antName;
	}
	public String getHillName() throws Exception{
		if(this != OWN_HILL){
			throw new Exception();
		}
		return entity;
	}
	public void setHillName(String hillName) throws Exception{
		if(this != OWN_HILL){
			throw new Exception();
		}
		this.entity = hillName;
	}
}
