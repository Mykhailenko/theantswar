package model;

import java.util.LinkedList;
import java.util.List;

public class MapModel {
	private String name;
	private List<AntModel> ants;
	private int width;
	private int heihgt;
	public MapModel() {
		this.width = 100;
		this.heihgt = 100;
		this.ants = new LinkedList<AntModel>();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<AntModel> getAnts() {
		return ants;
	}
	public void setAnts(List<AntModel> ants) {
		this.ants = ants;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeihgt() {
		return heihgt;
	}
	public void setHeihgt(int heihgt) {
		this.heihgt = heihgt;
	}
	
	
}
