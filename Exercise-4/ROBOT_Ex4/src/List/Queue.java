package List;


import part1.Node;
import rp13.search.interfaces.Agenda;

public class Queue<_action, _state> extends ListType<_action, _state>
{
	public Queue()
	{
		super();
	}

	@Override
	public Node<_action, _state> pop() {
		if(this.frontier.isEmpty())
			return null;
		this.explored.add(this.frontier.get(0).getState());
		return this.frontier.remove(0);
	}
}
