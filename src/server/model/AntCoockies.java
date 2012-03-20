package server.model;

public class AntCoockies {
	private long lastRequest;
	private long lastStep;
	private String playerName;
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
}
