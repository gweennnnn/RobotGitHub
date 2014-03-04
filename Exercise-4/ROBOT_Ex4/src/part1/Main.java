package part1;

import java.util.List;

import part1.Search.SearchType;
import puzzles.EightPuzzle;
import puzzles.EightPuzzleSuccessorFunction;
import puzzles.EightPuzzle.PuzzleMove;
import rp13.search.interfaces.SuccessorFunction;
import stringPuzzle.*;

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
	 
        solveStringPuzzle();
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

	public static void solveStringPuzzle()
	{

		StringPuzzle startstate = new StringPuzzle("java");
		startstate.jumble();
		System.out.println(startstate);
		StringPuzzle goalstate = new StringPuzzle("java");
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
}
