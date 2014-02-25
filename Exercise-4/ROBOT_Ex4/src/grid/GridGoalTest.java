package grid;
import rp13.search.interfaces.GoalTest;

public class GridGoalTest implements EqualityGoalTest<Grid> {

	private final Grid m_goal;

	public GridGoalTest(Grid _goal) {
		super(_goal);
	}

	@Override
	public boolean isGoal(Grid _state) {
		return m_goal.equals(_state);
	}

}

public class EqualityGoalTest<StateT> implements GoalTest<StateT> {

	public EqualityGoalTest(StateT _goal) {
		super();
		m_goal = _goal;
	}

	@Override
	public boolean isGoal(StateT _state) {
		return m_goal.equals(_state);
	}

}
