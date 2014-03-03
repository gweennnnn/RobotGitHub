package List;


public class AStarNode<_action, _state> extends Node<_action, _state> {
	
//	doesn't have the value sent, calculates it on the s
	private int value;
	

	public AStarNode(_action move, _state state, int value) {
		super(move, state);
		this.value = value;

	}
	
	public AStarNode(_action move, _state state,int value, AStarNode<_action, _state> predecessor) {
		super(move, state, predecessor);
		this.value = value;
	}

	public int getValue() {
		return value;
	}

}
