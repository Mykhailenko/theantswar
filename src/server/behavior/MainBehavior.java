package server.behavior;

import server.model.AntCoockie;
import server.model.GameBag;
import server.visual.GFrame;
import shared.Constants;
import shared.MessageToServer;
import shared.StepToServer;
import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
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
		this.repaintCounter = 0;
		this.throwFoodCounter = 0;
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
			String antName = getAntName(message);
			AntCoockie coockie = gameBag.getAntCoockies().get(antName);
			if(coockie!=null){// если мурашка с таким именем существует 
				try {
					if(badTime(message, coockie)){// если мурашка наглеет то мы её убиваем
						killAnt(antName);
					}
				} catch (UnreadableException e) {// смерть за эксепшн
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
				} catch (UnreadableException e) {		}
			}
		}
		block();
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
	public void killAnt(String antName) {
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

	private void checkAndMaybePaintMap(){
		if(repaintCounter == 0){
			//////////////
			//////////////
			gFrame.paint(gameBag.getStaticMap());
			repaintCounter = 5;
		}else{
			--repaintCounter;
		}
	}
	private void checkAndMaybeThrowFood() {
		if(throwFoodCounter == 0){
			
			throwFoodCounter = 5;
		}else{
			--throwFoodCounter;
		}
		
	}
	private String getAntName(ACLMessage message) {
		return message.getSender().getLocalName();
	}
}
