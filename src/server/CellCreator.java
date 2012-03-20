package server;

import server.model.Cell;
import server.model.Map;

public class CellCreator {
	public static Cell [][] createDedaultCells(){
		int width = 100;
		int height = 100;
		Cell [][] ola = fillEmpty(width, height);
		makeBorder(ola);
		return ola;
	}
	
	private static void makeBorder(Cell[][] ola) {
		int i, j;
		
		for(j = 0; j < ola[0].length; ++j){
			ola[0][j].setType(Cell.Type.WALL);
		}
		
		i = ola.length - 1;
		for(j = 0; j < ola[0].length; ++j){
			ola[i][j].setType(Cell.Type.WALL);
		}
		
		for(i = 0; i < ola.length; ++i){
			ola[i][0].setType(Cell.Type.WALL);
		}
		
		j = ola[0].length - 1;
		for(i = 0; i < ola.length; ++i){
			ola[i][j].setType(Cell.Type.WALL);
		}
	}

	private static Cell [][] fillEmpty(int w, int h){
		Cell [][] cells =  new Cell[w][h];
		for(int i = 0; i < w; ++i){
			cells[i] = new Cell[h];
			for(int j = 0; j < h; ++j){
				cells[i][j] = new Cell();
			}
		}
		return cells;
	}
}