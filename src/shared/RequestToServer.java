package shared;

/**
 * Запрос муравьём окрестности. Сервер возвращает объект типа ResponseFromServer
 * @author RedFox
 *
 */
public class RequestToServer implements MessageToServer{
	private static final long serialVersionUID = 562221041147970688L;

	public RequestToServer() {
	}
	@Override
	public Type getType() {
		return Type.REQUEST;
	}
	@Override
	public boolean isStep() {
		return false;
	}
	@Override
	public boolean isRequest() {
		return true;
	}
	
}
