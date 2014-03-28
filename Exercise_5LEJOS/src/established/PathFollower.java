package established;

import java.util.*;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.util.Matrix;

public class PathFollower extends LineFollower {
	
	protected final int FORWARD = 0;
	protected final int LEFT = 1;
	protected final int RIGHT = 2;
	protected final int TURNAROUND = 3;
	protected final int SONG = 7;
	protected int[] SEQUENCE;
	protected Random rand = new Random();
	
	public PathFollower(String path){
		
		SEQUENCE = makeSequence(path);
	}

	/**
	 * Traverses a random path
	 */
	public void runRandom()
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
	public void runPath()
	{
		init();

		
		while(_run && (SEQUENCE != null))
		{
			boolean midLine = LS_MIDDLE.getLightValue() >= s_mid.threshold;
			boolean leftLine = LS_LEFT.getLightValue() >= s_left.threshold;
			boolean rightLine = LS_RIGHT.getLightValue() >= s_right.threshold;
			
			if(!leftLine && !rightLine) {
				handleIntersection();
				followLine();
				}
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
	protected static int[] inputPath()
	{
		String input = "";
		boolean firstIteration = true;
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		while (Button.ESCAPE.isDown() || firstIteration)
		{
			LCD.clearDisplay();
			LCD.clearDisplay();
			System.out.println("Left or Right?");
			System.out.println(input);
			
			waitForPress();
			if (Button.ENTER.isDown()) input += "0"; //Forward
			if (Button.LEFT.isDown()) input += "1";  //Left
			if (Button.RIGHT.isDown()) input += "2"; //Right
			
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
		
		str += "0";
		
		int[] path = new int[str.length()];
		
		
		for(int i = 0; i < path.length; i++){
			path[i] = Integer.parseInt(Character.toString(str.charAt(i)));
			}
		
		
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
		
		int move = SEQUENCE[0];
		
		if(move == RIGHT){
			stop();
			turnRight();
		}
		else if (move == LEFT){
			stop();
			turnLeft();
		}
		else if (move == TURNAROUND){
			stop();
			turnRound();
		}
		else if (move == SONG){
			stop();
			playVictorySong();
			waitForPress();
		}
				

		nextMovement();
		
		
//		switch(SEQUENCE[0])
//		{
//		
//		case FORWARD:
//			//Keep going, don't turn.
//			stop();
//			goForward(50);
//			break;
//		case RIGHT:
//			stop();
//			turnRight();
//			stop();
//			goForward(50);
//			break;
//		case LEFT:
//			stop();
//			turnLeft();
//			stop();
//			goForward(50);
//			break;
//		case TURNAROUND:
//			turnRound();
//			stop();
//			goForward(50);
////			break;
//			
//		nextMovement();
//	}
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
