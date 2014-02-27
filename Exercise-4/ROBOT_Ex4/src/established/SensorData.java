package established;

import lejos.nxt.LightSensor;

public class SensorData {
	public int threshold; // Everything above this number is not a line
	public int accuracy;
	public int background; // The light value of the background
	public int value; // The current value detected (updated)

	public LightSensor ls;// The light sensor linked with this data.

	public SensorData(LightSensor ls) {
		this.ls = ls;
	}
	
	public float getError() {
		int difference = threshold - ls.getLightValue();
		return ((float) ((float) ((float) difference / (threshold - LineFollower.BLACK)) * 100f));
	}		
}