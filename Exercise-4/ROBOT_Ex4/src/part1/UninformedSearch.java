package part1;

import java.util.ArrayList;
import java.util.List;

import puzzles.EightPuzzle;
import puzzles.EightPuzzleSuccessorFunction;
import puzzles.EightPuzzle.PuzzleMove;
import puzzles.PuzzleInterface;
import List.AStar;
import List.Node;
import List.NodeInterface;
import List.Queue;
import List.Stack;
import rp13.search.interfaces.*;
import rp13.search.util.ActionStatePair;
import rp13.search.util.EqualityGoalTest;

public class UninformedSearch<_action> extends EqualityGoalTest<PuzzleInterface>
{
	private PuzzleInterface startState;
	private Agenda<NodeInterface<_action>> list; 
	
	public enum SearchType { DepthFirst, BreadthFirst, AStar };
	
	public UninformedSearch(PuzzleInterface startState, PuzzleInterface endState, SearchType searchType)
	{
		super(endState);
		this.startState = startState;
		
		if(searchType.equals(SearchType.DepthFirst))
			this.list = new Stack<_action>();
		else if(searchType.equals(SearchType.BreadthFirst))
			this.list = new Queue<_action>();
		else
			this.list = new AStar<_action>();
			
	}
	
	public PuzzleInterface getStartState() {
		return startState;
	}

	public Agenda<Node<_action>> getList() {
		return list;
	}
	
	public List<_action> search(SuccessorFunction<_action, PuzzleInterface> succFunct) 
    { 
        //Default start 
        NodeInterface<_action> startNode = new Node<_action>(null, startState); 
        //Default successors (empty) 
        ArrayList<ActionStatePair<_action, PuzzleInterface>> emptySuccs = new ArrayList<ActionStatePair<_action, PuzzleInterface>>(); 
        //Get the successors 
        succFunct.getSuccessors(startState, emptySuccs); 
        return search(startNode, succFunct, emptySuccs); 
    } 

	public List<_action> search(NodeInterface<_action> startNode, SuccessorFunction<_action, PuzzleInterface> succFunct, List<ActionStatePair<_action, PuzzleInterface>> successors)
	{	
		NodeInterface<_action> currentNode = startNode;
		this.list.push(startNode);
//		System.out.println("So, starting from the first node..");
//		int counter = 0;
		while (!this.list.isEmpty())
		{
			currentNode = this.list.pop(); // <------already adds an item to the explored set

//			System.out.println("CURRENT NODE!");
//			System.out.println(counter + " passes so far...");
//			System.out.println(currentNode.getMove());
//			System.out.println(currentNode.getState());
			if (this.isGoal(currentNode.getState()))
			{
				System.out.println("GOAL STATE FOUND");
				List<_action> solutionList = new ArrayList<_action> ();
				currentNode.getSolutionList(solutionList);
				return solutionList;
			}

			// FILLING IN THE FRONTIERS LIST WITH SUCCESSORS

			succFunct.getSuccessors(currentNode.getState(), successors); // <----------gets all the successors

//			if(successors.isEmpty())
//				System.out.println("Derp");
//
//			System.out.println("CURRENT CHILDREN");
			for (ActionStatePair<_action, PuzzleInterface> state : successors)
			{ 	
				Node<_action> tempNode = new Node<_action>(state.getAction(), state.getState(), currentNode);

				if (!list.contains(tempNode) && !tempNode.getMove().equals(null))
				{
//					System.out.println(tempNode.getMove());
//					System.out.println(tempNode.getState());
					this.list.push(tempNode); // <------------------adds it to the frontiers list
				}
			}
			successors.removeAll(successors);


//			System.out.println();
//			System.out.println();
//			System.out.println("NEXT NODE!");
//			counter++;

		}


//		System.out.println("No solution!");
		return new ArrayList<_action>();
	}
	
	 public static void main (String[] args) 
	    { 
	        //This is how you use it 
	        UninformedSearch<PuzzleMove> USearch = new UninformedSearch<PuzzleMove>
	                            (EightPuzzle.testEightPuzzle(), EightPuzzle.orderedEightPuzzle(), SearchType.AStar); 
	          
	        //With Delegation 
	        SuccessorFunction x = new EightPuzzleSuccessorFunction();
	        List<PuzzleMove> solutionList = USearch.search(x); 
	          
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
