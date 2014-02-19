package part3;

import java.awt.Rectangle;

import lejos.nxt.Button;
import lejos.nxt.SensorConstants;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.NXTCam;
import shared.ButtonPressListener;
import shared.Robot;

public class Part3 extends Robot {

	private final int SPEED = SLOW;
	private final int CORRECTION = 50;
	
	private final int CENTER = 200;
	NXTCam cam = new NXTCam(SensorPort.S4);
	
	public Part3()
	{
		//Add listener
		Button.ESCAPE.addButtonListener(new ButtonPressListener(this));
	}
	
	public void run()
	{
		cam.enableTracking(true);
		cam.setTrackingMode(NXTCam.OBJECT_TRACKING);		
		while(_run)
		{
			int index = getRed();
			Rectangle rect = cam.getRectangle(index);
			correctTowardsBall(rect);
		}
	}
	
	/**
	 * Searches for the reddest object
	 * @return The index position of the reddest object
	 */
	private int getRed() {
		
		//Get the index of the red object
		boolean found = false;
		int i;
		cam.sortBy(NXTCam.COLOR);
		
		System.out.println(cam.getNumberOfObjects());
		
		for (i = 0; (i < cam.getNumberOfObjects()) && !found; i++) //For each detected object
		{
			//Test the colour
			int color = cam.getObjectColor(i);
			if (color == SensorConstants.RED) 
			{
				found = true;
				System.out.println("RED!");
			}
		}
		
		return i;
	}

	protected void correctTowardsBall(Rectangle rect)
	{
		//Adjust the speed of each wheel, to aim towards the object.
		float speedLeft  = SPEED;
		float speedRight = SPEED;
		
		int centerOfRectangle = rect.x - rect.width;
		
		if (centerOfRectangle < CENTER) speedLeft -= CORRECTION;
		else 							speedRight -= CORRECTION;
		
		//Move using these speeds
		/*LEFT_WHEEL.setSpeed(speedLeft);
		RIGHT_WHEEL.setSpeed(speedRight);
		goForward();*/
		//System.out.println(centerOfRectangle);
	}
	
	/**
	 * Ends the program if the Escape button is pressed. Called by a ButtonPressListener.
	 */
	public void buttonResponse(Button b) {
		if (b == Button.ESCAPE)
			_run = false;
	}
	
	public static void main(String[] args) {
		Part3 p3 = new Part3();
		p3.run();

	}

}
