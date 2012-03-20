package server;

import java.util.List;
import java.util.Map;

import server.model.AntCoockies;
import server.model.HillCoockie;

import jade.core.behaviours.OneShotBehaviour;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

public class InitBehavior extends OneShotBehaviour {
	private Map<String, AntCoockies> data;
	private List<HillCoockie> hillCoockies;
	private ContainerController containerController;
	public InitBehavior(ContainerController containerController, Map<String, AntCoockies> data, List<HillCoockie> hillCoockies) {
		this.data = data;
		this.hillCoockies = hillCoockies;
		this.containerController = containerController;
	}
	@Override
	public void action() {
		for(HillCoockie hill : hillCoockies){
			if(hill.getPlayerName().equals(Server.gleb)){
				AgentController agent = null;
				String antName = Server.gleb + Server.GLEBC++;
				try {
					agent = containerController.createNewAgent(antName, Server.glebAgentClassName, null);
				} catch (StaleProxyException e) {
				}
				AntCoockies antCoockies = new AntCoockies();
				antCoockies.setAntName(antName);
				antCoockies.setLastRequest(0);
				antCoockies.setLastStep(0);
				antCoockies.setAgentController(agent);
				antCoockies.setPlayerName(Server.gleb);
				antCoockies.setX(hill.getX());
				antCoockies.setY(hill.getY());
				data.put(antCoockies.getAntName(), antCoockies);
				try {
					agent.start();
				} catch (StaleProxyException e) {
					e.printStackTrace();
				}
			}else{
				AgentController agent = null;
				String antName = Server.oleg + Server.OLEGC++;
				try {
					agent = containerController.createNewAgent(antName, Server.olegAgentClassName, null);
				} catch (StaleProxyException e) {
				}
				AntCoockies antCoockies = new AntCoockies();
				antCoockies.setAntName(antName);
				antCoockies.setLastRequest(0);
				antCoockies.setLastStep(0);
				antCoockies.setAgentController(agent);
				antCoockies.setPlayerName(Server.oleg);
				antCoockies.setX(hill.getX());
				antCoockies.setY(hill.getY());
				data.put(antCoockies.getAntName(), antCoockies);
				try {
					agent.start();
				} catch (StaleProxyException e) {
					e.printStackTrace();
				}
			}
		}
	}	

}
