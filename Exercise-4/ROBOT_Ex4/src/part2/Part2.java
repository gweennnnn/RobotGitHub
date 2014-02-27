package part2;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import lejos.util.Matrix;
import established.PathFollower;
import part1.UninformedSearch;
import rp13.search.problem.puzzle.EightPuzzle;
import rp13.search.problem.puzzle.EightPuzzle.PuzzleMove;
import grid.Grid.Direction;
import grid.Connection;
import grid.Grid;

public class Part2{

	Point start = new Point(0, 0);
	Point end = new Point(2, 2);
	Grid startState;
	Grid endState;
	
	private static final int[][] relativityRegulator = new int[][]{new int[]{0, -1,  1,  2},
																   new int[]{-1, 0,  2,  1},
												       			   new int[]{2,  1,  0, -1},
													   			   new int[]{1,  2, -1,  0}
													   			  };
	
	public enum GridDirection
	{
		UP(0), DOWN(1), LEFT(2), RIGHT(3);
		private final int val;
		
		private GridDirection(int val) {
			this.val = val;
		}
		
		private static GridDirection initialOrientation()
		{
			return UP;
		}
		
	}
	
	/**
	 * Construct a grid
	 */
	public Part2()
	{
		Connection[] blockages = defaultBlockages();
		startState = new Grid(blockages, start, 5, 5);
		endState = new Grid(blockages, end, 5, 5);
	}
	
	private Connection[] defaultBlockages()
	{
		// Generate Blockages on the grid
		Point s1 = new Point(1, 4);
		Point e1 = new Point(2, 4);
		Connection b1 = new Connection(s1, e1);

		Point s2 = new Point(1, 0);
		Point e2 = new Point(1, 1);
		Connection b2 = new Connection(s2, e2);

		Point s3 = new Point(2, 1);
		Point e3 = new Point(3, 1);
		Connection b3 = new Connection(s3, e3);

		Point s4 = new Point(2, 2);
		Point e4 = new Point(2, 3);
		Connection b4 = new Connection(s4, e4);

		Point s5 = new Point(4, 2);
		Point e5 = new Point(4, 3);
		Connection b5 = new Connection(s5, e5);

		Connection[] blockages = new Connection[] { b1, b2, b3, b4, b5 };

//		Connection[] blockages = new Connection[] {};
		return blockages;
	}

	public void run() {
		
		//(Whatever way) a new search is done
		UninformedSearch<Direction, Grid> s = new UninformedSearch<Direction, Grid>(startState, Grid.Direction.START, endState, UninformedSearch.SearchType.BreadthFirst);
		////However the results are received
		List<Direction> actionList = s.search(startState, succFunct, endState, successors));
		
		
		
		String path = listToPath(actionList);
		PathFollower pf = new PathFollower(path);
		pf.runPath();
		
//		List<Direction> l = new ArrayList<Direction>();
//		l.add(Direction.UP);   //Forward
//		l.add(Direction.RIGHT);//Right
//		l.add(Direction.UP);   //Left
//		l.add(Direction.LEFT); //Left
//		l.add(Direction.DOWN); //Left
//		
//		String sPath = Part2.listToPath(l);
//		if (sPath.equals("01222")) System.out.println("Relativity Regulator fully functional");
	}
	
	/**
	 * Translates a list of Grid Directions into a readable string code to construct a Pathfollower Robot.
	 * @param actionList The List of Grid movement Directions being followed
	 * @return A string code for the NXT Pathfollower Robot to follow.
	 */
	public static String listToPath(List<Direction> actionList)
	{
		ArrayList<GridDirection> movements = new ArrayList<GridDirection>();
		ArrayList<GridDirection> orientations = new ArrayList<GridDirection>();
		
		//Add an initial element, allowing for the fact that any orientation will be the same as the previous direction travelled.
		orientations.add(GridDirection.initialOrientation());
		
		//Translate Grid Directions into GridMovement indices. 
		for (Direction d : actionList)
		{
			if (d == Direction.UP){
				movements.add(GridDirection.UP);
				orientations.add(GridDirection.UP);
			}
			else if (d == Direction.DOWN){
				movements.add(GridDirection.DOWN);
				orientations.add(GridDirection.DOWN);
			}
			else if (d == Direction.LEFT){
				movements.add(GridDirection.LEFT);
				orientations.add(GridDirection.LEFT);
			}
			else if (d == Direction.RIGHT){
				movements.add(GridDirection.RIGHT);
				orientations.add(GridDirection.RIGHT);
			}
		}
		
		String s = "";
		//Translate Direction and Orientation to String
		for (int i = 0; i < movements.size(); i++)
		{
			/* Index i contains the direction of the proposed movement (relative to the graph) 
			 * and the orientation of the robot at the time of proposed movement 
			 * (which will be the same direction as the previous movement)
			 */
			GridDirection movement = movements.get(i);
			GridDirection orientation = orientations.get(i);
			s += submitRelativity(movement, orientation);
		}
		return s;
	}
	
	private static String submitRelativity(GridDirection gridDirection, GridDirection orientation)
	{
		return submitRelativity(gridDirection.val, orientation.val);
	}
	
	private static String submitRelativity(int gridDirection, int orientation) throws IllegalArgumentException
	{
		if ((gridDirection | orientation) == -1) throw new IllegalArgumentException("Trying to turn 180 degrees on the grid.");
		Integer movementCode = relativityRegulator[gridDirection][orientation];
		return movementCode.toString();
	}
	
	public static void main(String[] args) {
		Part2 part2 = new Part2();
		part2.run();
	}
}
