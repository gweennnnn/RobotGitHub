package part1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import rp13.search.interfaces.*;
import rp13.search.problem.puzzle.EightPuzzle;
import rp13.search.problem.puzzle.EightPuzzle.PuzzleMove;
import rp13.search.util.EqualityGoalTest;

public class UninformedSearch<_action, _state> extends EqualityGoalTest<_state>
{
	private _state startState;
	private EqualityGoalTest<_state> goaltest;
	private Agenda<Node<_action, _state>> searchType;
	
	public enum SearchType { DepthFirst, BreadthFirst };
	
	public UninformedSearch(_state startState, _state endState, SearchType search)
	{
		super(endState);
		this.startState = startState;
		if(search.equals(SearchType.DepthFirst))
			searchType = new Queue<_action, _state>();
		else
			searchType = new Stack<_action, _state>();	
	}
	
	public List<_action> search()
	{
		//goda your code
		
		return null;
	}
	
	public static void main (String[] args)
	{
		//This is how you use it
		UninformedSearch<PuzzleMove, EightPuzzle> x = new UninformedSearch<EightPuzzle.PuzzleMove, EightPuzzle>
							(EightPuzzle.randomEightPuzzle(), EightPuzzle.orderedEightPuzzle(), SearchType.BreadthFirst);
		
	}
}
