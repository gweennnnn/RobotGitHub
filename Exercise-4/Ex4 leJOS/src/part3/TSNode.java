package part3;

import java.awt.Point;
import java.util.ArrayList;

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
	
	/**
	 * Returns the neighbor closest to this position
	 */
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
	
	/**
	 * Returns the nearest neighbor within a subset of neighbors
	 * @return
	 */
	public Neighbor getNearestNeighbor(ArrayList<TSNode> subset){
		int minDist = 999999;
		Neighbor r_neighbor = null;
		//Hold the value of the highest mutual distance between nodes
		int equalDistance = 999999;
		ArrayList<Neighbor> equalDistanceNeighbors = new ArrayList<Neighbor>();
		
		//For all of the neighbors
		for (Neighbor n : neighbors)
		{
			//If its one that we're searching among
			if (ofSubset(subset, n))
			{
				//Select it if it's closest.
				int dist = n.getDistance();
				
				if (dist < minDist)
				{
					minDist = dist;
					r_neighbor = n;
					//New max distance found, reset the equalDistanceNeighbors
					equalDistanceNeighbors = new ArrayList<Neighbor>();
				}
				else if (dist == minDist)
				{
					//Store information about the neighbors of equal distance
					equalDistance = minDist;
					equalDistanceNeighbors.add(n);
				}
			}
		}
		
		//If there is more than one nearest neighbor
		if (minDist == equalDistance) 
		{
			int maxDist = 0;
			
			for (Neighbor n : equalDistanceNeighbors)
			{
				//Choose the one farthest from the bulk of the cities
				
				//Copy the subset to another arrayList
				ArrayList<TSNode> newSubset = new ArrayList<TSNode>();
				for (TSNode copyNode : subset)
				{
					newSubset.add(copyNode);
				}
				
				//Remove this from that subset
				newSubset.remove(n);
				
				//Get the distance to the center of the remaining cities
				Point centerOfRemaining = TravellingSalesman.getCenter(newSubset);
				int dist = getDistanceTo(centerOfRemaining);
				
				if (dist > maxDist)
				{
					maxDist = dist;
					r_neighbor = n;
				}
			}
		}
		
		return r_neighbor;
	}
	
	/**
	 * Calculate the Manhattan distance to a point
	 */
	private int getDistanceTo(Point p)
	{
		int xDist = Math.abs(p.x - position.x);
		int yDist = Math.abs(p.y - position.y);
		
		return xDist + yDist;
	}
	
	/**
	 * Helper function for the subset version of getNearestNeighbor method
	 * @param subset A list of TSNodes being searched
	 * @param n The neighbor object for which this is being checked
	 * @return
	 */
	private boolean ofSubset(ArrayList<TSNode> subset, Neighbor n)
	{
		TSNode neighborNode = n.getNode();
		return subset.contains(neighborNode);
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
	
	public Point getPosition() {
		return position;
	}
	
	public Neighbor[] getNeighbors() {
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
