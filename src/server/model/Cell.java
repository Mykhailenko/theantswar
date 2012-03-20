package server.model;

public enum Cell {
	FREE, WALL, FOOD, ANT, HILL;
	private String entity;

	public String getAntName() throws Exception {
		if(this != ANT){
			throw new Exception();
		}
		return entity;
	}

	public void setAntName(String antName) throws Exception {
		if(this != ANT){
			throw new Exception();
		}
		this.entity = antName;
	}
	public String getHillName() throws Exception{
		if(this != HILL){
			throw new Exception();
		}
		return entity;
	}
	public void setHillName(String hillName) throws Exception{
		if(this != HILL){
			throw new Exception();
		}
		this.entity = hillName;
	}
}
