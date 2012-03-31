package server.behavior;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import server.model.AntCoockie;
import server.model.Cell;
import server.model.HillCoockie;
import server.model.Cell.Type;
import server.model.GameBag;
import server.model.Map;
import server.model.Coordinate;
import server.visual.GFrame;
import shared.Constants;
import shared.MessageToServer;
import shared.StepToServer;
import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

public class MainBehavior extends SimpleBehaviour {
	private static final long serialVersionUID = 538601823417067372L;
	private Agent agent;
	private char repaintCounter;
	private char throwFoodCounter;
	private GameBag gameBag;
	private GFrame gFrame;
	private LocalityResponser localityResponser;
	private StepMaker stepMaker;
	public MainBehavior(Agent agent, GameBag gameBag, GFrame gFrame) {
		this.repaintCounter = 5;
		this.throwFoodCounter = 5;
		this.agent = agent;
		this.gameBag = gameBag;
		this.gFrame = gFrame;
		this.localityResponser = new LocalityResponser(gameBag);
		this.stepMaker = new StepMaker(gameBag, this);
	}
	@Override
	public void action() {
		ACLMessage message = agent.blockingReceive();
		if(message != null){
			System.out.println("Recieve!");
			String antName = getAntName(message);
			AntCoockie coockie = gameBag.getAntCoockies().get(antName);
			if(coockie!=null){// если мурашка с таким именем существует
				System.out.println("Exist!");
				MessageToServer mts = null;
				try {
					mts = (MessageToServer) message.getContentObject();
					// определим-ка какого типа к нам сообщение
					if(mts.isRequest()){// это запрос на получение окрестности. на него ещё и отвечать надо
						ACLMessage resp = localityResponser.message(coockie);
						agent.send(resp);
					}else if(mts.isStep()){// собственно ход
						StepToServer sts = (StepToServer) mts;
						stepMaker.makeStep(coockie, sts.getStepDirection());
					}
					checkAndMaybePaintMap();
					checkAndMaybeThrowFood();
					createAnts();
					System.out.println("ga: " + gameBag.getCountOfGlebAnts() + "; gh: " + gameBag.getCountOfGlebHills() + "; gu: " + gameBag.getUnbornGlebsAnt() +  
							"; oa: " + gameBag.getCountOfOlegAnts() + "; oh: " + gameBag.getCountOfOlegHills() + "; ou: " + gameBag.getUnbornOlegsAnt());
					
				} catch (UnreadableException e) {		}
			}
		}
	}
	/**
	 * первое. существует счетчик содержащий значение того сколоко ещё должно быть создано муравьев. 
	 * счетчик существеует потому что на каждом из муравейников колонии может стоят муравей колонии. и 
	 * тогда получается что муравью колонии негде родиться.
	 */
	public void createAnts(){
		if(gameBag.getUnbornGlebsAnt() > 0){
			List<HillCoockie> freeHills = freeHillsForPlayer(Constants.gleb);
			while(gameBag.getUnbornGlebsAnt() > 0 &&
					freeHills.size() > 0){
				HillCoockie hill = freeHills.get(0);
				freeHills.remove(0);
				gameBag.setUnbornGlebsAnt(gameBag.getUnbornGlebsAnt() - 1);
				createAntForGlebOnHill(hill);
			}
		}
		if(gameBag.getUnbornOlegsAnt() > 0){
			List<HillCoockie> freeHills = freeHillsForPlayer(Constants.oleg);
			while(gameBag.getUnbornOlegsAnt() > 0 &&
					freeHills.size() > 0){
				HillCoockie hill = freeHills.get(0);
				freeHills.remove(0);
				gameBag.setUnbornOlegsAnt(gameBag.getUnbornOlegsAnt() - 1);
				createAntForOlegOnHill(hill);
			}
		}
	}
	private void createAntForGlebOnHill(HillCoockie hill) {
		AgentController ac = null;
		String antName = Constants.gleb + gameBag.getAndIncGleb();
		try {
			ac = agent.getContainerController().createNewAgent(antName, Constants.glebAgentClassName, null);
		} catch (StaleProxyException e) {
		}
		AntCoockie antCoockies = new AntCoockie();
		antCoockies.setAntName(antName);
		antCoockies.setAgentController(ac);
		antCoockies.setPlayerName(Constants.gleb);
		antCoockies.setX(hill.getX());
		antCoockies.setY(hill.getY());
		gameBag.getAntCoockies().put(antCoockies.getAntName(), antCoockies);
		try {
			ac.start();
		} catch (StaleProxyException e) {
			e.printStackTrace();
		}
		gameBag.setCountOfGlebAnts(gameBag.getCountOfGlebAnts() + 1);
	}
	private void createAntForOlegOnHill(HillCoockie hill) {
		AgentController ac = null;
		String antName = Constants.oleg + gameBag.getAndIncOleg();
		try {
			ac = agent.getContainerController().createNewAgent(antName, Constants.olegAgentClassName, null);
		} catch (StaleProxyException e) {
		}
		AntCoockie antCoockies = new AntCoockie();
		antCoockies.setAntName(antName);
		antCoockies.setAgentController(ac);
		antCoockies.setPlayerName(Constants.oleg);
		antCoockies.setX(hill.getX());
		antCoockies.setY(hill.getY());
		gameBag.getAntCoockies().put(antCoockies.getAntName(), antCoockies);
		try {
			ac.start();
		} catch (StaleProxyException e) {
			e.printStackTrace();
		}
		gameBag.setCountOfOlegAnts(gameBag.getCountOfOlegAnts() + 1);
	}
	private List<HillCoockie> freeHillsForPlayer(String playerName){
		List<HillCoockie> result = new ArrayList<HillCoockie>();
		for(HillCoockie hill : gameBag.getHillCoockies()){
			if(hill.getPlayerName().equals(playerName) && noAntStayOnHill(hill)){
				result.add(hill);
			}
		}
		return result;
	}
	private boolean noAntStayOnHill(HillCoockie hill){
		return noAntStayOnCoordinates(hill.getX(), hill.getY());
	}
	private boolean noAntStayOnCoordinates(int x, int y){
		for(Iterator<java.util.Map.Entry<String, AntCoockie>> it = gameBag.getAntCoockies().entrySet().iterator(); it.hasNext() ;){
			java.util.Map.Entry<String, AntCoockie> entry = it.next();
			AntCoockie ant = entry.getValue();
			if(x == ant.getX() && 
					y == ant.getY()){
				return false;
			}
		}
		return true;
	}
	@Override
	public boolean done() {
		if(somebodyWin()){
			System.out.println("somebody win");
			System.out.println("ga: " + gameBag.getCountOfGlebAnts() + "; gh: " + gameBag.getCountOfGlebHills() + "; gu: " + gameBag.getUnbornGlebsAnt() +  
					"; oa: " + gameBag.getCountOfOlegAnts() + "; oh: " + gameBag.getCountOfOlegHills() + "; ou: " + gameBag.getUnbornOlegsAnt());
			return true;
		}
		return false;
	}
	private boolean somebodyWin() {
		boolean glebLost = glebLost();
		boolean olegLost = olegLost();
		if(glebLost && olegLost){
			congradulateWithDraw();
			return true;
		}else if(glebLost){
			congradulateOleg();
			return true;
		}else if(olegLost){
			congradulateGleb();
			return true;
		}else{
			return false;
		}
	}
	private void congradulateGleb() {
		System.out.println("gleb win");
	}
	private void congradulateOleg() {
		System.out.println("oleg win");
	}
	private void congradulateWithDraw() {
		System.out.println("it's a draw");
	}
	private boolean glebLost(){
		if(gameBag.getCountOfGlebAnts() == 0){
			if(gameBag.getCountOfGlebHills() > 0 && 
					gameBag.getUnbornGlebsAnt() > 0){
				return false;
			}else{
				return true;
			}
		}else{
			return false;
		}
	}
	private boolean olegLost() {
		if(gameBag.getCountOfOlegAnts() == 0){
			if(gameBag.getCountOfOlegHills() > 0 && 
					gameBag.getUnbornOlegsAnt() > 0){
				return false;
			}else{
				return true;
			}
		}else{
			return false;
		}
	}
	public void createAntForPlayer(String playerName){
		if(playerName.equals(Constants.gleb)){
			gameBag.setUnbornGlebsAnt(gameBag.getUnbornGlebsAnt() + 1);
		}else{
			gameBag.setUnbornOlegsAnt(gameBag.getUnbornOlegsAnt() + 1);
		}
	}
	public void killAnt(String antName) {
		System.out.println("killAnt " + antName);
		AntCoockie ac = gameBag.getAntCoockies().get(antName);
		try {
			ac.getAgentController().kill();
		} catch (StaleProxyException e) {
			e.printStackTrace();
		}finally{
			AntCoockie ant = gameBag.getAntCoockies().get(antName);
			if(ant.getPlayerName().equals(Constants.gleb)){
				gameBag.setCountOfGlebAnts(gameBag.getCountOfGlebAnts() - 1);
			}else{
				gameBag.setCountOfOlegAnts(gameBag.getCountOfOlegAnts() - 1);
			}
			gameBag.getAntCoockies().remove(antName);
		}
	}
	
	private void checkAndMaybePaintMap(){
//		System.out.println("checkAndMaybePa " + repaintCounter);
//		if(repaintCounter == 5){
//			System.out.println("checkAndMaybePaintMap() " + gameBag.getAntCoockies().size());
			Map detailMap = gameBag.getStaticMap().copy();
			for(Iterator<java.util.Map.Entry<String, AntCoockie>> it = gameBag.getAntCoockies().entrySet().iterator();
					it.hasNext(); ){
				java.util.Map.Entry<String, AntCoockie> entry = it.next();
				AntCoockie ant = entry.getValue();
				int x = ant.getX();
				int y = ant.getY();
				Cell cell = detailMap.getCells()[y][x];
				switch (cell.getType()) {
				case FREE:
					cell.setType(Type.ANT);
					cell.setAntName(ant.getAntName());
					cell.setPlayerName(ant.getPlayerName());
					break;
				case HILL:
					cell.setType(Type.ANT_HILL);
					cell.setAntName(ant.getAntName());
					cell.setPlayerName(ant.getPlayerName());
				default:
					break;
				}
				
			}
			//////////////
			//////////////
			gFrame.paint(detailMap);
			repaintCounter = 0;
//		}else{
//			++repaintCounter;
//		}
	}
	
	private void checkAndMaybeThrowFood() {
		if(throwFoodCounter == 5){
			List<Coordinate> freeCells = getAllFreeCells();
			List<Coordinate> choosenCells = getOnlyNFromAll(18, freeCells);
			for(int i = 0; i < choosenCells.size(); ++i){
				Coordinate pair = choosenCells.get(i);
				gameBag.getStaticMap().getCells()[pair.getY()][pair.getX()].setType(Type.FOOD);
			}
			throwFoodCounter = 0;
		}else{
			++throwFoodCounter;
		}
		
	}
	private List<Coordinate> getOnlyNFromAll(int N, List<Coordinate> all) {
		if((0 < N && N < all.size()) == false){
			return Collections.emptyList();
		}
		List<Coordinate> result = new LinkedList<Coordinate>();
		Random random = new Random();
		for(int i = 0; i < N; ++i){
			int luckyIndex = random.nextInt(all.size());
			Coordinate luckyCoord = all.get(luckyIndex);
			all.remove(luckyIndex);
			result.add(luckyCoord);
		}
		return result;
	}
	private List<Coordinate> getAllFreeCells() {
		List<Coordinate> freeCells = new LinkedList<Coordinate>();
		for(int i = 0; i < gameBag.getStaticMap().getCells().length; ++i){
			for(int j = 0; j < gameBag.getStaticMap().getCells()[0].length; ++j){
				Cell cell = gameBag.getStaticMap().getCells()[i][j]; 
				if(cell.getType().equals(Type.FREE) && noAntStayOnCoordinates(j, i)){
					Coordinate coordinate = new Coordinate(j, i);
					freeCells.add(coordinate);
				}
			}
		}
		return freeCells;
	}
	private String getAntName(ACLMessage message) {
		return message.getSender().getLocalName();
	}
}
