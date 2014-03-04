package part1;

import grid.Grid;
import grid.GridSuccessorFunction;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import List.Node;
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
	private Agenda<Node<_action, _state>> list; 
	
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
	
	public List<_action> search(SuccessorFunction<_action, _state> succFunct) 
    { 
        //Default start 
        Node <_action, _state> startNode = new Node<_action, _state>(null, startState); 
        //Default successors (empty) 
        ArrayList<ActionStatePair<_action, _state>> emptySuccs = new ArrayList<ActionStatePair<_action, _state>>(); 
        //Get the successors 
        succFunct.getSuccessors(startState, emptySuccs); 
        return search(startNode, succFunct, emptySuccs); 
    } 

	public List<_action> search(Node<_action, _state> startNode, SuccessorFunction<_action, _state> succFunct, List<ActionStatePair<_action, _state>> successors)
	{	
		Node<_action, _state> currentNode = startNode;
		this.list.push(startNode);
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

				if (!list.contains(tempNode) && !tempNode.getMove().equals(null))
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

		System.out.println("No solution!");
		return new ArrayList<_action>();
	}
	
	 public static void main (String[] args) 
	{ 
	    //EightPuzzle Search//
//	    UninformedSearch<PuzzleMove, EightPuzzle> epSearch = new UninformedSearch<PuzzleMove, EightPuzzle> 
//	                        (EightPuzzle.testEightPuzzle(), EightPuzzle.orderedEightPuzzle(), SearchType.DepthFirst); 
//	      
//	    List<PuzzleMove> solutionList = epSearch.search(new EightPuzzleSuccessorFunction()); 

	    
		// Grid Search // Random Grid
		Point startPoint = new Point(0, 0);
		Point endPoint = new Point(4, 9);
		Grid g = new Grid();
		Grid startGrid = new Grid(g, startPoint);
		Grid endGrid = new Grid(g, endPoint);
		
	    UninformedSearch<Grid.Direction, Grid> gSearch = new UninformedSearch<Grid.Direction, Grid> 
	                        (startGrid, endGrid, SearchType.DepthFirst); 
	    
	    List<Grid.Direction> solutionList = gSearch.search(new GridSuccessorFunction()); 
	    
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
