package server;

import server.model.AntCoockie;
import server.model.Cell;
import server.model.Constants;
import server.model.GameBag;
import server.visual.GFrame;
import server.visual.GameFrame;
import shared.MessageToServer;
import shared.StepToServer;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import jade.wrapper.StaleProxyException;

public class MainBehavior extends CyclicBehaviour {
	private static final long serialVersionUID = 538601823417067372L;
	private Agent agent;
	private char counter;
	private GameBag gameBag;
	private GFrame gFrame;
	public MainBehavior(Agent agent, GameBag gameBag, GFrame gFrame) {
		this.counter = 0;
		this.agent = agent;
		this.gameBag = gameBag;
		this.gFrame = gFrame;
	}
	@Override
	public void action() {
		ACLMessage message = agent.receive();
		if(message != null){
			String antName = getAntName(message);
			AntCoockie coockie = gameBag.getAntCoockies().get(antName);
			if(coockie!=null){
				try {
					if(badTime(message, coockie)){
						killAnt(antName);
					}
				} catch (UnreadableException e) {
					killAnt(antName);
				}
				MessageToServer mts = null;
				try {
					mts = (MessageToServer) message.getContentObject();
				} catch (UnreadableException e) {
					e.printStackTrace();
				}
				if(mts.isRequest()){
					
				}else if(mts.isStep()){
					StepToServer sts = (StepToServer) mts;
					int x = coockie.getX();
					int y = coockie.getY();
					
					switch (sts.getStepDirection()) {
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
						killAnt(antName);
					}else{
						Cell cell = gameBag.getStaticMap().getCells()[y][x];
						switch (cell.getType()) {
						case FREE:
							break;
						case WALL:
						default:
							killAnt(antName);
							break;
						}
					}
					
				}
				checkAndMaybePaintMap();
			}
		}
		block();
	}

	private boolean badCoordinates(int x, int y){
		if(0 <= x && x <= Constants.MAP_SIZE &&
				0 <= y && y <= Constants.MAP_SIZE){
			return false;
		}else{
			return true;
		}
		
	}
	private void killAnt(String antName) {
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
			long curr = System.currentTimeMillis();
			if(curr - coockie.getLastRequest() < Constants.INTERVAL){
				return false;
			}else{
				coockie.setLastRequest(curr);
				return true;
			}
		}else if(mts.getType().equals(MessageToServer.Type.STEP)){
			long curr = System.currentTimeMillis();
			if(curr - coockie.getLastStep() < Constants.INTERVAL){
				return false;
			}else{
				coockie.setLastStep(curr);
				return true;
			}
		}
		return false;
	}

	private String getAntName(ACLMessage message) {
		return message.getSender().getLocalName();
	}

	private void checkAndMaybePaintMap(){
		if(counter == 0){
			//////////////
			//////////////
			gFrame.paint(gameBag.getStaticMap());
			counter = 5;
		}else{
			--counter;
		}
	}
}
