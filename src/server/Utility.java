package server;

import server.model.AntCoockie;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
public class Utility {
	public static List<AntCoockie> getFriendsOfAnt(Map<String,AntCoockie> map, AntCoockie ant, int distance){
		List<AntCoockie> result = new LinkedList<AntCoockie>();
		for(Iterator<Map.Entry<String, AntCoockie>> it = map.entrySet().iterator(); it.hasNext(); ){
			Map.Entry<String , AntCoockie> e = it.next();
			if(isFriend(e.getValue(), ant) && getDistance(e.getValue(), ant) <= distance){
				result.add(e.getValue());
			}
		}
		return result;
	}
	public static List<AntCoockie> getOpponentsOfAnt(Map<String,AntCoockie> map, AntCoockie ant, int distance){
		List<AntCoockie> result = new LinkedList<AntCoockie>();
		for(Iterator<Map.Entry<String, AntCoockie>> it = map.entrySet().iterator(); it.hasNext(); ){
			Map.Entry<String , AntCoockie> e = it.next();
			if(isOpponent(e.getValue(), ant) && getDistance(e.getValue(), ant) <= distance){
				result.add(e.getValue());
			}
		}
		return result;
	}
	public static int getDistance(AntCoockie a, AntCoockie b){
		return Math.abs(a.getX()-b.getX())+Math.abs(a.getY()-b.getY());
	}
	public static int getDistance(int x0, int y0, int x1, int y1){
		return Math.abs(x0-x1) + Math.abs(y0-y1); 
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
