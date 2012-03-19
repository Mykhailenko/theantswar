package model;

import java.awt.Color;

public class AntModel {
	private String id;
	private String idKolonii;
	private int x;
	private int y;
	private Color color = Color.RED;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIdKolonii() {
		return idKolonii;
	}
	public void setIdKolonii(String idKolonii) {
		this.idKolonii = idKolonii;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	
}
