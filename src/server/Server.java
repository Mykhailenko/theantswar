package server;

import java.util.ArrayList;
import java.util.HashMap;

import server.behavior.InitBehavior;
import server.behavior.MainBehavior;
import server.model.AntCoockie;
import server.model.Cell;
import server.model.Cell.Type;
import server.model.GameBag;
import server.model.HillCoockie;
import server.model.Map;
import server.visual.GFrame;
import server.visual.GameFrame;
import shared.Constants;
import jade.core.Agent;

public class Server extends Agent{
	private static final long serialVersionUID = 7636300336377545451L;
	/**
	 * информация по текущей игре
	 */
	private static GameBag gameBag;
	private static GFrame gFrame ;
	public static void main(String [] rgs){
		gameBag = new GameBag();
		gameBag.setAntCoockies(new HashMap<String, AntCoockie>());
		gameBag.setHillCoockies(new ArrayList<HillCoockie>());
		gameBag.setStaticMap(new Map());
		initHills();
		putHillsOnMap();
		gFrame = new GameFrame();
		gFrame.paint(gameBag.getStaticMap());
		String []args = {"-gui", "-local-host","127.0.0.1", "Server:"+Server.class.getName()};
		jade.Boot.main(args);
	}
	private static void initHills(){
		HillCoockie hi0 = new HillCoockie();
		hi0.setPlayerName(Constants.oleg);
		hi0.setX(50);
		hi0.setY(40);
		gameBag.getHillCoockies().add(hi0);
		
		HillCoockie hi01 = new HillCoockie();
		hi01.setPlayerName(Constants.oleg);
		hi01.setX(35);
		hi01.setY(38);
		gameBag.getHillCoockies().add(hi01);
//		
		HillCoockie hi1 = new HillCoockie();
		hi1.setPlayerName(Constants.gleb);
		hi1.setX(40);
		hi1.setY(40);
		gameBag.getHillCoockies().add(hi1);
		
		HillCoockie hi2 = new HillCoockie();
		hi2.setPlayerName(Constants.gleb);
		hi2.setX(43);
		hi2.setY(43);
		gameBag.getHillCoockies().add(hi2);
	}
	/**
	 * До вызова этого метода статичиская карта содержит только пустые ячейки и стены
	 * 
	 */
	private static void putHillsOnMap(){
		for(HillCoockie hill : gameBag.getHillCoockies()){
			Cell cell = new Cell();
			cell.setType(Type.HILL);
			cell.setPlayerName(hill.getPlayerName());
			if(hill.getPlayerName().equals(Constants.gleb)){
				gameBag.setCountOfGlebHills(gameBag.getCountOfGlebHills() + 1);
			}else{
				gameBag.setCountOfOlegHills(gameBag.getCountOfOlegHills() + 1);
			}
			gameBag.getStaticMap().getCells()[hill.getY()][hill.getX()] = cell;
		}
	}
	@Override
	protected void setup() {
		MainBehavior mainBehavior = new MainBehavior(this, gameBag, gFrame);
		/**
		 * инициализация игры, т.е. создание в клетках где есть муравейники по одному муравью
		 */
		addBehaviour(new InitBehavior(mainBehavior, gameBag));
		
		/**
		 * вечный цикл приема сообщений
		 * каждые 5 ходов разбрасывается еда
		 * каждые 5 ходов обновляется гуи
		 */
		
		addBehaviour(mainBehavior);
	}
}

