package client.gleb.model;


public class HI3Message implements GMessage{
	private static final long serialVersionUID = 2770753792332743430L;
	@Override
	public Type getType() {
		return Type.HI3;
	}

}
