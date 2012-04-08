package client.gleb;

import jade.core.AID;
import jade.lang.acl.ACLMessage;

import java.util.ArrayList;
import java.util.List;

import server.model.Locality;
import shared.Cell;
import shared.Constants;
import shared.Coordinate;
import shared.Cell.Type;
import client.BaseAnt;
import client.gleb.model.Friend;
import client.gleb.model.HI1Message;

public class SmartAnt extends BaseAnt {
	private static final long serialVersionUID = 1662137747436727013L;
	private Cell [][] knownCells;
	private Coordinate currentPosition;
	private List<Friend> friends;
	public	SmartAnt() {
		knownCells = new Cell[Constants.MAP_SIZE*2 + 1][Constants.MAP_SIZE*2 + 1];
		for(int r = 0; r < Constants.MAP_SIZE*2+1; ++r){
			for(int c = 0; c < Constants.MAP_SIZE*2+1; ++c){
				Cell newCell = new Cell();
				newCell.setType(Type.UNVISIBLE);
				knownCells[r][c] = newCell;
			}
		}
		currentPosition = new Coordinate(Constants.MAP_SIZE, Constants.MAP_SIZE);
		friends = new ArrayList<Friend>();
	}
	@Override
	protected void setup() {
		while(true){
			getLocalityAndUpdateKnownCells();
		}
	}
	public Locality getLocalityAndUpdateKnownCells(){
		Locality locality = getLocality();
		updateKnownCells(locality);
		return locality;
	}
	public void updateKnownCells(Locality locality){
		for(int y = 0; y < locality.getCells().length; ++y){
			for(int x = 0; x < locality.getCells()[0].length; ++x){
				int yy = currentPosition.y + y - locality.getCenterY();
				int xx = currentPosition.x + x - locality.getCenterX();
				Cell cell = locality.getCells()[y][x];
				if(cell.getType() == Type.FRIEND_ANT){
					checkAndMaybeMakeFriendship(cell, new Coordinate(x, y));
				}
				knownCells[yy][xx] = cell;
			}
		}
	}
	private void checkAndMaybeMakeFriendship(Cell cell, Coordinate whereIsYou) {
		if(isNewFreinds(cell.getAntName())){
			makeFriendship(cell.getAntName(),whereIsYou);
		}
	}
	
	private boolean isNewFreinds(String antName) {
		for(Friend fr : friends){
			if(fr.getAntName().equals(antName)){
				return false;
			}
		}
		return true;
	}
	private void makeFriendship(String antName,Coordinate whereIsYou) {
		sendHiToNewFriend(antName, whereIsYou);
		Friend friend = new Friend();
		friend.setAntName(antName);
		friend.setCurrX(currentPosition.x);
		friend.setCurrY(currentPosition.y);
		friends.add(friend);
		
	}
	private void sendHiToNewFriend(String antName,Coordinate whereIsYou) {
		ACLMessage message = new ACLMessage(ACLMessage.INFORM);
		message.addReceiver(new AID(antName,AID.ISLOCALNAME));
		HI1Message hiMessage = new HI1Message();
		hiMessage.setMyNameIs(getLocalName());
		hiMessage.setFriends(friends);
		hiMessage.setWhereIveSeenYou(whereIsYou);
		send(message);
	}
}
