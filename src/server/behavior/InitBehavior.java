package server.behavior;

import server.model.GameBag;
import server.model.HillCoockie;
import shared.Constants;

import jade.core.behaviours.OneShotBehaviour;

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
		}
		mainBehavior.createAnts();
		System.out.println("after initialize " + gameBag.getAntCoockies().size());
	}	

}
