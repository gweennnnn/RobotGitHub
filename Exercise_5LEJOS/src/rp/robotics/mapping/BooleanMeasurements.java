package rp.robotics.mapping;

public class BooleanMeasurements{
	
	
	// Directional Values held by this data structure.
	public boolean north;
	public boolean south;
	public boolean east;
	public boolean west;
	
	/**
	 * Construct a new set of field measurements.
	 */
	public BooleanMeasurements(boolean north, boolean south, boolean east, boolean west) {
		this.north = north;
		this.south = south;
		this.east = east;
		this.west = west;
	}

	
	public boolean equals(BooleanMeasurements arg0) {
		BooleanMeasurements comparee = arg0;
		
		return  (comparee.north == north) &&
				(comparee.south == south) &&
				(comparee.east  == east)  &&
				(comparee.west  == west);
	}
}