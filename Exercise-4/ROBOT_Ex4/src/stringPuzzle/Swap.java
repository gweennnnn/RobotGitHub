package stringPuzzle;

public class Swap {
	private int first;
	private int second;
	
	public Swap(int first, int second) throws IllegalArgumentException{
		super();
		
		if (first == second) throw new IllegalArgumentException("You cannot swap the same character with itself");		
		this.first = first;
		this.second = second;
	}
	
	//Getters
	public int getFirst() {
		return first;
	}

	public int getSecond() {
		return second;
	}

}
