package shared;

import java.io.Serializable;

import server.model.StepDirection;

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

}
