package part2;
import java.awt.Point;

import grid.Connection;
import grid.Grid;

public class Part2 {

	Grid startState;
	Grid endState;
	
	public Part2()
	{
		Connection[] blockages;
		
		Point start = new Point(0, 0);
		Point end = new Point(0, 0);
		
		startState = new Grid(blockages, start, 5, 5);
		endState = new Grid(blockages, end, 5, 5);
	}

	public void run() {
		Part1<Grid> searcher = new Part1<Grid>(startState, endState, true);
		List<ActionStatePair<GDirection, Grid>> path = searcher.getPath();
		
		PathFollower pf = new PathFollower(path);
	}
	
	public static void main(String[] args) {
		Part2 part2 = new Part2();
		part2.run();
	}
}
