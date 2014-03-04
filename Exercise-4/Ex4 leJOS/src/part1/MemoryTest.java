package part1;

import java.util.List;

import established.Robot;
import part1.UninformedSearch.SearchType;
import rp13.search.problem.puzzle.EightPuzzle;
import rp13.search.problem.puzzle.EightPuzzleSuccessorFunction;
import rp13.search.problem.puzzle.EightPuzzle.PuzzleMove;
import List.Node;

public class MemoryTest extends Robot{

	public static void main (String[] args) 
    { 
		 //This is how you use it 
        UninformedSearch<PuzzleMove, EightPuzzle> USearch = new UninformedSearch<PuzzleMove, EightPuzzle> 
                            (EightPuzzle.testEightPuzzle(), EightPuzzle.orderedEightPuzzle(), SearchType.BreadthFirst); 
          
        //With Delegation 
        List<PuzzleMove> solutionList = USearch.search(new EightPuzzleSuccessorFunction()); 
          
        Node<EightPuzzle.PuzzleMove, EightPuzzle> firstNode = new Node<EightPuzzle.PuzzleMove, EightPuzzle> 
                                                                    (null, USearch.getStartState()); 
        //Without delegation 
          
        int count = 0; 
        for(int i = 0; i < solutionList.size(); i++)
        { 
            System.out.print(solutionList.get(i) + ", "); 
            count++; 
        } 
        System.out.println(); 
        System.out.println(count); 
    }
	
}
