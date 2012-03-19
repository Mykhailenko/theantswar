package agent.ant;

import java.util.Random;

import model.StepDirection;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.WakerBehaviour;
import jade.lang.acl.ACLMessage;

public class RandomAnt extends Agent{
	private static AID serverAID = null;
	private static AID getServerAID(){
		if(serverAID == null){
			serverAID = new AID("server", AID.ISLOCALNAME);
		}
		return serverAID;
	}
	@Override
	protected void setup() {
		addBehaviour(new CyclicBehaviour() {
			@Override
			public void action() {
				StepDirection stepDirection = getNewStepDirection();
				sendToServerNextStep(stepDirection);
				try {
					Thread.sleep(100);
				} catch (Exception e) {		}
			}
		});
	}

	private void sendToServerNextStep(StepDirection stepDirection) {
		sendNextStep(getServerAID(), stepDirection);
	}
	private void sendNextStep(AID reciever, StepDirection stepDirection){
		System.out.println("try to send " + stepDirection);
		ACLMessage message = new ACLMessage(ACLMessage.INFORM);
		message.addReceiver(reciever);
		message.setContent(stepDirection.toString());
		send(message);
	}
	private StepDirection getNewStepDirection() {
		Random random = new Random();
		int next = random.nextInt(5);
		if(next == 0){
			return StepDirection.LEFT;
		}else if(next == 1){
			return StepDirection.TOP;
		}else if(next == 2){
			return StepDirection.RIGHT;
		}else if(next == 3){
			return StepDirection.DOWN;
		}else if(next == 4){
			return StepDirection.STAY;
		}
		return StepDirection.STAY;
	}
}
