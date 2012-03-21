package shared;

import server.model.StepDirection;
/**
 * ��� �������� � �����-�� �������. �� ���� ������ �� �������.
 * @author RedFox
 *
 */
public class StepToServer implements MessageToServer {
	private static final long serialVersionUID = 433826977551649066L;
	private StepDirection stepDirection;
	
	public StepDirection getStepDirection() {
		return stepDirection;
	}

	public void setStepDirection(StepDirection stepDirection) {
		this.stepDirection = stepDirection;
	}

	@Override
	public Type getType() {
		return Type.STEP;
	}

	@Override
	public boolean isStep() {
		return true;
	}

	@Override
	public boolean isRequest() {
		return false;
	}

}
