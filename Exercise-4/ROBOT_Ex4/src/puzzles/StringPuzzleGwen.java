package puzzles;

import stringPuzzle.StringPuzzle;
import stringPuzzle.Swap;

public class StringPuzzleGwen implements PuzzleInterface{

	private String value;
	
	public StringPuzzleGwen(StringPuzzle val)
	{
		this(val.toString());
	}
	
	public StringPuzzleGwen()
	{
		this("java");
	}
	
	public StringPuzzleGwen(String value)
	{
		this.value = value;
	}
	
	public boolean isPossibleMove(Swap swap)
	{
		
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
