package part2;
import shared.Robot;
import lejos.nxt.Button;
import lejos.nxt.TouchSensor;

public class Part2WithoutListener extends Robot
{
	private static TouchSensor LEFT_TOUCHSENSOR = new TouchSensor(TOUCH_LEFT);
	private static TouchSensor RIGHT_TOUCHSENSOR = new TouchSensor(TOUCH_RIGHT);
	public boolean run = true;
	
	public void moveAway()
	{
		stop(true);
		goBackward(100);
		turnLeft();
		stop();
		delay(200);
	}
	
	public void run()
	{
		while(!LEFT_TOUCHSENSOR.isPressed() && !RIGHT_TOUCHSENSOR.isPressed())
		{
			goForward();
			
			if(LEFT_TOUCHSENSOR.isPressed() || RIGHT_TOUCHSENSOR.isPressed())
				moveAway();
			
			if (Button.ESCAPE.isDown())
			{
				stop(true);
				break;
			}
		}
	}
}
