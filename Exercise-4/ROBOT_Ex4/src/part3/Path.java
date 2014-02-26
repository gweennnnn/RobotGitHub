package part3;

import java.util.Iterator;

public class Path {
	TSNode[] order;

	public Path(TSNode[] order) {
		super();
		this.order = order;
	}
	
	public int getTotalDistance()
	{
		int total = 0;
		for (int i = 0; i < order.length-1; i++)
		{
			total += order[i].getDistanceByNode(order[i]);
		}
		return total;
	}
	
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
		e.setNeighbors(new Neighbor[]{
				new Neighbor(c, 6), 
				new Neighbor(f, 1)
		   });
		
		Path p = new Path(new TSNode[]{a, b, c, e});
		System.out.println(p.getTotalDistance());
	}
}
