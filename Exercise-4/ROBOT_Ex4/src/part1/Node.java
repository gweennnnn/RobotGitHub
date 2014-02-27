package part1;

import java.util.ArrayList;

import rp13.search.util.ActionStatePair;

public class Node<_action, _state> {
	private Node predecessor;
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
	
	public ArrayList<_action> solutionList(){
		
		ArrayList<_action> list;
		list = new ArrayList<_action>();

		
		if (predecessor != null)
		{
			list = predecessor.solutionList();
			list.add(getMove());
		}
		
		return list;
	}

	public Node getPredecessor() {
		return predecessor;
	}

	public _action getMove() {
		return move;
	}

	public _state getState() {
		return state;
	}
}
