package tsp;

/**
 * This is a heap class
 * 
 * @author Anshu Anand
 *
 */
public class MinHeap {

	int maxSize;
	int heapSize;
	HeapNode[] listOfHN;
	int[] indices;

	/**
	 * Constructor of the heap class
	 * 
	 * @param n
	 */
	public MinHeap(int n) {
		this.maxSize = n;
		listOfHN = new HeapNode[n + 1];
		indices = new int[n];
		listOfHN[0] = new HeapNode();
		listOfHN[0].key = Integer.MIN_VALUE;
		listOfHN[0].vertex = -1;
		heapSize = 0;
	}

	/**
	 * This method displays content of the heap
	 * 
	 * @Precondition Heap is created
	 * @Postcondition display result
	 */
	public void display() {
		for (int i = 0; i <= heapSize; i++) {
			System.out.println(" " + listOfHN[i].vertex + "   key   " + listOfHN[i].key);
		}
		System.out.println("________________________");
	}

	/**
	 * This method inserts new node in heap
	 * 
	 * @Precondition Heap exists
	 * @Postcondition node inserted
	 * @param x
	 */
	public void insert(HeapNode x) {
		heapSize++;
		int idx = heapSize;
		listOfHN[idx] = x;
		indices[x.vertex] = idx;
		bubbleUp(idx);
	}

	/**
	 * This method removes the heap node
	 * 
	 * @Precondition Heap exists
	 * @Postcondition node is removed
	 * @param pos
	 */
	public void bubbleUp(int pos) {
		int prntIdx = pos / 2;
		int currentIdx = pos;
		while (currentIdx > 0 && listOfHN[prntIdx].key > listOfHN[currentIdx].key) {
			HeapNode crntNode = listOfHN[currentIdx];
			HeapNode parentNode = listOfHN[prntIdx];

			// swap the positions
			indices[crntNode.vertex] = prntIdx;
			indices[parentNode.vertex] = currentIdx;
			swap(currentIdx, prntIdx);
			currentIdx = prntIdx;
			prntIdx = prntIdx / 2;
		}
	}

	/**
	 * This method extracts minimum node from heap
	 * 
	 * @Precondition heap exists
	 * @Postcondition minimum node extracted
	 * @return
	 */
	public HeapNode extractMin() {
		HeapNode min = listOfHN[1];
		HeapNode lastNode = listOfHN[heapSize];
//       update the indices[] and move the last node to the top
		indices[lastNode.vertex] = 1;
		listOfHN[1] = lastNode;
		listOfHN[heapSize] = null;
		heapDown(1);
		heapSize--;
		return min;
	}

	/**
	 * THis method adjusts the heap
	 * @Precondition heap is existing
	* @Postcondition heap is adjusted to be valid
	 * @param k
	 */
	public void heapDown(int k) {
		int smallest = k;
		int leftChldIndx = 2 * k;
		int rightChldIndx = 2 * k + 1;
		if (leftChldIndx < heapSize() && listOfHN[smallest].key > listOfHN[leftChldIndx].key) {
			smallest = leftChldIndx;
		}
		if (rightChldIndx < heapSize() && listOfHN[smallest].key > listOfHN[rightChldIndx].key) {
			smallest = rightChldIndx;
		}
		if (smallest != k) {

			HeapNode smallestNode = listOfHN[smallest];
			HeapNode kNode = listOfHN[k];

			// swap the positions
			indices[smallestNode.vertex] = k;
			indices[kNode.vertex] = smallest;
			swap(k, smallest);
			heapDown(smallest);
		}
	}

	/**
	 * This method swaps the element of heap
	 * @Precondition Heap exists
	* @Postcondition swap of node si done
	 * @param a
	 * @param b
	 */
	public void swap(int a, int b) {
		HeapNode temp = listOfHN[a];
		listOfHN[a] = listOfHN[b];
		listOfHN[b] = temp;
	}

	/**
	 * This method checks if hepa is empty
	 * @return
	 */
	public boolean isEmpty() {
		return heapSize == 0;
	}

	/**
	 * This method gives heap size
	 * @return
	 */
	public int heapSize() {
		return heapSize;
	}
}
