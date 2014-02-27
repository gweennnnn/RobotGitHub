package part3;

import java.awt.Point;

public class Part3 {

	public static void main(String[] args) {
		/*TSNode a = new TSNode("A");
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
		   });*/
		
		TSNode a = new TSNode(new Point(0, 0));
		TSNode b = new TSNode(new Point(0, 0));
		TSNode c = new TSNode(new Point(0, 0));
		TSNode d = new TSNode(new Point(0, 0));
		TSNode e = new TSNode(new Point(0, 0));
		TSNode f = new TSNode(new Point(0, 0));
		TSNode g = new TSNode(new Point(0, 0));
		TSNode h = new TSNode(new Point(0, 0));
		TSNode i = new TSNode(new Point(0, 0));
		
		TSNode[] allCities = new TSNode[]{a, b, c, d, e, f, g, h, i};
		TravellingSalesman ts = new TravellingSalesman(allCities, a);
//		ts.getBest();
	}

}
