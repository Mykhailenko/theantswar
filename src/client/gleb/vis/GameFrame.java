package client.gleb.vis;

import javax.swing.JFrame;

import shared.Cell;

public class GameFrame extends JFrame {
	private static final long serialVersionUID = 4501676717595892172L;
	private Map map;
	public GameFrame(String agentName) {
		setLayout(null);
		setTitle("Agent Name: " + agentName);
		setResizable(false);
		setBounds(100, 100, 180, 130);
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
