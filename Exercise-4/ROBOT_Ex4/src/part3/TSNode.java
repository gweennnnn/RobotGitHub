package part3;

import java.awt.Point;

public class TSNode {

	private Point position;
	private Neighbor[] neighbors;
	
	public TSNode(Point position)
	{
		this(position, null);
	}
	
	public TSNode(Point position, Neighbor[] neighbors) {
		super();
		this.position = position;
		setNeighbors(neighbors);
	}
	
	public Neighbor getNearestNeighbor(){
		int maxDist = 0;
		Neighbor r_neighbor = null;
		
		for (Neighbor n : neighbors)
		{
			int dist = n.getDistance();
			if (dist > maxDist)
			{
				maxDist = dist;
				r_neighbor = n;
			}
		}
		
		return r_neighbor;
	}
	
	public Neighbor getNeighborByDistance(int dist)
	{
		for (Neighbor n : neighbors)
		{
			if (n.getDistance() == dist) return n;
		}
		return null;
	}
	
	public int getDistanceByNeighbor(Neighbor _n)
	{
		for (Neighbor n : neighbors)
		{
			if (_n == n) return n.getDistance();
		}
		return -1;
	}
	
	public int getDistanceByNode(TSNode target)
	{
		for (Neighbor n : neighbors)
		{
			System.out.println(n);
			if (n.getNode().equals(target)) return n.getDistance();
		}
		return -1;
	}
	
	public boolean equals(TSNode target)
	{
		return position.x == target.getPosition().x &&
			   position.y == target.getPosition().y;
	}
	
	private Point getPosition() {
		return position;
	}
	
	private Neighbor[] getNeighbors() {
		return neighbors;
	}

	public void setNeighbors(Neighbor[] neighbors)
	{
		this.neighbors = neighbors;
	}
	
	public String toString()
	{
		return (position.toString());
	}
	
	public String toStringFull()
	{
		String s = position.toString() + " has neighbors: ";
		for (Neighbor n : neighbors)
		{
			s += "\n-";
			s += n;
		}
		
		return s;
	}
	
	public static void main(String[] args)
	{
		TSNode tsn = new TSNode(new Point(0, 0));
		
	}
	
}
