package server.model;

import java.io.Serializable;

import shared.Cell;

public class Locality implements Serializable{
	private static final long serialVersionUID = -3401066382538823534L;
	private Cell [][] cells;
	private int centerX;
	private int centerY;
	public Cell[][] getCells() {
		return cells;
	}
	public void setCells(Cell[][] cells) {
		this.cells = cells;
	}
	public int getCenterX() {
		return centerX;
	}
	public void setCenterX(int centerX) {
		this.centerX = centerX;
	}
	public int getCenterY() {
		return centerY;
	}
	public void setCenterY(int centerY) {
		this.centerY = centerY;
	}
	
}
