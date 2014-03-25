package grid;
import grid.GridBoard.Direction;
import grid.GridPuz;
import java.util.List;

import Interfaces.PuzzleInterface;
import Interfaces.SuccessorFunction;


public class GridSuccessorFunction implements
		SuccessorFunction<Direction, GridPuz> {

	@Override
	public void getSuccessors(GridPuz _state,
			List<ActionStatePair<Direction, GridPuz>> _successors) {

		//Make sure successors exist
		assert (_successors != null);

		//For every possible move
		for (Direction move : Direction.values()) 
		{
			//If legal
			if (_state.isPossibleMove(move))
			{
				//Simulate the move, and add it to the successors.
				GridPuz successor = new GridPuz(_state);
				successor.makeMove(move);
				_successors.add(new ActionStatePair<Direction, GridPuz>(move,successor));
			}
		}
	}

	public int getValue(PuzzleInterface state) {
		return 0;
	}

}
