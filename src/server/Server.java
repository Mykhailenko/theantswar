package server;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import client.BaseAnt;

import server.model.AntCoockies;
import server.model.HillCoockie;
import server.model.Map;
import server.visual.GFrame;
import server.visual.GameFrame;
import shared.MessageToServer;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

public class Server extends Agent{
	public static final String gleb = "GLEB";
	public static final String oleg = "OLEG";
	public static final String glebAgentClassName = BaseAnt.class.getName();
	public static final String olegAgentClassName = BaseAnt.class.getName();
	private static final long serialVersionUID = 1L;
	java.util.Map<String, AntCoockies> antCoockies = new HashMap<String, AntCoockies>();
	java.util.Map<String, HillCoockie> hillCoockies = new HashMap<String, HillCoockie>();
	GFrame gFrame ;
	Map staicMap;
	char counter = 5;
	public static final int INTERVAL = 1000;
	public static void main(String [] rgs){
		String []args = {"-gui", "-local-host","127.0.0.1", "server:"+Server.class.getName()};
		jade.Boot.main(args);
		GFrame gFrame = new GameFrame();
		gFrame.paint(new Map());
	}
	@Override
	protected void setup() {
		
		addBehaviour(new CyclicBehaviour() {
			private static final long serialVersionUID = 1L;
			@Override
			public void action() {
				ACLMessage message = receive();
				if(message != null){
					String antName = getAntName(message);
					AntCoockies coockie = antCoockies.get(antName);
					if(coockie!=null){
						try {
							if(badTime(message, coockie)){
								killAnt(antName);
							}
						} catch (UnreadableException e) {
							killAnt(antName);
						}
					checkAndMaybePaintMap();
					}
				}
				block();
			}
			
			private void killAnt(String antName) {
			}
			
			private boolean badTime(ACLMessage message, AntCoockies coockie) throws UnreadableException {
				MessageToServer mts = (MessageToServer) message.getContentObject();
				if(mts.getType().equals(MessageToServer.Type.REQUEST)){
					long curr = System.currentTimeMillis();
					if(curr - coockie.getLastRequest() < INTERVAL){
						return false;
					}else{
						coockie.setLastRequest(curr);
						return true;
					}
				}else if(mts.getType().equals(MessageToServer.Type.STEP)){
					long curr = System.currentTimeMillis();
					if(curr - coockie.getLastStep() < INTERVAL){
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
					gFrame.paint(staicMap);
					counter = 5;
				}else{
					--counter;
				}
			}
		});
	}
}
