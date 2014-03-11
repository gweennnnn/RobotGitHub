package List;

import grid.PuzzleInterface;
import java.util.List;


/**
 * 
 * @author Gwen & Goda c:
 *
 */
public interface NodeInterface<_action> {
	
	public void getSolutionList(List x);
	
	public NodeInterface<_action> getPredecessor();
	
	public void setPredecessor(NodeInterface<_action> predecessor);
	
	public _action getMove();
	
	public PuzzleInterface getState();
	
	public int getValue();
}
