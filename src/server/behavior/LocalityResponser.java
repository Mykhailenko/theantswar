package server.behavior;

import java.io.IOException;
import java.util.List;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import server.Utility;
import server.model.AntCoockie;
import server.model.GameBag;
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
		Cell [][] cells = getLocality(ant);
		ResponseFromServer rfs = new ResponseFromServer();
		rfs.setCells(cells);
		try {
			message.setContentObject(rfs);
		} catch (IOException e) {
		}
		return message;
	}
	public Cell [][] getLocality(AntCoockie ant){
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
		server.model.Cell [][] serverCells = new server.model.Cell[bottom - top + 1][right - left + 1];
		int i, j, k , l;
		for(i = top, j = 0; i <= bottom ; ++i, ++j){
			for(k = left, l = 0; k <= right; ++k, ++l){
				serverCells[l][k] = gameBag.getStaticMap().getCells()[j][i];
			}
		}
		List<AntCoockie> friends = Utility.getFriendsOfAnt(gameBag.getAntCoockies(), ant, Constants.LOCALITY_SIZE);
		for(AntCoockie friend :  friends){
//			friend.getX()
		}
		return null;
	}
}
