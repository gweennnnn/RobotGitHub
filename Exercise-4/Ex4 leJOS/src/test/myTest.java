package test;
import static org.testng.AssertJUnit.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;

import part1.Search;
import part1.Search.SearchType;
import puzzles.EightPuzzle.PuzzleMove;
import puzzles.StringSuccessorFunction;
import puzzles.*;
import rp13.search.interfaces.SuccessorFunction;

@Test
public class myTest {
	Search<PuzzleMove> searchEightPuzzle;
	Search<Swap> searchStringPuz;
	SuccessorFunction succFunct;
	StringPuzzle strpuz;
	SearchType astar = SearchType.AStar;
	SearchType breadthfirst = SearchType.BreadthFirst;
	SearchType depthfirst = SearchType.DepthFirst;
	List solution;
	
	@BeforeMethod
	public void setUp()
	{
		
	}
	
	public void testEightPuzzleAStar()
	{
		searchEightPuzzle = new Search<PuzzleMove>(EightPuzzle.testEightPuzzle(), EightPuzzle.orderedEightPuzzle(), astar);
		succFunct = new EightPuzzleSuccessorFunction();
		solution =  searchEightPuzzle.search(succFunct, astar);
		assertEquals("[DOWN, DOWN, LEFT, UP, LEFT, DOWN, RIGHT, RIGHT, UP, LEFT, LEFT, UP, RIGHT, RIGHT, DOWN, LEFT, UP, LEFT, DOWN, DOWN, RIGHT, RIGHT]",
						solution.toString());
	}
	
	public void testEightPuzzleBreadthFirst()
	{
		searchEightPuzzle = new Search<PuzzleMove>(EightPuzzle.testEightPuzzle2(), EightPuzzle.orderedEightPuzzle(), breadthfirst);
		succFunct = new EightPuzzleSuccessorFunction();
		solution =  searchEightPuzzle.search(succFunct, astar);
		assertEquals("[LEFT, DOWN, DOWN, RIGHT, RIGHT]",
						solution.toString());
	}
	
	public void testEightPuzzleDepthFirst()
	{
		searchEightPuzzle = new Search<PuzzleMove>(EightPuzzle.testEightPuzzle3(), EightPuzzle.orderedEightPuzzle(), depthfirst);
		succFunct = new EightPuzzleSuccessorFunction();
		solution =  searchEightPuzzle.search(succFunct, depthfirst);
		assertEquals("[RIGHT, DOWN, LEFT, LEFT, DOWN, RIGHT, RIGHT, UP, LEFT, LEFT, DOWN, RIGHT, RIGHT, UP, LEFT, LEFT, DOWN, RIGHT, RIGHT, UP, LEFT, LEFT, DOWN, RIGHT, RIGHT, UP, LEFT, LEFT, DOWN, RIGHT, RIGHT]",
						solution.toString());
	}
	
	public void testStringPuzzleAStar()
	{
		strpuz = new StringPuzzle("avaj");
		succFunct = new StringSuccessorFunction();
		searchStringPuz = new Search<Swap>(new StringPuzzle("java"), strpuz, astar);
		solution = searchStringPuz.search(succFunct, astar);
		assertEquals("[{0, 3}, {2, 1}]", solution.toString());
	}
	
	public void testStringPuzzleBreadthFirst()
	{
		strpuz = new StringPuzzle("hye");
		succFunct = new StringSuccessorFunction();
		searchStringPuz = new Search<Swap>(new StringPuzzle("hey"), strpuz, breadthfirst);
		solution = searchStringPuz.search(succFunct, breadthfirst);
		assertEquals("[{0, 1}, {0, 2}, {0, 1}]", solution.toString());
	}
	
	public void testStringPuzzleDepthFirst()
	{
		strpuz = new StringPuzzle("ih");
		succFunct = new StringSuccessorFunction();
		searchStringPuz = new Search<Swap>(new StringPuzzle("hi"), strpuz, depthfirst);
		solution = searchStringPuz.search(succFunct, breadthfirst);
		assertEquals("[{1, 0}]", solution.toString());
	}
}

