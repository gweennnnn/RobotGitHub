package List;

import java.util.List;

public interface NodeInterface<_action, _state> {
	public void getSolutionList(List<_action> x);
	
	public NodeInterface<_action, _state> getPredecessor();
	
	public void setPredecessor(NodeInterface<_action, _state> predecessor);
	
	public _action getMove();
	
	public _state getState();
	
	public int getValue();
}
