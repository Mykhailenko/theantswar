package client.gleb.model;

public class Order implements GMessage {
	private static final long serialVersionUID = 8769644617343853557L;

	public enum OrderType {
		PROTECTE_HILL, ATTACK, EAT;
	};
	private String marshal;
	private OrderType orderType;
	
	@Override
	public Type getType() {
		return Type.ORDER;
	}

	public String getMarshal() {
		return marshal;
	}

	public void setMarshal(String marshal) {
		this.marshal = marshal;
	}

	public OrderType getOrderType() {
		return orderType;
	}

	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}
	
}
