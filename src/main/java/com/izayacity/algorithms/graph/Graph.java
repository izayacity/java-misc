package com.izayacity.algorithms.graph;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.izayacity.algorithms.unionFind.UnionFind;

import java.util.*;

/**
 * CreatedBy:   Francis Xirui Yang
 * Date:        10/3/20
 * mailto:      izayacity@gmail.com
 * version:     1.0 since 1.0
 *
 * @author francis
 */
public class Graph<T> {

    private final Map<T, Map<T, Integer>> adjListMap;
    public final static int SRC_NODE = 0;
    public final static int DEST_NODE = 1;
    public final static int EDGE_NODES = 2;

    public Graph(final List<GraphEdge<T>> edges, final boolean undirected) {
        this.adjListMap = Maps.newHashMap();

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
        return this.adjListMap.size();
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

        for (T node : this.adjListMap.keySet()) {
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
        resList.add(new GraphNodeInfo<>(currNode, this.size() - resSize));
    }

    public List<EdgeInfo<T>> primMst(T startNode) {
        List<EdgeInfo<T>> list = new ArrayList<>();
        Set<T> visitedSet = new HashSet<>();
        PriorityQueue<EdgeInfo<T>> pq = new PriorityQueue<>(this.adjListMap.size(), Comparator.comparingInt(EdgeInfo::getVal));
        pq.offer(new EdgeInfo<>(null, startNode, 0));
        visitedSet.add(startNode);

        while (!pq.isEmpty()) {
            EdgeInfo<T> node = pq.poll();

            for (Map.Entry<T, Integer> entry : this.adjListMap.get(node.getDest()).entrySet()) {
                if (visitedSet.contains(entry.getKey())) {
                    continue;
                }
                EdgeInfo<T> edge = new EdgeInfo<>(node.getDest(), entry.getKey(), entry.getValue());
                pq.offer(edge);
                list.add(new EdgeInfo<>(edge.getSrc(), edge.getDest(), edge.getVal()));
                visitedSet.add(entry.getKey());
            }
        }
        return list;
    }

    public List<EdgeInfo<T>> kruskalMst() {
        List<EdgeInfo<T>> list = new ArrayList<>();
        List<EdgeInfo<T>> edgeList = new ArrayList<>();

        for (Map.Entry<T, Map<T, Integer>> entry : this.adjListMap.entrySet()) {
            T src = entry.getKey();

            for (Map.Entry<T, Integer> edgeEntry : this.adjListMap.get(src).entrySet()) {
                edgeList.add(new EdgeInfo<>(src, edgeEntry.getKey(), edgeEntry.getValue()));
            }
        }
        edgeList.sort(Comparator.comparingInt(EdgeInfo::getVal));
        UnionFind<T> edgeUnion = new UnionFind<>(new ArrayList<>(this.adjListMap.keySet()));

        for (EdgeInfo<T> edge : edgeList) {
            if (edgeUnion.find(edge.getSrc()) == edgeUnion.find(edge.getDest())) {
                continue;
            }
            edgeUnion.union(edge.getSrc(), edge.getDest());
            list.add(edge);
        }
        return list;
    }

    public static class EdgeInfo<T> {

        private final T src;
        private final T dest;
        private final int val;

        public EdgeInfo(T src, T dest, int val) {
            this.src = src;
            this.dest = dest;
            this.val = val;
        }

        public T getSrc() {
            return src;
        }

        public T getDest() {
            return dest;
        }

        public int getVal() {
            return val;
        }
    }

    public static class GraphNodeInfo<T> {

        private final T node;
        private final int val;

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
