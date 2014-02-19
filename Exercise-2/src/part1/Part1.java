package part1;
import lejos.nxt.Button;
import lejos.nxt.Sound;

import java.lang.String;

import shared.ButtonPressListener;
import shared.Robot;

public class Part1 extends Robot{
	
	private final int TRIANGLE = 0;
	private final int SQUARE = 1;
	private final int HEXAGON = 2;
	private final int LEMNISCATE = 3;
	private int selectedShape = LEMNISCATE;
	
	private boolean run = true;
	
	/**
	 * Greet the world, and offer to do tricks. Uses a TextMenu to allow user 
	 */
	public void run()
	{
		Button.ESCAPE.addButtonListener(new ButtonPressListener(this)); //Add button Listener
		Button.ENTER.addButtonListener(new ButtonPressListener(this)); //Add button Listener
			
		System.out.println("Hello World");
		waitForPress();
		
		while(run)
    		{
			makeShape(selectedShape);
    		}
		
		Sound.beep();
	}
	
	/**
	 * @param shape The specified shape to be made by the robot.
	 */
	private void makeShape(int shape)
	{
		switch (shape)
		{
		case TRIANGLE: 
			triangle();
			break;
		case SQUARE: 
			square();
			break;
		case HEXAGON: 
			hexagon();
			break;
		case LEMNISCATE: 
			lemniscate();
			break;
		}
	}
	
	//Driving Shapes
	/**
	 * Move around in a triangle shape
	 */
	private void triangle()
	{
		for (int i = 0; i < 3; i++)
		{
			turn(120);
			goForward(200);
		}
	}

	/**
	 * Move around in a square shape
	 */
	private void square()
	{
		for (int i = 0; i < 4; i++)
		{
			turn(90);
			goForward(160);
		}
	}
	
	/**
	 * Move around in a hexagon shape
	 */
	private void hexagon()
	{
		for (int i = 0; i < 6; i++)
		{
			turn(60);
			goForward(80);
		}
	}
	
	/**
	 * Move around in an infinity shape
	 */
	private void lemniscate()
	{
		pilot.steer(100, 440);
		pilot.steer(-100, -440);
	}
	
	public void touchResponse()
	{
		System.out.println("Ouch");
	}
	
	public void buttonResponse(Button b)
	{
		if(b == Button.ESCAPE) //End
			System.exit(0);
		if(b == Button.ENTER) //Increment shape
		{
			if (selectedShape != LEMNISCATE) selectedShape++;
			else selectedShape = TRIANGLE;
		}
	}
	
	public static void main(String[] args) {
		Part1 p1 = new Part1();
		p1.run();
	}
}
