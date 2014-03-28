package Main;


import java.awt.Point;
import java.util.List;

import Interfaces.SuccessorFunction;
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
import established.GridTraveller;
import established.Robot;
import grid.GridBoard.Direction;
import grid.GridPuz;
import grid.GridSuccessorFunction;

public class LabyrinthRobot extends GridTraveller{
	
	Heading relativeNorth 		= Heading.MINUS_Y;
	Heading relativeOrientation = Heading.PLUS_X;
	protected boolean localised = false;
	private static final float localisationThreshold = 0.8f; // The probability needed for a single point, when considered "Localised"

	private Point startPoint = new Point(0, 0);
	private static final Point midPoint = new Point(0, 0);
	private static final Point endPoint = new Point(0, 0);
	
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

		while (_run && !localised) {
			// Choose next direction to move
			Heading action = nextAction();
			// TODO Move there
			
			//================ ACTION ================\\
			distribution = actionModel.updateAfterMove(distribution, action);
			distribution.normalise();

			
			if (distribution.getHighestProb() >= localisationThreshold ) { localised = true; }
			
			//================ SENSING ================\\
			sensorModel.updateDistributionAfterSensing(distribution, 
													   gridMeasurements, 
													   getMeasurements());
			distribution.normalise();
			
			if (distribution.getHighestProb() >= localisationThreshold ) { localised = true; }
		}
		
		// Localised! Now make your way to the target
		startPoint = distribution.getLikelyPosition(); // TODO Start should be inherited from the superclass
		traverseSolution();
	}
	
	
	private void traverseSolution() {
		List<Direction> path = LabyrinthRobot.solveGrid2(startPoint, midPoint, endPoint); // TODO Repurpose Part 2 of the last exercise into the GridFollower, and create this method.
		setActionList(path);
		runActions();
	}


	//===================== MOVEMENTS =====================\\
	
	private Heading nextAction()
	{
		// TODO Write code that chooses the motions used to localise. May be more or less arbitrary.
		if (canMoveForward()) return Heading.PLUS_X;
		else 
		{
			int turnCounter = 0;
			
			// Turn right until there is somewhere to move
			while (!canMoveForward())
			{
				if (turnCounter < 4)
				{
					turnRight();
					turnCounter++;
				}
			}
			
			switch (turnCounter)
			{
			case 1:
				return Heading.PLUS_Y;
			case 2:
				return Heading.MINUS_X;
			case 3:
				return Heading.MINUS_Y;
			default:
				throw new AssertionError("Stuck in a box, with some BACON PANCAKES.");
			}
		}
		
	}
	
	private boolean canMoveForward()
	{
		return HEAD.getDistance() > 20;
	}
	
	// Find a destination
	public static List<Direction> solveGrid2(Point startpoint, Point midpoint, Point endpoint)
	{
		GridPuz start = new GridPuz(startpoint);
		GridPuz mid = new GridPuz(midpoint);
		GridPuz end = new GridPuz(endpoint);
		
		SuccessorFunction<Direction, GridPuz> succfunc = new GridSuccessorFunction();
		
		// Choose the kind of search
		Search.SearchType searchChoice = Search.SearchType.AStar;
		
		// Get to the mid
		Search<Direction> startToMidSearch = new Search(start, mid, searchChoice);
		
		// Get to the end
		Search<Direction> midToEndSearch = new Search<>(mid, end, searchChoice);
		
		List<Direction> solutionList = startToMidSearch.search(succfunc, searchChoice);
		// A null value, to show that we're halfway done.
		solutionList.add(null);
		// Directions to the end.
		solutionList.addAll(midToEndSearch.search(succfunc, searchChoice));
		
		return solutionList;
	}
	
	public static void playVictorySong()
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
