import java.util.List;

import rp13.search.interfaces.SuccessorFunction;
import rp13.search.util.ActionStatePair;

public class GridSuccessorFunction implements
		SuccessorFunction<Grid.Direction, Grid> {

	@Override
	public void getSuccessors(Grid _state, List<ActionStatePair<Grid.Direction, Grid>> _successors) {

		assert (_successors != null);

		for (Grid.Direction move : Grid.Direction.values()) 
		{
			if (_state.isPossibleMove(move))
			{
				Grid successor = new Grid(_state);
				successor.makeMove(move);
				_successors.add(new ActionStatePair<Grid.Direction, Grid>(move,successor));
			}
		}
	}
}
