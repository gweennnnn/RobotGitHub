package part1;

import shared.LineFollower;
import lejos.nxt.LCD;
import lejos.util.TextMenu;

public class Part1{

	public void run()
	{		
		//Set up a Selection Menu
		String[] tests = new String[2];
		tests[0] = "Wall Distancing";
		tests[1] = "Line Following";
		
		TextMenu selectionMenu = new TextMenu(tests, 1, "Choose Your Test");
		
		int sel = selectionMenu.select();
		
		LCD.clearDisplay();
				
		//Run the selected portion
		switch (sel) //Menu selections
		{
		case 0: 
			WallPortion wp = new WallPortion();
			wp.run();
			break;
		case 1:
			LineFollower lp = new LineFollower();
			lp.run();
			break;
		default:
			break;
		}

	}

	public static void main(String[] args) {
		Part1 part1 = new Part1();
		part1.run();
	}

}