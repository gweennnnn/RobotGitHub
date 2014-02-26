package part1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import List.Queue;
import List.Stack;
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
	private Agenda<Node<_action, _state>> list; 
	
	public enum SearchType { DepthFirst, BreadthFirst };
	
	public UninformedSearch(_state startState, _action startAction, _state endState, SearchType searchType)
	{
		super(endState);
		this.startState = startState;
		this.startAction = startAction;
		
		if(searchType.equals(SearchType.DepthFirst))
			this.list = new Queue<_action, _state>();
		else
			this.list = new Stack<_action, _state>();	
			
	}
	
	public _state getStartState() {
		return startState;
	}

	public Agenda<Node<_action, _state>> getList() {
		return list;
	}

	public List<_action> search(Node<_action, _state> currNode, SuccessorFunction<_action, _state> succFunct, List<ActionStatePair<_action, _state>> successors)
	{
//		//Why does this have to be recursive?
//		//goda your code
//		//this has to be recursive so i added everything from your previous while loop
//		if(this.isGoal(currNode.getState()))
//			return currNode.solutionList();
//		
//		succFunct.getSuccessors(currNode.getState(), successors);
//		
//		for(ActionStatePair<_action, _state> successor : successors)
//		{
//			if(!this.list.contains(new Node<_action, _state>(successor.getAction(), successor.getState()))) // <---doesn't this check only in successors list? when it should check in the checked list?
//			{
//				Node<_action, _state> node = new Node<_action, _state>(successor.getAction(), successor.getState());
//				
//				this.list.push(node);
//			}
//		}
//		
//		
//		if(this.getList().isEmpty()) // <----if successor list is empty
//			return null;
//		else
//		{
//			Node<_action, _state> tempNode = this.list.pop();
//			search(tempNode, succFunct, new ArrayList<ActionStatePair<_action, _state>>());
//		}
//		
//		return null;
		
		List<_action> failed = new ArrayList<_action>();
		
		List<ActionStatePair<_action, _state>> successorsList = new ArrayList<ActionStatePair<_action, _state>>();
		
		Node<_action, _state> currentNode = new Node<_action, _state>(startAction, startState);
		
		_state currentState = currNode.getState();
		
		
		

		
//		frontier.add(currentNode);
		this.list.push(currNode);
		
//			while(!frontier.isEmpty()){
			while(!this.list.isEmpty()){
				
			
				if(this.isGoal(currentState)){
//					return currentNode.solutionList();
					return currNode.solutionList();
				}
				//FILLING IN THE FRONTIERS LIST WITH SUCCESSORS
				
				succFunct.getSuccessors(currentState, successorsList); //<----------gets all the successors
				
				for(ActionStatePair<_action, _state> state : successorsList){ //<--------------saves all the successors as nodes
					
					if(!list.contains(currNode)){
						
						Node<_action, _state> node = new Node<_action, _state>(state.getAction(), state.getState(), currNode);
						
						this.list.push(node); //<------------------adds it to the frontiers list
					}

					
				}
				
				currNode = this.list.pop();		//<------already adds an item to the explored set	
				currentState = currNode.getState();
			}
			
			return failed;
			
		}
	}
	
	public static void main (String[] args)
	{
		//This is how you use it
		UninformedSearch<PuzzleMove, EightPuzzle> USearch = new UninformedSearch<PuzzleMove, EightPuzzle>
							(EightPuzzle.randomEightPuzzle(), EightPuzzle.orderedEightPuzzle(), SearchType.BreadthFirst);
		
		EightPuzzleSuccessorFunction succfunct = new EightPuzzleSuccessorFunction();
		
		Node<EightPuzzle.PuzzleMove, EightPuzzle> firstNode = new Node<EightPuzzle.PuzzleMove, EightPuzzle>
															        (PuzzleMove.START, USearch.getStartState());
		
		USearch.getList().push(firstNode);
		Node<EightPuzzle.PuzzleMove, EightPuzzle> currentNode = USearch.getList().peek();
		
		USearch.search(currentNode, succfunct, new ArrayList<ActionStatePair<PuzzleMove, EightPuzzle>>());
	}
}
