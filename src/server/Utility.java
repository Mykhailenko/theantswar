package server;

import server.model.AntCoockie;
import server.model.GameBag;
import shared.Cell;
import shared.Constants;

import java.util.LinkedList;
import java.util.List;
public class Utility {
	public static List<AntCoockie> getOpponentsOfAnt(List<AntCoockie> list, AntCoockie ant, int distance){
		List<AntCoockie> result = new LinkedList<AntCoockie>();
		for(AntCoockie op : list){
			if(isOpponent(op, ant) && getDistance(op, ant) <= distance){
				result.add(op);
			}
		}
		return result;
	}
	public static int getDistance(AntCoockie a, AntCoockie b){
		return Math.abs(a.getX()-b.getX())+Math.abs(a.getY()-b.getY());
	}
	public static boolean isOpponent(AntCoockie a, AntCoockie b){
		return !isFriend(a, b);
	}
	public static boolean isFriend(AntCoockie a, AntCoockie b){
		if(a.getPlayerName().equals(b.getPlayerName())){
			return true;
		}else{
			return false;
		}
	}
	public static Cell [][] getLocality(GameBag gameBag, AntCoockie ant){
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
		return null;
	}
}
