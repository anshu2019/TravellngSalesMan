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
			System.out.println("");
			System.out.println("KML file PGHCrimes.kml is created and saved in data directory");
			toKML(graph);

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

	/**
	 * This method creates KML file for google map
	 * @Precondition Graph is created
	 * @Postcondition KML file is created
	 * @param graph
	 */
	public static void toKML(WeightedGraph graph) {

		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter("data/PGHCrimes.kml");
		} catch (IOException e) {

			e.printStackTrace();
		}
		PrintWriter printWriter = new PrintWriter(fileWriter);
		printWriter.print("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
		printWriter.print("<kml xmlns=\"http://earth.google.com/kml/2.2\">\n");
		printWriter.print("<Document>");
		printWriter.print("<name>Pittsburgh TSP</name><description>TSP on Crime</description><Style id=\"style6\">\n");
		printWriter.print("<LineStyle>\n");
		printWriter.print("<color>73FF0000</color>\n");
		printWriter.print(" <width>5</width>\n");
		printWriter.print(" </LineStyle>\n");
		printWriter.print(" </Style>\n");
		printWriter.print(" <Style id=\"style6\">\n");
		printWriter.print("<LineStyle>\n");
		printWriter.print("<color>507800F0</color>\n");
		printWriter.print(" <width>5</width>\n");
		printWriter.print(" </LineStyle>\n");
		printWriter.print(" </Style>\n");
		printWriter.print("<Placemark>\n");
		printWriter.print("<name>" + "TSP Path" + "</name>\n");
		printWriter.print(" <description>" + "TSP Path" + "</description>\n");
		printWriter.print("<styleUrl>#style6</styleUrl>\n");
		printWriter.print(" <LineString>\n");
		printWriter.print("<tessellate>1</tessellate>\n");
		printWriter.print("  <coordinates>\n");
		
		for (Integer b : hamiltonianPath) {
			String longgitude = graph.graphData.get(b).lng;
			String lattitude = graph.graphData.get(b).lat;
			String thirdAxes = "0.000000";
			printWriter.print(longgitude + "," + lattitude + "," + thirdAxes + "\n");
		}
		// Add 0th element again at the end
		String longgitude1 = graph.graphData.get(hamiltonianPath.get(0)).lng;
		String lattitude1 = graph.graphData.get(hamiltonianPath.get(0)).lat;
		String thirdAxes1 = "0.000000";
		printWriter.print(longgitude1 + "," + lattitude1 + "," + thirdAxes1 + "\n");

		printWriter.print("  </coordinates>\n");
		printWriter.print("  </LineString>\n");
		printWriter.print("</Placemark>\n");
		printWriter.print("<Placemark>\n");
		printWriter.print("<name>" + "Optimal Path" + "</name>\n");
		printWriter.print(" <description>" + "Optimal Path" + "</description>\n");
		printWriter.print("<styleUrl>#style5</styleUrl>\n");
		printWriter.print(" <LineString>\n");
		printWriter.print("<tessellate>1</tessellate>\n");
		printWriter.print("  <coordinates>\n");
		
		for (Integer b : optimlPth) {
			String longgitude = graph.graphData.get(b).lng;
			String lattitude = graph.graphData.get(b).lat;
			String thirdAxes = "0.000000";
			printWriter.print(longgitude + "," + lattitude + "," + thirdAxes + "\n");
		}
		// Add 0th element again at the end
		String longgitude2 = graph.graphData.get(optimlPth.get(0)).lng;
		String lattitude2 = graph.graphData.get(optimlPth.get(0)).lat;
		String thirdAxes2 = "0.000000";
		printWriter.print(longgitude2 + "," + lattitude2 + "," + thirdAxes2 + "\n");
		
		printWriter.print("  </coordinates>\n");
		printWriter.print("  </LineString>\n");
		printWriter.print("</Placemark>\n");
		printWriter.print("</Document>\n");
		printWriter.print("</kml>\n");
		printWriter.close();

	}
}
