package established;

import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.OpticalDistanceSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.util.Delay;

public class Robot
{
	protected static final 		NXTRegulatedMotor RIGHT_WHEEL = Motor.B;
	protected static final 		NXTRegulatedMotor LEFT_WHEEL = Motor.C;
	public 	  static final 		SensorPort LIGHT_LEFT = SensorPort.S3;
	public    static final 		SensorPort LIGHT_RIGHT = SensorPort.S2;
	public	  static final		SensorPort LIGHT_MID = SensorPort.S1;
	public    static final 		OpticalDistanceSensor HEAD = new OpticalDistanceSensor(SensorPort.S4);
	public static boolean _run = true;

	//Speeds
	public    static final int	SLOW = 300;
	public    static final int 	REGULAR = 600;
	public    static final int 	FAST = 900;

	
	protected static final 		DifferentialPilot pilot = new DifferentialPilot(56, 100, LEFT_WHEEL, RIGHT_WHEEL);
	
	protected static void turnLeft()
	{
		turn(90);
    	//RIGHT_WHEEL.rotate(338);  
	}
	
	protected static void turnRight()
	{
		turn(-90);
		//LEFT_WHEEL.rotate(338);
	}
	
	
	protected static void turn(int angle)
	{
		//90 is 110 deg
		//360 is 440
		double x = ((double)angle / 360 ) * 440;
		int angleToTurn = (int)Math.round(x);
		pilot.rotate(angleToTurn);
	}
	
	protected static void turn(double turnRate, double angle, boolean immediateReturn) {
		pilot.steer(turnRate, angle, immediateReturn);
		//pilot.rotate(angle);
	}
	
	protected static void goForward()
	{
		LEFT_WHEEL.forward();
		RIGHT_WHEEL.forward();
	}
	
	protected static void goForward(int millimetres)
	{
		pilot.travel(millimetres);
	}
	
	protected static void goBackward()
	{
		LEFT_WHEEL.backward();
		RIGHT_WHEEL.backward();
	}
	
	protected static void goBackward(int millimetres)
	{
		pilot.travel(-millimetres);
	}
	
	protected static void stop()
	{
		pilot.stop();
	}
	
	protected static void stop(boolean immediateReturn)
	{
		LEFT_WHEEL.stop(true);
		RIGHT_WHEEL.stop(true);
	}
	
	protected static void delay(int time)
	{
		Delay.msDelay(time);
	}
	
	protected void setSpeed(int speed)
	{
		pilot.setTravelSpeed(speed);
	}
	
	protected static void waitForPress()
	{
		Button.waitForAnyPress();
	}
	
	public void touchResponse()
	{
		System.out.println("OUCH");
	}

	public void buttonResponse(Button b)
	{
			System.exit(0);
	}
}
