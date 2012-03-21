package shared;

import java.io.Serializable;

public class ResponseFromServer implements Serializable{
	private Cell direction[][];

	public ResponseFromServer(Cell[][] direction) {
		this.direction = direction;
	}

	public Cell[][] getDirection() {
		return direction;
	}

	public void setDirection(Cell[][] direction) {
		this.direction = direction;
	}
}
