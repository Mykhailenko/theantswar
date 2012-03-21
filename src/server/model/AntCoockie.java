package server.model;

import jade.wrapper.AgentController;

public class AntCoockie {
	private long lastRequest;
	private long lastStep;
	private String playerName;
	private int x;
	private int y;
	private AgentController agentController;
	private String antName;
	public long getLastRequest() {
		return lastRequest;
	}
	public void setLastRequest(long lastRequest) {
		this.lastRequest = lastRequest;
	}
	public long getLastStep() {
		return lastStep;
	}
	public void setLastStep(long lastStep) {
		this.lastStep = lastStep;
	}
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
