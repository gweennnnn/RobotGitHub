package Interfaces;

public interface PuzzleInterface
{
	public int calculateValue();
	
	public String toString();
	
	public boolean equals(Object _that);
	
	public int costToMove(Object move);
}
