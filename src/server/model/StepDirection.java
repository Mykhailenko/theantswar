package server.model;

public enum StepDirection {
	LEFT, UP, RIGHT, DOWN, STAY;
	public static StepDirection fromString(String str){
		if(str.equalsIgnoreCase("left")){
			return LEFT;
		}else if(str.equalsIgnoreCase("up")){
			return UP;
		}else if(str.equalsIgnoreCase("right")){
			return RIGHT;
		}else if(str.equalsIgnoreCase("down")){
			return DOWN;
		}else if(str.equalsIgnoreCase("stay")){
			return STAY;
		}else {
			return STAY;
		}
	}
	@Override
	public String toString() {
		switch (this) {
		case LEFT:
			return "left";
		case UP:
			return "up";
		case RIGHT:
			return "right";
		case DOWN:
			return "down";
		case STAY:
			return "stay";
		}
		return "yeppi-ka-yey";
	}
}
