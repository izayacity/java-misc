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
        LinkedList<BfsQueueNode<T>> queue = Lists.newLinkedList();
        Set<T> visited = Sets.newHashSet();
        queue.add(new BfsQueueNode<>(src, 0));
        visited.add(src);

        while (!queue.isEmpty()) {
            BfsQueueNode<T> current = queue.poll();

            for (T adjNode : this.adjListMap.get(current.getNode()).keySet()) {
                if (visited.contains(adjNode)) {
                    continue;
                }
                if (adjNode == dest) {
                    return current.getLevel() + 1;
                }
                queue.add(new BfsQueueNode<>(adjNode, current.getLevel() + 1));
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

    public static class BfsQueueNode<T> {
        private T node;
        private int level;

        public BfsQueueNode(T node, int level) {
            this.node = node;
            this.level = level;
        }

        public T getNode() {
            return node;
        }

        public int getLevel() {
            return level;
        }
    }
}
