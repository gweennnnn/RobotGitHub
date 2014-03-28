package rp.robotics.localisation;

import Main.LabyrinthRobot;
import rp.robotics.mapping.BooleanGridPosition;
import rp.robotics.mapping.BooleanMeasurements;
import rp.robotics.mapping.BooleanWallGrid;
import rp.robotics.mapping.DirectionMeasurements;
import rp.robotics.mapping.MeasuredGrid;

public class BooleanSensorModel {
	
// TODO Run the robot along a wall, measure known distances and compare the values that the sensor returns.
// TODO Then, create a range of probabilities based on those inaccuracies.
	
	/**
	 * Changes a given GPDistribution based on the results of a sensor model.
	 * @param dm The measurements taken by the sensor.
	 */ // TODO Change robot argument to measurements argument, calc measurements before this call.
	public void updateDistributionAfterSensing(GridPositionDistribution currentDist,
											   BooleanWallGrid mg,
											   BooleanMeasurements dm) {

		/*
		 * The position probability variables (that the robot is in that
		 * position) for each position
		 */
		float posProb =0;
		float equalityProb =1; // The probability that the dm measured distances equal the measurements at a given point.

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
					BooleanMeasurements trueDistances = mg.getMeasurementsAt(x, y);
					posProb = currentDist.getProbability(x, y);
					// Do the measurements match?
					
					//if a measurement matches multiplie the probability as of 0.9
					// if it doesn't - multiply for 0.1
					//that will set up the probability of sensing the walls around it correctly
					
					//check north values
//					if((dm.north && trueDistances.north) || (!dm.north && !trueDistances.north)){
					if((dm.north && trueDistances.north) ||
					   (dm.east && trueDistances.east) ||
					   (dm.south && trueDistances.south) ||
					   (dm.west && trueDistances.west) ||
					   ((!dm.north && !trueDistances.north) && (!dm.south && !trueDistances.south) && (!dm.east && !trueDistances.east) && (!dm.west && !trueDistances.west))
					   ){
						posProb *= equalityProb * 16f;
					}
						
					// Find the probability of being in this position, given those measurements
					
						
					currentDist.setProbability(x, y, posProb);
				}
			}
		}

//		currentDist.normalise();
	}
}



