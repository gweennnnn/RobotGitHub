package List;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import part1.Node;
import rp13.search.interfaces.Agenda;

public class ListType<_action, _state> implements Agenda<Node<_action, _state>>
{
	protected List<Node<_action, _state>> frontier;
	protected List<_state> explored;
	
	
	public ListType()
	{
		this.frontier = new ArrayList<Node<_action, _state>>();
		this.explored = new ArrayList<_state>();
	}
	
	public List<Node<_action, _state>> getFrontier() {
		return frontier;
	}

	public List<_state> getExplored() {
		return explored;
	}
	
	@Override
	public Iterator<Node<_action, _state>> iterator() {
		return frontier.iterator();
	}

	@Override
	public void push(Node<_action, _state> _item) {
		frontier.add(_item);
	}

	@Override
	public Node<_action, _state> pop() {
		System.out.println("Extend this class and overwrite this particular method");
		return null;
	}

	@Override
	public boolean isEmpty() {
		return frontier.isEmpty();
	}

	@Override
	public boolean contains(Node<_action, _state> _item) {
		return this.explored.contains(_item.getState());
	}

	@Override
	public Node<_action, _state> peek() {
		return this.frontier.get(0);
	}
}
