package server.visual;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.TextField;
import java.util.List;

import javax.swing.JFrame;

import model.AntModel;

public class GameFrame extends JFrame implements GFrame{
	private Map map;
	public GameFrame() {
		setLayout(null);
		setTitle("The Ant Wars");
		setResizable(false);
		setBounds(100, 100, 400, 450);
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

	@Override
	public void paint(server.model.Map map) {
		this.map.paint(map);
		
	}
	
}
