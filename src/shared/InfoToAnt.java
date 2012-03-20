package shared;

import java.io.Serializable;

public class InfoToAnt implements Serializable{
	private static final long serialVersionUID = 9045531310630236758L;
	private Cell [][] cells;
	
	public Cell[][] getCells() {
		return cells;
	}
	public void setCells(Cell[][] cells) {
		this.cells = cells;
	}
	
}
