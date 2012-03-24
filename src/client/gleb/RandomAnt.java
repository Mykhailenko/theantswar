package client.gleb;

import java.util.Random;

import server.model.StepDirection;

import client.BaseAnt;

public class RandomAnt extends BaseAnt{
	private static final long serialVersionUID = 8955629648617348488L;
	@Override
	protected void setup() {
		System.out.println("I'm alive!!! My name is " + getLocalName());
		Random random = new Random();
		while(true){
			StepDirection direction = StepDirection.STAY;
			int r = random.nextInt(4);
			if(r == 0){
				direction = StepDirection.DOWN;
			}else if(r == 1){
				direction = StepDirection.LEFT;
			}else if(r == 2){
				direction = StepDirection.RIGHT;
			}else if(r == 3){
				direction = StepDirection.UP;
			}
			makeStep(direction);
			long before = System.currentTimeMillis();
			sleep(1000);
			long after = System.currentTimeMillis();
			System.out.println("really sleep " + (after - before));
		}
			
	}
}
