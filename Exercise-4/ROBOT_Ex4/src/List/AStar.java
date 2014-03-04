package List;

/**
 * 
 * @author Gwen & Goda c:
 *
 */
public class AStar <_action> extends Queue<_action> {
	public AStar()
	{
		super();
	}
	
	
	@Override
	public void push(NodeInterface<_action> _item) {
		boolean added = false;
		if(frontier.size() == 0)
			frontier.add(_item);
		else
		{
			for(int i = 0; i < frontier.size(); i++)
			{
				if(_item.getValue() <= frontier.get(i).getValue())
				{
					frontier.add(i, _item);
					added = true;
					break;
				}
			}

			if (added == false) frontier.add(_item);
		}
	}
}