package server.behavior;

import server.model.AntCoockie;
import server.model.GameBag;
import server.model.HillCoockie;
import shared.Constants;

import jade.core.behaviours.OneShotBehaviour;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

public class InitBehavior extends OneShotBehaviour {
	private static final long serialVersionUID = 8503968226620053554L;
	private MainBehavior mainBehavior;
	private GameBag gameBag;
	public InitBehavior(MainBehavior mainBehavior, GameBag gameBag) {
		this.mainBehavior = mainBehavior;
		this.gameBag = gameBag;
	}
	@Override
	public void action() {
		for(HillCoockie hill : gameBag.getHillCoockies()){
			if(hill.getPlayerName().equals(Constants.gleb)){
				gameBag.setUnbornGlebsAnt(gameBag.getUnbornGlebsAnt() + 1);
			}else{
				gameBag.setUnbornOlegsAnt(gameBag.getUnbornOlegsAnt() + 1);
			}
//			if(hill.getPlayerName().equals(Constants.gleb)){
//				AgentController agent = null;
//				String antName = Constants.gleb + gameBag.getAndIncGleb();
//				try {
//					agent = containerController.createNewAgent(antName, Constants.glebAgentClassName, null);
//				} catch (StaleProxyException e) {
//				}
//				AntCoockie antCoockies = new AntCoockie();
//				antCoockies.setAntName(antName);
//				antCoockies.setLastRequest(0);
//				antCoockies.setLastStep(0);
//				antCoockies.setAgentController(agent);
//				antCoockies.setPlayerName(Constants.gleb);
//				antCoockies.setX(hill.getX());
//				antCoockies.setY(hill.getY());
//				gameBag.getAntCoockies().put(antCoockies.getAntName(), antCoockies);
//				try {
//					agent.start();
//				} catch (StaleProxyException e) {
//					e.printStackTrace();
//				}
//			}else{
//				AgentController agent = null;
//				String antName = Constants.oleg + gameBag.getAndIncOleg();
//				try {
//					agent = containerController.createNewAgent(antName, Constants.olegAgentClassName, null);
//				} catch (StaleProxyException e) {
//				}
//				AntCoockie antCoockies = new AntCoockie();
//				antCoockies.setAntName(antName);
//				antCoockies.setLastRequest(0);
//				antCoockies.setLastStep(0);
//				antCoockies.setAgentController(agent);
//				antCoockies.setPlayerName(Constants.oleg);
//				antCoockies.setX(hill.getX());
//				antCoockies.setY(hill.getY());
//				gameBag.getAntCoockies().put(antCoockies.getAntName(), antCoockies);
//				try {
//					agent.start();
//				} catch (StaleProxyException e) {
//					e.printStackTrace();
//				}
//			}
		}
		mainBehavior.createAnts();
		System.out.println("after initialize " + gameBag.getAntCoockies().size());
	}	

}
