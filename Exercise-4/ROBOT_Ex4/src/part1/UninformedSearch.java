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
	private _action startAction;
	private ListType<_action, _state> list;
	
	public enum SearchType { DepthFirst, BreadthFirst };
	
	public UninformedSearch(_state startState, _action startAction, _state endState, SearchType searchType)
	{
		super(endState);
		this.startState = startState;
		this.startAction = startAction;
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

	public List<_action> search(Node<_action, _state> startNode, SuccessorFunction<_action, _state> succFunct, List<ActionStatePair<_action, _state>> successors)
	{	
		Node<_action, _state> currentNode = startNode;
		System.out.println("So, starting from the first node..");
		int counter = 0;
		while (!this.list.isEmpty())
		{
			currentNode = this.list.pop(); // <------already adds an item to the explored set
			
			System.out.println("CURRENT NODE!");
			System.out.println(counter + " passes so far...");
			System.out.println(currentNode.getMove());
			System.out.println(currentNode.getState());
			if (this.isGoal(currentNode.getState()))
			{
				System.out.println("GOAL STATE FOUND");
				List<_action> solutionList = new ArrayList<_action> ();
				currentNode.getSolutionList(solutionList);
				return solutionList;
			}
			
			// FILLING IN THE FRONTIERS LIST WITH SUCCESSORS

			succFunct.getSuccessors(currentNode.getState(), successors); // <----------gets all the successors

			if(successors.isEmpty())
				System.out.println("Derp");
			
			System.out.println("CURRENT CHILDREN");
			for (ActionStatePair<_action, _state> state : successors)
			{ 	
				Node<_action, _state> tempNode = new Node<_action, _state>(state.getAction(), state.getState(), currentNode);
				
				if (!list.contains(tempNode) && !tempNode.getMove().equals(startAction))
				{
					System.out.println(tempNode.getMove());
					System.out.println(tempNode.getState());
					this.list.push(tempNode); // <------------------adds it to the frontiers list
				}
			}
			successors.removeAll(successors);
			
			
			System.out.println();
			System.out.println();
			System.out.println("NEXT NODE!");
			counter++;

		}
		
		
		
		return new ArrayList<_action>();
	}
	
	public static void main (String[] args)
	{
		//This is how you use it
		UninformedSearch<PuzzleMove, EightPuzzle> USearch = new UninformedSearch<PuzzleMove, EightPuzzle>
							(EightPuzzle.testEightPuzzle(), PuzzleMove.START, EightPuzzle.orderedEightPuzzle(), SearchType.DepthFirst);
		
		
		EightPuzzleSuccessorFunction succfunct = new EightPuzzleSuccessorFunction();
		
		Node<EightPuzzle.PuzzleMove, EightPuzzle> firstNode = new Node<EightPuzzle.PuzzleMove, EightPuzzle>
															        (PuzzleMove.START, USearch.getStartState());
		
		USearch.getList().push(firstNode);
		Node<EightPuzzle.PuzzleMove, EightPuzzle> currentNode = USearch.getList().peek();
		
		System.out.println("Start");
		List<PuzzleMove> moves = USearch.search(currentNode, succfunct, new ArrayList<ActionStatePair<PuzzleMove, EightPuzzle>>());
		
		if(moves.isEmpty())
			System.out.println("Empty");
		
		for(int i = 0; i < moves.size(); i++)
			System.out.print(moves.get(i) + ", ");
	}
}
