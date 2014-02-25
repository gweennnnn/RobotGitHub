package part3;

public class TSNode {

	String ID;
	Neighbor[] neighbors;
	
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
	
	public void setNeighbors(Neighbor[] neighbors)
	{
		this.neighbors = neighbors;
	}
	
	public static void main(String[] args)
	{
		TSNode tsn = new TSNode("A");
		
	}
	
}
