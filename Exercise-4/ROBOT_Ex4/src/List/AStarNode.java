package List;


public class AStarNode<_action, _state> extends Node<_action, _state> implements NodeInterface<_action, _state>{
	
	private int value;
	
	public AStarNode(_action move, _state state, int value) {
		super(move, state);
		this.value = value;
		this.setPredecessor(null);
		// TODO Auto-generated constructor stub
	}

	public AStarNode(_action move, _state state,int value, AStarNode<_action, _state> predecessor)
	{
		super(move, state);
		this.value = value;
		this.setPredecessor(predecessor);
	}
	
	@Override
	public int getValue()
	{
		return this.value;
	}
}