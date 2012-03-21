package server;

import server.model.AntCoockie;

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
}
