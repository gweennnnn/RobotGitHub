package grid;
import grid.Grid.Direction;

import java.util.List;

import puzzles.PuzzleInterface;
import rp13.search.interfaces.SuccessorFunction;
import rp13.search.util.ActionStatePair;

public class GridSuccessorFunction implements
		SuccessorFunction<Direction, Grid> {

	@Override
	public void getSuccessors(Grid _state, List<ActionStatePair<Direction, Grid>> _successors) {

		//Make sure successors exist
		assert (_successors != null);

		//For every possible move
		for (Direction move : Direction.values()) 
		{
			//If legal
			if (_state.isPossibleMove(move))
			{
				//Simulate the move, and add it to the successors.
				Grid successor = new Grid(_state);
				successor.makeMove(move);
				_successors.add(new ActionStatePair<Direction, Grid>(move,successor));
			}
		}
	}

	public int getValue(PuzzleInterface state) {
		return 0;
	}
}
