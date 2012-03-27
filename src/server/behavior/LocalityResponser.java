package server.behavior;

import java.io.IOException;
import java.util.List;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import server.Utility;
import server.model.AntCoockie;
import server.model.Cell.Type;
import server.model.GameBag;
import server.model.Locality;
import shared.Cell;
import shared.Constants;
import shared.ResponseFromServer;

/**
 * класс который имеют всю информацию о игре. и может каждому муравью "ответить"
 * @author tas
 *
 */
public class LocalityResponser {
	private GameBag gameBag;
	public LocalityResponser(GameBag gameBag) {
		this.gameBag = gameBag;
	}
	public ACLMessage message(AntCoockie ant){
		ACLMessage message = new ACLMessage(ACLMessage.INFORM);
		message.addReceiver(new AID(ant.getAntName(), AID.ISLOCALNAME));
		Locality loc = getLocality(ant);
		ResponseFromServer rfs = new ResponseFromServer();
		rfs.setLocality(loc);
		try {
			message.setContentObject(rfs);
		} catch (IOException e) {
		}
		return message;
	}
	public Locality getLocality(AntCoockie ant){
		int top, bottom, left, right;
		int x = ant.getX();
		int y = ant.getY();
		left = x - Constants.LOCALITY_SIZE;
		left = left >= 0 ? left : 0;
		
		right = x + Constants.LOCALITY_SIZE;
		right = right < Constants.MAP_SIZE ? right : Constants.MAP_SIZE - 1;
		
		top = y - Constants.LOCALITY_SIZE;
		top = top >= 0 ? top : 0;
		
		bottom = y + Constants.LOCALITY_SIZE;
		bottom = bottom < Constants.MAP_SIZE ? bottom : Constants.MAP_SIZE - 1;
		
		System.out.println("x=" + x + ";y=" + y + "left=" + left + ";right=" + right + ";top=" + top + ";bottom=" + bottom);
		server.model.Cell [][] serverCells = new server.model.Cell[bottom - top + 1][right - left + 1];
		int i, j, k , l;
		for(i = top, j = 0; i <= bottom ; ++i, ++j){
			for(k = left, l = 0; k <= right; ++k, ++l){
				serverCells[j][l] = gameBag.getStaticMap().getCells()[i][k];
			}
		}
		List<AntCoockie> friends = Utility.getFriendsOfAnt(gameBag.getAntCoockies(), ant, Constants.LOCALITY_SIZE);
		for(AntCoockie friend :  friends){
			int lx = friend.getX() - left;
			int ly = friend.getY() - top;
			server.model.Cell nativeCell = serverCells[ly][lx];
			switch (nativeCell.getType()) {
			case HILL:
				server.model.Cell newCell = new server.model.Cell();
				newCell.setType(Type.ANT_HILL);
				newCell.setAntName(friend.getAntName());
				newCell.setPlayerName(friend.getPlayerName());
				serverCells[ly][lx] = newCell;
				//////
				/////
				break;
			case FREE:
				server.model.Cell newCell1 = new server.model.Cell();
				newCell1.setType(Type.ANT);
				newCell1.setAntName(friend.getAntName());
				newCell1.setPlayerName(friend.getPlayerName());
				serverCells[ly][lx] = newCell1;
			default:
				System.out.println("Ant Can't be on food or wall!");
				break;
			}
		}
		List<AntCoockie> opponents = Utility.getOpponentsOfAnt(gameBag.getAntCoockies(), ant, Constants.LOCALITY_SIZE);
		for(AntCoockie op : opponents){
			int lx = op.getX() - left;
			int ly = op.getY() - top;
			server.model.Cell nativeCell = serverCells[ly][lx];
			switch (nativeCell.getType()) {
			case HILL:
				server.model.Cell newCell = new server.model.Cell();
				newCell.setType(Type.ANT_HILL);
				newCell.setAntName(op.getAntName());
				newCell.setPlayerName(op.getPlayerName());
				serverCells[ly][lx] = newCell;
				//////
				/////
				break;
			case FREE:
				server.model.Cell newCell1 = new server.model.Cell();
				newCell1.setType(Type.ANT);
				newCell1.setAntName(op.getAntName());
				newCell1.setPlayerName(op.getPlayerName());
				serverCells[ly][lx] = newCell1;
			default:
				System.out.println("Ant Can't be on food or wall!");
				break;
			}
		}
		Cell [][] cells = new Cell[bottom - top + 1][right - left + 1];
		for(int r = 0; r < serverCells.length; ++r){
			for(int c = 0; c < serverCells[0].length; ++c){
				server.model.Cell sCell = serverCells[r][c];
				Cell cell = new Cell();
				cells[r][c] = cell;
				switch (sCell.getType()) {
				case ANT:
					if(sCell.getPlayerName().equals(ant.getPlayerName())){
						cell.setType(shared.Cell.Type.FRIEND_ANT);
						cell.setAntName(ant.getAntName());
						cell.setPlayerName(ant.getPlayerName());
					}else{
						cell.setType(shared.Cell.Type.UNVISIBLE);
					}
					break;
				case ANT_HILL:
					if(sCell.getPlayerName().equals(ant.getPlayerName())){			
						cell.setType(shared.Cell.Type.FRIEND_ANT_HILL);
						cell.setAntName(ant.getAntName());
						cell.setPlayerName(ant.getPlayerName());
					}else{
						cell.setType(shared.Cell.Type.ENEMY_ANT_HILL);
					}
					break;
				case FOOD:
					cell.setType(shared.Cell.Type.FOOD);
					break;
				case FREE:
					cell.setType(shared.Cell.Type.FREE);
					break;
				case HILL:
					if(sCell.getPlayerName().equals(ant.getPlayerName())){
						cell.setType(shared.Cell.Type.FRIEND_HILL);
						cell.setPlayerName(ant.getPlayerName());
					}else{
						cell.setType(shared.Cell.Type.ENEMY_HILL);
					}
					break;
				case WALL:
					cell.setType(shared.Cell.Type.WALL);
					break;
				}
			}
		}
		Locality loc = cuttOffCorners(cells, ant.getX() - left, ant.getY() - top, Constants.LOCALITY_SIZE);
		return loc;
	}
	private Locality cuttOffCorners(Cell[][] cells, int centerX, int centerY, int distance){
		for(int i = 0; i < cells.length; ++i){
			for(int j = 0; j < cells[0].length; ++j){
				if(Utility.getDistance(i, j, centerX, centerY) > distance){
					cells[i][j].setType(shared.Cell.Type.UNVISIBLE);
				}
			}
		}
		Locality locality = new Locality();
		locality.setCells(cells);
		locality.setCenterX(centerX);
		locality.setCenterY(centerY);
		return locality;
	}
}
