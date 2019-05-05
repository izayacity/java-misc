package com.izayacity.algorithms.graph;

public class MST {
	// Number of vertices in the graph
	private static final int V = 5;

	// A util function to find the vertex with the minimum key
	// value, from the set of vertices (mstSet) not yet included in the MST
	int minKey(int key[], Boolean mstSet[]) {
		int min_index = -1;
		int min_val = Integer.MAX_VALUE;

		// Loop the vertice set to find the minimum key with the smallest key[v] value
		for (int i = 0; i < V ; i++) {
			if (!mstSet[i] && key[i] < min_val) {
				min_val = key[i];
				min_index = i;
			}
		}
		return min_index;
	}

	// Function to construct and print the MST for a graph represented
	// using adjacency matrix representation
	int[] primMST(int[][] graph) {
		int parent[] = new int[V];								// Array of prior nodes for each graph node to store the constructed MST
		int key[] = new int[V];									// Key values used to pick minimum weight edge in cut
		Boolean used[] = new Boolean[V];				// To represent set of vertices not yet included in MST

		// Initialize all keys as INFINITE
		for (int i = 0; i < V; i++) {
			key[i] = Integer.MAX_VALUE;
			used[i] = false;
		}
		key[0] = 0;				// Always include first vertex in MST
		parent[0] = -1;			// First node is the root of the MST

		// minKey() is O(V) so it’s O(V^2)
		// The MST will have V vertices, with V - 1 edges
		for (int i = 0; i < V  - 1; i++) {
			// Pick the minimum key vertex from the set of vertices
			// not yet included in the MST
			int u = minKey(key, used);

			// Add the picked vertex to the MST set
//			try {
				used[u] = true;
//			} catch (Exception e) {
//				System.out.println("Error: Invalid used[] array index -1");
//			}

			// Update key value and parent index of the adjacent
			// vertices of the picked picked vertex.
			for (int j = 0; j < V; j++) {
				// Update the key only if graph[u][v] is smaller than key[v]
				if (graph[u][j] != 0 && !used[j] && graph[u][j] < key[j]) {
					parent[j] = u;
					key[j] = graph[u][j];
				}
			}
		}
		return parent;
	}

	// A utility function to print the constructed MST stored in parent[]
	void printMST (int parent[], int graph[][]) {
		System.out.println("Edge		Weight");
		for(int i = 1; i < V; i++) {
			System.out.println(parent[i] + " - " + i + "				" + graph[i][parent[i]]);
		}
	}

	/* Let us create the following graph with the weight on each edges
           2	3
        (0)--(1)--(2)
        |	/ \   |
        6| 8/   \5 |7
        | /  	\ |
        (3)-------(4)
             9
	*/
	public static void main (String[] args) {
		MST t = new MST();
		int graph[][] = new int[][] {{0, 2, 0, 6, 0},
				{2, 0, 3, 8, 5},
				{0, 3, 0, 0, 7},
				{6, 8, 0, 0, 9},
				{0, 5, 7, 9, 0},
		};
		int parent[] = t.primMST(graph);
		t.printMST(parent, graph);
	}
}
