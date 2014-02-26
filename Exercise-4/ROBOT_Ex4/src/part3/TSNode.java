package part3;

public class TSNode {

	private String ID;
	private Neighbor[] neighbors;
	
	public TSNode(String ID)
	{
		this(ID, null);
	}
	
	public TSNode(String ID, Neighbor[] neighbors) {
		super();
		this.ID = ID;
		setNeighbors(neighbors);
	}
	
	public Neighbor getNearestNeighbor(){
		int maxDist = 0;
		Neighbor r_neighbor = null;
		
		for (Neighbor n : neighbors)
		{
			int dist = n.getDistance();
			if (dist > maxDist)
			{
				maxDist = dist;
				r_neighbor = n;
			}
		}
		
		return r_neighbor;
	}
	
	public Neighbor getNeighborByDistance(int dist)
	{
		for (Neighbor n : neighbors)
		{
			if (n.getDistance() == dist) return n;
		}
		return null;
	}
	
	public int getDistanceByNeighbor(Neighbor _n)
	{
		for (Neighbor n : neighbors)
		{
			if (_n == n) return n.getDistance();
		}
		return -1;
	}
	
	public int getDistanceByNode(TSNode target)
	{
		for (Neighbor n : neighbors)
		{
			System.out.println(n);
			if (n.getNode().equals(target)) return n.getDistance();
		}
		return -1;
	}
	
	public boolean equals(TSNode target)
	{
		return ID == target.getID();
	}
	
	private String getID() {
		return ID;
	}
	
	private Neighbor[] getNeighbors() {
		return neighbors;
	}

	public void setNeighbors(Neighbor[] neighbors)
	{
		this.neighbors = neighbors;
	}
	
	public String toString()
	{
		return (ID.toString());
	}
	
	public String toStringFull()
	{
		String s = ID.toString() + " has neighbors: ";
		for (Neighbor n : neighbors)
		{
			s += "\n-";
			s += n;
		}
		
		return (ID.toString() + " has neighbors: ");
	}
	
	public static void main(String[] args)
	{
		TSNode tsn = new TSNode("A");
		
	}
	
}
