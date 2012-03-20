package server.model;

import server.CellCreator;


public class Map {
	public Map() {
		cells = CellCreator.createDedaultCells();
		CellCreator.print(cells);
	}
	private Cell [][] cells;
	public int getW(){
		return cells.length;
	}
	public int getH(){
		return cells[0].length;
	}
	
	public Cell[][] getCells() {
		return cells;
	}
	public void setCells(Cell[][] cells) {
		this.cells = cells;
	}
	
}
