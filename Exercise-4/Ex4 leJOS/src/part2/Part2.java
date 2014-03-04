package part2;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import lejos.nxt.Button;
import established.GridTraveller;
import established.PathFollower;
import part1.UninformedSearch;
import rp13.search.problem.puzzle.EightPuzzle;
import rp13.search.problem.puzzle.EightPuzzle.PuzzleMove;
import grid.Grid.Direction;
import grid.Connection;
import grid.Grid;
import grid.GridSuccessorFunction;

/**
 * Part2 uses Part1 to find a path between two points, and passes on the list to a GridTraveller to make it traverse that path.
 * @author Jordan Bell
 *
 */
public class Part2{

	//Testing values
	public static Point start = new Point(0, 0);
	public static Point end = new Point(2, 2);
	public static Grid startState;
	public static Grid endState;
	
	/**
	 * Construct a Part 2 using a custom grid and custom directions.
	 */
	public Part2(Grid grid, Point start, Point end)
	{
		startState = new Grid(grid, start);
		endState = new Grid(grid, end);
	}
	
	/**
	 * Construct a grid using the hard-coded default values.
	 */
	public Part2()
	{
		Connection[] blockages = Grid.getTestingBlockages();
		startState = new Grid(blockages, start, 5, 5);
		endState = new Grid(blockages, end, 5, 5);
	}

	/**
	 * Run by searching for the solution
	 */
	public void run() {
		
		//(Whatever way) a new search is done
		Button.waitForAnyPress();
		UninformedSearch<Direction, Grid> s = new UninformedSearch<Direction, Grid>(startState, endState, UninformedSearch.SearchType.BreadthFirst);
		GridSuccessorFunction succFunct = new GridSuccessorFunction();
		List<Direction> actionList = s.search(succFunct);
		
		//Test values
//		List<Direction> actionList = new ArrayList<Direction>();
//		actionList.add(Direction.UP); //0 FORWARD
//		actionList.add(Direction.RIGHT); //1 RIGHT
//		actionList.add(Direction.RIGHT); //0 FORWARD
//		actionList.add(Direction.UP); //2 LEFT
//		actionList.add(Direction.LEFT); //2 LEFT
//		actionList.add(Direction.UP); //1 RIGHT
//		actionList.add(Direction.LEFT); //2 LEFT

		GridTraveller traveller = new GridTraveller(actionList);
		traveller.runActions();
	}
	
	/**
	 * Run a Searching and Finding robot
	 */
	public static void main(String[] args) {
		Part2 part2 = new Part2();
		part2.run();
	}
}
