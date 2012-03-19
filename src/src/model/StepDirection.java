package model;

public enum StepDirection {
	LEFT, TOP, RIGHT, DOWN, STAY;
	public static StepDirection fromString(String str){
		if(str.equalsIgnoreCase("left")){
			return LEFT;
		}else if(str.equalsIgnoreCase("top")){
			return TOP;
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
		case TOP:
			return "top";
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
