package Main;


import lejos.util.Delay;
import rp.robotics.localisation.ActionModel;
import rp.robotics.localisation.GridPositionDistribution;
import rp.robotics.localisation.ImperfectSensorModel;
import rp.robotics.localisation.PerfectActionModel;
import rp.robotics.localisation.PerfectSensorModel;
import rp.robotics.mapping.DirectionMeasurements;
import rp.robotics.mapping.GridMap;
import rp.robotics.mapping.Heading;
import rp.robotics.mapping.LocalisationUtils;
import rp.robotics.mapping.MeasuredGrid;
import rp.robotics.visualisation.GridPoseDistributionVisualisation;
import established.Robot;

public class LabyrinthRobot extends /*GridFollower*/ Robot{
	
	Heading relativeNorth 		= Heading.MINUS_Y;
	Heading relativeOrientation = Heading.PLUS_X;
	protected boolean localised = false;
	private static final float localisationThreshold = 0.8f; // The probability needed for a single point, when considered "Localised"
	
	public void run()
	{
		// leJOS Grid
		GridMap gridMap = LocalisationUtils.create2014Map1();

		// The probability distribution over the robot's location
		GridPositionDistribution distribution = new GridPositionDistribution(
				gridMap);
		
		MeasuredGrid gridMeasurements = new MeasuredGrid(gridMap);

		// view the map with 2 pixels as 1 cm
		GridPoseDistributionVisualisation mapVis = new GridPoseDistributionVisualisation(
				distribution, 2);

		// ActionModel & SensorModel
		ActionModel actionModel = new PerfectActionModel();
		ImperfectSensorModel sensorModel = new ImperfectSensorModel();

		while (_run) {
			if (localised)
			{
				
			}
			else
			{
				// Choose next direction to move
				Heading action = nextAction();
				
				//================ ACTION ================\\
				distribution = actionModel.updateAfterMove(distribution, action);
				distribution.normalise();
				
				//================ SENSING ================\\
				sensorModel.updateDistributionAfterSensing(distribution, 
														   gridMeasurements, 
														   getMeasurements());
				distribution.normalise();
				
				if (distribution.getHighestProb() >= localisationThreshold )
				{
					localised = true;
					start = distribution.getLikelyPosition();
				}
			}
		}
	}
	
	/**
	 * Assume that you are facing North, before taking the measurements.
	 */
	private DirectionMeasurements getStandardMeasurements()
	{
		int north, south, east, west;

		north = measureAndTurn();
		east = measureAndTurn();
		south = measureAndTurn();
		west = measureAndTurn();
		
		return new DirectionMeasurements(north, south, east, west);
	}
	
	/**
	 * Calculate the north, east, west and south measurements (assuming it knows the direciton of north)
	 * @return
	 */
	public DirectionMeasurements getMeasurements()
	{
		DirectionMeasurements dm = getStandardMeasurements();
		
		dm.correctOrientation(relativeNorth, relativeOrientation);
		return dm;
	}
	
	private int measureAndTurn()
	{
		int r_val = HEAD.getDistance();
		turnRight();
		
		return r_val;
	}
	
	public static void main(String[] args) {
		LabyrinthRobot robot = new LabyrinthRobot();
		robot.run();
	}

}
