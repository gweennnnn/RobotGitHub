package rp.robotics.mapping;

import static org.testng.Assert.*;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class DirectionMeasurementsTest {
	
	DirectionMeasurements dm1;
	DirectionMeasurements dm2;
	DirectionMeasurements dm3;
	DirectionMeasurements dm4;
	DirectionMeasurements dm5;
	DirectionMeasurements dm6;
	DirectionMeasurements dm7;
	DirectionMeasurements dm8;
	DirectionMeasurements dmTrue;
	
	@BeforeMethod
	public void reinit()
	{
		dm1 = new DirectionMeasurements(1, 2, 3, 4);
		dm2 = new DirectionMeasurements(1, 2, 3, 4);
		dm3 = new DirectionMeasurements(4, 3, 2, 1);
		dm4 = null;
		dm5 = new DirectionMeasurements(3, 3, 3, 3);
		dm6 = new DirectionMeasurements(4, 4, 4, 4);
		dm7 = new DirectionMeasurements(5, 1, 3, 13);
		dm8 = new DirectionMeasurements(3, 3, 3, 20);
		dmTrue = new DirectionMeasurements(3, 3, 3, 3);
	}

	//=================== BASIC FUNCTIONS ===================\\
	
	@Test
	public void testConstructor()
	{
		assertEquals(dm1.north, 1f);
		assertEquals(dm1.south, 2f);
		assertEquals(dm1.east,  3f);
		assertEquals(dm1.west,  4f);
	}
	
	@Test
	public void testEquality()
	{
		assertTrue (dm1.equals(dm2));
		assertFalse(dm1.equals(dm3));
	}
	
	
	//=================== ROTATION ===================\\
	
	@Test
	public void testRotation()
	{
		// Test conventional rotations
		dm4 = dm1.getRotation(0);
		assertEquals("N:1.0, S:2.0, E:3.0, W:4.0", dm4.toString());
		
		dm4 = dm1.getRotation(90);
		assertEquals("N:3.0, S:4.0, E:2.0, W:1.0", dm4.toString());
		
		dm4 = dm1.getRotation(180);
		assertEquals("N:2.0, S:1.0, E:4.0, W:3.0", dm4.toString());
		
		dm4 = dm1.getRotation(270);
		assertEquals("N:4.0, S:3.0, E:1.0, W:2.0", dm4.toString());

		
		// Test out-of-bounds angles
		dm4 = dm1.getRotation(360);
		assertEquals("N:1.0, S:2.0, E:3.0, W:4.0", dm4.toString());
		
		dm4 = dm1.getRotation(450);
		assertEquals("N:3.0, S:4.0, E:2.0, W:1.0", dm4.toString());
		
		dm4 = dm1.getRotation(900);
		assertEquals("N:2.0, S:1.0, E:4.0, W:3.0", dm4.toString());
		
		dm4 = dm1.getRotation(630);
		assertEquals("N:4.0, S:3.0, E:1.0, W:2.0", dm4.toString());
	}
	
	@Test
	public void test_90_OrienationCorrection()
	{
		dm1.correctOrientation(Heading.MINUS_X, Heading.MINUS_Y);
		assertEquals(dm1.toString(), "N:3.0, S:4.0, E:2.0, W:1.0");
		
		dm1.correctOrientation(Heading.PLUS_Y, Heading.MINUS_X);
		assertEquals(dm1.toString(), "N:2.0, S:1.0, E:4.0, W:3.0");
	}
	
	@Test
	public void test_180_OrienationCorrection()
	{
		dm1.correctOrientation(Heading.PLUS_Y, Heading.MINUS_Y);
		assertEquals(dm1.toString(), "N:2.0, S:1.0, E:4.0, W:3.0");
		
		dm1.correctOrientation(Heading.PLUS_X, Heading.MINUS_X);
		assertEquals(dm1.toString(), "N:1.0, S:2.0, E:3.0, W:4.0");
	}
	
	@Test
	public void test_270_OrienationCorrection()
	{
		dm1.correctOrientation(Heading.MINUS_X, Heading.PLUS_Y);		
		assertEquals(dm1.toString(), "N:4.0, S:3.0, E:1.0, W:2.0");
		
		dm1.correctOrientation(Heading.MINUS_X, Heading.PLUS_Y);		
		assertEquals(dm1.toString(), "N:2.0, S:1.0, E:4.0, W:3.0");
	}
	

	//=================== PROBABILITIES ===================\\
	
	@Test
	public void testProbabilityByDifference()
	{
		assertEquals(dm1.getEqualityProbability(dmTrue), 0.00040, 0.000001);
		assertEquals(dm3.getEqualityProbability(dmTrue), 0.00040, 0.000001);
		assertEquals(dm5.getEqualityProbability(dmTrue), 0.40960, 0.000001);
		assertEquals(dm6.getEqualityProbability(dmTrue), 0.00010, 0.000001);
		assertEquals(dm7.getEqualityProbability(dmTrue), 0.00004, 0.000001);
		assertEquals(dm8.getEqualityProbability(dmTrue), 0.0f);
	}
}
