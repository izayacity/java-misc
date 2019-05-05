package com.izayacity.algorithms.dijkstra;

import java.util.ArrayList;

public class Node {

	private int distFromSrc = Integer.MAX_VALUE;
	private boolean visited;
	private ArrayList<Edge> edges = new ArrayList<>();

	public int getDistFromSrc() {
		return distFromSrc;
	}

	public void setDistFromSrc(int distFromSrc) {
		this.distFromSrc = distFromSrc;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	public ArrayList<Edge> getEdges() {
		return edges;
	}

	public void setEdges(ArrayList<Edge> edges) {
		this.edges = edges;
	}
}
