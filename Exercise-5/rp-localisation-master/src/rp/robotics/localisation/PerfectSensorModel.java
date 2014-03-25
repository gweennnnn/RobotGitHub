package rp.robotics.localisation;

import java.util.Random;

import rp.robotics.mapping.DirectionMeasurements;
import rp.robotics.mapping.GridMap;
import rp.robotics.mapping.MeasuredGrid;


/**
 * An example of how you could start writing an action model given the available
 * classes.
 * 
 * @author nah
 * 
 */
public class PerfectSensorModel {

	protected static float accuracy = 1;
	
	/**
	 * Changes a given GPDistribution based on the results of a sensor model.
	 * @param dm The measurements taken by the sensor.
	 */
	public void updateDistributionAfterSensing(GridPositionDistribution currentDist,
											   MeasuredGrid mg) {

		// Commented out the random code to stop people using it without looking

		float prob;
		// iterate through points updating as appropriate
		for (int x = 0; x < currentDist.getGridWidth(); x++) {
			for (int y = 0; y < currentDist.getGridHeight(); y++) {
				// make sure to respect obstructed grid points
				if (!currentDist.isObstructed(x, y)) {
					
					// The measured distances
//					DirectionMeasurements dm = robot.getMeasurements();
					DirectionMeasurements dm = mg.getMeasurementsAt(x, y);
					// The actual distances at this position
					DirectionMeasurements trueDistances = mg.getMeasurementsAt(x, y);
					// Do they match?
					boolean measurementsMatchPosition = dm.equals(trueDistances);
					prob = currentDist.getProbability(x, y);
					
					float newProb = prob * (measurementsMatchPosition ? 1 : 0);
					currentDist.setProbability(x, y, newProb);
				}
			}
		}

		currentDist.normalise();
	}
}
