package part1;
import java.util.List;
import java.util.ArrayList;

import puzzles.EightPuzzle;
import puzzles.EightPuzzleSuccessorFunction;
import puzzles.EightPuzzle.PuzzleMove;
import rp13.search.interfaces.SuccessorFunction;
import rp13.search.util.ActionStatePair;


public class puzzles {

	public static void main(String[] args) {
		// TODO Auto-Ggenerated method stub
		
		EightPuzzle startState = EightPuzzle.randomEightPuzzle();
		EightPuzzle endState = EightPuzzle.orderedEightPuzzle();
		
		List<ActionStatePair<PuzzleMove, EightPuzzle>> frontiers = null;
		List<ActionStatePair<PuzzleMove, EightPuzzle>> successor = null;
		
		SuccessorFunction<PuzzleMove, EightPuzzle> sf = new EightPuzzleSuccessorFunction();
		
		sf.getSuccessors(startState, successor);
		
		

		
//		BreadthFirst x = new BreadthFirst();
//		
//		BreadthFirst.NextMove();
//		List<ActionStatePair<PuzzleMove, EightPuzzle>> successor = new List<ActionStatePair>();
//		sf.getSuccessors(_state, _successors);
		
		//System.out.println(x);
	}
	
	
}
