package grid;


import java.awt.Point;
import java.util.Random;

public class GridBoard {
	public static enum Direction {
		UP(new Point(0, 1)),
		DOWN(new Point(0, -1)),
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
	
	public static int width = 10;
	public static int height = 6;
	public static Connection[] blockages = exc5Grid();
	public static Point endGoal = new Point(4, 4);
	
	public static double getEndX()
	{
		return endGoal.getX();
	}
	
	public static double getEndY()
	{
		return endGoal.getY();
	}
	
	/**
	 * Validate the gridBlockages
	 */
	private static boolean validateBlockages() {
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
	
	public static boolean isPossibleMove(Direction _move, Point robotPosition) {
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
	
	private static Point addPoints(Point p1, Point p2) {
		Point r_point = new Point(p1.x + p2.x, p1.y + p2.y);
		return r_point;
	}
	
	private static boolean isBlockage(Point p1, Point p2) {
		
		Connection connection = new Connection(p1, p2);
		return isBlockage(connection);
	}
	
	private static boolean isBlockage(Connection c) {
		boolean xBad = (c.to.x >= width) || (c.from.x >= width);
		boolean yBad = (c.to.y >= height) || (c.from.y >= height);
		if (xBad) return false; //Not a valid connection
		if (yBad) return false; //Not a valid connection
		return GridBoard.isBlockage(c, blockages);
	}
	
	private static boolean isBlockage(Connection c, Connection[] blockages) 
	{
		for (Connection b : blockages)
		{
			if (b!=null)
			{
				if (b.equals(c)) return true;
			}
		}
		
		return false;
	}
	
	public static Point makeMove(Direction _move, Point robotPosition) {
		if (isPossibleMove(_move, robotPosition)) {
			// Where the robot will be
			return addPoints(robotPosition, _move.m_move);
		} else {
			return null;
		}
	}
	
	public static Connection[] exc5Grid(){
		
		Point s1 = new Point(2, 0);
		Point e1 = new Point(2, 1);
		Connection b1 = new Connection(s1, e1);
		
		Point s2 = new Point(2, 1);
		Point e2 = new Point(2, 2);
		Connection b2 = new Connection(s2, e2);
		
		Point s3 = new Point(2, 1);
		Point e3 = new Point(3, 1);
		Connection b3 = new Connection(s3, e3);
		
		Point s4 = new Point(1, 2);
		Point e4 = new Point(2, 2);
		Connection b4 = new Connection(s4, e4);
		
		Point s5 = new Point(1, 2);
		Point e5 = new Point(1, 3);
		Connection b5 = new Connection(s5, e5);
		
		Point s6 = new Point(0, 2);
		Point e6 = new Point(1, 2);
		Connection b6 = new Connection(s6, e6);
		
		Connection[] blockages = new Connection[] { b1, b2, b3, b4, b5, b6 };
		
		return blockages;
	}
	
	public static String print() {

		// use a string builder for efficiency
		StringBuilder sb = new StringBuilder();

		//For each row
		for (int y = height-1; y >= 0; y--) {
			sb.append("\n"); //Start a new line
			
			//Draw horizontals
			for (int x = 0; x < width; x++) {
				//Loop through the width x height grid Point data
				Point thisPosition = new Point(x, y);
				Point newPosition = new Point(x+1, y); //The node to the right
				boolean blockageHere = isBlockage(thisPosition, newPosition);

					
				sb.append("+");
				
				//Draw the connection
				if (x < width-1) //If not at the end of the line
				{
					if (!blockageHere)  sb.append("---");
					else 				sb.append("   ");
				}
			}
			
			sb.append("\n"); //Start a new line
			
			//Draw Verticals
			if (y != 0) //Only draw connections to the next row if there is a row below (not true when y == 0)
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
}
