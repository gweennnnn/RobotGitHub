package shared;

import java.util.ArrayList;
import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.util.Delay;

public class Robot
{
	public static final NXTRegulatedMotor SCANNER = Motor.A;
	public static final NXTRegulatedMotor RIGHT_WHEEL = Motor.B;
	public static final NXTRegulatedMotor LEFT_WHEEL = Motor.C;
	public static final SensorPort TOUCH_LEFT = SensorPort.S1;
	public static final SensorPort TOUCH_RIGHT = SensorPort.S4;
	public static final UltrasonicSensor HEAD = new UltrasonicSensor(SensorPort.S3);

	public static final int SLOW = 300;
	public static final int REGULAR = 600;
	public static final int FAST = 900;
	
	public static final DifferentialPilot pilot = new DifferentialPilot(56, 100, LEFT_WHEEL, RIGHT_WHEEL);
	
	public static void turnLeft()
	{
		turn(90);
    	//RIGHT_WHEEL.rotate(338);  
	}
	
	public static void turnRight()
	{
		turn(-90);
		//LEFT_WHEEL.rotate(338);
	}
	
	/**
	 * Returns an ArrayList containing the distances across a full revolution. USS must be attached to the SCANNER motor.
	 * @param interval The rotational interval at which the measurements take place.
	 * @return An ArrayList of distances for the full rotation.
	 */
	public static ArrayList<Integer> scan(int interval)
	{
		assert (interval < 360) && (interval > 0);
		
		ArrayList<Integer> distances = new ArrayList<Integer>();
		//SCANNER.setSpeed(1); //A one second rotation
		
		//At every interval of precision, take a measurement and add it to the ArrayList
		int iterations = (int) Math.floor(360/interval);
		int measurement;
		for (int i = 0; i < iterations; i++)
		{
			SCANNER.rotate(interval);
			measurement = HEAD.getDistance();
			distances.add(measurement);
		}
		
		//Unwind
		SCANNER.rotate(-360);
		
		return (distances);
	}
	
	public static void turn(int angle)
	{
		//90 is 110 deg
		//360 is 440
		double x = ((double)angle / 360 ) * 440;
		int angleToTurn = (int)Math.round(x);
		pilot.rotate(angleToTurn);
		
	}
	
	public static void goForward()
	{
		LEFT_WHEEL.forward();
		RIGHT_WHEEL.forward();
	}
	
	public static void goForward(int millimetres)
	{
		pilot.travel(millimetres);
	}
	
	public static void goBackward()
	{
		LEFT_WHEEL.backward();
		RIGHT_WHEEL.backward();
	}
	
	public static void goBackward(int millimetres)
	{
		pilot.travel(-millimetres);
	}
	
	public static void stop()
	{
		pilot.stop();
	}
	
	public static void stop(boolean immediateReturn)
	{
		LEFT_WHEEL.stop(true);
		RIGHT_WHEEL.stop(true);
	}
	
	public static void delay(int time)
	{
		Delay.msDelay(time);
	}
	
	public void setSpeed(int speed)
	{
		pilot.setTravelSpeed(speed);
	}
	
	public static void waitForPress()
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
	
	public void run()
	{
		
	}
}
