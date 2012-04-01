package client.gleb;


import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.omg.PortableServer.THREAD_POLICY_ID;

import server.model.Locality;
import server.model.StepDirection;
import shared.Cell;
import shared.Cell.Type;
import shared.Constants;
import shared.Coordinate;
import client.BaseAnt;
import client.gleb.model.FriendsInformation;
import client.gleb.vis.GameFrame;

public class AwesomeAnt extends BaseAnt{
	private static final long serialVersionUID = 1662137747436727013L;
	private Cell [][] knownCells;
	private Coordinate currentPosition;
	private Coordinate stepsFromCreation;
	private List<FriendsInformation> friendsInformation;
	private List<Coordinate> foods;
	private Coordinate currentFoodTarget;
	private Random random;
	private GameFrame gameFrame;
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
		stepsFromCreation = new Coordinate(0,0);
		currentFoodTarget = null;
		random = new Random();
		foods = new LinkedList<Coordinate>();
		gameFrame = new GameFrame(getLocalName());
	}
	@Override
	protected void setup() {
		while(true){
			getLocalityAndUpdateKnownCells();
			makeStep();
		}
	}
	private void makeStep() {
		if(!tryToMakeStupidStep()){
			checkAndSetTarget();
			makeStepForTarget();
		}
	}
	private void checkAndSetTarget() {
		if(currentFoodTarget == null){
			currentFoodTarget = foods.get(0);
			foods.remove(0);
		}
	}
	private boolean tryToMakeStupidStep(){
		if(currentFoodTarget == null && foods.isEmpty()){
			makeRandomStep();
			return true;
		}else{
			return false;
		}
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
		System.out.println("made random step");
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
	private void makeStepForTarget() {
		StepDirection direction = getNextStep();
		makeStep(direction);
		System.out.println("made step for target " + currentFoodTarget.x + " y=" + currentFoodTarget.y);
		updateCurrentPosition(direction);
		System.out.println("currPosss x=" + currentPosition.x + " y= " +currentPosition.y);
		checkIfWeOnTarget();
	}
	private void checkIfWeOnTarget() {
		if(currentFoodTarget.distance(currentPosition) == 0){
			currentFoodTarget = null;
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
		System.out.print("currPos.x=" + currentPosition.x + " y=" + currentPosition.y + " f.x=" + currentFoodTarget.x + " y=" + currentFoodTarget.y + " ");
		if(currentPosition.x != currentFoodTarget.x){
			if(currentPosition.x < currentFoodTarget.x){
				System.out.println("right");
				return StepDirection.RIGHT;
			}else{
				System.out.println("left");
				return StepDirection.LEFT;
			}
		}
		if(currentPosition.y != currentFoodTarget.y){
			if(currentPosition.y < currentFoodTarget.y){
				System.out.println("down");
				return StepDirection.DOWN;
			}else{
				System.out.println("up");
				return StepDirection.UP;
			}
		}
		return StepDirection.STAY;
	}
	public Locality getLocalityAndUpdateKnownCells(){
		Locality locality = getLocality();
		gameFrame.paint(locality.getCells());
		updateKnownCells(locality);
		return locality;
	}
	public void updateKnownCells(Locality locality){
		for(int y = 0; y < locality.getCells().length; ++y){
			for(int x = 0; x < locality.getCells()[0].length; ++x){
				int yy = currentPosition.y + y - locality.getCenterY();
				int xx = currentPosition.x + x - locality.getCenterX();
				Cell cell = locality.getCells()[y][x];
				if(cell.getType().equals(Type.FOOD)){
					foods.add(new Coordinate(xx, yy));
					System.out.println("found food on x = " + xx + " yy=" + yy);
//					try {
//						Thread.sleep(100000000);
//					} catch (InterruptedException e) {
//					}
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
