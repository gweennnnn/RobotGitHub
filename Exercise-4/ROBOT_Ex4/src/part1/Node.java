package part1;

import java.util.ArrayList;
import java.util.List;

import rp13.search.util.ActionStatePair;

public class Node<_action, _state> {
	private Node<_action, _state> predecessor;
	private _action move;
	private _state state;
	
	public Node(_action move, _state state) {
		this(move, state, null);
	}
	
	public Node(_action move, _state state, Node<_action, _state> predecessor) {
		super();
		this.predecessor = predecessor;
		this.move = move;
		this.state = state;
	}
	
	public void getSolutionList(List<_action> solutionList)
	{
		if (predecessor != null)
		{
			predecessor.getSolutionList(solutionList);
			solutionList.add(getMove());
		}
	}

	public Node getPredecessor() {
		return predecessor;
	}

	
	public void setPredecessor(Node<_action, _state> predecessor) {
		this.predecessor = predecessor;
	}

	public _action getMove() {
		return move;
	}

	public _state getState() {
		return state;
	}
}
