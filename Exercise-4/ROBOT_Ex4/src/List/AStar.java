package List;

public class AStar <_action> extends ListType<_action>
{
	public AStar()
	{
		super();
	}
	
	public void push(AStarNode<_action> item) {
		boolean added = false;
		if(frontier.size() == 0)
			frontier.add(item);
		else
		{
			for(int i = 0; i < frontier.size(); i++)
			{
				if(item.getValue() <= frontier.get(i).getValue())
				{
					frontier.add(i, item);
					added = true;
				}
			}
			if (added == false) frontier.add(item);
		}
	}
	
	@Override
	public AStarNode<_action>  pop() {
		System.out.println("Extend this class and overwrite this particular method");
		return null;
	}
}