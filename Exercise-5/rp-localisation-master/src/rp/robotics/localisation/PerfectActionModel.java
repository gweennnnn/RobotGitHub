package rp.robotics.localisation;

import lejos.robotics.navigation.Pose;
import rp.robotics.mapping.Heading;

/**
 * Example structure for an action model that should move the probabilities 1
 * cell in the requested direction. In the case where the move would take the
 * robot into an obstacle or off the map, this model assumes the robot stayed in
 * one place. This is the same as the model presented in Robot Programming
 * Lecture 14.
 * 
 * Note that this class doesn't actually do this, instead it shows you a
 * <b>possible</b> structure for your action model.
 * 
 * @author nah
 * 
 */
public class PerfectActionModel implements ActionModel {

	@Override
	public GridPositionDistribution updateAfterMove(
			GridPositionDistribution _from, Heading _heading) {

		// Create the new distribution that will result from applying the action model
		GridPositionDistribution to = new GridPositionDistribution(_from);
		int moveX = 0;
		int moveY = 0;

		// Move the probability in the correct direction for the action
		if (_heading == Heading.PLUS_X) {

			moveX = -1;
			movePlus(_from, to, moveX, moveY);

		} else if (_heading == Heading.PLUS_Y) {

			moveY = -1;
			movePlus(_from, to, moveX, moveY);

		} else if (_heading == Heading.MINUS_X) {
			moveX = 1;
			moveMinus(_from, to, moveX, moveY);

		} else if (_heading == Heading.MINUS_Y) {
			moveY = 1;
			moveMinus(_from, to, moveX, moveY);
		}

		return to;
	}

	/**
	 * Move probabilities from _from one cell in the plus x or y direction into
	 * _to
	 * 
	 * @param _from
	 * @param _to
	 * @param moveX
	 * @param moveY
	 */

	private void movePlus(GridPositionDistribution _from,
			GridPositionDistribution _to, int moveX, int moveY) {

		// iterate through points updating as appropriate

		for (int y = (_to.getGridHeight() - 1); y > (0 + moveX); y--) {
			for (int x = (_to.getGridWidth() - 1); x > (0 + moveY); x--) {
				move(_from, _to, moveX, moveY, x, y);
			}
		}

	}

	private void moveMinus(GridPositionDistribution _from,
			GridPositionDistribution _to, int moveX, int moveY) {

		// iterate through points updating as appropriate

		for (int y = 0; y < _to.getGridHeight(); y++) {
			for (int x = 0; x < _to.getGridWidth(); x++) {
				move(_from, _to, moveX, moveY, x, y);
			}
		}

	}

	protected void move(GridPositionDistribution _from,
			GridPositionDistribution _to, int moveX, int moveY, int x, int y) {
		// position before move
		int fromX = x + moveX;
		int fromY = y + moveY;

		// position after move
		int toX = x;
		int toY = y;

		// make sure to respect obstructed grid points

		// and if the move is possible to make
		// In other words : no walls in between points

		// and if the previous point is a valid point

		if (!_to.isObstructed(x, y)
				&& _from.getGridMap().isValidTransition(fromX, fromY, toX, toY)
				&& _to.isValidGridPoint(toX, toY)) {

			float fromProb;
			float currentProb = _to.getProbability(toX, toY);

			// the action model should work out all of the different
			// ways (x,y) in the _to grid could've been reached based on
			// the _from grid and the move taken (in this case
			// HEADING.PLUS_X)

			// if the previous grid is not a valid point --> set prob. to0

			if (!_from.isValidGridPoint(fromX, fromY)) {
				fromProb = 0;
			}
			// TODO Doesn't this override the previous statement? 
			fromProb = _from.getProbability(fromX, fromY);

			// set probability for position after move

			_to.setProbability(toX, toY, fromProb + currentProb);
			_to.setProbability(fromX, fromY, 0);
		}
	}

}
