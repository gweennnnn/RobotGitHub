import java.awt.Point;
import java.util.Random;

/**
 * A class to represent a traversable robot grid. Takes into account hard-coded
 * blockages on the grid.
 * 
 * @author Jordan Bell
 * 
 */
public class Grid {

	/**
	 * The enumeration of the directions the robot can take
	 *
	 */
	public enum Direction {
		UP(new Point(0, -1)), 
		DOWN(new Point(0, 1)), 
		LEFT(new Point(-1, 0)), 
		RIGHT(new Point(1, 0));

		private final Point m_move;

		private Direction(Point _move) { m_move = _move; }

		private static final Direction[] VALUES = values();
		private static final int SIZE = VALUES.length;
		private static final Random RANDOM = new Random();

		public static Direction randomDirection() {
			return VALUES[RANDOM.nextInt(SIZE)];
		}

	}

	public int width = 5;
	public int height = 5;
	private Point robotPosition;
	private Connection[] blockages = new Connection[] {};

	/**
	 * Create a new grid by copying another grid
	 */
	public Grid(Grid _that) {
		this(_that.getBlockages(), _that.getPosition(), _that.width, _that.height);
	}

	/**
	 * Create a custom grid
	 * @param blockages The connections between points where the agent may not traverse.
	 * @param startPosition The start position of the agent
	 * @param width The width of the grid
	 * @param height The height of the grid
	 * @throws IllegalArgumentException If the points supplied in blockages or startPosition do not exist on the grid
	 */
	public Grid(Connection[] blockages, Point startPosition, int width, int height) throws IllegalArgumentException {
		this.blockages = blockages;
		robotPosition = startPosition;
		this.width = width;
		this.height = height;
		
		if (!validateBlockages())  throw new IllegalArgumentException("You can't have those blockages on a grid that size. Either increase the size, or rethink your blockages.");
		if (!validateStart()) throw new IllegalArgumentException("The start position " + startPosition + " does not exist on a grid of size " + width + "x" + height);
	}

	/**
	 * Validate the startPosition
	 */
	private boolean validateStart() {
		boolean xValid = (robotPosition.x >= 0) && (robotPosition.x < width);
		boolean yValid = (robotPosition.y >= 0) && (robotPosition.y < height);
		
		return (xValid && yValid);
	}

	/**
	 * Validate the gridBlockages
	 */
	private boolean validateBlockages() {
		boolean valid = true;
		for (Connection b : blockages)
		{
			Point[] connectionPoints = new Point[] {b.to, b.from};
			
			for (Point p : connectionPoints)
			{
				boolean xValid = (p.x >= 0) && (p.x < width);
				boolean yValid = (p.y >= 0) && (p.y < height);

				if (!xValid || !yValid) valid = false;
			}
		}
		return valid;
	}

	// Getter functions
	public Point getPosition() {
		return (robotPosition);
	}

	public Connection[] getBlockages() {
		return blockages;
	}

	/**
	 * Checks whether the given move is possible given blockages
	 * 
	 * @param _move The move to make.
	 * @return Returns true if the move is possible, else false.
	 */
	public boolean isPossibleMove(Direction _move) {
		//Find where the robot will go
		boolean possible;
		Point newRobotPosition = addPoints(robotPosition, _move.m_move);

		//If move stays within the grid
		boolean xValid = (newRobotPosition.x >= 0) && (newRobotPosition.x < width);
		boolean yValid = (newRobotPosition.y >= 0) && (newRobotPosition.y < height);
		possible = xValid && yValid;
		
		//If move isn't blocked
		Connection movement = new Connection(robotPosition, newRobotPosition);
		possible = possible && !isBlockage(movement);
		
		return possible;
	}

	//Check a connection for if it's a blockage
	private boolean isBlockage(Point p1, Point p2) {
		
		Connection connection = new Connection(p1, p2);
		return isBlockage(connection);
	}

	private boolean isBlockage(Connection c) {
		if ((c.to.x | c.from.x) >= width) return false;
		if ((c.to.y | c.from.y) >= height) return false;
		
		boolean isIt = false;
		for (Connection b : blockages)
			if (b.equals(c)) isIt = true;
		return isIt;
	}


	/**
	 * Attempts to move the robot to a new position.
	 * 
	 * @param _move The move to make.
	 * @return Returns true if the move was possible, else false.
	 */
	public boolean makeMove(Direction _move) {
		if (isPossibleMove(_move)) {
			// Where the robot will be
			robotPosition = addPoints(robotPosition, _move.m_move);
			return true;
		} else {
			return false;
		}
	}

	//Add the coordinates of two points together
	private Point addPoints(Point p1, Point p2) {
		Point r_point = new Point(p1.x + p2.x, p1.y + p2.y);
		return r_point;
	}

	@Override
	public String toString() {

		// use a string builder for efficiency
		StringBuilder sb = new StringBuilder();

		for (int y = height-1; y >= 0; y--) {
			
			sb.append("\n"); //Start a new line
			
			//Draw horizontals
			for (int x = 0; x < width; x++) {
				//Loop through the width x height grid Point data
				Point thisPosition = new Point(x, y);
				Point newPosition = new Point(x+1, y); //The node to the right
				boolean blockageHere = isBlockage(thisPosition, newPosition);
				
				//Draw the node
				if (thisPosition.equals(robotPosition))
					sb.append("#"); //Represent the robot's position
				else
					sb.append("+"); //Represent a junction
				
				//Draw the connection
				if (x < height-1) //If not at the end of the line
				{
					if (!blockageHere)  sb.append("---");
					else 				sb.append("   ");
				}
			}
			
			sb.append("\n"); //Start a new line
			
			//Draw Verticals
			if (y > 0)
			{
				for (int x = 0; x < width; x++) {
					//Loop through the width x height grid Point data
					Point thisPosition = new Point(x, y);
					Point newPosition = new Point(x, y-1); //The node below
					boolean blockageHere = isBlockage(thisPosition, newPosition);
					
					if (!blockageHere) 	sb.append("|");
					else 				sb.append(" ");
					sb.append("   ");
				}
			}
		}
		return sb.toString();
	}
	
	public static void main(String[] args) {

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

		Grid grid = new Grid(blockages, new Point(0, 0), 5, 5);

		System.out.println(grid);
	}

}
