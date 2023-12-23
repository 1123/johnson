package jgraphalgos.johnson;

import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.DirectedSparseGraph;

import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;
import java.util.Stack;

import jgraphalgos.johnson.Johnson;
import jgraphalgos.WeightedEdge;
import jgraphalgos.johnson.Johnson.JohnsonIllegalStateException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class TestJohnson {

    /**
     * Test for detection of two binary cycles.
     *
     * @throws JohnsonIllegalStateException
     */

    @Test
    public void testJohnson() throws JohnsonIllegalStateException {
        DirectedGraph<Integer, WeightedEdge> dsg = new DirectedSparseGraph<Integer, WeightedEdge>();
        dsg.addEdge(new WeightedEdge(1.0f), 1, 3);
        dsg.addEdge(new WeightedEdge(1.0f), 3, 1);
        dsg.addEdge(new WeightedEdge(1.0f), 3, 2);
        dsg.addEdge(new WeightedEdge(1.0f), 2, 3);
        Johnson j = new Johnson(dsg);
        j.findCircuits();
        assertSame(2, j.circuits.size());
        Stack<Integer> expected1 = new Stack<Integer>();
        // TODO: there is no necessity for a node appearing twice within a cycle.
        expected1.addAll(Arrays.asList(1, 3, 1));
        assertTrue(j.circuits.contains(expected1));
    }

    /**
     * Test with one ternary cycle.
     *
     * @throws JohnsonIllegalStateException
     */

    @Test
    public void testJohnson2() throws JohnsonIllegalStateException {
        DirectedGraph<Integer, WeightedEdge> dsg = new DirectedSparseGraph<Integer, WeightedEdge>();
        dsg.addEdge(new WeightedEdge(1.0f), 1, 2);
        dsg.addEdge(new WeightedEdge(1.0f), 2, 1);
        dsg.addEdge(new WeightedEdge(1.0f), 2, 3);
        dsg.addEdge(new WeightedEdge(1.0f), 4, 5);
        dsg.addEdge(new WeightedEdge(1.0f), 5, 6);
        dsg.addEdge(new WeightedEdge(1.0f), 6, 4);
        Johnson j = new Johnson(dsg);
        j.findCircuits();
        assertSame(2, j.circuits.size());
        assertTrue(j.circuits.get(1).contains(4));
        assertTrue(j.circuits.get(1).contains(5));
        assertTrue(j.circuits.get(1).contains(6));
    }

    /**
     * test for the method subGraphFrom.
     */

    @Test
    public void testSubGraphFrom() {
        DirectedGraph<Integer, WeightedEdge> dsg = new DirectedSparseGraph<Integer, WeightedEdge>();
        dsg.addEdge(new WeightedEdge(1.0f), 1, 3);
        dsg.addEdge(new WeightedEdge(1.0f), 3, 1);
        dsg.addEdge(new WeightedEdge(1.0f), 3, 2);
        dsg.addEdge(new WeightedEdge(1.0f), 2, 3);
        DirectedGraph<Integer, WeightedEdge> subGraph = Johnson.subGraphFrom(2, dsg);
        assertTrue(subGraph.containsVertex(2));
        assertTrue(subGraph.containsVertex(3));
        System.err.println(subGraph);
    }

    /*  from https://stackoverflow.com/questions/27218194/should-d-b-johnsons-elementary-circuits-algorithm-produce-distinct-results:
    gr := gs.NewGraph()

    a := gr.CreateAndAddToGraph("A")
    b := gr.CreateAndAddToGraph("B")
    c := gr.CreateAndAddToGraph("C")
    d := gr.CreateAndAddToGraph("D")
    e := gr.CreateAndAddToGraph("E")
    f := gr.CreateAndAddToGraph("F")

    gr.Connect(a, b, 1)
    gr.Connect(b, c, 1)
    gr.Connect(c, a, 1)

    gr.Connect(d, e, 1)
    gr.Connect(e, f, 1)
    gr.Connect(f, d, 1)
     */


    @Test
    public void testTwoTernaryCycles() throws JohnsonIllegalStateException {
        DirectedGraph<Integer, WeightedEdge> dsg = new DirectedSparseGraph<>();
        dsg.addEdge(new WeightedEdge(1.0f), 1, 2);
        dsg.addEdge(new WeightedEdge(1.0f), 2, 3);
        dsg.addEdge(new WeightedEdge(1.0f), 3, 1);

        dsg.addEdge(new WeightedEdge(1.0f), 4, 5);
        dsg.addEdge(new WeightedEdge(1.0f), 5, 6);
        dsg.addEdge(new WeightedEdge(1.0f), 6, 4);

        Johnson j = new Johnson(dsg);
        j.findCircuits();
        System.err.println(j.circuits);
        assertEquals(2, j.circuits.size());
    }

    /**
     * Test for a graph with one binary cycle.
     * @throws JohnsonIllegalStateException
     */

    @Test
    public void testJohnson3() throws JohnsonIllegalStateException {
        DirectedGraph<Integer, WeightedEdge> dsg = new DirectedSparseGraph<Integer, WeightedEdge>();
        dsg.addEdge(new WeightedEdge(1.0f), 2, 1);
        dsg.addEdge(new WeightedEdge(1.0f), 1, 2);
        dsg.addEdge(new WeightedEdge(1.0f), 3, 2);
        dsg.addEdge(new WeightedEdge(1.0f), 3, 1);
        Johnson j = new Johnson(dsg);
        j.findCircuits();
        assertEquals(1, j.circuits.size());
        System.err.println(j.circuits.get(0));
        assertTrue(j.circuits.get(0).contains(2));
        assertTrue(j.circuits.get(0).contains(1));
    }

    @Test
    public void testJohnson4() throws JohnsonIllegalStateException {
        DirectedGraph<Integer, WeightedEdge> dsg = new DirectedSparseGraph<Integer, WeightedEdge>();
        dsg.addEdge(new WeightedEdge(1.0f), 1, 3);
        dsg.addEdge(new WeightedEdge(1.0f), 3, 2);
        dsg.addEdge(new WeightedEdge(1.0f), 3, 1);
        dsg.addEdge(new WeightedEdge(1.0f), 2, 1);
        Johnson j = new Johnson(dsg);
        j.findCircuits();
        System.err.println(j.circuits);
        assertEquals(2, j.circuits.size());
        assertTrue(j.circuits.get(0).contains(1));
        assertTrue(j.circuits.get(0).contains(3));
    }

    /**
     * Test for detection of strongly connected components.
     * @throws JohnsonIllegalStateException: if the computation reaches a state that should never be reached.
     */

    @Test
    public void testTarjan3() throws JohnsonIllegalStateException {
        DirectedGraph<Integer, WeightedEdge> dsg = new DirectedSparseGraph<Integer, WeightedEdge>();
        dsg.addEdge(new WeightedEdge(1.0f), 1, 2);
        dsg.addEdge(new WeightedEdge(1.0f), 2, 1);
        dsg.addEdge(new WeightedEdge(1.0f), 2, 3);
        dsg.addEdge(new WeightedEdge(1.0f), 2, 4);
        dsg.addEdge(new WeightedEdge(1.0f), 4, 2);
        DirectedGraph<Integer, WeightedEdge> leastScc = Johnson.leastSCC(dsg);
        System.err.println(leastScc);
        assertTrue(leastScc.getVertices().contains(1));
        assertTrue(leastScc.getVertices().contains(2));
        assertTrue(leastScc.getVertices().contains(4));
        assertEquals(3, leastScc.getVertexCount());
    }
    

    @Test
    public void testLargeGraph() throws JohnsonIllegalStateException {
        DirectedGraph<Integer, WeightedEdge> dg = new DirectedSparseGraph<Integer, WeightedEdge>();
        Random r = new Random();
        int nodes = 1000;
        int edges = 1000;
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
