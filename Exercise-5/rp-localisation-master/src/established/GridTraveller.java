package established;

import java.util.ArrayList;
import java.util.List;

import lejos.nxt.Button;
import grid.GridBoard.Direction;

/**
 * Grid Traveller takes a List of Directions from the grid, and travels along them.
 * @author Jordan Bell
 *
 */
public class GridTraveller {
	
	private List<Direction> actionList;

	private static final int[][] relativityRegulator = new int[][]{new int[]{0, -1,  2,  1},
																   new int[]{-1, 0,  1,  2},
																   new int[]{1,  2,  0, -1},
																   new int[]{2,  1, -1,  0}
																  };
	public enum GridDirection
	{
		UP(0), DOWN(1), LEFT(2), RIGHT(3), SONG(4);
		private final int val;
		
		private GridDirection(int val) {
			this.val = val;
		}
		
		//Edit this if we want to set the initial orientaiton to something different.
		private static GridDirection initialOrientation() { return UP; }
	}
	
	public GridTraveller(List<Direction> actionList)
	{
		setActionList(actionList);
	}
	
	public void setActionList(List<Direction> path)
	{
		this.actionList = path;
	}
	
	/**
	 * Run using a custom action list
	 * @param actionList
	 */
	public void runActions()
	{
		String path = listToPath(actionList);
		System.out.println(path);
		Button.waitForAnyPress();
		PathFollower pf = new PathFollower(path);
		pf.runPath();
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
			else if (d == null)
			{
				movements.add(GridDirection.SONG);
				orientations.add(GridDirection.SONG);
			}
		}
		
		String s = "";
		
		//Translate Direction and Orientation to String values
		for (int i = 0; i < movements.size(); i++)
		{
			/* Index i contains the direction of the proposed movement (relative to the graph) 
			 * and the orientation of the robot at the time of proposed movement 
			 * (which will be the same direction as the previous movement)
			 */
			GridDirection movement = movements.get(i);
			GridDirection orientation = orientations.get(i);
			if ((movement == GridDirection.SONG) && (orientation == GridDirection.SONG)) s += "S";
			else s += submitRelativity(movement, orientation);
		}
		return s;
	}
	
	/**
	 * Returns a relative movement code, based on the direction move along the grid, and the orientation of the robot.
	 * @param gridDirection Which way, from a fixed perspective, the robot should move along the grid.
	 * @param orientation The direction, relative to the same fixed perspective as the grid, that the robot is facing.
	 * @return The movement code for the robot, in 0s, 1s, and 2s translating to forward, right and left respectively.
	 */
	private static String submitRelativity(GridDirection gridDirection, GridDirection orientation)
	{
		return submitRelativity(gridDirection.val, orientation.val);
	}
	
	/**
	 * Returns a relative movement code, based on the direction move along the grid, and the orientation of the robot.
	 * @param gridDirection The corresponding index that relates to a given GridDirection.
	 * @param orientation The corresponding index that relates to a given Orientation.
	 * @return The movement code for the robot, in 0s, 1s, and 2s translating to forward, right and left respectively.
	 */
	private static String submitRelativity(int gridDirection, int orientation) throws IllegalArgumentException
	{
		if ((gridDirection | orientation) == -1) throw new IllegalArgumentException("Trying to turn 180 degrees on the grid.");
		Integer movementCode = relativityRegulator[gridDirection][orientation];
		return movementCode.toString();
	}

}
