package client.gleb.model;

import java.util.ArrayList;
import java.util.List;

import shared.Coordinate;

public class HI1Message implements GMessage{
	private static final long serialVersionUID = -4528246873272207900L;
	private List<String> friends;
	private List<Coordinate> freeCells = new ArrayList<Coordinate>();
	private List<Coordinate> wallCells = new ArrayList<Coordinate>();
	private List<Coordinate> enemyHills = new ArrayList<Coordinate>();
	private Coordinate whereIveSeenYou;
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
		return Type.HI1;
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
