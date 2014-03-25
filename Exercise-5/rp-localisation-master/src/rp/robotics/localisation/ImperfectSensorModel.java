package rp.robotics.localisation;

import Main.LabyrinthRobot;
import rp.robotics.mapping.DirectionMeasurements;
import rp.robotics.mapping.MeasuredGrid;


/**
 * An example of how you could start writing an action model given the available
 * classes.
 * 
 * @author nah
 * 
 */
public class ImperfectSensorModel {

	// TODO Run the robot along a wall, measure known distances and compare the values that the sensor returns.
	// TODO Then, create a range of probabilities based on those inaccuracies.
	
	/**
	 * Changes a given GPDistribution based on the results of a sensor model.
	 * @param dm The measurements taken by the sensor.
	 */
	public void updateDistributionAfterSensing(GridPositionDistribution currentDist,
											   MeasuredGrid mg,
											   LabyrinthRobot robot) {
		
		// The probability variable (that the robot is in that position) for each position
		float positionProbability;

		// Test using the measurements at the robot's location.
		DirectionMeasurements dm = robot.getMeasurements();

		// Iterate through points updating as appropriate
		for (int x = 0; x < currentDist.getGridWidth(); x++) 
		{
			for (int y = 0; y < currentDist.getGridHeight(); y++) 
			{
				// Continue if valid position
				if (!currentDist.isObstructed(x, y)) 
				{
					// True distances from this position
					DirectionMeasurements trueDistances = mg.getMeasurementsAt(x, y);
					// Do the measurements match?
					float equalityProbability = dm.getEqualityProbability(trueDistances);
					
					positionProbability = currentDist.getProbability(x, y);
					
					float newProb = positionProbability * equalityProbability;
					currentDist.setProbability(x, y, newProb);
				}
			}
		}

		currentDist.normalise();
	}
}
