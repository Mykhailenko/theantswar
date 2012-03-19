package server;

import jade.core.Agent;

public class Server extends Agent{
	public static void main(String [] rgs){
		String []args = {"-gui", "-local-host","127.0.0.1", "server:"+Server.class.getName()};
		jade.Boot.main(args);
	}
	@Override
	protected void setup() {
		
	}
}
