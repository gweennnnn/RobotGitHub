package grid;

import grid.GridBoard.Direction;
import java.awt.Point;

import Interfaces.PuzzleInterface;

public class GridPuz implements PuzzleInterface{
	private Point currPoint;
	
	public GridPuz(GridPuz puz)
	{
		this(puz.currPoint);
	}
	
	public GridPuz(Point currPoint) {
		this.currPoint = currPoint;
	}
	
	public Point getCurrPoint()
	{
		return this.currPoint;
	}
	
	public double getCurrX()
	{
		return currPoint.getX();
	}
	
	public double getCurrY()
	{
		return currPoint.getY();
	}
	
	public boolean isPossibleMove(Direction move)
	{
		return GridBoard.isPossibleMove(move, currPoint);
	}
	
	public void makeMove(Direction move)
	{
		this.currPoint = GridBoard.makeMove(move, currPoint);
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
		
		if(x.getCurrPoint() == ((GridPuz) obj).getCurrPoint())
			return true;
		return false;
		
	}
	
	@Override
	public String toString() {
		return "Points: " + this.getCurrX() + ", " + this.getCurrY();
	}
}
