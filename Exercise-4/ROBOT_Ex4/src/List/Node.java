package List;

import java.util.List;


public class Node<_action, _state> implements NodeInterface<_action, _state>{
	private NodeInterface<_action, _state> predecessor;
	private _action move;
	private _state state;
	
	public Node(_action move, _state state) {
		this(move, state, null);
	}
	
	public Node(_action move, _state state, NodeInterface<_action, _state> predecessor) {
		super();
		this.predecessor = predecessor;
		this.move = move;
		this.state = state;
	}

	

	@Override
	public NodeInterface<_action, _state> getPredecessor() {
		return this.predecessor;
	}

	@Override
	public void setPredecessor(NodeInterface<_action, _state> predecessor) {
		this.predecessor = predecessor;
	}
	
	@Override
	public _action getMove() {
		return move;
	}

	@Override
	public _state getState() {
		return state;
	}

	@Override
	public void getSolutionList(List<_action> x) {
		NodeInterface<_action, _state> currNode = this;
				
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
