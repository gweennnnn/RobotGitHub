package part3;

public class Neighbor {

	TSNode node;
	int distance;
	
	public Neighbor(TSNode node, int distance) {
		super();
		this.node = node;
		this.distance = distance;
	}
	
	public TSNode getNode() {
		return node;
	}
	
	public int getDistance() {
		return distance;
	}
	
}
