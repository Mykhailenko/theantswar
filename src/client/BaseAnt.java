package client;

import java.io.IOException;

import server.model.Locality;
import server.model.StepDirection;
import shared.RequestToServer;
import shared.ResponseFromServer;
import shared.StepToServer;
import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

public class BaseAnt extends Agent{
	private static final long serialVersionUID = 5526620659063148593L;
	protected final static AID serverAID = new AID("Server", AID.ISLOCALNAME);
	protected final Locality getLocality(){
		RequestToServer request = new RequestToServer();
		ACLMessage message = new ACLMessage(ACLMessage.INFORM);
		try {
			message.setContentObject(request);
		} catch (IOException e) {
		}
		message.addReceiver(serverAID);
		send(message);
		ACLMessage resp = blockingReceive();
		ResponseFromServer responseObject = null;
		try {
			responseObject = (ResponseFromServer) resp.getContentObject();
		} catch (UnreadableException e) {
			e.printStackTrace();
		}
		Locality locality = responseObject.getLocality();
		return locality;
	}
	protected final void makeStep(StepDirection direction){
		StepToServer stepToServer = new StepToServer();
		stepToServer.setStepDirection(direction);
		ACLMessage message = new ACLMessage(ACLMessage.INFORM);
		try {
			message.setContentObject(stepToServer);
		} catch (IOException e) {
			e.printStackTrace();
		}
		message.addReceiver(serverAID);
		send(message);
	}
	protected final void sleep(long mls){
		long start = System.currentTimeMillis();
		while(true){
			long delta = System.currentTimeMillis() - start;
			if(delta >= mls){
				break;
			}
			try {
				Thread.sleep(mls - delta);
			} catch (InterruptedException e) {	}
		}
	}
}
