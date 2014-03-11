package List;

/**
 * 
 * @author Gwen & Goda c:
 *
 */
public class Stack<_action> extends ListType<_action>
{
	public Stack()
	{
		super();
	}
	
	@Override
	public NodeInterface<_action> pop() {
		if(this.frontier.isEmpty())
			return null;
		int lastItem = this.frontier.size() - 1;
		this.explored.add(this.frontier.get(lastItem).getState());
		return this.frontier.remove(lastItem);
	}
}
