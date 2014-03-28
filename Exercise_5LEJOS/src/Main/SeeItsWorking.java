package Main;

import established.GridTraveller;
import grid.GridBoard;
import grid.GridPuz;
import grid.GridSuccessorFunction;
import grid.GridBoard.Direction;

import java.awt.Point;
import java.util.List;

import lejos.nxt.Button;
import Interfaces.PuzzleInterface;
import Interfaces.SuccessorFunction;
import Main.Search.SearchType;


public class SeeItsWorking {
	
	private static SearchType searchChoice = SearchType.AStar;
	
	public static void main(String[] args) {
		
//		solveGridPuzzle();
		solveGrid2(new Point(2, 2), new Point(5, 4), new Point(10, 6));
	}
	
	public static List<Direction> solveGrid2(Point startpoint, Point midpoint, Point endpoint)
	{
		GridPuz start = new GridPuz(startpoint);
		GridPuz mid = new GridPuz(midpoint);
		GridPuz end = new GridPuz(endpoint);
		
		SuccessorFunction<Direction, GridPuz> succfunc = new GridSuccessorFunction();
		
		// Choose the kind of search
		Search.SearchType searchChoice = Search.SearchType.AStar;
		
		// Get to the mid
		Search<Direction> startToMidSearch = new Search(start, mid, searchChoice);
		
		// Get to the end
		Search<Direction> midToEndSearch = new Search<>(mid, end, searchChoice);
		
		List<Direction> solutionList = startToMidSearch.search(succfunc, searchChoice);
		
		if(!solutionList.get(solutionList.size()-1).equals(Direction.UP))
			solutionList.add(Direction.UP);
		// A null value, to show that we're halfway done.
		solutionList.add(null);
		// Directions to the end.
		solutionList.addAll(midToEndSearch.search(succfunc, searchChoice));
		
		return solutionList;
	}
	
	public static void solveGridPuzzle(){
		GridPuz startstate = new GridPuz(new Point(0, 0));
//		System.out.println("START STATE: ");
//		System.out.println(startstate);
//		
//		System.out.println("--------------------------");
		
		GridPuz endstate = new GridPuz(GridBoard.endGoal);
//		System.out.println("END STATE: ");
//		System.out.println(endstate);	
//		
//		System.out.println("--------------------------");
		
		SuccessorFunction<Direction, GridPuz> succfunc = new GridSuccessorFunction();
		
		Search<Direction> USearch = new Search<Direction>
        (startstate, endstate, searchChoice); 
		
		List<Direction> solutionList = USearch.search(succfunc, searchChoice); 

				int count = 0; 
				for(int i = 0; i < solutionList.size(); i++) 
				{ 
				System.out.print(solutionList.get(i).toString() + ", "); 
				count++; 
				} 
				System.out.println(); 
				System.out.println("Total Moves: " + count); 	
				
				Button.waitForAnyPress();
				
				GridTraveller traveller = new GridTraveller(solutionList);
				
				traveller.runActions();
	}
}
