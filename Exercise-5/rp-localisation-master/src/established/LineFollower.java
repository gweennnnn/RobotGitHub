package established;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;

public class LineFollower extends Robot {

	protected LightSensor LS_LEFT;
	protected LightSensor LS_RIGHT;
	protected LightSensor LS_MIDDLE;
	protected SensorData  s_left;
	protected SensorData  s_right;
	protected SensorData  s_mid;

	//Magic Numbers
	protected final float LEFT_ACCURACY = (float) 0.8;
	protected final float RIGHT_ACCURACY = (float) 0.8;
	protected final float MID_ACCURACY = (float) 0.95;
	protected final int   SPEED = 200;
	public static final float BLACK = 29;

	public void run() {
		init(); //Scan the area in front, consider the scanned area the background.

		while (_run) {
			boolean lineMid = s_mid.ls.getLightValue() < s_mid.threshold;
			boolean lineLeft = s_left.ls.getLightValue() < s_left.threshold;
			boolean lineRight = s_right.ls.getLightValue() < s_right.threshold;
			
			
			if (!lineMid && !lineLeft && !lineRight) { //NONE
				turnAround();
			}
			else { //ANY
				followLine();
			}
		}
	}
	
	protected void turnAround()
	{
		goForward(80);
		stop();
		turn(200);
		goForward();
	}
	
	protected void followLine()
	{
		//Calculate the error for each right and left sensor
		float leftError = s_left.getError();
		float rightError = s_right.getError();
		
		//Calculate speeds with error correction
		float speedLeft  = (SPEED - (4 * (leftError - rightError)));
		float speedRight = (SPEED - (4 * (rightError - leftError)));
		
		//Move using these speeds
		LEFT_WHEEL.setSpeed(speedLeft);
		RIGHT_WHEEL.setSpeed(speedRight);
		goForward();
	}
	
	/**
	 * Ends the program if the Escape button is pressed. Called by a ButtonPressListener.
	 */
	public void buttonResponse(Button b) {
		if (b == Button.ESCAPE)
			_run = false;
	}

	/* =================== INITIALISATION =================== */
	/**
	 * Initialises the sensors, and the corresponding SensorData constructs
	 */
	protected void init() {
		LCD.clearDisplay();
		
		//Wait until ready to scan
		System.out.println("Place away from lines, and press ENTER");
		waitForPress();
		
		//Get the sensors ready
		LS_LEFT = new LightSensor(LIGHT_LEFT);
		LS_RIGHT = new LightSensor(LIGHT_RIGHT);
		LS_MIDDLE = new LightSensor(LIGHT_MID);

		//Ready the SensorData constructs
		s_left = new SensorData(LS_LEFT);
		s_right = new SensorData(LS_RIGHT);
		s_mid = new SensorData(LS_MIDDLE);

		//Add listener
		Button.ESCAPE.addButtonListener(new ButtonPressListener(this));
		delay(400);

		//Initialise the background light values, for each sensor.
		s_left.background = 	s_left.ls.getLightValue();
		s_right.background = 	s_right.ls.getLightValue();
		s_mid.background = 		s_mid.ls.getLightValue();

		//Consider the threshold of each sensor, based on each of their detected values (to be relative to each sensor's inaccuracies)
		s_left.threshold =  Math.round((s_left.background  - BLACK)	* LEFT_ACCURACY  + BLACK);
		s_mid.threshold =   Math.round((s_mid.background   - BLACK) * MID_ACCURACY 	 + BLACK);
		s_right.threshold = Math.round((s_right.background - BLACK) * RIGHT_ACCURACY + BLACK);

		LCD.clearDisplay();
		System.out.println("Initialisation  complete. Press ENTER once above a line.");
		waitForPress();
	}
	
}
