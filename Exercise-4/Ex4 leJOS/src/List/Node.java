package List;

import java.util.List;

import puzzles.PuzzleInterface;

/**
 * 
 * @author Gwen & Goda c:
 *
 */
public class Node<_action> implements NodeInterface<_action>{
	protected NodeInterface<_action> predecessor;
	protected _action move;
	protected PuzzleInterface state;
	
	public Node(_action move, PuzzleInterface state) {
		this(move, state, null);
	}
	
	public Node(_action move, PuzzleInterface state, NodeInterface<_action> predecessor) {
		super();
		this.predecessor = predecessor;
		this.move = move;
		this.state = state;
	}

	

	@Override
	public NodeInterface<_action> getPredecessor() {
		return this.predecessor;
	}

	@Override
	public void setPredecessor(NodeInterface<_action> predecessor) {
		this.predecessor = (Node<_action>) predecessor;
	}
	
	@Override
	public _action getMove() {
		return move;
	}

	@Override
	public PuzzleInterface getState() {
		return state;
	}

	@Override
	public void getSolutionList(List x) {
		NodeInterface<_action> currNode = this;
				
		while(currNode.getMove() != null)
		{
			x.add(0, currNode.getMove());
			currNode = currNode.getPredecessor();
		}
	}

	@Override
	public int getValue() {
		// TODO Auto-generated method stub
		return -1;
	}
}
