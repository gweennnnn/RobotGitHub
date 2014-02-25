package part3;

public class Part3 {

	public static void main(String[] args) {
		TSNode a = new TSNode("A");
		TSNode b = new TSNode("B");
		TSNode c = new TSNode("C");
		TSNode d = new TSNode("D");
		TSNode e = new TSNode("E");
		TSNode f = new TSNode("F");
		TSNode g = new TSNode("G");
		TSNode h = new TSNode("H");
		TSNode i = new TSNode("I");

		a.setNeighbors(new Neighbor[]{
				new Neighbor(g, 5), 
				new Neighbor(d, 10), 
				new Neighbor(b, 8)
		   });
		b.setNeighbors(new Neighbor[]{
				new Neighbor(a, 10), 
				new Neighbor(c, 3)
		   });
		c.setNeighbors(new Neighbor[]{
				new Neighbor(b, 3), 
				new Neighbor(e, 6)
		   });
		d.setNeighbors(new Neighbor[]{
				new Neighbor(a, 8), 
				new Neighbor(h, 3)
		   });
		
		TSNode[] allCities = new TSNode[]{a, b, c, d,e, f, g, h, i};
		TravellingSalesman ts = new TravellingSalesman(allCities, a);
//		ts.getBest();
	}

}
