package List;

import puzzles.PuzzleInterface;


public class AStarNode<_action> extends Node<_action> implements NodeInterface<_action>{
	
	private int value;
	private int costToGetHere;
	
	public AStarNode(_action move, PuzzleInterface state) {
		this(move, state, state.calculateValue(), null);
	}

	public AStarNode(_action move, PuzzleInterface state,int value, AStarNode<_action> predecessor)
	{
		super(move, state);
		this.costToGetHere = state.costToMove(move) + predecessor.getCostToGetHere();
		this.value = state.calculateValue() + this.getCostToGetHere();

		this.setPredecessor(predecessor);
	}
	
	@Override
	public int getValue()
	{
		return this.value;
	}

	public int getCostToGetHere() {
		return this.costToGetHere;
	}

	
}