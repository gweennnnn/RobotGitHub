package rp.robotics.localisation;

import Main.LabyrinthRobot;
import rp.robotics.mapping.DirectionMeasurements;
import rp.robotics.mapping.MeasuredGrid;

public class Goda_Sensor_Model {
	

		// TODO Run the robot along a wall, measure known distances and compare the values that the sensor returns.
		// TODO Then, create a range of probabilities based on those inaccuracies.
		
		/**
		 * Changes a given GPDistribution based on the results of a sensor model.
		 * @param dm The measurements taken by the sensor.
		 */ // TODO Change robot argument to measurements argument, calc measurements before this call.
		public void updateDistributionAfterSensing(GridPositionDistribution currentDist,
												   MeasuredGrid mg,
												   DirectionMeasurements dm) {

			/*
			 * The position probability variables (that the robot is in that
			 * position) for each position
			 */
			float posProb;
			float equalityProb; // The probability that the dm measured distances equal the measurements at a given point.

			// Iterate through points updating as appropriate
			for (int x = 0; x < currentDist.getGridWidth(); x++) 
			{
				for (int y = 0; y < currentDist.getGridHeight(); y++) 
				{
					// Continue if valid position
					if (!currentDist.isObstructed(x, y)) 
					{
						// True distances from this position
						//DISTANCES FROM A MAP
						DirectionMeasurements trueDistances = mg.getMeasurementsAt(x, y);
						// Do the measurements match?
						equalityProb = dm.getEqualityProbability(trueDistances);
						
						// Find the probability of being in this position, given those measurements
						posProb = currentDist.getProbability(x, y);
						posProb *= equalityProb;
						
						currentDist.setProbability(x, y, posProb);
					}
				}
			}

			currentDist.normalise();
		}
	}

