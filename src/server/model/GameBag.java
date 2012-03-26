package server.model;

import java.util.List;

public class GameBag {
	private java.util.Map<String, AntCoockie> antCoockies;
	private List<HillCoockie> hillCoockies;
	private Map staticMap;
	private long glebLastN;
	private long olegLastN;
	private int unbornGlebsAnt;
	private int unbornOlegsAnt;
	private int countOfGlebAnts;
	private int countOfOlegAnts;
	private int countOfGlebHills;
	private int countOfOlegHills;
	
	
	
	public GameBag() {
		glebLastN = 0;
		olegLastN = 0;
		unbornGlebsAnt = 0;
		unbornOlegsAnt = 0;
		countOfGlebAnts = 0;
		countOfGlebHills = 0;
		countOfOlegAnts = 0;
		countOfOlegHills = 0;
	}
	
	
	public int getUnbornGlebsAnt() {
		return unbornGlebsAnt;
	}


	public void setUnbornGlebsAnt(int unbornGlebsAnt) {
		this.unbornGlebsAnt = unbornGlebsAnt;
	}


	public int getUnbornOlegsAnt() {
		return unbornOlegsAnt;
	}


	public void setUnbornOlegsAnt(int unbornOlegsAnt) {
		this.unbornOlegsAnt = unbornOlegsAnt;
	}


	public void incrementGleb(){
		++glebLastN;
	}
	public void incrementOleg(){
		++olegLastN;
	}
	public void decrementGleb(){
		--glebLastN;
	}
	public void decrementOleg(){
		--olegLastN;
	}
	public long getGlebLastN() {
		return glebLastN;
	}
	public long getAndIncGleb(){
		long l = glebLastN;
		++glebLastN;
		return l;
	}
	public long getAndIncOleg(){
		long l = olegLastN;
		++olegLastN;
		return l;
	}
	public void setGlebLastN(long glebLastN) {
		this.glebLastN = glebLastN;
	}
	public long getOlegLastN() {
		return olegLastN;
	}
	public void setOlegLastN(long olegLastN) {
		this.olegLastN = olegLastN;
	}
	public java.util.Map<String, AntCoockie> getAntCoockies() {
		return antCoockies;
	}
	public void setAntCoockies(java.util.Map<String, AntCoockie> antCoockies) {
		this.antCoockies = antCoockies;
	}
	public List<HillCoockie> getHillCoockies() {
		return hillCoockies;
	}
	public void setHillCoockies(List<HillCoockie> hillCoockies) {
		this.hillCoockies = hillCoockies;
	}
	public Map getStaticMap() {
		return staticMap;
	}
	public void setStaticMap(Map staticMap) {
		this.staticMap = staticMap;
	}


	public int getCountOfGlebAnts() {
		return countOfGlebAnts;
	}


	public void setCountOfGlebAnts(int countOfGlebAnts) {
		this.countOfGlebAnts = countOfGlebAnts;
	}


	public int getCountOfOlegAnts() {
		return countOfOlegAnts;
	}


	public void setCountOfOlegAnts(int countOfOlegAnts) {
		this.countOfOlegAnts = countOfOlegAnts;
	}


	public int getCountOfGlebHills() {
		return countOfGlebHills;
	}


	public void setCountOfGlebHills(int countOfGlebHills) {
		this.countOfGlebHills = countOfGlebHills;
	}


	public int getCountOfOlegHills() {
		return countOfOlegHills;
	}


	public void setCountOfOlegHills(int countOfOlegHills) {
		this.countOfOlegHills = countOfOlegHills;
	}
	
	
}
