package rp.robotics.localisation;

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

		// Create the new distribution that will result from applying the action
		// model
		GridPositionDistribution to = new GridPositionDistribution(_from);

		// Move the probability in the correct direction for the action
		if (_heading == Heading.PLUS_X) {
			movePlusX(_from, to);
		} else if (_heading == Heading.PLUS_Y) {
			// you could implement a movePlusY etc. or you could find a way do
			// do all moves in a single method. Hint: all changes are just + or
			// - 1 to an x or y value.
		} else if (_heading == Heading.MINUS_X) {

		} else if (_heading == Heading.MINUS_Y) {

		}

		return to;
	}

	/**
	 * Move probabilities from _from one cell in the plus x direction into _to
	 * 
	 * @param _from
	 * @param _to
	 */
	private void movePlusX(GridPositionDistribution _from,
			GridPositionDistribution _to) {

		// iterate through points updating as appropriate
		for (int y = 1; y < _to.getGridHeight(); y++) {

			for (int x = 1; x < _to.getGridWidth(); x++) {

				
				
				// make sure to respect obstructed grid points
				if (!_to.isObstructed(x, y)) {
					
					// position before move
					int fromX = x -1;
					int fromY = y;
					float fromProb;

					// position after move
					int toX = x;
					int toY = y;

					// the action model should work out all of the different
					// ways (x,y) in the _to grid could've been reached based on
					// the _from grid and the move taken (in this case
					// HEADING.PLUS_X)
					
				//if the previous grid point was obstructed --> set prob. to 0
					
				if (_from.isObstructed(fromX, fromY)){
					fromProb = 0;
				}
				
				//if the previous grid is not a valid point --> set prob. to0
				
				else if (_from.isValidGridPoint(fromX, fromY)){
					fromProb = 0;
				}
				
				else fromProb = _from.getProbability(fromX, fromY);

					// for example if the only way to have got to _to (x,y) was
					// from _from (x-1, y) (i.e. there was a PLUS_X move from
					// (x-1, y) then you write that to the (x, y) value

					// The below code does not move the value, just copies
					// it to the same position

					

					// set probability for position after move
					_to.setProbability(toX, toY, fromProb);

				}
			}
		}
	}
}
