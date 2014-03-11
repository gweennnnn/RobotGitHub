package Main;

import grid.Grid;
import grid.GridSuccessorFunction;
import grid.Grid.Direction;
import java.util.List;
import Interfaces.SuccessorFunction;
import Main.Search.SearchType;


public class testClassToShowItsWorking {
	
	private static SearchType searchChoice = SearchType.AStar;
	
	public static void main(String[] args) {
		Grid startstate = Grid.exc5Grid();
		System.out.println("START STATE: ");
		System.out.println(startstate);
	}
	
	public static void solveGridPuzzle(){

		
		Grid startstate = Grid.testStartGrid();
		System.out.println("START STATE: ");
		System.out.println(startstate);
		
		System.out.println("--------------------------");
		
		Grid endstate = Grid.testEndGrid();
		System.out.println("END STATE: ");
		System.out.println(endstate);	
		
		System.out.println("--------------------------");
		
		SuccessorFunction succfunc = new GridSuccessorFunction();
		
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
	}
}
