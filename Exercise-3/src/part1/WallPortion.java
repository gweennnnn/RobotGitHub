package part1;

import lejos.nxt.Button;
import shared.ButtonPressListener;
import shared.Robot;

public class WallPortion extends Robot {

	private final int WALL_DISTANCE = 200;
	
	public WallPortion() {
		//Add button listener
		Button.ESCAPE.addButtonListener(new ButtonPressListener(this));
	}
	
	public void run()
	{
		while (_run) {
			int detectedDistance = HEAD.getDistance();
			int difference = detectedDistance - WALL_DISTANCE;
			correctDistance(difference);
		}
	}
	
	private void correctDistance(int diff) {
		// The proportional distance from the target distance
		float speed = (float) diff / (float) WALL_DISTANCE; 

		// Set proportional speeds
		RIGHT_WHEEL.setSpeed((int) (FAST * speed));
		LEFT_WHEEL.setSpeed((int) (FAST * speed));

		// Move in the direction of the polarity (positive or negative) of the difference in distance
		if (diff > 0)
			goForward();
		else
			goBackward();
	}
	
	public void buttonResponse(Button b) {
		if (b == Button.ESCAPE)
			_run = false;
	}
	
}
