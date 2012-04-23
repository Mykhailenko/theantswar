package client.oleg;

import java.util.LinkedList;

import server.model.StepDirection;
import shared.Cell.Type;

public class Target {
	LinkedList<StepDirection> target;
	int stepsAmount = 0;
	Type type;
	CoordinateCell cell;
	
	public LinkedList<StepDirection> getTarget() {
		return target;
	}
	public void setTarget(LinkedList<StepDirection> target) {
		this.target = target;
	}
	public int getStepsAmount() {
		return stepsAmount;
	}
	public void setStepsAmount(int stepsAmount) {
		this.stepsAmount = stepsAmount;
	}
	public CoordinateCell getCell() {
		return cell;
	}
	public void setCell(CoordinateCell cell) {
		this.cell = cell;
	}
	
	public Target(LinkedList<StepDirection> target, int stepsAmount, Type type,
			CoordinateCell cell) {
		super();
		this.target = target;
		this.stepsAmount = stepsAmount;
		this.type = type;
		this.cell = cell;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public StepDirection makeStep(){
		stepsAmount--;
		return target.pollFirst();
	}
}
