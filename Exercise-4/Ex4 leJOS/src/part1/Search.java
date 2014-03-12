package part1;

import java.util.ArrayList;
import java.util.List;

import lejos.nxt.Button;
import List.AStar;
import List.AStarNode;
import List.Node;
import List.NodeInterface;
import List.Queue;
import List.Stack;
import puzzles.PuzzleInterface;
import rp13.search.interfaces.*;
import rp13.search.util.ActionStatePair;
import rp13.search.util.EqualityGoalTest;

/**
 * 
 * @author Gwen & Goda c:
 *
 */
public class Search<_action> extends EqualityGoalTest<PuzzleInterface>
{
	private PuzzleInterface startState;
	private Agenda<NodeInterface<_action>> list; 
	
	public enum SearchType { DepthFirst, BreadthFirst, AStar };
	
	
	
	public Search(PuzzleInterface startState, PuzzleInterface endState, SearchType searchType)
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

	public Agenda<NodeInterface<_action>> getList() {
		return list;
	}
	
	public List<_action> search(SuccessorFunction<_action, PuzzleInterface> succFunct, SearchType search) 
    { 

		NodeInterface<_action> startNode;
		if(search.equals(SearchType.AStar))
			startNode = new AStarNode<_action>(null, startState); 
		else
			startNode = new Node<_action>(null, startState); 
			
        ArrayList<ActionStatePair<_action, PuzzleInterface>> emptySuccs = new ArrayList<ActionStatePair<_action, PuzzleInterface>>(); 
        succFunct.getSuccessors(startState, emptySuccs); 
        
        
        return search(startNode, succFunct, emptySuccs, search); 
    } 

	public List<_action> search(NodeInterface<_action> startNode, SuccessorFunction<_action, PuzzleInterface> succFunct,
								List<ActionStatePair<_action, PuzzleInterface>> successors, SearchType search)
	{	
		
		NodeInterface<_action> currentNode = startNode;
		this.list.push(startNode);
		
		int counter = 0;
		while (!this.list.isEmpty())
		{

			currentNode = this.list.pop();
			System.out.println(counter + " passes so far...");
//			System.out.println(currentNode.getMove());
//			System.out.println(currentNode.getState());
//			System.out.println("Pass? " + this.isGoal(currentNode.getState()));
			if (this.isGoal(currentNode.getState()))
			{
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
			//CHILDREN
			for (ActionStatePair<_action, PuzzleInterface> state : successors)
			{ 	
				
				NodeInterface<_action> tempNode;
				if(search.equals(SearchType.AStar))									//GET THE VALUE
					tempNode = new AStarNode<_action>(state.getAction(), state.getState(), (AStarNode<_action>) currentNode);
				else
					tempNode = new Node<_action>(state.getAction(), state.getState(), (Node<_action>) currentNode); 

					
				if (!list.contains(tempNode) && !tempNode.getMove().equals(null))
				{

					this.list.push(tempNode);
				}
			}
			
			
			successors.clear();
			counter++;
			
			
//			System.out.println();
//			System.out.println();
////			System.out.println("NEXT NODE!");


		}
		
//		System.out.println("return");
//		Button.waitForAnyPress();
		
		return new ArrayList<_action>();
	}

}
