package agent;

import java.awt.Color;
import java.awt.Dimension;
import java.util.List;

import agent.ant.RandomAnt;

import model.AntModel;

import jade.core.behaviours.Behaviour;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;

public class CreateAntsBehavior extends Behaviour{
	private int cur = 0;
	private int size = 0;
	private Color color = Color.RED;
	private Dimension center = new Dimension(50, 50);
	private String antName = "ant-red-";
	private List<AntModel> ants;
	private String className = RandomAnt.class.getName();
	private AgentContainer c;
	public CreateAntsBehavior(AgentContainer c, List<AntModel> ants) {
		this.c = c;
		this.ants = ants;
	}
	@Override
	public void action() {
		String name = antName + cur ;
        try {
        	AntModel antModel = new AntModel();
        	antModel.setColor(color);
        	antModel.setX(center.width);
        	antModel.setY(center.height);
        	antModel.setId(name);
        	ants.add(antModel);
            AgentController a = c.createNewAgent( name, className, null );
            a.start();
            
        }
        catch (Exception e){}
        ++cur;
	}

	@Override
	public boolean done() {
		return cur == size;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Dimension getCenter() {
		return center;
	}

	public void setCenter(Dimension center) {
		this.center = center;
	}

	public String getAntName() {
		return antName;
	}

	public void setAntName(String antName) {
		this.antName = antName;
	}

	public List<AntModel> getAnts() {
		return ants;
	}

	public void setAnts(List<AntModel> ants) {
		this.ants = ants;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public AgentContainer getC() {
		return c;
	}

	public void setC(AgentContainer c) {
		this.c = c;
	}
	
}
