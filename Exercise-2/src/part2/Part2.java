package part2;
import shared.Robot;
import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.util.TextMenu;

public class Part2{

	private Part2WithListener wl;
	private Part2WithoutListener wol;
	
	private TextMenu testMenu;
	private TextMenu speedMenu;
	
	private boolean endProgram = false;
	
	private void init()
	{
		wl = new Part2WithListener();
		wol = new Part2WithoutListener();
	}
	
	private void showTestMenu()
	{
		LCD.clearDisplay();
		
		String[] tests = new String[2];
		tests[0] = "With Listener";
		tests[1] = "Without Listener";
		
		testMenu = new TextMenu(tests, 1, "Choose Your Test");
	}
	
	private void showSpeedMenu()
	{
		LCD.clearDisplay();
		
		String[] speeds = new String[3];
		speeds[0] = "Slow";
		speeds[1] = "Regular";
		speeds[2] = "Fast";
		
		speedMenu = new TextMenu(speeds, 1, "Select Speed");
	}
	
	private void test(boolean listener)
	{
		Robot testClass = (listener) ? wl : wol;
		
		showSpeedMenu();
		int sel = speedMenu.select();
		
		Button.waitForAnyPress();
		if (!Button.ESCAPE.isDown()){		
			switch (sel) //Menu selections
			{
			case 0: 
				testClass.setSpeed(Robot.SLOW);
				testClass.run();
				break;
			case 1:
				testClass.setSpeed(Robot.REGULAR);
				testClass.run();
				break;
			case 2:
				testClass.setSpeed(Robot.FAST);
				testClass.run();
				break;
			default:
				break;
			}
		}
		
	}
	
	public void start()
	{
		init();
		showTestMenu();
		
		while(!endProgram)
    	{
			int sel = testMenu.select();
			Button.waitForAnyPress();
			if (!Button.ESCAPE.isDown()){
				switch (sel) //Menu selections
				{
				case 0: 
					test(true);
					break;
				case 1: 
					test(false);
					break;
				default:
					break;
				}
			}
			else endProgram = true;
    	}
	}
	
	public static void main(String[] args) {
		Part2 tester = new Part2();
		tester.start();
	}

}
