package part1;

import grid.Grid;
import grid.Grid.Direction;
import grid.GridSuccessorFunction;

import java.util.List;

import part1.Search.SearchType;
import puzzles.EightPuzzle;
import puzzles.EightPuzzleSuccessorFunction;
import puzzles.EightPuzzle.PuzzleMove;
import puzzles.StringPuzzle;
import puzzles.StringSuccessorFunction;
import puzzles.Swap;
import rp13.search.interfaces.SuccessorFunction;

/**
 * 
 * @author Gwen & Goda c:
 *
 */
public class Main {
	private static SearchType searchChoice = SearchType.AStar;
	
	public static void main (String[] args) 
    { 
        //This is how you use it 
	 
//        solveGridPuzzle();
		Grid startstate = Grid.exc5Grid();
		System.out.println("START STATE: ");
		System.out.println(startstate);
		
    }
	
	
	public static void solveEightPuzzle()
	{
		Search<PuzzleMove> USearch = new Search<PuzzleMove>
        (EightPuzzle.testEightPuzzle(), EightPuzzle.orderedEightPuzzle(), searchChoice); 

		//With Delegation 
		SuccessorFunction succfunc = new EightPuzzleSuccessorFunction();
		List<PuzzleMove> solutionList = USearch.search(succfunc, searchChoice); 
		
		int count = 0; 
		for(int i = 0; i < solutionList.size(); i++) 
		{ 
		System.out.print(solutionList.get(i) + ", "); 
		count++; 
		} 
		System.out.println(); 
		System.out.println("Total Moves: " + count); 
	}

	public static void solveStringPuzzle(String word)
	{

		StringPuzzle startstate = new StringPuzzle();
		startstate.jumble();
		System.out.println(startstate);
		StringPuzzle goalstate = new StringPuzzle(word);
		SuccessorFunction succfunc = new StringSuccessorFunction();
		Search<Swap> USearch = new Search<Swap>(startstate, goalstate, searchChoice);
				List<Swap> solutionList = USearch.search(succfunc, searchChoice); 
				
				int count = 0; 
				for(int i = 0; i < solutionList.size(); i++) 
				{ 
				System.out.print(solutionList.get(i).toString() + ", "); 
				count++; 
				} 
				System.out.println(); 
				System.out.println("Total Moves: " + count); 
			
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

