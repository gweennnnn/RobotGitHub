package rp.robotics.mapping;

public class BooleanGridPosition {
	protected int _x;
	protected int _y;
	protected GridMap grid;
	protected BooleanMeasurements dm;

	private final int wallThreshold = 200;
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
		north = grid.rangeToObstacleFromGridPoint(_x, _y, Heading.MINUS_Y) < wallThreshold;
		east =  grid.rangeToObstacleFromGridPoint(_x, _y, Heading.PLUS_X) < wallThreshold;
		south = grid.rangeToObstacleFromGridPoint(_x, _y, Heading.PLUS_Y) < wallThreshold;
		west =  grid.rangeToObstacleFromGridPoint(_x, _y, Heading.MINUS_X) < wallThreshold;

		
		dm = new BooleanMeasurements(north, south, east, west);
	}
}
