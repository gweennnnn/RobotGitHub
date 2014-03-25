package rp.robotics.mapping;

import Main.mainClass;

/**
 * Stores a corresponding grid array of measurements as each position.
 * @author Jordan Bell
 *
 */
public class MeasuredGrid {

	protected MeasuredGridPosition[][] positions;
	
	// TODO Model this grid's size after the side of the MapGrid
	public MeasuredGrid(GridMap grid)
	{
		int width = grid.getGridWidth();
		int height = grid.getGridHeight();
		positions = new MeasuredGridPosition[width][height];
		
		for (int i = 0; i < width; i++) 
		{
			for (int j = 0; j < height; j++) 
			{
				positions[i][j] = new MeasuredGridPosition(i, j, grid);
			}
		}
	}
	
	public DirectionMeasurements getMeasurementsAt(int _x, int _y)
	{
		return positions[_x][_y].getDM();
	}
	
	public static void main(String[] args) {
		
	}
}
