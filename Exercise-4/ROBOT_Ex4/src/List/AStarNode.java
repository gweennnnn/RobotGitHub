package List;

import puzzles.PuzzleInterface;


public class AStarNode<_action> extends Node<_action> implements NodeInterface<_action>{
	
	private int value;
	
	public AStarNode(_action move, PuzzleInterface state) {
		super(move, state);
		this.value = 0;
		this.setPredecessor(null);
		// TODO Auto-generated constructor stub
	}

	public AStarNode(_action move, PuzzleInterface state,int value, AStarNode<_action> predecessor)
	{
		super(move, state);
		this.value = state.calculateValue() + predecessor.value;
		this.setPredecessor(predecessor);
	}
	
	@Override
	public int getValue()
	{
		return this.value;
	}
}