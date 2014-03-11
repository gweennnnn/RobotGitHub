package rp13.search.util;

import rp13.search.interfaces.GoalTest;

/**
 * A goal test which calls the equals methods of an object.
 * 
 * @author Nick Hawes
 * 
 * @param <StateT>
 */
public class EqualityGoalTest<StateT> implements GoalTest<StateT> {

	private final StateT m_goal;

	public EqualityGoalTest(StateT _goal) {
		super();
		m_goal = _goal;
	}

	@Override
	public boolean isGoal(StateT _state) {
//		System.out.println(m_goal.equals(_state));
		return m_goal.toString().equals(_state.toString());
	}

}
