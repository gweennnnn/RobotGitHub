package rp.robotics.localisation;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import lejos.util.Delay;
import rp.robotics.mapping.DirectionMeasurements;
import rp.robotics.mapping.GridMap;
import rp.robotics.mapping.Heading;
import rp.robotics.mapping.LocalisationUtils;
import rp.robotics.mapping.MeasuredGrid;
import rp.robotics.visualisation.GridPoseDistributionVisualisation;

public class MarkovLocalisationSkeleton {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		JFrame frame = new JFrame("Map Viewer");
		frame.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent _arg0) {

			}

			@Override
			public void windowIconified(WindowEvent _arg0) {

			}

			@Override
			public void windowDeiconified(WindowEvent _arg0) {

			}

			@Override
			public void windowDeactivated(WindowEvent _arg0) {

			}

			@Override
			public void windowClosing(WindowEvent _arg0) {

			}

			@Override
			public void windowClosed(WindowEvent _arg0) {

				System.exit(0);

			}

			@Override
			public void windowActivated(WindowEvent _arg0) {

			}
		});

		GridMap gridMap = LocalisationUtils.create2014Map1();

		// The probability distribution over the robot's location
		GridPositionDistribution distribution = new GridPositionDistribution(
				gridMap);
		
		MeasuredGrid gridMeasurements = new MeasuredGrid(gridMap);

		// view the map with 2 pixels as 1 cm
		GridPoseDistributionVisualisation mapVis = new GridPoseDistributionVisualisation(
				distribution, 2);

		frame.add(mapVis);
		frame.pack();
		frame.setSize(1050, 600);
		frame.setVisible(true);

		// ActionModel actionModel = new DummyActionModel();
		ActionModel actionModel = new PerfectActionModel();

		// SensorModel sensorModel = new DummySensorModel();
		PerfectSensorModel sensorModel = new PerfectSensorModel();

		while (true) {
//		for(int i = 0; i< 12 ; i++){
//			System.out.println("-------------------------------------------------");
			
			// Do some action
			// E.g. attempting to move one node in the PLUS_X direction
			Heading action = Heading.PLUS_X;

			// I'm faking movement by waiting for some time
			Delay.msDelay(5000);

			// Once action is completed, apply action model based on the move
			// the robot took. This creates a new instance of
			// GridPoseDistribution and assigns it to distribution
//			distribution = actionModel.updateAfterMove(distribution, action);

			// Update visualisation. Only necessary because it needs to know
			// about the new distribution instance
			
			distribution.normalise();
			mapVis.setDistribution(distribution);
			

//			System.out.println("map sum: " + distribution.sumProbabilities());
			

			//================ Sensing ================\\
			
			// Once completed apply sensor model as appropriate. This changes
			// the distribution directly (i.e. by reference)
			sensorModel.updateDistributionAfterSensing(distribution, gridMeasurements);

			// Note, as the sensor model changes the distribution directly, the
			// visualisation will update automatically so
			// mapVis.setDistribution is not necessary after the sensor model

			distribution.normalise();
			mapVis.setDistribution(distribution);
		}

//		System.out.println();
//		System.out.println();
//		System.out.println("---------------------");
//		System.out.println();
//		System.out.println();
//		
//		for(int y = 0; y < distribution.getGridHeight(); y++){
//			for(int x = 0; x < distribution.getGridWidth(); x++){
//				System.out.print("[" + distribution.getProbability(x, y) + "]");
//			}
//			System.out.println();
//		}
	}
}
