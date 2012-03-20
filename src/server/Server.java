package server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import client.BaseAnt;

import server.model.AntCoockies;
import server.model.Cell;
import server.model.Cell.Type;
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
	private static java.util.Map<String, AntCoockies> antCoockies = new HashMap<String, AntCoockies>();
	private static List<HillCoockie> hillCoockies;
	private static GFrame gFrame ;
	private static Map staticMap;
	private static char counter = 5;
	public static final int INTERVAL = 1000;
	public static int GLEBC = 0;
	public static int OLEGC = 0;
	public static void main(String [] rgs){
		initHills();
		staticMap = new Map();
		putHillsOnMap();
		GFrame gFrame = new GameFrame();
		gFrame.paint(staticMap);
		String []args = {"-gui", "-local-host","127.0.0.1", "server:"+Server.class.getName()};
		jade.Boot.main(args);
	}
	private static void initHills(){
		hillCoockies = new ArrayList<HillCoockie>();
		HillCoockie hi0 = new HillCoockie();
		hi0.setPlayerName(gleb);
		hi0.setX(14);
		hi0.setY(14);
		hillCoockies.add(hi0);
		
		HillCoockie hi1 = new HillCoockie();
		hi1.setPlayerName(gleb);
		hi1.setX(14);
		hi1.setY(20);
		hillCoockies.add(hi1);
	}
	private static void putHillsOnMap(){
		for(HillCoockie hillCoockie : hillCoockies){
			Cell cell = new Cell();
			cell.setType(Type.HILL);
			cell.setPlayerName(hillCoockie.getPlayerName());
			staticMap.getCells()[hillCoockie.getY()][hillCoockie.getX()] = cell;
		}
	}
	@Override
	protected void setup() {
		addBehaviour(new InitBehavior(getContainerController(), antCoockies, hillCoockies));
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
					gFrame.paint(staticMap);
					counter = 5;
				}else{
					--counter;
				}
			}
		});
	}
}

