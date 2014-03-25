package rp.robotics.mapping;

public class DirectionMeasurements{
	public float north;
	public float south;
	public float east;
	public float west;
	
	/**
	 * Construct a new set of measurements.
	 */
	public DirectionMeasurements(float north, float south, float east, float west) {
		this.north = north;
		this.south = south;
		this.east = east;
		this.west = west;
	}

	@Override
	public boolean equals(Object arg0) {
		DirectionMeasurements comparee = (DirectionMeasurements) arg0;
		
		return  (comparee.north == north) &&
				(comparee.south == south) &&
				(comparee.east  == east)  &&
				(comparee.west  == west);
	}
	
	/**
	 * Returns a copy of this set of measurements, given a rotation angle (clockwise)
	 */
	public DirectionMeasurements getRotation(int angle)
	{
		DirectionMeasurements r_measurements;
		
		// Change the directions accordingly.
		switch (angle)
		{
		case 0:
			r_measurements = new DirectionMeasurements(north, south, east, west);
			break;
		case 90:
			r_measurements = new DirectionMeasurements(east, west, south, north);
			break;
		case 180:
			r_measurements = new DirectionMeasurements(south, north, west, east);
			break;
		case 270:
			r_measurements = new DirectionMeasurements(west, east, north, south);
			break;
		default:
			throw new IllegalArgumentException(angle + " is not a valid rotation angle.");
		}
		
		return r_measurements;
	}
	
	/**
	 * Copy the measurements of another set of direction measurements.
	 */
	private void copy(DirectionMeasurements target) {
		this.north = target.north;
		this.south = target.south;
		this.east  = target.east;
		this.west  = target.west;
	}
	
	/**
	 * Change the measurements of this set based on an angle of rotation.
	 * @param angle
	 */
	public void rotate(int angle)
	{
		copy(getRotation(angle));
	}
	
	/**
	 * Corrects the measurements, based on an expected orientation, and the actual one.
	 * @param expectedHeading The expected orientation, which will be corrected.
	 * @param actualHeading The actual orientation, to be correct toward.
	 */
	public void correctOrientation(Heading expectedHeading, Heading actualHeading)
	{
		int e = (int) Heading.toDegrees(expectedHeading);
		int a = (int) Heading.toDegrees(actualHeading);
		
		rotate(e-a);
	}
	
	public String toString()
	{
		return "N: " + north + " S: " + south + " E: " + east + " W: " + west;
	}
}