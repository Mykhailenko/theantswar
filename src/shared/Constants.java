package shared;

import client.BaseAnt;
import client.gleb.RandomAnt;

public class Constants {
	public static final int LOCALITY_SIZE = 5;
	public static final int ATTACK_SIZE = 3;
	public static final int MAP_SIZE = 100;
	public static final String gleb = "GLEB";
	public static final String oleg = "OLEG";
	public static final String glebAgentClassName = RandomAnt.class.getName();
	public static final String olegAgentClassName = BaseAnt.class.getName();
	public static final int INTERVAL = 1000;
}
