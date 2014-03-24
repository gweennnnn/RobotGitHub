package grid;

import java.awt.Point;

import Interfaces.PuzzleInterface;

public class GridPuz implements PuzzleInterface{
	public Point currPoint;
	
	public double getCurrX()
	{
		return currPoint.getX();
	}
	
	public double getCurrY()
	{
		return currPoint.getY();
	}
	
	
	@Override
	public int calculateValue() {
		double x = currPoint.getX();
		double y = currPoint.getY();
		
		double goal_x = GridBoard.getEndX();
		double goal_y = GridBoard.getEndY();
		
		double distance = Math.hypot(goal_x - x, goal_y - y);
		return (int)distance;
	}

	@Override
	public int costToMove(Object move) {
		return 1;
	}

	@Override
	public boolean equals(Object obj) {
		GridPuz x = (GridPuz) obj;
		
		if((x.getCurrX() == this.getCurrX()) && (x.getCurrY() == this.getCurrY()))
			return true;
		return false;
		
	}
	
	@Override
	public String toString() {
		return "Points: " + this.getCurrX() + ", " + this.getCurrY();
	}
}
