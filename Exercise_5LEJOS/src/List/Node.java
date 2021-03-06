package List;

import java.util.List;

import Interfaces.NodeInterface;
import Interfaces.PuzzleInterface;



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
		this.predecessor = predecessor;
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
		return -1;
	}
	
	@Override
	public String toString() {
		String x = "Node(" + getMove() + ", " + getValue() + ")";
		return x;
	}

	@Override
	public boolean equals(Object x) {
		PuzzleInterface y = ((NodeInterface<_action>) x).getState();
		return this.getState().equals(y); 
	}

}
