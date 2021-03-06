package puzzles;
import java.util.List;

import rp13.search.interfaces.SuccessorFunction;
import rp13.search.util.ActionStatePair;

public class StringSuccessorFunction implements
		SuccessorFunction<Swap, StringPuzzle> {

	@Override
	public void getSuccessors(StringPuzzle _state, List<ActionStatePair<Swap, StringPuzzle>> _successors) {

		assert (_successors != null);

		for (int i = 0; i < _state.getValue().length(); i++) 
		{
			for (int j = 0; j < _state.getValue().length(); j++) 
			{
				if (j != i)
				{
					Swap move = new Swap(i, j);
					if (_state.isPossibleMove(move))
					{
						StringPuzzle successor = new StringPuzzle(_state);
						successor.makeMove(move);
						_successors.add(new ActionStatePair<Swap, StringPuzzle>(move,successor));
					}
				}
			}
		}
	}
}

