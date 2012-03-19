package server.model;

public enum CellType {
	UNVISIBLE, FREE, WALL, FOOD, ENEMY, FRIEND, OWN_ANTHILL, ENEMY_ANTHILL;
	private String antName;

	public String getAntName() throws Exception {
		if(this != FRIEND){
			throw new Exception();
		}
		return antName;
	}

	public void setAntName(String antName) throws Exception {
		if(this != FRIEND){
			throw new Exception();
		}
		this.antName = antName;
	}
	
}
