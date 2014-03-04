package stringPuzzle;
import grid.Connection;

import java.awt.Point;
import java.util.Random;

import puzzles.EightPuzzle;
import puzzles.PuzzleInterface;

/**
 * A class to represent a traversable robot grid. Takes into account hard-coded
 * blockages on the grid.
 * 
 * @author Jordan Bell
 * 
 */
public class StringPuzzle implements PuzzleInterface {

	public String value;

	/**
	 * Create a new puzzle by copying another string
	 */
	public StringPuzzle(StringPuzzle _that) {
		this(_that.toString());
	}
	
	public StringPuzzle() {
		this("JumblePuzzle");
	}

	public StringPuzzle(String value) {
		this.value = value;
	}

	 /**
	 * Checks whether the given move is possible given blockages
	 * 
	 * @param _move The move to make.
	 * @return Returns true if the move is possible, else false.
	 */
	public boolean isPossibleMove(Swap swap) {
		
		//Check that the swapping letters exist
		boolean positive = (swap.getFirst() | swap.getSecond()) >= 0;
		boolean notOOB = (swap.getFirst() | swap.getSecond()) < value.length();
		boolean sameChar = value.charAt(swap.getFirst()) == value.charAt(swap.getSecond());
		//Checking if it's swapping the same character position is done in the Swap constructor.
		
		return positive && notOOB;
	}

	/**
	 * Attempts to move the robot to a new position.
	 * 
	 * @param _move The move to make.
	 * @return Returns true if the move was possible, else false.
	 */
	public boolean makeMove(Swap swap) {
		if (isPossibleMove(swap)) {
			
			//The two characters being swapped.
			char first = value.charAt(swap.getFirst());
			char second = value.charAt(swap.getSecond());
			
			String oldVal = value;
			value = "";
			
			for (int i = 0; i < oldVal.length(); i++)
			{
				if (i == swap.getFirst()) value += second;		//Set the second where the first was
				else if (i == swap.getSecond()) value += first; //Set the first where the second was
				else value += oldVal.charAt(i);					//Do nothing, use same character
			}
			return true;
		} else {
			return false;
		}
	}
	
	public void jumble()
	{
		Random generator = new Random();
		int range = value.length();
		
		for (int i = 0; i < 100; i++){
			int a = generator.nextInt(range);
			int b = a;
			
			//Ensure that b never equals a
			while (b == a) b = generator.nextInt(range);
			
			makeMove(new Swap(a, b));
		}
	}

	/**
	 * Checks for equality with another grid
	 * @param g The grid being compared to.
	 * @return Whether or not the values of g are equal with the ones in this grid.
	 */
	@Override
	public boolean equals(Object _that)
	{
		
		if (_that instanceof StringPuzzle)
		{
			StringPuzzle that = (StringPuzzle) _that;
			return (value.equals(that));
		}
			
		return false;
	}

	@Override
	public String toString() {
		return value;
	}
	
	@Override
	public int calculateValue() {
		StringPuzzle Goal = new StringPuzzle();
		
		int return_value = 0;
		
		for(int i=0; i < this.value.length(); i++)
			 if(this.value.charAt(i) != Goal.value.charAt(i)) return_value++;
		
		
		return return_value;
	}
	
	@Override
	public int costToMove(Object move) {
		if(move instanceof Swap)
		return 1;
		else return -1;
	}
}
