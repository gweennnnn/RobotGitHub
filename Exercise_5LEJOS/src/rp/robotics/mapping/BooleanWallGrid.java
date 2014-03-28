package rp.robotics.mapping;

import Main.mainClass;

public class BooleanWallGrid {

	protected BooleanGridPosition[][] positions;
	
	// TODO Model this grid's size after the side of the MapGrid
	public BooleanWallGrid(GridMap grid)
	{
		int width = grid.getGridWidth();
		int height = grid.getGridHeight();
		positions = new BooleanGridPosition[width][height];
		
		for (int i = 0; i < width; i++) 
		{
			for (int j = 0; j < height; j++) 
			{
				positions[i][j] = new BooleanGridPosition(i, j, grid);
			}
		}
	}
	
	public BooleanMeasurements getMeasurementsAt(int _x, int _y)
	{
		return positions[_x][_y].getDM();
	}
	
	public static void main(String[] args) {
		
	}
}
