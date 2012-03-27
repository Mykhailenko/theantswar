package client.gleb.vis;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import shared.Cell;

public class Map extends JPanel{
	private static final long serialVersionUID = -5727672996310615106L;
	private Cell [][]cells;
	private int cellWidth = 10;
	private int cellHeight = 10;
	private Color backgroundColor = Color.BLUE;
	private Color lineColor = Color.BLACK;
	public Map() {
		cells = null;
		setSize(110, 110);
		setBackground(backgroundColor);
		
	}
	public void paint(shared.Cell [][] cells) {
		this.cells = cells;
		repaint();
		
	}
	
	@Override
	public void paint(Graphics g) {
		if(cells==null){
			return;
		}
		int wcount = cells[0].length;
		int hcount = cells.length;
		int width = wcount * cellWidth;
		int height = hcount * cellHeight;
		for(int i = 0; i < wcount; ++i){
			for(int j = 0; j < hcount; ++j){
				g.setColor(getColorForCell(cells[i][j]));
				g.fillRect(j*cellHeight, i*cellWidth,  cellWidth, cellHeight);
			}
		}
		g.setColor(lineColor);
		
		for(int i = 0; i < wcount; ++i){
			g.drawLine(cellWidth * i, 0, cellWidth * i, height);
		}
		for(int i = 0; i < hcount; ++i){
			g.drawLine(0, cellHeight * i, width, cellHeight * i);
		}
	}
	private Color getColorForCell(Cell cell) {
		switch (cell.getType()) {
		case FREE:
			return Color.WHITE;
			
		case FRIEND_ANT:
		case FRIEND_ANT_HILL:
			return Color.GREEN;
		case ENEMY_ANT:
		case ENEMY_ANT_HILL:
			return Color.RED;
		case FRIEND_HILL:
			return Color.BLUE;
		case ENEMY_HILL:
			return Color.ORANGE;
		case FOOD:
			return Color.CYAN;
		case WALL:
			return Color.BLACK;
		case UNVISIBLE:
			return Color.PINK;
		default:
			return Color.ORANGE;
		}
	}
}
