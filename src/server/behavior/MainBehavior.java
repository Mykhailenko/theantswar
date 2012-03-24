package server.behavior;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import server.model.AntCoockie;
import server.model.Cell;
import server.model.HillCoockie;
import server.model.Cell.Type;
import server.model.GameBag;
import server.model.Map;
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
		
		ACLMessage message = agent.receive();
		if(message != null){
			System.out.println("Recieve!");
			String antName = getAntName(message);
			AntCoockie coockie = gameBag.getAntCoockies().get(antName);
			if(coockie!=null){// если мурашка с таким именем существует
				System.out.println("Exist!");
				try {
					if(badTime(message, coockie)){// если мурашка наглеет то мы её убиваем
						System.out.println("it's bad time");
						killAnt(antName);
					}
				} catch (UnreadableException e) {// смерть за эксепшн
					System.out.println("exep " + e);
					killAnt(antName);
				}
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
				} catch (UnreadableException e) {		}
			}
		}
		block();
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
		antCoockies.setLastRequest(0);
		antCoockies.setLastStep(0);
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
		antCoockies.setLastRequest(0);
		antCoockies.setLastStep(0);
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
	}
	private List<HillCoockie> freeHillsForPlayer(String playerName){
		List<HillCoockie> result = new ArrayList<HillCoockie>();
		for(HillCoockie hill : gameBag.getHillCoockies()){
			if(hill.getPlayerName().equals(playerName) && noOneStayOnHill(hill)){
				result.add(hill);
			}
		}
		return result;
	}
	private boolean noOneStayOnHill(HillCoockie hill){
		for(Iterator<java.util.Map.Entry<String, AntCoockie>> it = gameBag.getAntCoockies().entrySet().iterator(); it.hasNext() ;){
			java.util.Map.Entry<String, AntCoockie> entry = it.next();
			AntCoockie ant = entry.getValue();
			if(hill.getX() == ant.getX() && 
					hill.getY() == ant.getY()){
				return false;
			}
		}
		return true;
	}
	@Override
	public boolean done() {
		if(somebodyWin()){
			congradulateSomebody();
			return true;
		}
		return false;
	}
	
	private void congradulateSomebody() {
		
	}
	private boolean somebodyWin() {
		return false;
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
			gameBag.getAntCoockies().remove(antName);
		}
	}
	
	private boolean badTime(ACLMessage message, AntCoockie coockie) throws UnreadableException {
		MessageToServer mts = (MessageToServer) message.getContentObject();
		if(mts.getType().equals(MessageToServer.Type.REQUEST)){
			if(coockie.getLastRequest() == 0){
				coockie.setLastRequest(System.currentTimeMillis());
				return false;
			}
			if(System.currentTimeMillis() - coockie.getLastRequest() < Constants.INTERVAL){
				System.out.println("Kill "+ coockie.getAntName()+"because " + (System.currentTimeMillis()- coockie.getLastRequest()) );
				return true;
			}else{
				coockie.setLastRequest(System.currentTimeMillis());
				return false;
			}
		}else if(mts.getType().equals(MessageToServer.Type.STEP)){
			if(coockie.getLastStep() == 0){
				coockie.setLastStep(System.currentTimeMillis());
				return false;
			}
			if(System.currentTimeMillis() - coockie.getLastStep() < Constants.INTERVAL){
				System.out.println("Kill "+ coockie.getAntName()+"because " + (System.currentTimeMillis() - coockie.getLastStep()) );
				return true;
			}else{
				coockie.setLastStep(System.currentTimeMillis());
				return false;
			}
		}
		return false;
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
			
			throwFoodCounter = 0;
		}else{
			++throwFoodCounter;
		}
		
	}
	private String getAntName(ACLMessage message) {
		return message.getSender().getLocalName();
	}
}
