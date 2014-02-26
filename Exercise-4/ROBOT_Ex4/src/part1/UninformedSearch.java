package part1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import List.Queue;
import List.Stack;
import List.ListType;
import rp13.search.interfaces.*;
import rp13.search.problem.puzzle.EightPuzzle;
import rp13.search.problem.puzzle.EightPuzzle.PuzzleMove;
import rp13.search.problem.puzzle.EightPuzzleSuccessorFunction;
import rp13.search.util.ActionStatePair;
import rp13.search.util.EqualityGoalTest;

public class UninformedSearch<_action, _state> extends EqualityGoalTest<_state>
{
	private _state startState;
	private ListType<_action, _state> list;
	
	public enum SearchType { DepthFirst, BreadthFirst };
	
	public UninformedSearch(_state startState, _state endState, SearchType searchType)
	{
		super(endState);
		this.startState = startState;
		if(searchType.equals(SearchType.DepthFirst))
			this.list = new Stack<_action, _state>();	
		else
			this.list = new Queue<_action, _state>();
			
	}
	
	public _state getStartState() {
		return startState;
	}

	public Agenda<Node<_action, _state>> getList() {
		return list;
	}

	public List<_action> search(Node<_action, _state> currNode, SuccessorFunction<_action, _state> succFunct, List<ActionStatePair<_action, _state>> successors)
	{
		//goda your code
		//this has to be recursive so i added everything from your previous while loop
		System.out.println("Entered search");
		System.out.println(currNode.getState());
		if(this.isGoal(currNode.getState()))
			return currNode.solutionList();

		succFunct.getSuccessors(currNode.getState(), successors);
		
		for(int i = 0; i < successors.size(); i++)
		{
			System.out.println(successors);
		}
		return new ArrayList<_action>();
	}
	
	public static void main (String[] args)
	{
		//This is how you use it
		UninformedSearch<PuzzleMove, EightPuzzle> USearch = new UninformedSearch<PuzzleMove, EightPuzzle>
							(EightPuzzle.testEightPuzzle(), EightPuzzle.orderedEightPuzzle(), SearchType.BreadthFirst);
		
		
		EightPuzzleSuccessorFunction succfunct = new EightPuzzleSuccessorFunction();
		
		Node<EightPuzzle.PuzzleMove, EightPuzzle> firstNode = new Node<EightPuzzle.PuzzleMove, EightPuzzle>
															        (PuzzleMove.START, USearch.getStartState());
		
		USearch.getList().push(firstNode);
		Node<EightPuzzle.PuzzleMove, EightPuzzle> currentNode = USearch.getList().peek();
		
		List<PuzzleMove> moves = USearch.search(currentNode, succfunct, new ArrayList<ActionStatePair<PuzzleMove, EightPuzzle>>());
		
		
	}
}
