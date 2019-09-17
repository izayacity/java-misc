package com.izayacity.algorithms.graph;

import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class GraphTest {

    private Gson gson = new Gson();

    Graph<Character> newGraph() {
        List<GraphEdge<Character>> edges = Arrays.asList(
                new GraphEdge<>('A', 'C'),
                new GraphEdge<>('B', 'C'),
                new GraphEdge<>('C', 'E'),
                new GraphEdge<>('E', 'F'),
                new GraphEdge<>('D', 'F'),
                new GraphEdge<>('G', 'Z')
        );
        return new Graph<Character>(edges, true);
    }

    @Test
    void addEdgeTest() {
        Graph<Character> graph = newGraph();
        Assertions.assertNotNull(graph);
        //System.out.println(gson.toJson(graph));
    }

    @Test
    void connectionLevel() {
    }

    @Test
    void connectionLevelBfs() {
        Graph<Character> graph = newGraph();
        Assertions.assertEquals(1, graph.connectionLevel('A', 'C'));
        Assertions.assertEquals(2, graph.connectionLevel('A', 'B'));
        Assertions.assertEquals(2, graph.connectionLevel('A', 'E'));
        Assertions.assertEquals(3, graph.connectionLevel('A', 'F'));
        Assertions.assertEquals(4, graph.connectionLevel('A', 'D'));
        Assertions.assertEquals(0, graph.connectionLevel('A', 'G'));
        Assertions.assertEquals(1, graph.connectionLevel('A', 'A'));
    }

    @Test
    void connectionLevelDfs() {
        Graph<Character> graph = newGraph();
        Assertions.assertEquals(1, graph.connectionLevelDfs('A', 'C'));
        Assertions.assertEquals(2, graph.connectionLevelDfs('A', 'B'));
        Assertions.assertEquals(2, graph.connectionLevelDfs('A', 'E'));
        Assertions.assertEquals(3, graph.connectionLevelDfs('A', 'F'));
        Assertions.assertEquals(4, graph.connectionLevelDfs('A', 'D'));
        Assertions.assertEquals(0, graph.connectionLevelDfs('A', 'G'));
        Assertions.assertEquals(1, graph.connectionLevelDfs('A', 'A'));
    }
}