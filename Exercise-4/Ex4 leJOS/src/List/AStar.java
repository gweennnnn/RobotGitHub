package List;

import rp13.search.interfaces.Agenda;

public class AStar <_action, _state> extends ListType<_action, _state>
{
	public AStar()
	{
		super();
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

}
