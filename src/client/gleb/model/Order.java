package client.gleb.model;

public class Order implements GMessage {
	private static final long serialVersionUID = 8769644617343853557L;

	private String marshal;
	private GMessage.Type type;
	
	@Override
	public Type getType() {
		return type;
	}

	public String getMarshal() {
		return marshal;
	}

	public void setMarshal(String marshal) {
		this.marshal = marshal;
	}

	public void setType(GMessage.Type type) {
		this.type = type;
	}
	
}
