package jgraphalgos.johnson;

import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.DirectedSparseGraph;

import org.junit.Test;

import java.util.Random;

import jgraphalgos.johnson.Johnson;
import jgraphalgos.WeightedEdge;
import jgraphalgos.johnson.Johnson.JohnsonIllegalStateException;

import static org.junit.Assert.assertTrue;

public class TestJohnson {

    @Test
    public void testJohnson() throws JohnsonIllegalStateException {
        DirectedGraph<Integer, WeightedEdge> dsg = new DirectedSparseGraph<Integer, WeightedEdge>();
        dsg.addEdge(new WeightedEdge((float) 1.0), 1, 3);
        dsg.addEdge(new WeightedEdge((float) 1.0), 3, 1);
        dsg.addEdge(new WeightedEdge((float) 1.0), 3, 2);
        dsg.addEdge(new WeightedEdge((float) 1.0), 2, 3);
        Johnson j = new Johnson(dsg);
        j.findCircuits();
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
        Johnson j = new Johnson(dsg);
        j.findCircuits();
        assertTrue(j.circuits.size() == 2);
        assertTrue(j.circuits.get(1).contains(4));
        assertTrue(j.circuits.get(1).contains(5));
        assertTrue(j.circuits.get(1).contains(6));
    }

    @Test
    public void testSubGraphFrom() {
        DirectedGraph<Integer, WeightedEdge> dsg = new DirectedSparseGraph<Integer, WeightedEdge>();
        dsg.addEdge(new WeightedEdge((float) 1.0), 1, 3);
        dsg.addEdge(new WeightedEdge((float) 1.0), 3, 1);
        dsg.addEdge(new WeightedEdge((float) 1.0), 3, 2);
        dsg.addEdge(new WeightedEdge((float) 1.0), 2, 3);
        DirectedGraph<Integer, WeightedEdge> subGraph = Johnson.subGraphFrom(2, dsg);
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
        Johnson j = new Johnson(dsg);
        j.findCircuits();
        assertTrue(j.circuits.size() == 1);
        System.err.println(j.circuits.get(0));
        assertTrue(j.circuits.get(0).contains(2));
        assertTrue(j.circuits.get(0).contains(1));
    }

    @Test
    public void testJohnson4() throws JohnsonIllegalStateException {
        DirectedGraph<Integer, WeightedEdge> dsg = new DirectedSparseGraph<Integer, WeightedEdge>();
        dsg.addEdge(new WeightedEdge((float) 1.0), 1, 3);
        dsg.addEdge(new WeightedEdge((float) 1.0), 3, 2);
        dsg.addEdge(new WeightedEdge((float) 1.0), 3, 1);
        dsg.addEdge(new WeightedEdge((float) 1.0), 2, 1);
        Johnson j = new Johnson(dsg);
        j.findCircuits();
        System.err.println(j.circuits);
        assertTrue(j.circuits.size() == 2);
        assertTrue(j.circuits.get(0).contains(1));
        assertTrue(j.circuits.get(0).contains(3));
    }

    @Test
    public void testTarjan3() throws JohnsonIllegalStateException {
        DirectedGraph<Integer, WeightedEdge> dsg = new DirectedSparseGraph<Integer, WeightedEdge>();
        dsg.addEdge(new WeightedEdge((float) 1.0), 1, 2);
        dsg.addEdge(new WeightedEdge((float) 1.0), 2, 1);
        dsg.addEdge(new WeightedEdge((float) 1.0), 2, 3);
        dsg.addEdge(new WeightedEdge((float) 1.0), 2, 4);
        dsg.addEdge(new WeightedEdge((float) 1.0), 4, 2);
        DirectedGraph<Integer, WeightedEdge> leastScc = Johnson.leastSCC(dsg);
        System.err.println(leastScc);
        assertTrue(leastScc.getVertices().contains(1));
        assertTrue(leastScc.getVertices().contains(2));
        assertTrue(leastScc.getVertices().contains(4));
        assertTrue(leastScc.getVertexCount() == 3);
    }
    
    //@Test
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
        new Johnson(dg).findCircuits();
    }
}
