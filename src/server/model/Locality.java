package server.model;

import java.io.Serializable;

public class Locality implements Serializable{
	private CellType [][] a = new CellType[Constants.LOCALITY_SIZE][Constants.LOCALITY_SIZE];
	public int size(){
		return Constants.LOCALITY_SIZE;
	}
	public CellType getCell(int left, int top){
		return a[top][left]; 
	}
	public Locality (CellType[][]a){
		this.a = a;
	}
}
