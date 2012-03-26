package server.behavior;

import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

import java.util.List;

import server.Utility;
import server.model.AntCoockie;
import server.model.Cell;
import server.model.HillCoockie;
import server.model.Cell.Type;
import server.model.GameBag;
import server.model.StepDirection;
import shared.Constants;

public class StepMaker {
	private GameBag gameBag;
	private MainBehavior mainBehavior;
	public StepMaker(GameBag gameBag, MainBehavior mainBehavior) {
		this.gameBag = gameBag;
		this.mainBehavior = mainBehavior;
	}
	/**
	 * Данный метод вызывается для того чтобы походить муравьем ant в направлении direction  
	 * @param ant
	 * @param direction
	 * @return
	 */
	public void makeStep(AntCoockie ant, StepDirection direction){
		int x = ant.getX();
		int y = ant.getY();
		switch (direction) {
		case DOWN:
			++y; 
			break;
		case RIGHT:
			++x;
			break;
		case UP:
			--y;
			break;
		case LEFT:
			--x;
			break;
		case STAY:
		default:
			break;
		}
		if(badCoordinates(x, y)){
			mainBehavior.killAnt(ant.getAntName());
		}else{
			Cell cell = gameBag.getStaticMap().getCells()[y][x];
			switch (cell.getType()) {
			case FREE:
				ant.setX(x);
				ant.setY(y);
				break;
			case FOOD:
				mainBehavior.createAntForPlayer(ant.getPlayerName());
				cell.setType(Type.FREE);
				ant.setX(x);
				ant.setY(y);
			case ANT:
			case ANT_HILL:
				break;
			case HILL:
				if(!cell.getPlayerName().equals(ant.getPlayerName())){
					cell.setType(Type.FREE);
					killHillOn(x, y);
					ant.setX(x);
					ant.setY(y);
				}
				break;
			case WALL:
			default:
				System.out.println("kill ant by the wall");
				mainBehavior.killAnt(ant.getAntName());
				break;
			}
			List<AntCoockie> opponents = Utility.getOpponentsOfAnt(gameBag.getAntCoockies(), ant, Constants.ATTACK_SIZE);
			if(opponents.size() == 1 ||
					opponents.size() == 2){
				// kill each other
				mainBehavior.killAnt(ant.getAntName());
				mainBehavior.killAnt(opponents.get(0).getAntName());
			}else if(opponents.size() > 2){
				mainBehavior.killAnt(ant.getAntName());
			}
		}
	}
	private void killHillOn(int x, int y){
		for(HillCoockie hill : gameBag.getHillCoockies()){
			if(hill.getX() == x && hill.getY() == y){
				if(hill.getPlayerName().equals(Constants.gleb)){
					gameBag.setCountOfGlebHills(gameBag.getCountOfGlebHills() - 1);
				}else{
					gameBag.setCountOfOlegHills(gameBag.getCountOfOlegHills() - 1);
				}
				gameBag.getHillCoockies().remove(hill);
				break;
			}
		}
	}
	
	private boolean badCoordinates(int x, int y){
		if(0 <= x && x <= Constants.MAP_SIZE &&
				0 <= y && y <= Constants.MAP_SIZE){
			return false;
		}else{
			return true;
		}
	}
}