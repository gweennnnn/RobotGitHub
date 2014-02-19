package part3;
import shared.ButtonPressListener;
import shared.Robot;
import shared.TouchSensorListener;
import lejos.nxt.Button;

public class Part3 extends Robot
{
	private final int MAX_DIST = 25;
	private final int MIN_DIST = 15;
	private int CURR_DIST;
	
	private boolean run = true;
	private boolean touch = false;
	
	public void run()
	{
		Button.ESCAPE.addButtonListener(new ButtonPressListener(this));
		TOUCH_LEFT.addSensorPortListener(new TouchSensorListener(this));
		TOUCH_RIGHT.addSensorPortListener(new TouchSensorListener(this));
		
		CURR_DIST = getAverageDistance();
		
		System.out.println("Press any button to start.");
    	waitForPress();
    	HEAD.continuous();
    	
    	while(run)
        {
    		CURR_DIST = getAverageDistance();
        	
        	//If current distance is within range
        	if(CURR_DIST <= MAX_DIST)
        	{
        		//far enough
        		if(CURR_DIST >= MIN_DIST)
        		{
        			System.out.println("JUST KEEP MOVING...");
        			goForward();
        		}
        		//not far enough
        		else
        		{
        			System.out.println("MOVE AWAY");
	        		moveSlightlyAwayFromWall();
        		}
        	}
        	//If current distance is within acceptable range
        	else if(CURR_DIST <= MAX_DIST+5)
        	{
        		System.out.println("MOVE TOWARDS");
        		moveSlightlyTowardsWall();
        	}
        	else
        	{
        		System.out.println("CORNER!(MAYBE)");
        		stopAndTurnRight();
        	} 	
        	
        	if(touch && run)
        	{
        		System.out.println("WALL!");
        		reverseAndTurnLeft();
        		touch = false;
        	}
        }
	}
	
	private void moveSlightlyAwayFromWall()
	{
		turn(40);
		goForward(100);
		turn(-30);
		stop();
		delay(100);
	}
	
	private void moveSlightlyTowardsWall()
	{
		turn(-40);
		goForward(100);
		turn(30);
		stop();
		delay(100);
	}
	
	private void stopAndTurnRight()
	{
		stop();
		goForward(150);
		stop();
		turnRight();
		goForward(170);
		stop();
		delay(100);
	}
	
	private void reverseAndTurnLeft()
	{
		goBackward(50);
		stop();
		turnLeft();
		stop();
		delay(100);
	}
	
	private int getAverageDistance()
	{
		int[] arr = new int[10];
		
		int x = 0;
		for(int i = 0; i < arr.length; i++)
		{
			x = x + HEAD.getDistance();
		}
		
		
		double result = (double)x / 10;
		
		int result2 = (int)Math.round(result);
		
		return result2;
	}
	
	@Override
	public void touchResponse()
	{
		touch = true;
	}
	
	@Override
	public void buttonResponse(Button b)
	{
		if (b == Button.ESCAPE)
			run = false;
	}
	
	public static void main(String args[])
	{
		Part3 p3 = new Part3();
		p3.run();
	}
	
}