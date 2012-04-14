package client.gleb.model;

import java.util.List;

import shared.Coordinate;

public class HI2Message implements GMessage{
	private static final long serialVersionUID = 2770753792332743430L;
	private List<String> friends;
	private Coordinate whereIveSeenYou;
	private List<Coordinate> freeCells;
	private List<Coordinate> wallCells;
	private List<Coordinate> enemyHills;
	private String myNameIs;
	public List<String> getFriends() {
		return friends;
	}
	public void setFriends(List<String> friends) {
		this.friends = friends;
	}
	public Coordinate getWhereIveSeenYou() {
		return whereIveSeenYou;
	}
	public void setWhereIveSeenYou(Coordinate whereIveSeenYou) {
		this.whereIveSeenYou = whereIveSeenYou;
	}
	public String getMyNameIs() {
		return myNameIs;
	}
	public void setMyNameIs(String myNameIs) {
		this.myNameIs = myNameIs;
	}
	@Override
	public Type getType() {
		return Type.HI2;
	}
	public List<Coordinate> getFreeCells() {
		return freeCells;
	}
	public void setFreeCells(List<Coordinate> freeCells) {
		this.freeCells = freeCells;
	}
	public List<Coordinate> getWallCells() {
		return wallCells;
	}
	public void setWallCells(List<Coordinate> wallCells) {
		this.wallCells = wallCells;
	}
	public List<Coordinate> getEnemyHills() {
		return enemyHills;
	}
	public void setEnemyHills(List<Coordinate> enemyHills) {
		this.enemyHills = enemyHills;
	}
	
}
