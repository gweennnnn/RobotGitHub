package grid;

import java.awt.Point;

/**
 * A collection of two points
 * 
 * @author Jordan Bell
 */
public class Connection {
	
	public Point from;
	public Point to;
	
	public Connection(Point from, Point to) {
		this.from = from;
		this.to = to;
	}
	
	/**
	 * @param c The connection being compared to
	 * @return Whether or not both of the points in the connection are equal, regardless of permutation.
	 */
	public boolean equals(Connection c)
	{
		boolean isEqual =	(c.from.equals(from) && c.to.equals(to)) || 
							(c.to.equals(from) && c.from.equals(to));
				
		return isEqual;
	}
	
	public String toString()
	{
		return "(" + from.x + ", " + from.y + ") to (" + to.x + ", " + to.y + ")";
	}
}
