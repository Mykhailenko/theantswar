package server.visual;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import server.model.Cell;

public class Map extends JPanel{
//	private MapModel model;
	private server.model.Map model;
	private int cellWidth = 4;
	private int cellHeight = 4;
	private Color backgroundColor = Color.BLUE;
	private Color lineColor = Color.BLACK;
	public Map() {
		model = new server.model.Map();
		setSize(cellWidth * 100, cellHeight * 100);
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
		g.setColor(Color.RED);
		
		for(int i = 0; i < wcount; ++i){
			g.drawLine(cellWidth * i, 0, cellWidth * i, height);
		}
		for(int i = 0; i < hcount; ++i){
			g.drawLine(0, cellHeight * i, width, cellHeight * i);
		}
		for(int i = 0; i < model.getW(); ++i){
			for(int j = 0; j < model.getH(); ++j){
				g.setColor(getColorForCell(model.getCells()[i][j]));
				g.fillRect(i*cellWidth, j*cellHeight, cellWidth, cellHeight);
			}
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
			return Color.YELLOW;
		case FOOD:
			return Color.GREEN;
		case WALL:
			return Color.BLACK;
		default:
			return Color.ORANGE;
		}
	}
//	private void drawAnts(Graphics g) {
//		for(AntModel antModel : model.getAnts()){
//			g.setColor(antModel.getColor());
//			g.fillRect(antModel.getX()*cellWidth, antModel.getY()*cellHeight, cellWidth, cellHeight);
//		}
//	}
	
}
