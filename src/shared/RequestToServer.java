package shared;

import java.io.Serializable;

public class RequestToServer implements MessageToServer{
	private static final long serialVersionUID = -1057213833541284910L;

	@Override
	public Type getType() {
		return Type.REQUEST;
	}

}
