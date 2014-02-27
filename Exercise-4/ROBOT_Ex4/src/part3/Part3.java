package part3;

import established.PathFollower;
import grid.Grid;
import grid.Grid.Direction;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import part2.Part2;

public class Part3 {
	
	Grid grid;

	public static void main(String[] args) {
		
		TSNode a = new TSNode(new Point(0, 0));
		TSNode b = new TSNode(new Point(3, 3));
		TSNode c = new TSNode(new Point(2, 1));
		TSNode d = new TSNode(new Point(1, 4));
		TSNode e = new TSNode(new Point(4, 2));
		
		TSNode[] allCities = new TSNode[]{a, b, c, d, e};
		TravellingSalesman ts = new TravellingSalesman(allCities, a);
		
		TSNode[] order = ts.getBestOrder();
		List<Direction> actionList = orderToActions(order);
		String sPath = "";
		for (TSNode n : order)
		{
			sPath += Part2.listToPath(actionList);
		}
		
		//Plug the string into the pathFollower
		PathFollower pf = new PathFollower(sPath);
		pf.runPath();
		
	}
	
	private static List<Direction> orderToActions(TSNode[] travelOrder)
	{
		ArrayList<Direction> actions = new ArrayList<Direction>();
		
		for (TSNode n : travelOrder)
		{
			actions = search.search();
		}
		
		return actions;
	}

}
