package net.sf.graphalgorithms.johnson;

import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import net.sf.graphalgorithms.johnson.Johnson.JohnsonIllegalStateException;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.Random;

import static org.junit.Assert.assertTrue;

public class TestJohnson {

    @Test
    public void testJohnson() throws JohnsonIllegalStateException {
        DirectedGraph<Integer, WeightedEdge> dsg = new DirectedSparseGraph<Integer, WeightedEdge>();
        dsg.addEdge(new WeightedEdge((float) 1.0), 1, 3);
        dsg.addEdge(new WeightedEdge((float) 1.0), 3, 1);
        dsg.addEdge(new WeightedEdge((float) 1.0), 3, 2);
        dsg.addEdge(new WeightedEdge((float) 1.0), 2, 3);
        new Johnson().findCircuits(dsg);
    }

    @Test
    public void testJohnson2() throws JohnsonIllegalStateException {
        DirectedGraph<Integer, WeightedEdge> dsg = new DirectedSparseGraph<Integer, WeightedEdge>();
        dsg.addEdge(new WeightedEdge((float) 1.0), 1, 2);
        dsg.addEdge(new WeightedEdge((float) 1.0), 2, 1);
        dsg.addEdge(new WeightedEdge((float) 1.0), 2, 3);
        dsg.addEdge(new WeightedEdge((float) 1.0), 4, 5);
        dsg.addEdge(new WeightedEdge((float) 1.0), 5, 6);
        dsg.addEdge(new WeightedEdge((float) 1.0), 6, 4);
        new Johnson().findCircuits(dsg);
    }

    @Test
    public void testSubGraphFrom() {
        DirectedGraph<Integer, WeightedEdge> dsg = new DirectedSparseGraph<Integer, WeightedEdge>();
        dsg.addEdge(new WeightedEdge((float) 1.0), 1, 3);
        dsg.addEdge(new WeightedEdge((float) 1.0), 3, 1);
        dsg.addEdge(new WeightedEdge((float) 1.0), 3, 2);
        dsg.addEdge(new WeightedEdge((float) 1.0), 2, 3);
        DirectedGraph<Integer, WeightedEdge> subGraph = new Johnson().subGraphFrom(2, dsg);
        assertTrue(subGraph.containsVertex(2));
        assertTrue(subGraph.containsVertex(3));
        System.err.println(subGraph);
    }

    @Test
    public void testJohnson3() throws JohnsonIllegalStateException {
        DirectedGraph<Integer, WeightedEdge> dsg = new DirectedSparseGraph<Integer, WeightedEdge>();
        dsg.addEdge(new WeightedEdge((float) 1.0), 2, 1);
        dsg.addEdge(new WeightedEdge((float) 1.0), 1, 2);
        dsg.addEdge(new WeightedEdge((float) 1.0), 3, 2);
        dsg.addEdge(new WeightedEdge((float) 1.0), 3, 1);
        new Johnson().findCircuits(dsg);
    }

    @Test
    public void testJohnson4() throws JohnsonIllegalStateException {
        DirectedGraph<Integer, WeightedEdge> dsg = new DirectedSparseGraph<Integer, WeightedEdge>();
        dsg.addEdge(new WeightedEdge((float) 1.0), 1, 3);
        dsg.addEdge(new WeightedEdge((float) 1.0), 3, 2);
        dsg.addEdge(new WeightedEdge((float) 1.0), 3, 1);
        dsg.addEdge(new WeightedEdge((float) 1.0), 2, 1);
        new Johnson().findCircuits(dsg);
    }

    @Test
    public void testLargeGraph() throws JohnsonIllegalStateException {
        DirectedGraph<Integer, WeightedEdge> dg = new DirectedSparseGraph<Integer, WeightedEdge>();
        Random r = new Random();
        int nodes = 4000;
        int edges = 5000;
        while (edges > 0) {
            int from = r.nextInt(nodes) + 1;
            int to = r.nextInt(nodes) + 1;
            float weight = r.nextFloat();
            if (dg.findEdge(from, to) == null && from != to) {
                dg.addEdge(new WeightedEdge(weight), from, to);
                edges--;
            }
        }
        System.err.println("edges: " + dg.getEdgeCount());
        System.err.println(dg);
        new Johnson().findCircuits(dg);
    }
}
