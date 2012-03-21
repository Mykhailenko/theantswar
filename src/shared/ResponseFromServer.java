package shared;

import java.io.Serializable;

public class ResponseFromServer implements Serializable{
	private static final long serialVersionUID = 3715051751516683440L;
	private Cell [][] cells;
	public Cell[][] getCells() {
		return cells;
	}
	public void setCells(Cell[][] cells) {
		this.cells = cells;
	}
}
