package server.visual;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import model.AntModel;
import model.MapModel;


public class Map extends JPanel{
	private MapModel model;
	private int cellWidth = 4;
	private int cellHeight = 4;
	private Color backgroundColor = Color.BLUE;
	private Color lineColor = Color.BLACK;
	public Map() {
		model = new MapModel();
		setSize(cellWidth * model.getWidth(), cellHeight * model.getHeihgt());
		setBackground(backgroundColor);
		
	}
	@Override
	public void paint(Graphics g) {
		int wcount = model.getWidth();
		int hcount = model.getHeihgt();
		int width = wcount * cellWidth;
		int height = hcount * cellHeight;
		g.setColor(Color.LIGHT_GRAY);
		for(int i = 0; i < wcount; ++i){
			g.drawLine(cellWidth * i, 0, cellWidth * i, height);
		}
		for(int i = 0; i < hcount; ++i){
			g.drawLine(0, cellHeight * i, width, cellHeight * i);
		}
		drawAnts(g);
	}
	private void drawAnts(Graphics g) {
		for(AntModel antModel : model.getAnts()){
			g.setColor(antModel.getColor());
			g.fillRect(antModel.getX()*cellWidth, antModel.getY()*cellHeight, cellWidth, cellHeight);
		}
	}
	private void drawAnt(AntModel ant){
		
	}
	public MapModel getModel() {
		return model;
	}
	public void setModel(MapModel model) {
		this.model = model;
	}
	public int getCellWidth() {
		return cellWidth;
	}
	public void setCellWidth(int cellWidth) {
		this.cellWidth = cellWidth;
	}
	public int getCellHeight() {
		return cellHeight;
	}
	public void setCellHeight(int cellHeight) {
		this.cellHeight = cellHeight;
	}
	public Color getBackgroundColor() {
		return backgroundColor;
	}
	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	public Color getLineColor() {
		return lineColor;
	}
	public void setLineColor(Color lineColor) {
		this.lineColor = lineColor;
	}
	
	
}
