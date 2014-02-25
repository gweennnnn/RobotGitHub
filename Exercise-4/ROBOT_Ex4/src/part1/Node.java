package part1;

import java.util.ArrayList;

import rp13.search.util.ActionStatePair;

public class Node<_action, _state>{
	private Node predecessor;
	private ActionStatePair<_action, _state> data;
	
	public Node(ActionStatePair<_action, _state> data) {
		this(data, null);
	}
	
	public Node(ActionStatePair<_action, _state> data, Node predecessor) {
		super();
		this.predecessor = predecessor;
		this.data = data;
	}
	
	public Node getPredecessor() {
		return predecessor;
	}

	public void setPredecessor(Node predecessor) {
		this.predecessor = predecessor;
	}

	public ActionStatePair<_action, _state> getData() {
		return data;
	}

	public void setData(ActionStatePair<_action, _state> data) {
		this.data = data;
	}

	public ArrayList<_action> solutionList(){
		
		ArrayList<_action> list;
		
		
		
		if (predecessor == null) 
		{
			list = new ArrayList<_action>();
		}
		else 
		{
			_action action = data.getAction();
			list = predecessor.solutionList();
			list.add(action);
		}
		return list;
	}
}
