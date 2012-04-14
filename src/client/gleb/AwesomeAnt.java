package client.gleb;


import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import server.model.Locality;
import server.model.StepDirection;
import shared.Cell;
import shared.Cell.Type;
import shared.Constants;
import shared.Coordinate;
import client.BaseAnt;
import client.gleb.model.GMessage;
import client.gleb.model.Order;
import client.gleb.vis.GameFrame;

public class AwesomeAnt extends BaseAnt{
	private static final long serialVersionUID = 1662137747436727013L;
	private Cell [][] knownCells;
	private Coordinate currentPosition;
	private Coordinate stepsFromCreation;
	private List<String> friends;
	private List<Coordinate> foods;
	private Random random;
	private GameFrame gameFrame;
	private String bossName;
	private String myName;
	private int myN;
	private Order order;
	private Locality locality;
	private Coordinate target;
	public AwesomeAnt() {
		knownCells = new Cell[Constants.MAP_SIZE*2 + 1][Constants.MAP_SIZE*2 + 1];
		for(int r = 0; r < Constants.MAP_SIZE*2+1; ++r){
			for(int c = 0; c < Constants.MAP_SIZE*2+1; ++c){
				Cell newCell = new Cell();
				newCell.setType(Type.UNVISIBLE);
				knownCells[r][c] = newCell;
			}
		}
		currentPosition = new Coordinate(Constants.MAP_SIZE, Constants.MAP_SIZE);
		random = new Random();
		foods = new LinkedList<Coordinate>();
		gameFrame = new GameFrame(getLocalName());
		friends = new ArrayList<String>();
		foods = new ArrayList<Coordinate>();
		target = null;
	}
	@Override
	protected void setup(){
		myName = getLocalName();
		bossName = myName;
		myN = Integer.parseInt(myName.substring(Constants.gleb.length()));
		while(true){
			getLocalityAndUpdateKnownCells();
			makefriendship();
			if(newboss()){
				if(amiboss()){
					thinking();
				}else{
					waitingfororders();
				}
			}
			MAKESTEPFORDREAM();
//			waitingformessages();
		}
	}
	
	private void MAKESTEPFORDREAM() {
		if(order != null){
			switch (order.getType()) {
			case ORDER_ATTACK:
				
				break;
			case ORDER_PROTECTE:
				
				break;
			case ORDER_EAT:
				eat();
				break;
			default:
				break;
			}
		}else{
			eat();
		}
	}
	private void eat(){
		if(target == null){
			target = getNearestFood();
			if(target != null){
				makeSmartStep(getNextStep());
			}else{
				makeRandomStep();
			}
		}else{
			makeSmartStep(getNextStep());
		}
	}
	public void makeSmartStep(StepDirection st){
		makeStep(st);
		updateCurrentPosition(st);
		checkIfWeOnTarget();
	}
	private void makefriendship() {
		for(int y = 0; y < locality.getCells().length; ++y){
			for(int x = 0; x < locality.getCells()[0].length; ++x){
				Cell cell = locality.getCells()[y][x];
				if(cell.getType() == Type.FRIEND_ANT || 
						cell.getType() == Type.FRIEND_ANT_HILL){
					String friendName = cell.getAntName();
					boolean newFriend = true;
					for(String friend : friends){
						if(friend.equals(friendName)){
							newFriend = false;
							break;
						}
					}
					if(newFriend){
						makeFriendship(cell, new Coordinate(x, y));
					}
				}
				
			}
		}
	}
	private void makeFriendship(Cell cell, Coordinate where) {
		String friendName = cell.getAntName();
		int friendN = Integer.parseInt(friendName.substring(Constants.gleb.length()));
		UtilityForFriendship ut = new UtilityForFriendship(friendName, friends, 
				knownCells, myName, where, this);
		if(myN < friendN){
			ut.sendH1();
			if(ut.recieveH2()){
				ut.sendH3();
			}
		}else{
			if(ut.recieveH1()){
				ut.sendH2();
				if(ut.recieveH3()){
					
				}
			}
		}
	}
	private void thinking() {
		int soldierSize = friends.size();
		int knownMyHills = 1;
		int knownOpHills = 0;
		List<String> fr = new ArrayList<String>();
		fr.addAll(friends);
		if(soldierSize < 5){
			protecteHill(1, fr);
			getFood(4, fr);
		}else if(5 <= soldierSize && soldierSize < 10){
			int forProtecte = knownMyHills * 1;
			int attack = soldierSize - forProtecte;
			attack = attack > 3 ? 3 : attack;
			protecteHill(forProtecte, fr);
			attack(attack, fr);
			int forFood = soldierSize - forProtecte - attack;
			getFood(forFood, fr);
		}else{
			int forProtecte = knownMyHills * 2;
			protecteHill(forProtecte, fr);
			int remain = soldierSize - forProtecte;
			int forFood = remain * 4 / 10;
			getFood(forFood, fr);
			int attack = remain - forFood;
			attack(attack, fr);
		}
	}
	private void attack(int attack, List<String> fr) {
		for(int i = 0; i < attack; ++i){
			String friend = fr.get(0);
			fr.remove(0);
			sendAttack(friend);
		}
	}
	private void sendAttack(String friend) {
		ACLMessage message = new ACLMessage(ACLMessage.INFORM);
		message.addReceiver(new AID(friend, AID.ISLOCALNAME));
		Order order = new Order();
		order.setType(client.gleb.model.GMessage.Type.ORDER_ATTACK);
		order.setMarshal(myName);
		send(message);
	}
	private void getFood(int forFood, List<String> fr) {
		for(int i = 0; i < forFood; ++i){
			String friend = fr.get(0);
			fr.remove(0);
			sendGetFood(friend);
		}
	}
	private void sendGetFood(String friend) {
		ACLMessage message = new ACLMessage(ACLMessage.INFORM);
		message.addReceiver(new AID(friend, AID.ISLOCALNAME));
		Order order = new Order();
		order.setType(client.gleb.model.GMessage.Type.ORDER_EAT);
		order.setMarshal(myName);
		send(message);
	}
	private void protecteHill(int forProtecte, List<String> fr) {
		for(int i = 0; i < forProtecte; ++i){
			String friend = fr.get(0);
			fr.remove(0);
			sendProtecteHill(friend);
		}
	}
	private void sendProtecteHill(String friend) {
		ACLMessage message = new ACLMessage(ACLMessage.INFORM);
		message.addReceiver(new AID(friend, AID.ISLOCALNAME));
		Order order = new Order();
		order.setType(client.gleb.model.GMessage.Type.ORDER_PROTECTE);
		order.setMarshal(myName);
		send(message);
	}
	private void waitingfororders() {
		long startWaiting = System.currentTimeMillis();
		while(startWaiting + 1000 > System.currentTimeMillis()){
			sleep(50);
			ACLMessage message = receive();
			if(message != null){
				// TODO process order
				GMessage gm = null;
				try {
					gm = (GMessage) message.getContentObject();
					if(gm.getType() == client.gleb.model.GMessage.Type.ORDER_ATTACK || 
							gm.getType() == client.gleb.model.GMessage.Type.ORDER_EAT || 
							gm.getType() == client.gleb.model.GMessage.Type.ORDER_PROTECTE ){
						order = (Order) gm;
					}
				} catch (UnreadableException e) {
					e.printStackTrace();
				}
				
			}
		}
	}
	private void waitingformessages() {
		while(getLastRequest() < System.currentTimeMillis() + INTERVAL){
			sleep(50);
			ACLMessage message = receive();
			if(message != null){
				// TODO process msg
			}
		}
	}
	private boolean amiboss() {
		return myName.equals(bossName);
	}
	private boolean newboss() {
		String mostValuable = myName;
		for(String friend : friends){
			if(AwesomeAnt.glavneeThan(friend, mostValuable)){
				mostValuable = friend;
			}
		}
		return !bossName.equals(mostValuable);
	}
	private static boolean glavneeThan(String name1, String name2){
		int d1 = Integer.parseInt(name1.substring(4));
		int d2 = Integer.parseInt(name2.substring(4));
		return d1 < d2;
	}
	private void makeRandomStep() {
		StepDirection direction = StepDirection.STAY;
		while(direction.equals(StepDirection.STAY)){
			direction = getRandomDirection();
			Cell cell = getCellOnWhichWeWantStep(direction);
			if(notAvailableCell(cell)){
				direction = StepDirection.STAY;
			}
		}
		makeStep(direction);
	}
	private boolean notAvailableCell(Cell cell) {
		if(cell.getType().equals(Type.WALL)){
			return true;
		}else{
			return false;
		}
	}
	private Cell getCellOnWhichWeWantStep(StepDirection direction) {
		switch (direction) {
		case DOWN:
			return knownCells[currentPosition.y+1][currentPosition.x];
		case LEFT:
			return knownCells[currentPosition.y][currentPosition.x-1];
		case RIGHT:
			return knownCells[currentPosition.y][currentPosition.x+1];
		case UP:
			return knownCells[currentPosition.y-1][currentPosition.x];
		}
		return null;
	}
	private StepDirection getRandomDirection(){
		StepDirection direction = StepDirection.STAY;
		int r = random.nextInt(4);
		if(r == 0){
			direction = StepDirection.DOWN;
		}else if(r == 1){
			direction = StepDirection.LEFT;
		}else if(r == 2){
			direction = StepDirection.RIGHT;
		}else if(r == 3){
			direction = StepDirection.UP;
		}
		return direction;
	}
	private void checkIfWeOnTarget() {
		if(target.distance(currentPosition) == 0){
			target = null;
		}
	}
	private void updateCurrentPosition(StepDirection direction) {
		switch (direction) {
		case DOWN:
			currentPosition.y++;
			break;
		case LEFT:
			currentPosition.x--;
			break;
		case RIGHT:
			currentPosition.x++;
			break;
		case UP:
			currentPosition.y--;
			break;
		}
	}
	private StepDirection getNextStep(){
		if(currentPosition.x != target.x){
			if(currentPosition.x < target.x){
				return StepDirection.RIGHT;
			}else{
				return StepDirection.LEFT;
			}
		}
		if(currentPosition.y != target.y){
			if(currentPosition.y < target.y){
				return StepDirection.DOWN;
			}else{
				return StepDirection.UP;
			}
		}
		return StepDirection.STAY;
	}
	public void getLocalityAndUpdateKnownCells(){
		locality = getLocality();
		gameFrame.paint(locality.getCells());
		updateKnownCells(locality);
	}
	public void updateKnownCells(Locality locality){
		for(int y = 0; y < locality.getCells().length; ++y){
			for(int x = 0; x < locality.getCells()[0].length; ++x){
				int yy = currentPosition.y + y - locality.getCenterY();
				int xx = currentPosition.x + x - locality.getCenterX();
				Cell cell = locality.getCells()[y][x];
				if(cell.getType().equals(Type.FOOD)){
					foods.add(new Coordinate(xx, yy));
				}
				knownCells[yy][xx] = cell;
			}
		}
	}
	public List<StepDirection> createPathTo(Coordinate target){
		return null;
	}
	
	public Coordinate getNearestFood(){
		Coordinate result = null;
		for(Coordinate coordinate : foods){
			if(result == null){
				result = coordinate;
			}else if(result.distance(currentPosition) > coordinate.distance(currentPosition)){
				result = coordinate;
			}
		}
		return result;
	}
}
