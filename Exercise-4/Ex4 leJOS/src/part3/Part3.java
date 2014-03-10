package part3;

import grid.Grid;
import part2.Ex4Part2;

public class Part3 {
	
	private Grid grid;
	private TSNode currentLocation;
	
	/**
	 * Construct with hard-coded data
	 */
	public Part3()
	{
		grid = new Grid(Grid.getTestingGrid());
	}
	
	public void run()
	{
		//Initialise a new Travelling Salesman
		TravellingSalesman ts = new TravellingSalesman(); //Using testing constructor
		TSNode[] order = ts.getBestOrder();
		
		currentLocation = ts.getStart();
		for (TSNode destination : order)
		{
			Ex4Part2 route = new Ex4Part2(grid, currentLocation.getPosition(), destination.getPosition());
			route.run(); //Physically move to the destination
			currentLocation = destination; //Now that you're at that destination, make that your currentLocation
		}
	}

	public static void main(String[] args) {
		Part3 p3 = new Part3();
		p3.run();
		
	}
	
}
