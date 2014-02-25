package grid;
import grid.Grid.Direction;

import java.util.List;

import rp13.search.interfaces.SuccessorFunction;
import rp13.search.util.ActionStatePair;

public class GridSuccessorFunction implements
		SuccessorFunction<Direction, Grid> {

	@Override
	public void getSuccessors(Grid _state, List<ActionStatePair<Direction, Grid>> _successors) {

		assert (_successors != null);

		for (Direction move : Direction.values()) 
		{
			if (_state.isPossibleMove(move))
			{
				Grid successor = new Grid(_state);
				successor.makeMove(move);
				_successors.add(new ActionStatePair<Direction, Grid>(move,successor));
			}
		}
	}
}
