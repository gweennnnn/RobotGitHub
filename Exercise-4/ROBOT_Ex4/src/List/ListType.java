package List;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import puzzles.PuzzleInterface;
import rp13.search.interfaces.Agenda;

public class ListType<_action> implements Agenda<Node<_action>>
{
	protected List<Node<_action>> frontier;
	protected List<PuzzleInterface> explored;
	
	
	public ListType()
	{
		this.frontier = new ArrayList<Node<_action>>();
		this.explored = new ArrayList<PuzzleInterface>();
	}
	
	public List<Node<_action>> getFrontier() {
		return frontier;
	}

	public List<PuzzleInterface> getExplored() {
		return explored;
	}
	
	@Override
	public Iterator<Node<_action>> iterator() {
		return frontier.iterator();
	}

	@Override
	public void push(Node<_action> _item) {
		frontier.add(_item);
	}

	@Override
	public Node<_action> pop() {
		System.out.println("Extend this class and overwrite this particular method");
		return null;
	}

	@Override
	public boolean isEmpty() {
		return frontier.isEmpty();
	}

	@Override
	public boolean contains(Node<_action> _item) {
		return this.explored.contains(_item.getState());
	}

	@Override
	public Node<_action> peek() {
		return this.frontier.get(0);
	}

}
