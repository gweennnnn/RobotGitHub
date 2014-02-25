package part1;

import java.util.ArrayList;

import rp13.search.util.ActionStatePair;

public class Node<_action, _state> {
	Node predecessor;
	ActionStatePair<_action, _state> data;
	
	public Node(ActionStatePair<_action, _state> data) {
		this(data, null);
	}
	
	public Node(ActionStatePair<_action, _state> data, Node predecessor) {
		super();
		this.predecessor = predecessor;
		this.data = data;
	}
	
	public ArrayList<_action> solutionList(){
		
		ArrayList<_action> list;
		list = new ArrayList<_action>();

		
		if (predecessor == null) 
		{
			return list;
		}
		else 
		{
			list = predecessor.solutionList();
			list.add(data.getAction());
		}
		
		return list;
	}
}
