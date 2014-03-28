package rp.robotics.mapping;

public class BooleanGridPosition {
	protected int _x;
	protected int _y;
	protected GridMap grid;
	protected BooleanMeasurements dm;
	
	public BooleanGridPosition(int x, int y, GridMap grid) {
		this._x = x;
		this._y = y;
		this.grid = grid;
		
		// Save the directional measurements to this data class.
		calcDirections();
	}
	
	public BooleanMeasurements getDM()
	{
		return dm;
	}
	
	protected void calcDirections()
	{
		boolean north;
		boolean south;
		boolean east;
		boolean west;
		
		// Save all directions
		north = grid.rangeToObstacleFromGridPoint(_x, _y, Heading.MINUS_Y) < 200;
		east =  grid.rangeToObstacleFromGridPoint(_x, _y, Heading.PLUS_X) < 200;
		south = grid.rangeToObstacleFromGridPoint(_x, _y, Heading.PLUS_Y) < 200;
		west =  grid.rangeToObstacleFromGridPoint(_x, _y, Heading.MINUS_X) < 200;

		
		dm = new BooleanMeasurements(north, south, east, west);
	}
}
