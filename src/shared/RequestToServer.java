package shared;

import java.io.Serializable;

import server.model.StepDirection;

public class RequestToServer implements Serializable{
	private StepDirection step;

	public RequestToServer(String step) {
		super();
		this.step = StepDirection.fromString(step);
	}

	public StepDirection getStep() {
		return step;
	}

	public void setStep(StepDirection step) {
		this.step = step;
	}
	
}
