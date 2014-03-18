package rp.robotics.mapping;

/**
 * A data class used to save the prewritten, known directional measurements for
 * each position on the grid.
 * 
 * This will save time for on-the-go, repeated calculation in the GridMap class,
 * by saving all of the data at the beginning of the run, instead of on the go.
 * 
 * @author Jordan Bell
 * 
 */
public class MeasuredGridPosition {
	protected int _x;
	protected int _y;
	protected GridMap grid;
	protected DirectionMeasurements dm;
	
	public MeasuredGridPosition(int x, int y, GridMap grid) {
		this._x = x;
		this._y = y;
		this.grid = grid;
		
		// Save the directional measurements to this data class.
		calcDirections();
	}
	
	public DirectionMeasurements getDM()
	{
		return dm;
	}
	
	protected void calcDirections()
	{
		float north;
		float south;
		float east;
		float west;
		
		// Save all directions
		north = grid.rangeToObstacleFromGridPoint(_x, _y, Heading.MINUS_Y);
		east =  grid.rangeToObstacleFromGridPoint(_x, _y, Heading.PLUS_X);
		south = grid.rangeToObstacleFromGridPoint(_x, _y, Heading.PLUS_Y);
		west =  grid.rangeToObstacleFromGridPoint(_x, _y, Heading.MINUS_X);
		
		dm = new DirectionMeasurements(north, south, east, west);
	}
}
