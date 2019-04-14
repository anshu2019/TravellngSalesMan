package tsp;

/**
 * This class is Node for a Heap
 * @author Anshu Anand
 *
 */
public class HeapNode {

	public int vertex;
	public double key;
	
	/**
	 * Constructor
	 */
	public HeapNode() {
		
	}
	
	/**
	 * Constructor to intialize the node
	 * @param v
	 * @param w
	 */
	public HeapNode(int v,double w) {
		vertex=v;
		key = w;		
	}
	
	
}
