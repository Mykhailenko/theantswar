package server.behavior;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import server.model.AntCoockie;
import server.model.GameBag;

/**
 * класс который имеют всю информацию о игре. и может каждому муравью "ответить"
 * @author tas
 *
 */
public class LocalityResponser {
	private GameBag gameBag;
	public LocalityResponser(GameBag gameBag) {
		this.gameBag = gameBag;
	}
	public ACLMessage message(AntCoockie ant){
		ACLMessage message = new ACLMessage(ACLMessage.INFORM);
		message.addReceiver(new AID(ant.getAntName(), AID.ISLOCALNAME));
		/**
		 * code here
		 */
		return message;
	}
	
}
