package server.model;

import java.util.List;

public class GameBag {
	private java.util.Map<String, AntCoockie> antCoockies;
	private List<HillCoockie> hillCoockies;
	private Map staticMap;
	private long glebLastN;
	private long olegLastN;
	public GameBag() {
		glebLastN = 0;
		olegLastN = 0;
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
	
}
