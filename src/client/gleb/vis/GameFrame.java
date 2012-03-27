package client.gleb.vis;

import javax.swing.JFrame;

import shared.Cell;

public class GameFrame extends JFrame {
	private static final long serialVersionUID = 4501676717595892172L;
	private Map map;
	public GameFrame() {
		setLayout(null);
		setTitle("The Ant Wars");
		setResizable(false);
		setBounds(100, 100, 450, 500);
		setVisible(true);
		map = new Map();
		map.setLocation(0, 30);
		add(map);
	}
	
	public Map getMap() {
		return map;
	}
	public void setMap(Map map) {
		this.map = map;
	}

	public void paint(Cell [][] cells) {
		this.map.paint(cells);
		
	}
	
}
