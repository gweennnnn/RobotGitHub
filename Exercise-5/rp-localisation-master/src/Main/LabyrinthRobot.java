package Main;

import com.sun.xml.internal.messaging.saaj.packaging.mime.Header;

import rp.robotics.mapping.DirectionMeasurements;
import rp.robotics.mapping.Heading;
import established.GridTraveller;
import established.Robot;

public class LabyrinthRobot extends Robot{
	
	Heading orientation = Heading.PLUS_X;
	
	/**
	 * Assume that you are facing North, before taking the measurements.
	 */
	private DirectionMeasurements getStandardMeasurements()
	{
		int north, south, east, west;

		north = measureAndTurn();
		east = measureAndTurn();
		south = measureAndTurn();
		west = measureAndTurn();
		
		return new DirectionMeasurements(north, south, east, west);
	}
	
	public DirectionMeasurements getMeasurements()
	{
		DirectionMeasurements dm = getStandardMeasurements();
		
		dm.correctOrientation(orientation, Heading.MINUS_Y);
		return dm;
	}
	
	private int measureAndTurn()
	{
		int r_val = HEAD.getDistance();
		turnRight();
		
		return r_val;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
