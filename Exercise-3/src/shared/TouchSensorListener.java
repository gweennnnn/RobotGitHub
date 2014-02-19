package shared;

import lejos.nxt.SensorPort;
import lejos.nxt.SensorPortListener;

public class TouchSensorListener implements SensorPortListener
{
	private Robot subject;
	
	public TouchSensorListener(Robot subject)
	{
		this.subject = subject;
	}
	
	public void stateChanged(SensorPort aSource, int aOldValue, int aNewValue)
	{
		//The size of movement detected
		int change = Math.abs(aOldValue - aNewValue);
		if (change > 400) //If the change is big enough
		{
			if (aNewValue < aOldValue) //Only trigger when the buttons are pushing in
				subject.touchResponse();
		}

	}

}
