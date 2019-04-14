package tsp;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import java.text.ParseException;

/**
 * This is main class
 * 
 * @author Anshu Anand
 *
 */
public class Main {
	private static Scanner userInput;
	public static Date startDate;
	public static Date endDate;
	public static final String filepath = "data/CrimeLatLonXY1990.csv";
	public static LinkedList<Integer> hamiltonianPath = new LinkedList<Integer>();
	static String input1;
	static String input2;

	static LinkedList<Integer> optimlPth = new LinkedList<Integer>();
	static double optimalDistance = 0;

	/**
	 * This static void main method
	 * 
	 * @param args
	 * @throws ParseException
	 * @throws IOException
	 */
	public static void main(String[] args) throws ParseException, IOException {

		System.out.println("Travelling Salesman Problem Demo.");
		userInput = new Scanner(System.in);

		System.out.println("Enter Start date");
		input1 = userInput.nextLine();

		if (!input1.isEmpty() && input1 != null) {
			startDate = Helper.getFormatedDate(input1);
		} else {
			System.out.println("Terminating, No input provided,Try again ");
		}

		System.out.println("Enter End date");
		input2 = userInput.nextLine();
		if (!input2.isEmpty() && input2 != null) {
			endDate = Helper.getFormatedDate(input2);
		} else {
			System.out.println("Terminating, No input provided,Try again ");
		}

		LinkedList<String> graphData = Helper.readDataFromFile(filepath, startDate, endDate);
		int crimeCnt = graphData.size();
		if (crimeCnt > 0) {
			// instantiate a graph object
			WeightedGraph graph = new WeightedGraph(crimeCnt);
			// create graph with data obtained
			graph.CreateGraph(graphData);
			// graph.printAdjacencyMatrix();

			// Apply PRIM approach of minimum spanning tree
			MSTPrim prim = new MSTPrim(crimeCnt, graph);
			HeapNode[] mst = prim.applyMSTPrim();
			// prim.printMST();

			LinkedList<Integer>[] tree = new LinkedList[crimeCnt];

			for (int k = 0; k < crimeCnt; k++) {
				tree[k] = new LinkedList<Integer>();
			}

			for (int k = 1; k < crimeCnt; k++) {
				tree[mst[k].vertex].add(k);
			}
			int root = 0;
			traversePreorder(Integer.toString(root), tree);
			printData(graph, hamiltonianPath);

			// apply Brute Force approach
			BruteForceTSP bruteForce = new BruteForceTSP(crimeCnt, graph);
			bruteForce.getAllPath();
			bruteForce.getOptimalPath();
			optimlPth = bruteForce.optimalPath;
			optimalDistance = bruteForce.optimalDist;

			System.out.println("");
			System.out.println("");
			System.out.println("Looking at every permutation to find the optimal solution");
			System.out.println("The best permutation");
			for (Integer b : optimlPth) {
				System.out.print(b + "  ");
			}
			System.out.println("");
			System.out.println("Optimal Cycle length = " + optimalDistance + " miles");

		}

	}

	/**
	 * This is pre-order traversal method
	 * 
	 * @Precondition MST tree is provided for traversal
	 * @Postcondition Traversal result is returned
	 * @param node
	 * @param tree
	 */
	static void traversePreorder(String node, LinkedList<Integer>[] tree) {
		if (node == null)
			return;

		int n = Integer.parseInt(node);
		// traverse the left node first
		hamiltonianPath.add(n);
		// traverse the left node first
		if (!tree[n].isEmpty()) {
			if (tree[n].size() > 1) {
				traversePreorder(tree[n].getLast().toString(), tree);
				traversePreorder(tree[n].getFirst().toString(), tree);
			} else {
				traversePreorder(tree[n].getLast().toString(), tree);
			}

		}
	}

	/**
	 * This method prints the data of graph traversal
	 * 
	 * @Precondition Graph and traversal path is provided
	 * @Postcondition result is printed
	 * @param graph
	 * @param hamiltonianPath
	 */
	static void printData(WeightedGraph graph, LinkedList<Integer> hamiltonianPath) {
		double totalPath = 0;

		System.out.println("");
		System.out.println("Crime records between " + input1 + " and " + input2);
		System.out.println("------------------------------------------------------");
		for (int u : hamiltonianPath) {
			System.out.println(graph.graphData.get(u).toString());
		}

		System.out.println("");
		System.out.println("Hamiltonian Cycle (not necessarily optimum):");
		for (int b = 0; b < hamiltonianPath.size() + 1; b++) {

			if (b == hamiltonianPath.size() - 1) {
				System.out.print(hamiltonianPath.get(b) + "  ");
				totalPath += graph.graphDist[hamiltonianPath.get(b)][0];
			} else if (b == hamiltonianPath.size()) {
				System.out.println("0");

			} else {
				System.out.print(hamiltonianPath.get(b) + "  ");
				totalPath += graph.graphDist[hamiltonianPath.get(b)][hamiltonianPath.get(b + 1)];
			}
		}

		System.out.println("");
		System.out.println("Length Of cycle: " + totalPath + " miles");

	}

}
