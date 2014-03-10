package grid;
import java.awt.Point;
import java.util.Random;
import java.lang.Math;

import puzzles.PuzzleInterface;

/**
 * A class to represent a traversable robot grid. Takes into account hard-coded
 * blockages on the grid.
 * 
 * @author Jordan Bell
 * 
 */
public class Grid implements PuzzleInterface{

	/**
	 * The enumeration of the directions the robot can take
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
	private static Point r1 = new Point(1,2);
	private static Point r2 = new Point(4,4);

	/**
	 * Create a new grid by copying another grid
	 */
	public Grid(Grid _that) {
		this(_that.getBlockages(), _that.getPosition(), _that.width, _that.height);
	}
	
	/**
	 * Create a new random grid
	 */
	public Grid() {
		this(randomGrid());
	}
	
	/**
	 * Create a new grid by copying another grid, with a custom start position.
	 */
	public Grid(Grid _that, Point start) {
		this(_that.getBlockages(), start, _that.width, _that.height);
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
	
	
	public static Grid testStartGrid(){
		
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

		Point s5 = new Point(3, 3);
		Point e5 = new Point(4, 3);
		Connection b5 = new Connection(s5, e5);

		Connection[] blockages = new Connection[] { b1, b2, b3, b4, b5 };
		
		//RANDOM
//		Grid grid = Grid.randomGrid(20, 5, 5);
		
		
		Grid grid = new Grid(blockages, r1, 5, 5);
		
		return grid;
		
	}
	
	public static Grid testEndGrid(){
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

		Point s5 = new Point(3, 3);
		Point e5 = new Point(4, 3);
		Connection b5 = new Connection(s5, e5);

		Connection[] blockages = new Connection[] { b1, b2, b3, b4, b5 };
		
		//RANDOM
//		Grid grid = Grid.randomGrid(20, 5, 5);
		
	
		
		Grid grid = new Grid(blockages, r2, 5, 5);
		
		return grid;
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
		boolean xBad = (c.to.x >= width) || (c.from.x >= width);
		boolean yBad = (c.to.y >= height) || (c.from.y >= height);
		if (xBad) return false; //Not a valid connection
		if (yBad) return false; //Not a valid connection
		return Grid.isBlockage(c, this.blockages);
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
	
	/**
	 * Manually set a new position on the grid
	 * @param newPosition The position of the robot.
	 */
	public void setOverridePosition(Point newPosition)
	{
		robotPosition = newPosition;
	}

	/**
	 * Add the coordinates of two points together
	 */
	private Point addPoints(Point p1, Point p2) {
		Point r_point = new Point(p1.x + p2.x, p1.y + p2.y);
		return r_point;
	}
	
	/**
	 * Get a complete random grid
	 */
	public static Grid randomGrid()
	{
		Random generator = new Random();
		return randomGrid(generator.nextInt(50));
	}
	
	/**
	 * Get a random grid with a set number of blockages, but a random size
	 * @param numberOfBlockages The number of blockages found in the grid
	 */
	public static Grid randomGrid(int numberOfBlockages)
	{
		Random generator = new Random();
		int w = -1;
		int h = -1;
		
		while (!validBlockageNumber(numberOfBlockages, w, h))
		{
			w = generator.nextInt(10);
			h = generator.nextInt(10);
		}
		
		return randomGrid(numberOfBlockages, w, h);
	}
	
	/**
	 * Get a random grid, with specified blockages and width and height
	 * @param numberOfBlockages Number of blockages on the grid
	 * @param width Width of the grid
	 * @param height Height of the grid
	 */
	public static Grid randomGrid(int numberOfBlockages, int width, int height)
	{
		//Make sure the numbers given are doable.
		if (!validBlockageNumber(numberOfBlockages, width, height))
			throw new IllegalArgumentException("Cannot create a random grid with " + numberOfBlockages + " blockages on a grid of width " + width + " and height " + height + ".");
		
		Grid r_grid = null; //The grid being returned
		Connection[] blockages = new Connection[numberOfBlockages];
		
		Random generator = new Random();
		
		for (int i = 0; i < blockages.length; i++)
		{
			Connection blockage = null;
			
			//Make a new blockage
			do
			{
				//The two points whose path to the other is being blocked
				Point p1;
				Point p2;
				
				//Generate the first point
				int x1 = generator.nextInt(width);
				int y1 = generator.nextInt(height);
				p1 = new Point(x1, y1);
				
					/* The second point is trickier, and less random. 
					 * It must be within the grid, and share a 
					 * coordinate with the first point
					 */
				//Use Integer objects, so that sharedCoord can point to one of them
				int x2;
				int y2;

				boolean xOrY = generator.nextBoolean(); //Decide which coordinate is shared
				
				if (xOrY) //Shares the x-coordinate
				{
					x2 = x1;
					//Check to see if it's on the edge
					boolean topEdge = (y1 == height-1);
					boolean bottomEdge = (y1 == 0);
					
					if (topEdge)
					{
						y2 = y1 - 1;
					}
					else if (bottomEdge)
					{
						y2 = y1 + 1;
					}
					else //Not near an edge
					{
						boolean upOrDown = generator.nextBoolean();
						
						y2 = (upOrDown) ? y1+1 : y1-1; 
					}
				}
				else //Shares the y-coordinate
				{
					y2 = y1;
					//Check to see if it's on the edge
					boolean topEdge = (x1 == width-1);
					boolean bottomEdge = (x1 == 0);
					
					if (topEdge)
					{
						x2 = x1 - 1;
					}
					else if (bottomEdge)
					{
						x2 = x1 + 1;
					}
					else //Not near an edge
					{
						boolean upOrDown = generator.nextBoolean();
						
						x2 = (upOrDown) ? x1+1 : x1-1; 
					}
				}
				
				p2 = new Point(x2, y2);

				//Add this random blockage to the list of blockages
				blockage = new Connection(p1, p2);
			} while (isBlockage(blockage, blockages)); //Repeat if the generated blockage already exists.
			
			//Once a valid and new blockage is created, add it to the blockages
			blockages[i] = blockage;
		}
		
		Point start = new Point(generator.nextInt(width), generator.nextInt(height));
		r_grid = new Grid(blockages, start, width, height);
		return r_grid;
	}
	
	/**
	 * Uses a derived equation to calculate whether or not the number of blockages is impossible on a grid of a certain size.
	 * @param bnum Number of blockages
	 * @param w Width of the grid
	 * @param h Height of the grid
	 * @return Whether or not the blockages/grid size is valid.
	 */
	private static boolean validBlockageNumber(int bnum, int w, int h)
	{
		if ((w|h) < 0) return false;
		int maxBlockages = 2*w*h - w - h; //Derived equation for the max number of blockages on a grid of width w and height h.
		return bnum <= maxBlockages;
	}
	
	/**
	 * Checks for equality with another grid
	 * @param g The grid being compared to.
	 * @return Whether or not the values of g are equal with the ones in this grid.
	 */
	public boolean equals(Grid g)
	{
		//Equal Size
		boolean e3 = (width == g.width);
		boolean e4 = (height == g.height);

		//Equal Blockages
		boolean e1 = true;
		Connection[] gBlks = g.getBlockages();
		for (int i = 0; i < blockages.length; i++)
		{
			if (blockages[i] != gBlks[i]) e1 = false;
		}
		
		//Equal position
		boolean e2 = (robotPosition.x == g.getPosition().x) && 
					 (robotPosition.y == g.getPosition().y);
		
		return (e1 && e2 && e3 && e4);
	}

	@Override
	public String toString() {

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

				//Draw the node
				if (thisPosition.equals(robotPosition))
					sb.append("R"); //Represent the robot's position
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
	
	public static void main(String[] args) {
		Grid grid = getAssignmentGrid();
		System.out.println(grid);
	}
	
	public static Connection[] getTestingBlockages()
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
		return blockages;
	}

	public static Grid getTestingGrid()
	{
		Grid g = new Grid(getTestingBlockages(), new Point(0, 0), 5, 5);
		
		return g;
	}
	
	public static Grid getAssignmentGrid()
	{
		// Generate Blockages on the grid
		Point s1 = new Point(1, 1);
		Point e1 = new Point(1, 2);
		Connection b1 = new Connection(s1, e1);
		
		Point s2 = new Point(3, 0);
		Point e2 = new Point(4, 0);
		Connection b2 = new Connection(s2, e2);
		
		Point s3 = new Point(5, 0);
		Point e3 = new Point(5, 1);
		Connection b3 = new Connection(s3, e3);
		
		Point s4 = new Point(6, 1);
		Point e4 = new Point(6, 2);
		Connection b4 = new Connection(s4, e4);
		
		Point s5 = new Point(3, 2);
		Point e5 = new Point(4, 2);
		Connection b5 = new Connection(s5, e5);

		Point s6 = new Point(3, 2);
		Point e6 = new Point(3, 3);
		Connection b6 = new Connection(s6, e6);
		
		Point s7 = new Point(0, 3);
		Point e7 = new Point(0, 4);
		Connection b7 = new Connection(s7, e7);
		
		Point s8 = new Point(2, 4);
		Point e8 = new Point(2, 5);
		Connection b8 = new Connection(s8, e8);
		
		Point s9 = new Point(0, 5);
		Point e9 = new Point(1, 5);
		Connection b9 = new Connection(s9, e9);
		
		Point s10 = new Point(4, 4);
		Point e10 = new Point(4, 5);
		Connection b10 = new Connection(s10, e10);

		Point s11 = new Point(4, 5);
		Point e11 = new Point(3, 5);
		Connection b11 = new Connection(s11, e11);
		
		Point s12 = new Point(5, 4);
		Point e12 = new Point(5, 5);
		Connection b12 = new Connection(s12, e12);
		
		Point s13 = new Point(6, 6);
		Point e13 = new Point(6, 5);
		Connection b13 = new Connection(s13, e13);
		
		//These last ones vary, based on which grid it's on
		Point s14 = new Point(1, 6);
		Point e14 = new Point(1, 7);
		Connection b14 = new Connection(s14, e14);
		
		Point s15 = new Point(2, 6);
		Point e15 = new Point(2, 7);
		Connection b15 = new Connection(s15, e15);
		
		Connection[] blockages = new Connection[] { b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15 };

		Grid g = new Grid(blockages, new Point(0, 0), 7, 10);
		
		return g;
	}
	


	@Override
	public int calculateValue()
	{

		double x = this.getPosition().getX();
		double y = this.getPosition().getY();
		
		double goal_x = testEndGrid().getPosition().getX();
		double goal_y = testEndGrid().getPosition().getY();
		
		double x_coord = goal_x - x;
		double y_coord = goal_y - y;
		
		double x_square = x_coord * x_coord;
		double y_square = y_coord * y_coord;
		
		double value = x_square + y_square;
		
		double distance = Math.sqrt(value);
		
		
		
		return (int)distance;
	}

	@Override
	public int costToMove(Object move) {
		// TODO Auto-generated method stub
		return 1;
	}
}
