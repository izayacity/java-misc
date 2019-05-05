package com.izayacity.algorithms.graph;

import java.util.LinkedList;

public class GFG {
	static class Graph {
		int V;
		LinkedList<Integer> adjListArray[];

		Graph(int V) {
			this.V = V;
			adjListArray = new LinkedList[V];
			// Create a new list for each vertex such that adjacent nodes can be stored
			for(int i = 0; i < V; i++) {
				adjListArray[i] = new LinkedList<>();
			}
		}
	}

	static void printGraph(Graph graph) {
		for(int v = 0; v < graph.V; v++) {
			System.out.println("Adjacency list of vertex " + v);
			System.out.print("head");

			for(Integer val : graph.adjListArray[v]) {
				System.out.print(" -> " + val);
			}
			System.out.println("\n");
		}
	}

	static void addEdge(Graph graph, int src, int dest) {
		graph.adjListArray[src].addFirst(dest);
		graph.adjListArray[dest].addFirst(src);
	}

	public static void main(String args[]) {
		int V= 5;
		Graph graph = new Graph(V);
		addEdge(graph, 0, 1);
		addEdge(graph, 0, 4);
		addEdge(graph, 1, 2);
		addEdge(graph, 1, 3);
		addEdge(graph, 1, 4);
		addEdge(graph, 2, 3);
		addEdge(graph, 3, 4);
		printGraph(graph);
	}
}
