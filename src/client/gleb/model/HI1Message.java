package client.gleb.model;

import java.io.Serializable;
import java.util.List;

import shared.Coordinate;

public class HI1Message implements GMessage{
	private static final long serialVersionUID = -4528246873272207900L;
	private List<Friend> friends;
	private Coordinate whereIveSeenYou;
	private String myNameIs;
	public List<Friend> getFriends() {
		return friends;
	}
	public void setFriends(List<Friend> friends) {
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
	
}
