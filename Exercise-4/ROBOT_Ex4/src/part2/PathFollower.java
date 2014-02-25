package part2;

import java.util.*;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.util.TextMenu;
import shared.LineFollower;

public class PathFollower extends LineFollower {
	
	private final int LEFT = 0;
	private final int RIGHT = 1;
	private int[] SEQUENCE = makeSequence("1111");
	private Random rand = new Random();
	
	public PathFollower(List<ActionStatePair<Direction, State>> path){
		
	}

	/**
	 * Traverses a random path
	 */
	private void runRandom()
	{
		init(); //Scan the area
		
		waitForPress();
		while(_run)
		{
			boolean midLine = LS_MIDDLE.getLightValue() >= s_mid.threshold;
			boolean leftLine = LS_LEFT.getLightValue() >= s_left.threshold;
			boolean rightLine = LS_RIGHT.getLightValue() >= s_right.threshold;
			
			if(!leftLine && !rightLine) //INTERSECTION
			{
				stop();
				goForward(50);
				chooseRandomDirection();
			}
			else
			{
				if (midLine && leftLine && rightLine) turnAround();
				else followLine();
			}
		}
	}
	
	/**
	 * Traverses a set path.
	 */
	private void runPath()
	{
		init();
		
		while(_run && (SEQUENCE != null))
		{
			boolean midLine = LS_MIDDLE.getLightValue() >= s_mid.threshold;
			boolean leftLine = LS_LEFT.getLightValue() >= s_left.threshold;
			boolean rightLine = LS_RIGHT.getLightValue() >= s_right.threshold;
			
			if(!leftLine && !rightLine) handleIntersection();
			else // just a straight line
			{
				if (!midLine && !leftLine && !rightLine) turnAround();
				else followLine();
			}
		}
	}
	/*=================== PATHTAKING CODE ===================*/
	/**
	 * Allow the user to input their own path.
	 */
	private static int[] inputPath()
	{
		String input = "";
		boolean firstIteration = true;
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		while (!(Button.ENTER.isDown() || Button.ESCAPE.isDown()) || firstIteration)
		{
			LCD.clearDisplay();
			LCD.clearDisplay();
			System.out.println("Left or Right?");
			System.out.println(input);
			
			waitForPress();
			if (Button.LEFT.isDown()) input += "0";
			if (Button.RIGHT.isDown()) input += "1";
			
			firstIteration = false;
		}
		
		return makeSequence(input);
	}
	
	/**
	 * Construct a path sequence, based on a string argument.
	 * @param str The String representation of the path. 0 = Left. 1 = Right.
	 * @return An integer array of the string.
	 */
	public static int[] makeSequence(String str) {
		int[] path = new int[str.length()];
		
		for(int i = 0; i < path.length; i++)
			path[i] = Integer.parseInt(Character.toString(str.charAt(i)));
		return path;
    }
	
	/**
	 * If there is still a path, remove the current instruction from the queue. Otherwise, end the sequence.
	 */
	private void nextMovement()
	{
		if(SEQUENCE.length <= 1)
			SEQUENCE = null;
		else
		{
			int[] temp = new int[SEQUENCE.length - 1];
			for(int i = 1; i < SEQUENCE.length; i++)
			{
				temp[i-1] = SEQUENCE[i];
			}
			SEQUENCE = temp;
		}
	}
	
	/*================== MOVEMENT CODE =================*/
	
	private void handleIntersection()
	{
		stop();
		goForward(50);
		
		switch(SEQUENCE[0])
		{
			case LEFT:
				turnLeft();
				break;
			case RIGHT:
				turnRight();
				break;
		}
		nextMovement();
	}
	
	private void chooseRandomDirection()
	{
		int randomChoice = rand.nextInt(2);
		
		if(randomChoice == RIGHT)
			turnRight();
		else
			turnLeft();

		goForward(50);
	}
}
