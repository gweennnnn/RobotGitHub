package List;

import Interfaces.NodeInterface;

/**
 * 
 * @author Gwen & Goda c:
 *
 */
public class Queue<_action> extends ListType<_action>
{
	public Queue()
	{
		super();
	}

	@Override
	public NodeInterface<_action> pop() {
		if(this.frontier.isEmpty())
			return null;
		this.explored.add(this.frontier.get(0).getState());
		return this.frontier.remove(0);
	}
	
	@Override
	public String toString() {
		String x = "Frontier \n";
		for(int i = 0; i < frontier.size(); i++)
		{
			x = x + frontier.get(i).toString() + "\n";
		}
		return x;
	}
}
