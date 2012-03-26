package server.model;

import jade.wrapper.AgentController;

public class AntCoockie {
	private String playerName;
	private int x;
	private int y;
	private AgentController agentController;
	private String antName;
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public AgentController getAgentController() {
		return agentController;
	}
	public void setAgentController(AgentController agentController) {
		this.agentController = agentController;
	}
	public String getAntName() {
		return antName;
	}
	public void setAntName(String antName) {
		this.antName = antName;
	}
	
}
