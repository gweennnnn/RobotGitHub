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

	/**
	 * Changes a given GPDistribution based on the results of a sensor model.
	 * @param dm The measurements taken by the sensor.
	 */
	public void updateDistributionAfterSensing(GridPositionDistribution currentDist,
											   MeasuredGrid mg) {
		// New Probability Calculation, used for each point
		float oldProb;
		boolean measurementsMatchPosition;
		float newProb;

		// Test using a dummy position
		DirectionMeasurements dm = mg.getMeasurementsAt(3, 0);

		// Iterate through points updating as appropriate
		for (int x = 0; x < currentDist.getGridWidth(); x++) {
			for (int y = 0; y < currentDist.getGridHeight(); y++) {
				// make sure to respect obstructed grid points
				if (!currentDist.isObstructed(x, y)) {
					
					// The actual distances at this position
					DirectionMeasurements trueDistances = mg.getMeasurementsAt(x, y);
					// Do the measured distances match?
					measurementsMatchPosition = dm.equals(trueDistances);
					oldProb = currentDist.getProbability(x, y);
					
					newProb = oldProb * (measurementsMatchPosition ? 1 : 0);
					currentDist.setProbability(x, y, newProb);
				}
			}
		}

		currentDist.normalise();
	}
}
