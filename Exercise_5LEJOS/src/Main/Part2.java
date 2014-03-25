package Main;
import established.GridTraveller;
import grid.GridBoard.Direction;
import grid.GridPuz;
import grid.GridSuccessorFunction;

import java.util.List;

import lejos.geom.Point;
import lejos.nxt.Button;
import lejos.nxt.Sound;
import lejos.util.Delay;
import Interfaces.SuccessorFunction;
import Main.Search.SearchType;

/**
 * Part2 uses Part1 to find a path between two points, and passes on the list to a GridPuzTraveller to make it traverse that path.
 * @author Jordan Bell
 * 
 *
 */
public class Part2{

	//Testing values
	public static Point start = new Point(0, 0);
	public static Point end = new Point(2, 2);
	public static GridPuz startState;
	public static GridPuz endState;
	
	/**
	 * Construct a Part 2 using a custom GridPuz and custom directions.
	 */
	public Part2(Point start, Point end)
	{
		startState = new GridPuz(start);
		endState = new GridPuz(end);
	}
	
	/**
	 * Run by searching for the solution
	 */
	public void run() {
		Search<Direction> s = new Search<Direction>(startState, endState, SearchType.AStar); 
		SuccessorFunction succFunct = new GridSuccessorFunction();
		System.out.println("Running Search!");
		Delay.msDelay(100);
		List<Direction> actionList = s.search(succFunct, SearchType.AStar);
		Sound.beep();
		System.out.println("ROUTE FOUND: "+ actionList);
		Delay.msDelay(100);
		
		//Test values
//		List<Direction> actionList = new ArrayList<Direction>();
//		actionList.add(Direction.UP); //0 FORWARD
//		actionList.add(Direction.RIGHT); //1 RIGHT
//		actionList.add(Direction.RIGHT); //0 FORWARD
//		actionList.add(Direction.UP); //2 LEFT
//		actionList.add(Direction.LEFT); //2 LEFT
//		actionList.add(Direction.UP); //1 RIGHT
//		actionList.add(Direction.LEFT); //2 LEFT
		Button.waitForAnyPress();
		
		GridTraveller traveller = new GridTraveller(actionList);
		
		traveller.runActions();
	}
	
	/**
	 * Run a Searching and Finding robot
	 */
	public static void main(String[] args) {
		Part2 part2 = new Part2(new Point(0, 0), new Point(2, 4));
		part2.run();
	}
}
