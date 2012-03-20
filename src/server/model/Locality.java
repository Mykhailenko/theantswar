package server.model;

import java.io.Serializable;

import shared.Cell;

public class Locality implements Serializable{
	private Cell [][] a = new Cell[Constants.LOCALITY_SIZE][Constants.LOCALITY_SIZE];
	public int size(){
		return Constants.LOCALITY_SIZE;
	}
	public Cell getCell(int left, int top){
		return a[top][left]; 
	}
	public Locality (Cell[][]a){
		this.a = a;
	}
}
