package part2;
import shared.Robot;
import shared.TouchSensorListener;
import lejos.nxt.Button;

public class Part2WithListener extends Robot{
	
	public boolean run = true;
	public boolean turn = false;
	
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
		TOUCH_LEFT.addSensorPortListener(new TouchSensorListener(this));
		TOUCH_RIGHT.addSensorPortListener(new TouchSensorListener(this));
		
		while(run)
			if (turn)
			{
				turn = false;
				moveAway();
			}
			goForward();
	}

	@Override
	public void touchResponse()
	{
		turn = true;
	}

	@Override
	public void buttonResponse(Button b) {
		if(b == Button.ESCAPE)
			run = false;
	}

}
