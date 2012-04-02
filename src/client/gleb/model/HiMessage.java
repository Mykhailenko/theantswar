package client.gleb.model;

import java.io.Serializable;
import java.util.List;

import shared.Coordinate;

public class HiMessage implements Serializable{
	private static final long serialVersionUID = -4528246873272207900L;
	private List<FriendsInformation> friends;
	private Coordinate whereIveSeenYou;
	private String myNameIs;
	public List<FriendsInformation> getFriends() {
		return friends;
	}
	public void setFriends(List<FriendsInformation> friends) {
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
	
}
