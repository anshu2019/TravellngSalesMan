package tsp;

import java.util.LinkedList;

/**
 * This class applys Brute Force algorithm on TSP problem
 * @author Anshu Anand
 *
 */
public class BruteForceTSP {
	int vrtxCount = 0;
	static LinkedList<Integer>[] allPath;
	int[] shortestPath;
	int noOfPlausiblePath = 0;
	WeightedGraph graph;

	int[] itrnodes; // nodes whose various permuation will be done
	 int pathCounter = 0;
	
	 double optimalDist=Double.MAX_VALUE; //final optimal value
      LinkedList<Integer> optimalPath; //final optimal value
    
	public BruteForceTSP(int vertexCount, WeightedGraph g) {
		graph = g;
		vrtxCount = vertexCount;
		optimalPath = new LinkedList<>();
		noOfPlausiblePath = Helper.getFactorial(vertexCount - 1);

		allPath = new LinkedList[noOfPlausiblePath];
		for (int p = 0; p < noOfPlausiblePath; p++) {
			// initialize all paths as linked list
			allPath[p] = new LinkedList<Integer>();

			// first node of all the paths will be 0
			allPath[p].add(0);
		}
		// integer list to genearate various permuatation
		itrnodes = new int[vertexCount - 1];
		for (int i = 0; i < vertexCount - 1; i++) {
			itrnodes[i] = i + 1;
		}

	}

	/**
	 * This method generates all the possible paths on Graph
	 * @Precondition Graph exists
	 * @Postcondition all paths are calculated
	 */
	public void getAllPath() {
		pathCounter = 0;
		permute(itrnodes, 0);
		
	}

	/**
	 * This method find various permutation of paths
	 * Precondition Graph exists
	 * @Postcondition all paths permutation calculated
	 * @param a
	 * @param k
	 */
	public void permute(int[] a, int k) {

		if (k == a.length) {
			for (int i = 0; i < a.length; i++) {
				//System.out.print(" [" + a[i] + "] ");
				allPath[pathCounter].add(a[i]);
			}

			// last node in path should be 0
			allPath[pathCounter].add(0);
			pathCounter++;
			
			//System.out.println();
		} else {
			for (int i = k; i < a.length; i++) {
				int temp = a[k];
				a[k] = a[i];
				a[i] = temp;
				permute(a, k + 1);
				temp = a[k];
				a[k] = a[i];
				a[i] = temp;

			}
		}
	}
	
	/**
	 * This method finds optimal path among all paths
	 * @Precindition All paths are already calculated
	 * @Post condition Optimal path found
	 */
	public void getOptimalPath() {
		System.out.println("");
		System.out.println("Listed below possible paths with distances");
		for(int u=0;u <allPath.length ; u++) {
			double dist =0;
			LinkedList<Integer> l =allPath[u]; // get the listed path
			
			//calculate the cost...
			for(int v=0; v<l.size();v++) {
				if(v != l.size()-1) {
					dist+= graph.graphDist[l.get(v)][l.get(v+1)];
					
				}
			}		
			
			System.out.println("Path "+u+": " +l + " || Total distance of the path: " + dist);
			
			if(dist <= optimalDist) {
				optimalDist = dist;
				optimalPath = l;
				
			}
		}
	}

}
