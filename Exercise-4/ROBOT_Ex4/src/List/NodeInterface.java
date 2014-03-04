package List;

import java.util.List;

import puzzles.PuzzleInterface;

public interface NodeInterface<_action> {
	
	public void getSolutionList(List<_action> x);
	
	public NodeInterface<_action> getPredecessor();
	
	public void setPredecessor(NodeInterface<_action> predecessor);
	
	public _action getMove();
	
	public PuzzleInterface getState();
	
	public int getValue();
}
