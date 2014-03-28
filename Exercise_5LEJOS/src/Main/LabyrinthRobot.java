package Main;


import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import Interfaces.SuccessorFunction;
import lejos.nxt.Sound;
import lejos.util.Delay;
import rp.robotics.localisation.ActionModel;
import rp.robotics.localisation.BooleanSensorModel;
import rp.robotics.localisation.GridPositionDistribution;
import rp.robotics.localisation.ImperfectSensorModel;
import rp.robotics.localisation.PerfectActionModel;
import rp.robotics.localisation.PerfectSensorModel;
import rp.robotics.mapping.BooleanMeasurements;
import rp.robotics.mapping.BooleanWallGrid;
import rp.robotics.mapping.DirectionMeasurements;
import rp.robotics.mapping.GridMap;
import rp.robotics.mapping.Heading;
import rp.robotics.mapping.LocalisationUtils;
import rp.robotics.mapping.MeasuredGrid;
//import rp.robotics.visualisation.GridPoseDistributionVisualisation;s
import established.GridTraveller;
import established.LineFollower;
import established.Robot;
import grid.GridBoard.Direction;
import grid.GridBoard;
import grid.GridPuz;
import grid.GridSuccessorFunction;

public class LabyrinthRobot extends LineFollower {
	
//	public LabyrinthRobot(List<Direction> actionList) {
//		super(actionList);
//	}

	Heading relativeNorth = Heading.MINUS_Y;
	Heading relativeOrientation = Heading.PLUS_X;
	protected boolean localised = false;
	private static final float localisationThreshold = 0.45f; // The probability needed for a single point, when considered "Localised"

	private Point startPoint = new Point(2, 2);
	private static final Point midPoint = new Point(1, 1);
	private static final Point endPoint = new Point(5, 3);
	protected Heading currentHeading = Heading.PLUS_X;
	int x = -1;
	int y = -1;
	float z = -1f;
	GridPositionDistribution distribution;
	ArrayList values;
	//===================== MAIN =====================\\
	
	public void run()
	{
		init();
		
		// leJOS Grid
		GridMap gridMap = LocalisationUtils.create2014Map1();
//		System.out.println("created map");
		

		// The probability distribution over the robot's location
		distribution = new GridPositionDistribution(
				gridMap);
//		System.out.println("created grid position distribution");
		
//		MeasuredGrid gridMeasurements = new MeasuredGrid(gridMap);
		BooleanWallGrid gridMeasurements = new BooleanWallGrid(gridMap);
//		System.out.println("created measured grid");

		// view the map with 2 pixels as 1 cm
//		GridPoseDistributionVisualisation mapVis = new GridPoseDistributionVisualisation(
//				distribution, 2);

		// ActionModel & SensorModel
//		ActionModel actionModel = new PerfectActionModel();
////		System.out.println("perfect action model");
////		ImperfectSensorModel sensorModel = new ImperfectSensorModel();
//		
//		BooleanSensorModel sensorModel = new BooleanSensorModel();
////		System.out.println("imperfect sensor model");
//
//		while (_run && !localised) {
//			// Choose next direction to move
////			System.out.println("created map");
//			Heading action = nextAction(currentHeading);
//			currentHeading = action;
////			System.out.println("get new action");
//			
//			// TODO Move there
//			
//			//================ ACTION ================\\
//			distribution = actionModel.updateAfterMove(distribution, action);
//			
//			System.out.println("---------------");
//			System.out.println("We Moved : "+ action);
//			
////			System.out.println("update after move");
//			distribution.normalise();
////			System.out.println("normalise");
//			
//			if(this.haveWeFoundOurselves())
//			{
//				localised = true; 
//				System.out.println("WE FOUND OURSELVES");
//				Sound.beep();
//				System.out.println("Coord: " + x + "," + y);
//				waitForPress();
//				break;
//			}
//			
//			//================ SENSING ================\\
//			//System.out.println("pre sensor");
//			sensorModel.updateDistributionAfterSensing(distribution, 
//													   gridMeasurements, 
//													   getMeasurements(action));
//			
////			System.out.println("post sensor");
//			distribution.normalise();
////			System.out.println("normalise");
//			values = distribution.getHighestProb();
//			x = (int) values.get(0);
//			y = (int) values.get(1);
//			z = (float) values.get(2);
//			
//			if(this.haveWeFoundOurselves())
//			{
//				localised = true; 
//				System.out.println("WE FOUND OURSELVES");
//				Sound.beep();
//				System.out.println("Coord: " + x + "," + y);
//				waitForPress();
//				break;
//			}
//		}
//		
//		//make sure our robot is facing up for the search
//		if(currentHeading.equals(Heading.PLUS_X)){
//			turnLeft();
//		}
//		else if(currentHeading.equals(Heading.PLUS_Y)){
//			turnRound();
//		}
//		else if(currentHeading.equals(Heading.MINUS_X)){
//			turnRight();
//		}
		
		// Localised! Now make your way to the target
		startPoint = new Point(8, 4); // TODO Start should be inherited from the superclass
		
		traverseSolution(startPoint, midPoint);
		stop();
		playVictorySong();
		waitForPress();
		traverseSolution(midPoint, endPoint);
		stop();
		playVictorySong();
		waitForPress();
	}
	
	
	private boolean haveWeFoundOurselves()
	{
		values = distribution.getHighestProb();
	
		x = (int) values.get(0);
		y = (int) values.get(1);
		z = (float) values.get(2);
//		System.out.println("PROB: " + z);
//		System.out.println("X: " + x);
//		System.out.println("Y: " + y);
		if (z >= localisationThreshold )
			return true;
		return false;
	}
	
	private void traverseSolution(Point start, Point end) {
		List<Direction> path = LabyrinthRobot.solveGrid(start, end); // TODO Repurpose Part 2 of the last exercise into the GridFollower, and create this method.
		waitForPress();
		GridTraveller traveller = new GridTraveller(path);
		
		traveller.runActions();
	}


	//===================== MOVEMENTS =====================\\
	private boolean isItOnALine()
	{
		boolean lineMid = s_mid.ls.getLightValue() < s_mid.threshold;
		boolean lineLeft = s_left.ls.getLightValue() < s_left.threshold;
		boolean lineRight = s_right.ls.getLightValue() < s_right.threshold;
		
		if(lineLeft && lineRight)
			return false;
		else if (!lineLeft && !lineRight && !lineMid){
			turnRound();
			return false;
		}
		else 
			return true;
	}
	
	private Heading nextAction(Heading heading)
	{
		// TODO Write code that chooses the motions used to localise. May be more or less arbitrary.
		if (canMoveForward()) {
			
			while(isItOnALine())
				followLine();
			
			stop();
			goForward(50);
			return heading;
		}
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
			if (heading.equals(Heading.PLUS_X))
			{
				switch (turnCounter)
				{
				case 1:{
				
					
					while(isItOnALine())
						followLine();
					
					stop();
					goForward(50);
					return Heading.PLUS_Y;}
				case 2:{
					
					
					while(isItOnALine())
						followLine();
					stop();
					goForward(50);
					return Heading.MINUS_X;
				}
					
				case 3:{
									
					while(isItOnALine())
						followLine();
					stop();
					goForward(50);
					return Heading.MINUS_Y;
				}
				default:
					throw new AssertionError("Stuck in a box, with some BACON PANCAKES.");
				}
			}
			else if (heading.equals(Heading.PLUS_Y)){
				switch (turnCounter)
				{
				case 1:{
				
					
					while(isItOnALine())
						followLine();
					
					stop();
					goForward(50);
					return Heading.MINUS_X;}
				case 2:{
					
					
					while(isItOnALine())
						followLine();
					stop();
					goForward(50);
					return Heading.MINUS_Y;
				}
					
				case 3:{
									
					while(isItOnALine())
						followLine();
					stop();
					goForward(50);
					return Heading.PLUS_X;
				}
				default:
					throw new AssertionError("Stuck in a box, with some BACON PANCAKES.");
				}
			}
			
			else if (heading.equals(Heading.MINUS_X)){
				switch (turnCounter)
				{
				case 1:{
				
					
					while(isItOnALine())
						followLine();
					
					stop();
					goForward(50);
					return Heading.MINUS_Y;}
				case 2:{
					
					
					while(isItOnALine())
						followLine();
					stop();
					goForward(50);
					return Heading.PLUS_X;
				}
					
				case 3:{
									
					while(isItOnALine())
						followLine();
					stop();
					goForward(50);
					return Heading.PLUS_Y;
				}
				default:
					throw new AssertionError("Stuck in a box, with some BACON PANCAKES.");
				}
			}
			
			//MINUS Y
			else{
				switch (turnCounter)
				{
				case 1:{
				
					
					while(isItOnALine())
						followLine();
					
					stop();
					goForward(50);
					return Heading.PLUS_X;}
				case 2:{
					
					
					while(isItOnALine())
						followLine();
					stop();
					goForward(50);
					return Heading.PLUS_Y;
				}
					
				case 3:{
									
					while(isItOnALine())
						followLine();
					stop();
					goForward(50);
					return Heading.MINUS_X;
				}
				default:
					throw new AssertionError("Stuck in a box, with some BACON PANCAKES.");
				}
			}
		}
		
	}
	
	private boolean canMoveForward()
	{
		int value = HEAD.getDistance();
//		System.out.println("Distance :" + value);
		return value > 200;
	}
	
	public static List<Direction> solveGrid(Point startpoint, Point endpoint)
	{
		GridPuz start = new GridPuz(startpoint);
		GridPuz end = new GridPuz(endpoint);
		SuccessorFunction<Direction, GridPuz> succfunc = new GridSuccessorFunction();
		
		// Choose the kind of search
		Search.SearchType searchChoice = Search.SearchType.AStar;
		
		// Get to the mid
		Search<Direction> startToMidSearch = new Search(start, end, searchChoice);
		return startToMidSearch.search(succfunc, searchChoice);
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
		
		if(!solutionList.get(solutionList.size()-1).equals(Direction.UP))
			solutionList.add(Direction.UP);
		// A null value, to show that we're halfway done.
		solutionList.add(null);
		// Directions to the end.
		solutionList.addAll(midToEndSearch.search(succfunc, searchChoice));
		
		return solutionList;
	}
	
	
	
	//===================== MEASUREMENTS =====================\\
	
	/**
	 * Assume that you are facing North, before taking the measurements.
	 * NOT ANYMORE
	 */
	private BooleanMeasurements getStandardMeasurements(Heading action)
	{
		boolean north, south, east, west;
		
		//if the robot is facing north MINUS_Y		
		if (action.equals(Heading.MINUS_Y)){
			north = measureAndTurn();
			east = measureAndTurn();
			south = measureAndTurn();
			west = measureAndTurn();
		}
		//east PLUS_X
		else if(action.equals(Heading.PLUS_X)){
			east = measureAndTurn();
			south = measureAndTurn();
			west = measureAndTurn();
			north = measureAndTurn();
		}
		//south PLUS_Y
		else if(action.equals(Heading.PLUS_Y)){
			south = measureAndTurn();
			west = measureAndTurn();
			north = measureAndTurn();
			east = measureAndTurn();
		}
		//west MINUS_X
		else {
			west = measureAndTurn();
			north = measureAndTurn();
			east = measureAndTurn();
			south = measureAndTurn();
		}
				
		return new BooleanMeasurements(north, south, east, west);
	}
	
	/**
	 * Calculate the north, east, west and south measurements (assuming it knows the direciton of north)
	 * @return what is happening?\
	 * 
	 */
	public BooleanMeasurements getMeasurements(Heading action)
	{
		BooleanMeasurements dm = getStandardMeasurements(action);
		
		return dm;
	}
	
	private boolean measureAndTurn()
	{
		delay(500);
		boolean r_val = HEAD.getDistance() < 200;
		turnRight();
		
		return r_val;
	}
	
	public static void main(String[] args) {
		LabyrinthRobot robot = new LabyrinthRobot();
		robot.run();
	}

}
