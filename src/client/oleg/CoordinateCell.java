package client.oleg;

import shared.Cell;
import shared.Cell.Type;
import shared.Coordinate;

public class CoordinateCell {
	Cell cell;
	Coordinate coordinate;
	public Cell getCell() {
		return cell;
	}
	public void setCell(Cell cell) {
		this.cell = cell;
	}
	public Coordinate getCoordinate() {
		return coordinate;
	}
	public void setCoordinate(Coordinate coordinate) {
		this.coordinate = coordinate;
	}
	public CoordinateCell(Cell cell, Coordinate coordinate) {
		super();
		this.cell = cell;
		this.coordinate = coordinate;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CoordinateCell other = (CoordinateCell) obj;
		if (cell == null) 
			if (other.cell != null)
				return false;
		if (coordinate == null) 
			if (other.coordinate != null)
				return false;
		if(coordinate.getX() == other.getCoordinate().getX() && coordinate.getY() == other.getCoordinate().getY())
			return true;
		if(cell.getType().equals(Type.FRIEND_ANT) || cell.getType().equals(Type.ENEMY_ANT))
			if(cell.getAntName().equals(other.getCell().getAntName()))
			return true;
		return false;
	}
	
}
