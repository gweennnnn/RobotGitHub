package List;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import part1.Node;
import rp13.search.interfaces.Agenda;

public class Stack<_action, _state> extends ListType<_action, _state>
{
	public Stack()
	{
		super();
	}
	
	@Override
	public Node<_action, _state> pop() {
		if(this.frontier.isEmpty())
			return null;
		int lastItem = this.frontier.size() - 1;
		this.explored.add(this.frontier.get(lastItem).getState());
		return this.frontier.remove(lastItem);
	}
}
