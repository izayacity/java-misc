package com.izayacity.algorithms.dijkstra;

public class Edge {

	private int fromNode;
	private int toNode;
	private int length;

	public Edge(int fromNode, int toNode, int length) {
		this.fromNode = fromNode;
		this.toNode = toNode;
		this.length = length;
	}

	public int getFromNode() {
		return fromNode;
	}

	public int getToNode() {
		return toNode;
	}

	public int getLength() {
		return length;
	}

	public int getNeighbor(int node) {
		if(fromNode == node) {
			return toNode;
		} else {
			return fromNode;
		}
	}
}
