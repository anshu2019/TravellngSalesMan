package tsp;

import java.util.LinkedList;

/**
 * This class is for Graph object
 * 
 * @author Anshu Anand
 *
 */
public class WeightedGraph {

	public Double[][] graphDist;
	public LinkedList<Vertex> graphData = new LinkedList<Vertex>();
	public int vrtxCnt = 0;

	/**
	 * Thsi is the constructor for the Graph
	 * 
	 * @param nodeCnt
	 */
	public WeightedGraph(int nodeCnt) {
		vrtxCnt = nodeCnt;
		graphDist = new Double[vrtxCnt][vrtxCnt];
	}

	/**
	 * This method creates graph
	 * 
	 * @Precondition Graph data is provided
	 * @Postcondition Graph is created
	 * @param crimeList
	 */
	public void CreateGraph(LinkedList<String> crimeList) {

		for (String data : crimeList) {
			Vertex vrtx = new Vertex(data);
			graphData.add(vrtx);
		}

		for (int i = 0; i < vrtxCnt; i++) {
			Vertex v1 = graphData.get(i);
			for (int j = 0; j < vrtxCnt; j++) {
				Vertex v2 = graphData.get(j);
				double distBtwnCrime = Helper.getDistanceInMile(v1.xCord, v1.yCord, v2.xCord, v2.yCord);
				graphDist[i][j] = distBtwnCrime;
			}
		}

	}

	/**
	 * This method prints the adjacency matrix of graph
	 * 
	 * @Precondition Graph is existing
	 * @Postcondition Matrix is printed
	 */
	public void printAdjacencyMatrix() {
		for (int i = 0; i < vrtxCnt; i++) {
			System.out.println();
			for (int j = 0; j < vrtxCnt; j++) {
				System.out.print(graphDist[i][j] + "  ");
			}
		}
	}

}
