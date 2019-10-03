package com.izayacity.algorithms.graph;

import java.util.*;
import java.util.LinkedList;

/**
 * CreatedBy:   Francis Xirui Yang
 * Date:        10/1/19
 * mailto:      izayacity@gmail.com
 * version:     1.0 since 1.0
 */
// A Java program to find bridges in a given undirected graph
// This class represents a undirected graph using adjacency list representation
public class GraphBridge {

    private int V; // No. of vertices
    // Array of lists for Adjacency List Representation
    private LinkedList[] adj;
    private int time = 0;
    private static final int NIL = -1;

    // Constructor
    GraphBridge(int v) {
        V = v;
        adj = new LinkedList[v];
        for (int i = 0; i < v; ++i) {
            adj[i] = new LinkedList();
        }
    }

    // Function to add an edge into the graph
    void addEdge(int v, int w) {
        adj[v].add(w); // Add w to v's list.
        adj[w].add(v); //Add v to w's list
    }

    // the value low[v] indicates earliest visited vertex reachable from subtree rooted with v.
    // The condition for an edge (u, v) to be a bridge is, “low[v] > disc[u]”.
    // u --> The vertex to be visited next
    // visited[] --> keeps tract of visited vertices
    // disc[] --> Stores discovery times of visited vertices
    // parent[] --> Stores parent vertices in DFS tree
    void bridgeUtil(int u, boolean[] visited, int[] disc, int[] low, int[] parent) {
        // Mark the current node as visited
        visited[u] = true;
        // Initialize discovery time and low value
        disc[u] = low[u] = ++time;

        // Go through all vertices aadjacent to this
        Iterator<Integer> i = adj[u].iterator();
        while (i.hasNext()) {
            int v = i.next(); // v is current adjacent of u

            // If v is not visited yet, then make it a child of u in DFS tree and recur for it.
            // If v is not visited yet, then recur for it
            if (!visited[v]) {
                parent[v] = u;
                bridgeUtil(v, visited, disc, low, parent);
                // Check if the subtree rooted with v has a
                // connection to one of the ancestors of u
                low[u] = Math.min(low[u], low[v]);
                // If the lowest vertex reachable from subtree
                // under v is below u in DFS tree, then u-v is
                // a bridge
                if (low[v] > disc[u]) {
                    System.out.println(u + " " + v);
                }
            } else if (v != parent[u]) {
                // Update low value of u for parent function calls.
                low[u] = Math.min(low[u], disc[v]);
            }
        }
    }

    // DFS based function to find all bridges. It uses recursive
    // function bridgeUtil()
    void bridge() {
        // Mark all the vertices as not visited
        boolean[] visited = new boolean[V];
        int[] disc = new int[V];
        int[] low = new int[V];
        int[] parent = new int[V];

        // Initialize parent and visited, and ap(articulation point)
        // arrays
        for (int i = 0; i < V; i++) {
            parent[i] = NIL;
            visited[i] = false;
        }

        // Call the recursive helper function to find Bridges
        // in DFS tree rooted with vertex 'i'
        for (int i = 0; i < V; i++) {
            if (!visited[i]) {
                bridgeUtil(i, visited, disc, low, parent);
            }
        }
    }

    public static void main(String args[]) {
        // Create graphs given in above diagrams
        System.out.println("Bridges in first graph ");
        GraphBridge g1 = new GraphBridge(5);
        g1.addEdge(1, 0);
        g1.addEdge(0, 2);
        g1.addEdge(2, 1);
        g1.addEdge(0, 3);
        g1.addEdge(3, 4);
        g1.bridge();
        System.out.println();

        System.out.println("Bridges in Second graph");
        GraphBridge g2 = new GraphBridge(4);
        g2.addEdge(0, 1);
        g2.addEdge(1, 2);
        g2.addEdge(2, 3);
        g2.bridge();
        System.out.println();

        System.out.println("Bridges in Third graph ");
        GraphBridge g3 = new GraphBridge(7);
        g3.addEdge(0, 1);
        g3.addEdge(1, 2);
        g3.addEdge(2, 0);
        g3.addEdge(1, 3);
        g3.addEdge(1, 4);
        g3.addEdge(1, 6);
        g3.addEdge(3, 5);
        g3.addEdge(4, 5);
        g3.bridge();
    }
}
// This code is contributed by Aakash Hasija
