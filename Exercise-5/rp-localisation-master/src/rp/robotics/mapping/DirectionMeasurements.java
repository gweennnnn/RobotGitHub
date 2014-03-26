package rp.robotics.mapping;

public class DirectionMeasurements{
	
	/** The maximum distance detected by the robot's sensor */
	protected static final int MAX_DISTANCE = 255;
	
	// Directional Values held by this data structure.
	public float north;
	public float south;
	public float east;
	public float west;
	
	/**
	 * Construct a new set of field measurements.
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
	 * Returns a copy of this set of measurements, given a rotation angle
	 * (counter-clockwise)
	 */
	public DirectionMeasurements getRotation(int angle)
	{
		DirectionMeasurements r_measurements;
		
		// Normalise the angle to be between particular values
		while (angle >= 360) { angle -= 360; }
		while (angle < 0)    { angle += 360; }
		
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
			throw new IllegalArgumentException(angle + " is not a valid rotation angle. Must be 0, 90, 180 or 270 (+- 360n)");
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

		rotate(a-e);
	}
	
	public String toString()
	{
		return "N:" + north + ", S:" + south + ", E:" + east + ", W:" + west;
	}

	/**
	 * Use the sensor accuracies to determine the probability of equality with another set of DirectionMeasurements
	 * @param trueDistances The true DirectionMeasurements at a given position.
	 * @return The proability that the agent is in that given position, to match the measurements.
	 */
	public float getEqualityProbability(DirectionMeasurements trueDistances) {

		// Calculate the average difference between them
		float totalProbability = 1;
		totalProbability *= SensorInaccuracies.getProbabilityByDifference((north - trueDistances.north));
		totalProbability *= SensorInaccuracies.getProbabilityByDifference((south - trueDistances.south));
		totalProbability *= SensorInaccuracies.getProbabilityByDifference((east -  trueDistances.east));
		totalProbability *= SensorInaccuracies.getProbabilityByDifference((west -  trueDistances.west));
			
		return totalProbability;
	}
}

class SensorInaccuracies
{
	private static SensorInaccuracies inaccuracies = new SensorInaccuracies();
	public DifferenceAccuracyPair[] DAs = new DifferenceAccuracyPair[5];
	
	private SensorInaccuracies()
	{
		// Between 0 and 0
		DAs[0] = new DifferenceAccuracyPair(0, 0,  0.80f);
		// Between 0 and 1
		DAs[1] = new DifferenceAccuracyPair(0, 1,  0.10f);
		// Between 1 and 3
		DAs[2] = new DifferenceAccuracyPair(1, 3,  0.05f);
		// Between 3 and 6
		DAs[3] = new DifferenceAccuracyPair(3, 6,  0.03f);
		// Between 6 and 10
		DAs[4] = new DifferenceAccuracyPair(6, 10, 0.02f);
	}
	
	/**
	 * Given a difference in measurements (between measured, and true values)
	 * return the probability that the agent is in its measured position.
	 * 
	 * @param difference
	 *            The difference between the measured distance, and the true
	 *            distance.
	 * @return The probability that the agent is in a given position, depending
	 *         on that difference.
	 */
	public static float getProbabilityByDifference(float difference)
	{
		difference = Math.abs(difference);
		for (DifferenceAccuracyPair dap : inaccuracies.DAs){
			if (dap.matchesDifference(difference)){
				return dap.accuracy;
			}
		}
		
		return 0; // Not within known ranges, consider it 0%
	}
}

/**
 * A pair: difference in measurements and corresponding accuracy.
 * @author Jordan Bell
 *
 */
class DifferenceAccuracyPair
{
	private int minDifference; // Exclusive
	private int maxDifference; // Inclusive
	public 	float accuracy;
	
	/**
	 * Construct a new pair: difference and corresponding accuracy.
	 * @param minDifference The minimum difference bound.
	 * @param maxDifference The maximum difference bound.
	 * @param accuracy The probability that the measured value represents the true value.
	 */
	public DifferenceAccuracyPair(int minDifference, int maxDifference, float accuracy)
	{
		this.minDifference = minDifference;
		this.maxDifference = maxDifference;
		this.accuracy = accuracy;
	}
	
	/**
	 * Calculate if a difference is contained within this DAP.
	 * @param difference The difference being checked for
	 * @return If that difference is within the ranges set.
	 */
	public boolean matchesDifference(float difference)
	{
		boolean withinRange = (difference >  minDifference) &&
							  (difference <= maxDifference);
		
		// There is an exception if the max and min are equal, and hence would never fulfil the above predicate.
		if (minDifference == maxDifference) withinRange = (difference == maxDifference);

		return withinRange;
	}
}
