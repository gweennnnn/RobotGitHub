package rp.robotics.mapping;

public class DirectionMeasurements {
	public float north;
	public float south;
	public float east;
	public float west;
	
	public DirectionMeasurements(float north, float south, float east, float west) {
		super();
		this.north = north;
		this.south = south;
		this.east = east;
		this.west = west;
	}

	@Override
	public boolean equals(Object arg0) {
		boolean isEqual = true;
		
		// Write equality calculation
		
		return isEqual;
	}
	
	public String toString()
	{
		return "N: " + north + " S: " + south + " E: " + east + " W: " + west;
	}
}