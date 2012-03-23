package server.model;

import server.CellCreator;


public class Map {
	
	private Cell [][] cells;
	
	public Map() {
		cells = CellCreator.createDedaultCells();
	}
	public Map copy(){
		Map cpy = new Map();
		for(int i = 0; i < cells.length; ++i){
			for(int j = 0; j < cells[0].length; ++j){
				cpy.getCells()[i][j] = cells[i][j].copy();
			}
		}
		return cpy;
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
