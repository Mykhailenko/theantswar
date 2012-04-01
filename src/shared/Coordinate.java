package shared;

public class Coordinate {
	public int x;
	public int y;
	
	public Coordinate(int x, int y){
		this.x = x;
		this.y = y;
		
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
	public int distance(Coordinate coordinate){
		return Math.abs(this.x - coordinate.x) + Math.abs(this.y - coordinate.y);
	}
}