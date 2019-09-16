package com.izayacity.algorithms.graph;

public class GraphEdge<T> {

    private T fromNode;
    private T toNode;
    private int weight;

    public GraphEdge(T fromNode, T toNode, int weight) {
        this.fromNode = fromNode;
        this.toNode = toNode;
        this.weight = weight;
    }

    public GraphEdge(T fromNode, T toNode) {
        this.fromNode = fromNode;
        this.toNode = toNode;
        this.weight = 1;
    }

    public T getFromNode() {
        return fromNode;
    }

    public void setFromNode(T fromNode) {
        this.fromNode = fromNode;
    }

    public T getToNode() {
        return toNode;
    }

    public void setToNode(T toNode) {
        this.toNode = toNode;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
