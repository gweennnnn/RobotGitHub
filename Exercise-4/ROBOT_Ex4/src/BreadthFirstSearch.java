import java.util.List;

import rp13.search.problem.puzzle.EightPuzzle;
import rp13.search.problem.puzzle.EightPuzzleSuccessorFunction;
import rp13.search.problem.puzzle.EightPuzzle.PuzzleMove;



public class BreadthFirstSearch {
	
	//WHERE WE START SEARCHING
	EightPuzzle startState;
	
	//WHERE WE WANT TO END UP
	EightPuzzle endState;
	
	//ALL POSSIBLE MOVES
	EightPuzzleSuccessorFunction sf;
	
	//LIST OF FRONTIER STATES
	List<EightPuzzle> frontier;
	
	

	public BreadthFirstSearch(EightPuzzle startState, EightPuzzle endState) {
		super();
		this.startState = startState;
		this.endState = endState;
	}
	
	//CHECKS IF THE CURRENT STATE IS THE GOAL WE'RE SEARCHING FOR
	
	public boolean isGoalState(EightPuzzle currentState) {
		 
		return currentState.equals(endState);
	}
	
	//CHECKS IF A PARTICULAR STATE IS IN A LIST (F.E. FRONTIERS LIST)
	
	public boolean isInList(){
		
		//LOTS OF CODE
		
		return true;
	}
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
}
