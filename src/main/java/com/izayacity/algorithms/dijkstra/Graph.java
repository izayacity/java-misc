package com.izayacity.algorithms.dijkstra;

//	create vertex set Q
//
//			for each vertex v in graph:
//			dist[v] = INFINITE
//			prev[v] = NULL
//			add v to Q
//
//			while Q is not empty:
//			u = vertex in Q with min dist
//			remove u from Q
//
//			for each neighbor v of u:
//			val = dist[u] + graph[u][v]
//			if val < dist[v]:
//		dist[v] = val
//		prev[v] = u
//
//		return dist[], prev[]

import java.util.ArrayList;

public class Graph {

	private Node[] nodes;
	private int noNodes;
	private Edge[] edges;
	private int noEdges;

	public Graph(Edge[] edges) {
		this.edges = edges;
		this.noNodes = calNoNodes(edges);
		this.nodes = new Node[this.noNodes];

		for(int i = 0; i < this.noNodes; i++) {
			this.nodes[i] = new Node();
		}
		this.noEdges = edges.length;

		for(int i = 0; i < this.noEdges; i++) {
			this.nodes[edges[i].getFromNode()].getEdges().add(edges[i]);
			this.nodes[edges[i].getToNode()].getEdges().add(edges[i]);
		}
	}

	private int calNoNodes(Edge[] edges) {
		int count = 0;

		for(Edge e : edges) {
			if(e.getToNode() > count) {
				count = e.getToNode();
			}
			if(e.getFromNode() > count) {
				count = e.getFromNode();
			}
		}
		count++;
		return count;
	}

	public void printResult() {
		StringBuilder output = new StringBuilder("Number of  nodes = "
				+ this.noNodes + "\nNumber of edges = " + this.noEdges);

		for(int i = 0; i < this.nodes.length; i++) {
			output.append("\nThe shortest distance from node 0 to node "
					+ i + " is " + nodes[i].getDistFromSrc());
		}
		System.out.println(output.toString());
	}

	public Node[] getNodes() {
		return nodes;
	}

	public int getNoNodes() {
		return noNodes;
	}

	public Edge[] getEdges() {
		return edges;
	}

	public int getNoEdges() {
		return noEdges;
	}

	public void dijkstra() {
		this.nodes[0].setDistFromSrc(0);
		int next = 0;

		for(int i = 0; i < this.nodes.length; i++) {
			ArrayList<Edge> currEdges = this.nodes[next].getEdges();

			for(int j = 0; j < currEdges.size(); j++) {
				int neighbor = currEdges.get(j).getNeighbor(next);

				if(this.nodes[neighbor].isVisited()) {
					continue;
				}
				int tmp = this.nodes[next].getDistFromSrc()
						+ currEdges.get(j).getLength();

				if(tmp < nodes[neighbor].getDistFromSrc()) {
					nodes[neighbor].setDistFromSrc(tmp);
				}
			}
			nodes[next].setVisited(true);
			next = minDist();
		}
	}

	private int minDist() {
		int res = 0;
		int min = Integer.MAX_VALUE;

		for(int i = 0; i < this.nodes.length; i++) {
			int curr = this.nodes[i].getDistFromSrc();
			if(!this.nodes[i].isVisited() && curr < min) {
				min = curr;
				res = i;
			}
		}
		return res;
	}
}

