package part1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import rp13.search.interfaces.*;
import rp13.search.util.EqualityGoalTest;

public class UninformedSearch<action, state, succFunct> implements Agenda<Node<action, state>> {
	private List<Node<action, state>> frontier;
	private List<state> explored;
	private state startState;
	private EqualityGoalTest<state> goaltest;
	
	public UninformedSearch(state startState, state endState)
	{
		this.startState = startState;
		this.frontier = new ArrayList<Node<action, state>>();
		this.explored = new ArrayList<state>();
		goaltest = new EqualityGoalTest<state>(endState);
	}

	@Override
	public Iterator<Node<action, state>> iterator() {
		// TODO Auto-generated method stub
		return frontier.iterator();
	
	
	}

	@Override
	public void push(Node<action, state> _item) {
		frontier.add(_item);
		
	}

	@Override
	public Node<action, state> pop() {
		// TODO Auto-generated method stub
		return frontier.remove(0);
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return this.frontier.isEmpty();
	}

	@Override
	public boolean contains(Node<action, state> _item) {
		// TODO Auto-generated method stub
		for(int i = 0; i < this.explored.size(); i++)
		{
			if(this.explored.get(i) == _item.getData().getState())
				return true;
		}
		return false;
	}
	
	public boolean isGoal(state _state)
	{
		return goaltest.isGoal(_state);
	}
}
