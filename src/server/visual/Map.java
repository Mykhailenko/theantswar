package server.visual;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import server.model.Cell;

public class Map extends JPanel{
	private static final long serialVersionUID = -5727672996310615106L;
	private server.model.Map model;
	private int cellWidth = 4;
	private int cellHeight = 4;
	private Color backgroundColor = Color.BLUE;
	private Color lineColor = Color.BLACK;
	public Map() {
		model = new server.model.Map();
		setSize(500, 500);
		setBackground(backgroundColor);
		
	}
	public void paint(server.model.Map map) {
		model = map;
		repaint();
		
	}
	
	@Override
	public void paint(Graphics g) {
		int wcount = model.getW();
		int hcount = model.getH();
		System.out.println("in paint " + wcount);
		
		int width = wcount * cellWidth;
		int height = hcount * cellHeight;
		for(int i = 0; i < model.getW(); ++i){
			for(int j = 0; j < model.getH(); ++j){
				g.setColor(getColorForCell(model.getCells()[i][j]));
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
		case ANT:
		case ANT_HILL:
			return Color.RED;
		case HILL:
			return Color.BLUE;
		case FOOD:
			return Color.GREEN;
		case WALL:
			return Color.BLACK;
		default:
			return Color.ORANGE;
		}
	}
}
