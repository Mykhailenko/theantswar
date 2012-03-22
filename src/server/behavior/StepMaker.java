package server.behavior;

import server.model.AntCoockie;
import server.model.Cell;
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
				break;
			case WALL:
			default:
				mainBehavior.killAnt(ant.getAntName());
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
