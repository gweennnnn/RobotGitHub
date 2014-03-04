package List;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import puzzles.PuzzleInterface;
import rp13.search.interfaces.Agenda;

/**
 * 
 * @author Gwen & Goda c:
 *
 */
public class ListType<_action> implements Agenda<NodeInterface<_action>>
{
	protected List<NodeInterface<_action>> frontier;
	protected List<PuzzleInterface> explored;
	
	
	public ListType()
	{
		this.frontier = new ArrayList<NodeInterface<_action>>();
		this.explored = new ArrayList<PuzzleInterface>();
	}
	
	public List<NodeInterface<_action>> getFrontier() {
		return frontier;
	}

	public List<PuzzleInterface> getExplored() {
		return explored;
	}
	
	@Override
	public Iterator<NodeInterface<_action>> iterator() {
		return frontier.iterator();
	}

	@Override
	public void push(NodeInterface<_action> _item) {
		frontier.add(_item);
	}

	@Override
	public NodeInterface<_action> pop() {
		System.out.println("Extend this class and overwrite this particular method");
		return null;
	}

	@Override
	public boolean isEmpty() {
		return frontier.isEmpty();
	}

	@Override
	public boolean contains(NodeInterface<_action> _item) {
		return this.explored.contains(_item.getState());
	}

	@Override
	public NodeInterface<_action> peek() {
		return this.frontier.get(0);
	}

}
