package part3;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import part1.Search;
import part1.Search.SearchType;
import part2.Part2;
import rp13.search.interfaces.SuccessorFunction;
import grid.Grid;
import grid.GridSuccessorFunction;
import grid.Grid.Direction;

public class TravellingSalesman {

	TSNode[] cities;
	ArrayList<TSNode> visited;
	ArrayList<TSNode> remaining;
	Part2 traveller;
	Grid grid;
	TSNode start;
	public static TSNode testingStart = new TSNode(new Point(0, 0));
	
	//ALTER THE CITIES BEING TRAVELLED TO
	public static TSNode[] getTestingCities()
	{
		//Generate cities
		TSNode a = new TSNode(new Point(0, 0));
		TSNode b = new TSNode(new Point(3, 3));
		TSNode c = new TSNode(new Point(2, 1));
		TSNode d = new TSNode(new Point(1, 4));
		TSNode e = new TSNode(new Point(4, 2));
		
		TSNode[] cities = new TSNode[]{a, b, c, d, e};
		return cities;
	}
	
	/**
	 * Construct all fields
	 */
	public TravellingSalesman(TSNode[] cities, TSNode start, Grid grid) {
		this.start = start;
		this.cities = cities;
		visited = new ArrayList<TSNode>();
		remaining = new ArrayList<TSNode>();

		//By default, have all cities as remaining, except the start
		for (int i = 0; i < cities.length; i++)
		{
			if (cities[i] != start) remaining.add(cities[i]);
		}
		
		this.grid = grid;
		traveller = new Part2();
		initDistances();
	}
	
	/**
	 * Construct a TravellingSalesman with the testing values, but custom start
	 */
	public TravellingSalesman(TSNode startPosition)
	{
		this(getTestingCities(), startPosition, Grid.getTestingGrid());
	}
	
	/**
	 * Construct a TravellingSalesman with the testing values.
	 */
	public TravellingSalesman()
	{
		this(getTestingCities(), testingStart, Grid.getTestingGrid());
	}
	
	public TSNode getStart()
	{
		return start;
	}
	
	/**
	 * Calculate all of the connections between all of the cities.
	 */
	private void initDistances()
	{
		for (TSNode city : cities)
		{
			setNodeNeighbors(city);
		}
	}
	
	/**
	 * MAIN MEAT OF THIS CLASS
	 * Get the order of cities to travel, which contains the smallest distance between cities.
	 */
	public TSNode[] getBestOrder()
	{
		//Return identifier
		TSNode[] r_order = new TSNode[cities.length+1];
		r_order[0] = start; //Begin with the start point
		
		//Loop through different positions, beginning at the start
		TSNode currentPosition = start;
		
		//Loop through until there are no more remaining
		while (!remaining.isEmpty())
		{
			//Get the closest city to this one
			Neighbor closest = currentPosition.getNearestNeighbor(remaining);
			TSNode node = closest.getNode();
			//Add this to the visited cities
			visited.add(node);
			
			//Remove this from the remaining
			remaining.remove(node);
			
			//Update the theoretical position
			currentPosition = node;
		}
		
		//From arrayList to array
		int i;
		for (i = 0; i < visited.size(); i++)
		{
			r_order[i+1] = visited.get(i);
		}
		
		//Return to origin
		r_order[i+1] = start;
		
		return r_order;
	}
	
	/**
	 * Take a TSNode, and set its neighbors using the other cities known of in this class
	 */
	private void setNodeNeighbors(TSNode city)
	{
		//"Set" Identifier
		Neighbor[] s_neighbors = new Neighbor[cities.length-1];
		int n_counter = 0; //Create a separate counter for the neighbors, as otherwise you'd get a null value for one of the indices.
		
		//Get a theoretical grid state, with this node as the robot's position.
		Grid currentPosition = new Grid(grid, city.getPosition());
		
		for (int i = 0; i < cities.length; i++) //For each other city
		{
			TSNode otherCity = cities[i];
			if (!(otherCity == city)) //Where not the same city
			{
				//Get the path to the otherCity
				Grid targetPosition = new Grid(grid, otherCity.getPosition());
				int distanceToTarget = calculateDistance(currentPosition, targetPosition);
				
				//Save the info as a neighbor
				Neighbor neighbor = new Neighbor(otherCity, distanceToTarget);

				s_neighbors[n_counter] = neighbor;
				n_counter++;
			}
		}
		
		city.setNeighbors(s_neighbors);
	}
	
	/**
	 * Calculate the distance between two places, using grid data
	 */
	private int calculateDistance(Grid g1, Grid g2)
	{
		//SEARCH LENGTH HEURISTIC
		Search<Direction> s = new Search<Direction>(g1, g2, SearchType.AStar); 
		SuccessorFunction succFunct = new GridSuccessorFunction();
		List<Direction> path = s.search(succFunct, SearchType.AStar);
		
		int distanceToTarget = path.size(); //Distance to target is the length of the path taken to get there.
		return distanceToTarget;
		
//		//MANHATTAN DISTANCE HEURISTIC
//		Point p1 = g1.getPosition();
//		Point p2 = g2.getPosition();
//		
//		int xDist = Math.abs(p1.x - p2.x);
//		int yDist = Math.abs(p1.y - p2.y);
//		
//		int manhattanDistance = xDist + yDist;
//		return manhattanDistance;
	}

	/**
	 * Get the center point of a cluster of TSNodes.
	 * @param subset The cluster of TSNodes whose center is being calcualted.
	 * @return The center of the cluster.
	 */
	public static Point getCenter(ArrayList<TSNode> subset) {
		//Hold the totals
		int avgX = 0;
		int avgY = 0;
		
		for (TSNode n : subset)
		{
			avgX += n.getPosition().x;
			avgY += n.getPosition().y;
		}

		//Calculate the average
		avgX /= subset.size();
		avgY /= subset.size();
		
		return new Point(avgX, avgY);
	}
	
	public static void main(String[] args)
	{
		TravellingSalesman ts = new TravellingSalesman();
		TSNode[] order = ts.getBestOrder();
		
		for (TSNode tsn : order)
		{
			System.out.println(tsn);
		}
	}
}
