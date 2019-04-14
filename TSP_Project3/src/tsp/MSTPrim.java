package tsp;

/**
 * This class runs PRIM algorithm
 * 
 * @author Anshu Anand
 *
 */
public class MSTPrim {

	boolean[] mstSet; // list of vertex not yet included
	HeapNode[] primResult; // array to store Constructed MST
	double[] key; // key to pick minimum edge
	HeapNode[] hN;

	int vCnt = 0;
	WeightedGraph graph;

	/**
	 * Constructor for the class
	 * 
	 * @param vrtxCnt
	 * @param g
	 */
	public MSTPrim(int vrtxCnt, WeightedGraph g) {

		graph = g;
		vCnt = vrtxCnt;
		mstSet = new boolean[vrtxCnt];
		primResult = new HeapNode[vrtxCnt];
		key = new double[vrtxCnt];
		hN = new HeapNode[vrtxCnt];

		for (int i = 0; i < vrtxCnt; i++) {

			hN[i] = new HeapNode();
			hN[i].vertex = i;
			hN[i].key = Double.MAX_VALUE;

			primResult[i] = new HeapNode();
			// primResult[i].vertex = -1;

			mstSet[i] = false;
			key[i] = Integer.MAX_VALUE; // all the keys are integer intially
		}
		primResult[0].vertex = -1;
		key[0] = 0;
	}

	/**
	 * This method initiates the algorithm
	 * 
	 * @Precondition Graph exists
	 * @Postcondition MST is returned
	 * @return
	 */
	public HeapNode[] applyMSTPrim() {
		hN[0].key = 0;

		MinHeap minHp = new MinHeap(vCnt);

		for (int i = 0; i < vCnt; i++) {
			minHp.insert(hN[i]);
		}

		while (!minHp.isEmpty()) {

			// pick minimum key node
			HeapNode extractedNode = minHp.extractMin(); // extract min node

			int u = extractedNode.vertex;
			mstSet[u] = true; // node is selected

			for (int v = 0; v < vCnt; v++)

				if (graph.graphDist[u][v] != 0 && mstSet[v] == false && graph.graphDist[u][v] < hN[v].key) {

					deleteMin(minHp, graph.graphDist[u][v], v);

					primResult[v].vertex = u; // update the parent node for destination
					primResult[v].key = graph.graphDist[u][v];
					hN[v].key = graph.graphDist[u][v];
				}
		}

		return primResult;

	}

	/**
	 * This method deleted the minimum node from heap
	 * 
	 * @Precondition Heap is existing and valid
	 * @Postcondition minimum node si removed
	 * @param minHeap
	 * @param newKey
	 * @param vertex
	 */
	public void deleteMin(MinHeap minHeap, double newKey, int vertex) {

		int index = minHeap.indices[vertex];

		HeapNode node = minHeap.listOfHN[index];
		node.key = newKey;
		minHeap.bubbleUp(index);
	}

	/**
	 * This method prints the Minimum Spanning tree
	 * 
	 * @Precondition MST is created
	 * @Postcondition MST is printed
	 */
	public void printMST() {
		double total_min_weight = 0;
		System.out.println("Minimum Spanning Tree: ");
		System.out.println("Edge \tWeight");
		for (int i = 1; i < vCnt; i++) {
			System.out.println(primResult[i].vertex + " - " + i + "\t" + graph.graphDist[i][primResult[i].vertex]);
			total_min_weight += graph.graphDist[i][primResult[i].vertex];
		}
		System.out.println("Total minimum weight: " + total_min_weight);
	}
}
