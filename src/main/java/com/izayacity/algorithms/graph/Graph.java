package com.izayacity.algorithms.graph;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.util.*;

public class Graph<T> {

    private Map<T, Map<T, Integer>> adjListMap;
    private Set<T> graphNodes;

    public Graph(final List<GraphEdge<T>> edges, final boolean undirected) {
        this.adjListMap = Maps.newHashMap();
        this.graphNodes = Sets.newHashSet();

        for (GraphEdge<T> edge : edges) {
            this.addEdge(edge.getFromNode(), edge.getToNode(), edge.getWeight(), undirected);
        }
    }

    public Graph(final List<GraphEdge<T>> edges) {
        this(edges, false);
    }

    public void addEdge(final T src, final T dest) {
        this.addEdge(src, dest, 1);
    }

    public void addEdge(final T src, final T dest, final int weight) {
        if (!adjListMap.containsKey(src)) {
            this.adjListMap.put(src, new HashMap<>());
        }
        this.adjListMap.get(src).put(dest, weight);
        this.graphNodes.add(src);
        this.graphNodes.add(dest);
    }

    public void addUndirectedEdge(final T src, final T dest, final int weight) {
        this.addEdge(src, dest, weight);
        this.addEdge(dest, src, weight);
    }

    public void addUndirectedEdge(final T src, final T dest) {
        this.addEdge(src, dest);
        this.addEdge(dest, src);
    }

    public void addEdge(final T src, final T dest, final boolean undirected) {
        if (undirected) {
            this.addUndirectedEdge(src, dest);
        } else {
            this.addEdge(src, dest);
        }
    }

    public void addEdge(final T src, final T dest, final int weight, final boolean undirected) {
        if (undirected) {
            this.addUndirectedEdge(src, dest, weight);
        } else {
            this.addEdge(src, dest, weight);
        }
    }

    public int size() {
        return this.graphNodes.size();
    }

    public int edges() {
        return this.adjListMap.size();
    }

    public int connectionLevel(final T src, final T dest) {
        if (src == dest) {
            return 1;
        }
        if (!this.adjListMap.containsKey(src) || !this.adjListMap.containsKey(dest)) {
            return 0;
        }
        LinkedList<GraphNodeInfo<T>> queue = Lists.newLinkedList();
        Set<T> visited = Sets.newHashSet();
        queue.add(new GraphNodeInfo<>(src, 0));
        visited.add(src);

        while (!queue.isEmpty()) {
            GraphNodeInfo<T> current = queue.poll();

            for (T adjNode : this.adjListMap.get(current.getNode()).keySet()) {
                if (visited.contains(adjNode)) {
                    continue;
                }
                if (adjNode == dest) {
                    return current.getVal() + 1;
                }
                queue.add(new GraphNodeInfo<>(adjNode, current.getVal() + 1));
                visited.add(adjNode);
            }
        }
        return 0;
    }

    public int connectionLevelDfs(final T src, final T dest) {
        if (src == dest) {
            return 1;
        }
        Set<T> visited = Sets.newHashSet();
        int[] result = new int[]{Integer.MAX_VALUE};
        connectionLevelDfsUtil(src, dest, visited, result, 0);
        if (result[0] > this.size()) {
            return 0;
        }
        return result[0];
    }

    private void connectionLevelDfsUtil(final T src, final T dest, Set<T> visited, int[] result, int current) {
        if (!this.adjListMap.containsKey(src) || !this.adjListMap.containsKey(dest)) {
            result[0] = 0;
            return;
        }
        visited.add(src);

        for (T adjNode : this.adjListMap.get(src).keySet()) {
            if (visited.contains(adjNode)) {
                continue;
            }
            if (adjNode == dest) {
                if (current < result[0]) {
                    result[0] = current + 1;
                }
                return;
            }
            connectionLevelDfsUtil(adjNode, dest, visited, result, current + 1);
        }
    }

    public void topologicalSort() {
        List<GraphNodeInfo<T>> resList = Lists.newArrayList();
        Set<T> visited = new HashSet<>();

        for (T node : this.graphNodes) {
            if (!visited.contains(node)) {
                topologicalSortUtil(node, visited, resList);
            }
        }
        for (GraphNodeInfo info : resList) {
            System.out.println("node " + info.getNode().toString() + " value " + info.getVal());
        }
    }

    private void topologicalSortUtil(T currNode, Set<T> visited, List<GraphNodeInfo<T>> resList) {
        visited.add(currNode);
        if (this.adjListMap.containsKey(currNode)) {
            for (T adjNode : this.adjListMap.get(currNode).keySet()) {
                if (visited.contains(adjNode)) {
                    continue;
                }
                topologicalSortUtil(adjNode, visited, resList);
            }
        }
        int resSize = resList.size();
        resList.add(new GraphNodeInfo<>(currNode, this.graphNodes.size() - resSize));
    }

    public static class GraphNodeInfo<T> {
        private T node;
        private int val;

        public GraphNodeInfo(T node, int val) {
            this.node = node;
            this.val = val;
        }

        public T getNode() {
            return node;
        }

        public int getVal() {
            return val;
        }
    }
}
