package Main;


import lejos.nxt.Sound;
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
 // TODO Extend the class that runs and traverses the grid search. 
public class LabyrinthRobot extends /*GridFollower*/ Robot{
	
	Heading relativeNorth 		= Heading.MINUS_Y;
	Heading relativeOrientation = Heading.PLUS_X;
	protected boolean localised = false;
	private static final float localisationThreshold = 0.8f; // The probability needed for a single point, when considered "Localised"
	
	
	//===================== MAIN =====================\\
	
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
			if (!localised){
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
					start = distribution.getLikelyPosition(); // TODO Start should be inherited from the superclass
				}
			}
			
			// Localised! Now make your way to the target
			runSearchTraversal(); // TODO Repurpose Part 2 of the last exercise into the GridFollower, and create this method.
			playVictorySong();
		}
	}
	
	
	//===================== MOVEMENTS =====================\\
	
	private Heading nextAction()
	{
		// TODO Write code that chooses the motions used to localise. May be more or less arbitrary.
		return Heading.values()[0];
	}
	
	private void playVictorySong()
	{
		Sound.playNote(Sound.FLUTE, 784, 90);
		Delay.msDelay(4);
		Sound.playNote(Sound.FLUTE, 740, 90);
		Delay.msDelay(4);
		Sound.playNote(Sound.FLUTE, 622, 90);
		Sound.playNote(Sound.FLUTE, 440, 90);
		Delay.msDelay(4);
		Sound.playNote(Sound.FLUTE, 415, 90);
		Delay.msDelay(4);
		Sound.playNote(Sound.FLUTE, 659, 90);
		Delay.msDelay(4);
		Sound.playNote(Sound.FLUTE, 831, 90);
		Sound.playNote(Sound.FLUTE, 1047, 270);
	}
	
	//===================== MEASUREMENTS =====================\\
	
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
