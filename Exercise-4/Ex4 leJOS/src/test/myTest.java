package test;
import static org.testng.AssertJUnit.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;

import part1.Search;
import part1.Search.SearchType;
import puzzles.EightPuzzle;
import puzzles.EightPuzzle.PuzzleMove;
import puzzles.EightPuzzleSuccessorFunction;
import puzzles.StringPuzzle;
import puzzles.Swap;
import rp13.search.interfaces.SuccessorFunction;

@Test
public class myTest {
	Search<PuzzleMove> search;
	Search<Swap> wordPuzzleAStar;
	SuccessorFunction succFunct;
	StringPuzzle strpuz;
	SearchType searchType1 = SearchType.AStar;
	List<PuzzleMove> solution;
	
	@BeforeMethod
	public void setUp()
	{
		strpuz = new StringPuzzle("java");
		strpuz.jumble();
		wordPuzzleAStar = new Search<Swap>(strpuz.orderedWord(), strpuz, SearchType.AStar);
	}
	
	public void testEightPuzzleAStar()
	{
		search = new Search<PuzzleMove>(EightPuzzle.testEightPuzzle(), EightPuzzle.orderedEightPuzzle(), searchType1);
		solution =  search.search(succFunct, searchType1);
		assertEquals("[DOWN, DOWN, LEFT, UP, LEFT, DOWN, RIGHT, RIGHT, UP, LEFT, LEFT, UP, RIGHT, RIGHT, DOWN, LEFT, UP, LEFT, DOWN, DOWN, RIGHT, RIGHT]",
						solution.toString());
	}
	
	public void testStringPuzzleAStar()
	{
		wordPuzzleAStar = new Search<Swap>(strpuz.orderedWord(), strpuz, SearchType.AStar);
	}
}

