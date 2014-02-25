package part1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import rp13.search.interfaces.Agenda;

public class Queue<_action, _state> implements Agenda<Node<_action, _state>>
{
	private List<Node<_action, _state>> frontier;
	private List<_state> explored;
	
	public Queue()
	{
		this.frontier = new ArrayList<Node<_action, _state>>();
		this.explored = new ArrayList<_state>();
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
		explored.add(frontier.get(0).getData().getState());
		return frontier.remove(0);
	}

	@Override
	public boolean isEmpty() {
		return frontier.isEmpty();
	}

	@Override
	public boolean contains(Node<_action, _state> _item) {
		return this.explored.contains(_item.getData().getState());
	}
}
