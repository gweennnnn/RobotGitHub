package List;

import java.util.List;

import puzzles.PuzzleInterface;

/**
 * 
 * @author Gwen & Goda c:
 *
 */
public class AStarNode<_action> extends Node<_action> implements NodeInterface<_action>{
	
	private int value;
	private int costToGetHere;
	
	public AStarNode(_action move, PuzzleInterface state) {
		this(move, state, null, 0);
	}

	public AStarNode(_action move, PuzzleInterface state, AStarNode<_action> predecessor)
	{
		this(move, state, predecessor, (state.costToMove(move) + predecessor.getCostToGetHere()));
	}
	
	public AStarNode(_action move, PuzzleInterface state, AStarNode<_action> predecessor, int costToGetHere)
	{
		super(move, state);
		this.costToGetHere = costToGetHere;
		this.value =  state.calculateValue() + this.costToGetHere;
		this.setPredecessor(predecessor);
	}
	
	@Override
	public _action getMove()
	{
		return this.move;
	}
	
	@Override
	public int getValue()
	{
		return this.value;
	}

	public int getCostToGetHere() {
		return this.costToGetHere;
	}

//	@Override
//	public void getSolutionList(List x) {
//		NodeInterface<_action> currNode = this;
//				
//		while(currNode.getMove() != null)
//		{
//			System.out.println("MOVE" + currNode.getMove());
//			System.out.println("value: " + currNode.getValue());
//			currNode = currNode.getPredecessor();
//		}
//	}
	
}