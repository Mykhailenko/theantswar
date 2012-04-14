package client.gleb;

import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

import java.io.IOException;
import java.util.List;

import shared.Cell;
import shared.Constants;
import shared.Cell.Type;
import shared.Coordinate;

import client.gleb.model.GMessage;
import client.gleb.model.HI1Message;
import client.gleb.model.HI2Message;
import client.gleb.model.HI3Message;

public class UtilityForFriendship {
	private static final int TIMEOUT = 50;
	private static final int N = 5;
	private String friendName;
	private List<String> friends;
	private Cell[][] knownCells;
	private String myName;
	private Coordinate coordinate;
	private AwesomeAnt agent;
	private List<String> recievedfriends;
	public UtilityForFriendship(String fiendName, List<String> friends, 
			Cell[][] knownCells, String myName, Coordinate coordinate, AwesomeAnt agent) {
		this.friendName = fiendName;
		this.friends = friends;
		this.knownCells = knownCells;
		this.coordinate = coordinate;
		this.myName = myName;
		this.agent = agent;
	}
	public void sendH1(){
		HI1Message hi1Message = new HI1Message();
		hi1Message.setMyNameIs(myName);
		hi1Message.setFriends(friends);
		hi1Message.setWhereIveSeenYou(coordinate);
		
		for(int y = 0; y < knownCells.length; ++y){
			for(int x = 0; x < knownCells[0].length; ++x){
				if(knownCells[y][x].getType() == Type.ENEMY_ANT_HILL ||
						knownCells[y][x].getType() == Type.FRIEND_HILL){
					hi1Message.getEnemyHills().add(new Coordinate(x, y));
				}else if(knownCells[y][x].getType() == Type.FREE){
					hi1Message.getFreeCells().add(new Coordinate(x, y));
				}else if(knownCells[y][x].getType() == Type.WALL){
					hi1Message.getWallCells().add(new Coordinate(x, y));
				}
			}
		}
		ACLMessage aclMessage = new ACLMessage(ACLMessage.INFORM);
		try {
			aclMessage.setContentObject(hi1Message);
		} catch (IOException e) {
			e.printStackTrace();
		}
		aclMessage.addReceiver(new AID(friendName, AID.ISLOCALNAME));
		aclMessage.setOntology("h1");
		agent.send(aclMessage);
	}
	public boolean recieveH1(){
		ACLMessage aclm = null;
		HI1Message hi1Message = null;
		for(int i = 0; i < N; ++i){
			aclm = agent.receive(MessageTemplate.MatchSender(new AID(friendName, AID.ISLOCALNAME)));
			if(aclm !=  null){
				try {
					GMessage gm = (GMessage) aclm.getContentObject();
					if(gm.getType() == client.gleb.model.GMessage.Type.HI1){
						hi1Message = (HI1Message) gm;
					}
				} catch (UnreadableException e) {
				}
				if(hi1Message != null){
					if(coordinate.x != -hi1Message.getWhereIveSeenYou().x ||
							coordinate.y != -hi1Message.getWhereIveSeenYou().y){
						hi1Message = null;
						break;
					}
					for(Coordinate c : hi1Message.getWallCells()){
						knownCells[c.y + coordinate.y][c.x - coordinate.x].setType(Type.WALL);
					}
					for(Coordinate c : hi1Message.getFreeCells()){
						Cell cell = knownCells[c.y + coordinate.y][c.x - coordinate.x];
						if(cell.getType() == Type.UNVISIBLE){
							cell.setType(Type.FREE);
						}
					}
					for(Coordinate c : hi1Message.getEnemyHills()){
						Cell cell = knownCells[c.y + coordinate.y][c.x - coordinate.x];
						switch (cell.getType()) {
						case UNVISIBLE:
							cell.setType(Type.ENEMY_HILL);
							cell.setPlayerName(Constants.oleg);
							break;
						default:
							break;
						}
					}
					recievedfriends = hi1Message.getFriends();
					break;
				}
			}
			agent.sleep(TIMEOUT);
		}
		return hi1Message != null ? true : false;
	}
	public void sendH2(){
		HI2Message hi2Message = new HI2Message();
		hi2Message.setMyNameIs(myName);
		hi2Message.setFriends(friends);
		hi2Message.setWhereIveSeenYou(coordinate);
		
		for(int y = 0; y < knownCells.length; ++y){
			for(int x = 0; x < knownCells[0].length; ++x){
				if(knownCells[y][x].getType() == Type.ENEMY_ANT_HILL ||
						knownCells[y][x].getType() == Type.FRIEND_HILL){
					hi2Message.getEnemyHills().add(new Coordinate(x, y));
				}else if(knownCells[y][x].getType() == Type.FREE){
					hi2Message.getFreeCells().add(new Coordinate(x, y));
				}else if(knownCells[y][x].getType() == Type.WALL){
					hi2Message.getWallCells().add(new Coordinate(x, y));
				}
			}
		}
		ACLMessage aclMessage = new ACLMessage(ACLMessage.INFORM);
		try {
			aclMessage.setContentObject(hi2Message);
		} catch (IOException e) {
			e.printStackTrace();
		}
		aclMessage.addReceiver(new AID(friendName, AID.ISLOCALNAME));
		aclMessage.setOntology("h2");
		agent.send(aclMessage);
	}
	public boolean recieveH2(){
		ACLMessage aclm = null;
		HI2Message hi2Message = null;
		for(int i = 0; i < N; ++i){
			aclm = agent.receive(MessageTemplate.MatchSender(new AID(friendName, AID.ISLOCALNAME)));
			try {
				GMessage gm = (GMessage) aclm.getContentObject();
				if(gm.getType() == client.gleb.model.GMessage.Type.HI2){
					hi2Message = (HI2Message) gm;
				}
			} catch (UnreadableException e) {
			}
			if(hi2Message != null){
				if(coordinate.x != -hi2Message.getWhereIveSeenYou().x ||
						coordinate.y != -hi2Message.getWhereIveSeenYou().y){
					hi2Message = null;
					break;
				}
				for(Coordinate c : hi2Message.getWallCells()){
					knownCells[c.y + coordinate.y][c.x - coordinate.x].setType(Type.WALL);
				}
				for(Coordinate c : hi2Message.getFreeCells()){
					Cell cell = knownCells[c.y + coordinate.y][c.x - coordinate.x];
					if(cell.getType() == Type.UNVISIBLE){
						cell.setType(Type.FREE);
					}
				}
				for(Coordinate c : hi2Message.getEnemyHills()){
					Cell cell = knownCells[c.y + coordinate.y][c.x - coordinate.x];
					switch (cell.getType()) {
					case UNVISIBLE:
						cell.setType(Type.ENEMY_HILL);
						cell.setPlayerName(Constants.oleg);
						break;
					default:
						break;
					}
				}
				recievedfriends = hi2Message.getFriends();
				break;
			}
			agent.sleep(TIMEOUT);
		}
		return hi2Message != null ? true : false;
	}
	public void sendH3(){
		HI3Message hi3Message = new HI3Message();
		ACLMessage aclMessage = new ACLMessage(ACLMessage.INFORM);
		aclMessage.addReceiver(new AID(friendName,AID.ISLOCALNAME));
		try {
			aclMessage.setContentObject(hi3Message);
		} catch (IOException e) {
			e.printStackTrace();
		}
		aclMessage.setOntology("h3");
		friends.addAll(recievedfriends);
		friends.add(friendName);
		agent.send(aclMessage);
	}
	public boolean recieveH3(){
		ACLMessage aclm = null;
		HI3Message hi3Message = null;
		for(int i = 0; i < N; ++i){
			aclm = agent.receive(MessageTemplate.MatchSender(new AID(friendName, AID.ISLOCALNAME)));
			try {
				GMessage gm = (GMessage) aclm.getContentObject();
				if(gm.getType() == client.gleb.model.GMessage.Type.HI1){
					hi3Message = (HI3Message) gm;
				}
			} catch (UnreadableException e) {
			}
			if(hi3Message != null){
				break;
			}
			agent.sleep(TIMEOUT);
		}
		friends.addAll(recievedfriends);
		friends.add(friendName);
		return hi3Message != null ? true : false;
		
	}
}
