package client.oleg;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import server.model.Locality;
import server.model.StepDirection;
import shared.Cell;
import shared.Coordinate;
import shared.Cell.Type;
import shared.Constants;
import client.BaseAnt;

public class MonsterAnt extends BaseAnt{
	Cell map[][];
	String myName;
	boolean mainAgent = true;
	List<CoordinateCell> friends;
	List<CoordinateCell> enemies;
	List<CoordinateCell> foods;
	Coordinate currentCoordinates;
	Coordinate lastCoordinates;
	Locality locality;
	LinkedList<Target> targets = new LinkedList<Target>();
	Target currentTarget = null;
	
	public MonsterAnt(){
		map = new Cell[Constants.MAP_SIZE*2+1][Constants.MAP_SIZE*2+1];
		for(int i = 0; i < Constants.MAP_SIZE*2+1; i++)
			for(int j = 0; j < Constants.MAP_SIZE*2+1; j++){
				map[i][j] = new Cell();
				map[i][j].setType(Type.UNVISIBLE);
			}
		friends = new ArrayList<CoordinateCell>();
		enemies = new ArrayList<CoordinateCell>();
		foods = new ArrayList<CoordinateCell>();
		currentCoordinates = new Coordinate(Constants.MAP_SIZE, Constants.MAP_SIZE);
		lastCoordinates = new Coordinate(0, 0);
	}
	@Override
	protected void setup(){
		myName = getLocalName();
		while(true){
			updateMap();
			printFoods();
			System.out.println("my("+currentCoordinates.getX()+";"+currentCoordinates.getY()+")");
			
			currentTarget = getCurrentTarget();
			
			if(currentTarget != null){
				System.out.println("target: (" + currentTarget.getCell().getCoordinate().getX()+";"+currentTarget.getCell().getCoordinate().getY()+")");
				printPath(currentTarget);
				
				if(currentTarget.getStepsAmount() > 0){
					while(currentTarget.getStepsAmount() > 0){
						makeCurrentStep(currentTarget.makeStep());
					}
					currentTarget = null;
				}
			}
			makeStep(getRandomeStep());			
		}
	}
	private void updateMap(){
		locality = getLocality();
		Cell cells[][] = locality.getCells();
		foods = new ArrayList<CoordinateCell>();
		for(int i = 0; i < cells.length; i++)
			for(int j = 0; j < cells[i].length; j++){
				int y = currentCoordinates.getY()+i-locality.getCenterY();
				int x = currentCoordinates.getX()+j-locality.getCenterX();
				//System.out.println("("+x+";"+y+")"+"i="+i+" j="+j);
				map[y][x] = cells[i][j];
				CoordinateCell c = new CoordinateCell(cells[i][j],new Coordinate(x,y));
				if(cells[i][j].getType() == Type.FRIEND_ANT){
					if(!friends.contains(c))
						friends.add(c);
				}
				if(cells[i][j].getType() == Type.FOOD){
					if(!foods.contains(c))
						foods.add(c);
				}
				if(cells[i][j].getType() == Type.ENEMY_ANT){
					if(!enemies.contains(c))
						enemies.add(c);
				}
			}
		
	}
	private String getMainAnt(){
		int min = Integer.parseInt(myName.substring(4));
		int my  = min;
		for(CoordinateCell cell : friends){
			
		}
		return "OLEG"+min;
	}
	
	private Target getCurrentTarget(){
		targets.addLast(getFoodPath());
		if(!targets.isEmpty()){
			return targets.pollFirst();
		}
		return null;
	}
	private Target getFoodPath(){
		if(foods.size() != 0){
			//System.out.println(foods.size());
			LinkedList<StepDirection> targetPath = new LinkedList<StepDirection>();
			CoordinateCell min = foods.get(0);
			int stepCount = 0;
			for(CoordinateCell cell : foods){
				if(min.getCoordinate().distance(currentCoordinates) > cell.getCoordinate().distance(currentCoordinates))
					min = cell;
			}
			if(min.getCoordinate().getX() > currentCoordinates.getX())
				for(int i = currentCoordinates.getX(); i < min.getCoordinate().getX(); i++){
					targetPath.offerLast(StepDirection.RIGHT);
					stepCount++;
				}
			else
				for(int i = currentCoordinates.getX(); i > min.getCoordinate().getX(); i--){
					targetPath.offerLast(StepDirection.LEFT);
					stepCount++;
				}
			if(min.getCoordinate().getY() > currentCoordinates.getY())
				for(int i = currentCoordinates.getY(); i < min.getCoordinate().getY(); i++){
					targetPath.offerLast(StepDirection.DOWN);
					stepCount++;
				}
			else
				for(int i = currentCoordinates.getY(); i > min.getCoordinate().getY(); i--){
					targetPath.offerLast(StepDirection.UP);
					stepCount++;
				}
			return new Target(targetPath,stepCount,min.getCell().getType(),min);
		}
		return null;
	}
	private StepDirection getRandomeStep(){
		Random random = new Random();
		int r = random.nextInt(5);
		if(r == 0)
			return StepDirection.DOWN;
		if(r == 1)
			return StepDirection.LEFT;
		if(r == 2)
			return StepDirection.RIGHT;
		if(r == 3)
			return StepDirection.UP;
		return StepDirection.STAY;
	}
	
	private void makeCurrentStep(StepDirection step){
		switch(step){
		case UP:
			currentCoordinates.setY(currentCoordinates.getY()-1);
			break;
		case DOWN:
			currentCoordinates.setY(currentCoordinates.getY()+1);
			break;
		case LEFT:
			currentCoordinates.setX(currentCoordinates.getX()-1);
			break;
		case RIGHT:
			currentCoordinates.setX(currentCoordinates.getX()+1);
			break;
		case STAY:
			break;
		}
		makeStep(step);
	}
	private void printFoods(){
		for(CoordinateCell cell : foods)
			System.out.println("("+cell.getCoordinate().getX()+";"+cell.getCoordinate().getY()+")");
	}
	private void printPath(Target t){
		for(StepDirection step : t.getTarget())
			System.out.println(step);
	}
}
