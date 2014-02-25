package stringPuzzle;
import grid.Connection;

import java.awt.Point;
import java.util.Random;

/**
 * A class to represent a traversable robot grid. Takes into account hard-coded
 * blockages on the grid.
 * 
 * @author Jordan Bell
 * 
 */
public class StringPuzzle {

	public String value;

	/**
	 * Create a new puzzle by copying another string
	 */
	public StringPuzzle(StringPuzzle _that) {
		this(_that.toString());
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
		//Checking if it's swapping the same character is done in the Swap constructor.
		
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
	public boolean equals(StringPuzzle sp)
	{
		return (value.equals(sp.value));
	}

	@Override
	public String toString() {
		return value;
	}
	
	public static void main(String[] args) {

		StringPuzzle sp = new StringPuzzle("stringpuzzle");

		System.out.println(sp);
		
		sp.makeMove(new Swap(0, 6));
		System.out.println(sp);
		
		sp.makeMove(new Swap(2, 8));
		System.out.println(sp);
		
		sp.makeMove(new Swap(4, 10));
		System.out.println(sp);
		
		sp.jumble();
		System.out.println("Random: " + sp);
		
		
	}

}
