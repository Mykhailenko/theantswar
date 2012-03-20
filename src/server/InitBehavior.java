package server;

import java.util.Map;

import server.model.AntCoockies;

import jade.core.behaviours.OneShotBehaviour;

public class InitBehavior extends OneShotBehaviour {
	private Map<String, AntCoockies> data;
	private server.model.Map mapa;
	public InitBehavior(Map<String, AntCoockies> data, server.model.Map mapa) {
		this.data = data;
		this.mapa = mapa;
	}
	@Override
	public void action() {
		
	}

}
