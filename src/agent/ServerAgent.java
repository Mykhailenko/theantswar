package agent;

import java.awt.Color;
import java.awt.Dimension;
import java.util.List;

import agent.ant.RandomAnt;
import model.AntModel;
import model.StepDirection;
import server.visual.GameFrame;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;

@SuppressWarnings("serial")
public class ServerAgent extends Agent{
	private GameFrame gameFrame;
	public static void main(String [] rgs){
		String []args = {"-gui", "-local-host","127.0.0.1", "server:"+ServerAgent.class.getName()};
		jade.Boot.main(args);
	}
//	@Override
//	protected void setup() {
//		gameFrame = new GameFrame();
//		gameFrame.setVisible(true);
//		List<AntModel> ants = gameFrame.getMap().getModel().getAnts();
//		CreateAntsBehavior redCreator = new CreateAntsBehavior(getContainerController(), ants);
//		redCreator.setSize(20);
//		redCreator.setAntName("ant-red");
//		redCreator.setColor(Color.RED);
//		redCreator.setCenter(new Dimension(30, 50));
//		addBehaviour(redCreator);
//		CreateAntsBehavior blueCreator = new CreateAntsBehavior(getContainerController(), ants);
//		blueCreator.setSize(30);
//		blueCreator.setAntName("ant-blue");
//		blueCreator.setColor(Color.BLUE);
//		blueCreator.setCenter(new Dimension(70, 50));
//		addBehaviour(blueCreator);
//		addBehaviour(new CyclicBehaviour() {
//			@Override
//			public void action() {
//				ACLMessage message = receive();
//				if(message != null){
//					System.out.println(message.getContent());
//					StepDirection direction = StepDirection.fromString(message.getContent());
//					String antName = getAntName(message);
//					AntModel ant = searchAnt(antName);
//					moveAnt(ant, direction);
//				}
//				gameFrame.repaint();
//				block();
//			}
//
//
//
//		});
//	}
//	private String getAntName(ACLMessage message) {
//		return message.getSender().getLocalName();
//	}
//	private AntModel searchAnt(String antName) {
//		for(AntModel antModel : gameFrame.getMap().getModel().getAnts()){
//			if(antModel.getId().equals(antName)){
//				return antModel;
//			}
//		}
//		return null;
//	}
//	private void moveAnt(AntModel ant, StepDirection direction) {
//		switch (direction) {
//		case LEFT:
//			ant.setX(ant.getX()-1);
//			break;
//		case TOP:
//			ant.setY(ant.getY()-1);
//			break;
//		case RIGHT:
//			ant.setX(ant.getX()+1);
//			break;
//		case DOWN:
//			ant.setY(ant.getY()+1);
//			break;
//		}		
//	}
}
