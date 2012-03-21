package server.model;

import server.CellCreator;


public class Map {
	
	private Cell [][] cells;
	
	public Map() {
		cells = CellCreator.createDedaultCells();
	}
	
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
