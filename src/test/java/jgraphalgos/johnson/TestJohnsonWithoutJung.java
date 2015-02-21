package jgraphalgos.johnson;

import jgraphalgos.tarjan.MyDag;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;
import java.util.Stack;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class TestJohnsonWithoutJung {

    /**
     * Test for detection of two binary cycles.
     */

    @Test
    public void testJohnson() throws Johnson.JohnsonIllegalStateException {
        MyDag<Integer> dsg = new MyDag<>();
        dsg.addEdge(1, 3);
        dsg.addEdge(3, 1);
        dsg.addEdge(3, 2);
        dsg.addEdge(2, 3);
        Johnson j = new Johnson(dsg);
        j.findCircuits();
        assertSame(j.circuits.size(), 2);
        Stack<Integer> expected1 = new Stack<>();
        // TODO: there is no necessity for a node appearing twice within a cycle.
        expected1.addAll(Arrays.asList(1, 3, 1));
        assertTrue(j.circuits.contains(expected1));
    }

    /**
     * Test with one ternary cycle.
     */

    @Test
    public void testJohnson2() throws Johnson.JohnsonIllegalStateException {
        MyDag<Integer> dsg = new MyDag<>();
        dsg.addEdge(1, 2);
        dsg.addEdge(2, 1);
        dsg.addEdge(2, 3);
        dsg.addEdge(4, 5);
        dsg.addEdge(5, 6);
        dsg.addEdge(6, 4);
        Johnson j = new Johnson(dsg);
        j.findCircuits();
        assertSame(j.circuits.size(), 2);
        assertTrue(j.circuits.get(1).contains(4));
        assertTrue(j.circuits.get(1).contains(5));
        assertTrue(j.circuits.get(1).contains(6));
    }

    /**
     * test for the method subGraphFrom.
     */

    @Test
    public void testSubGraphFrom() {
        MyDag<Integer> dsg = new MyDag<>();
        dsg.addEdge(1, 3);
        dsg.addEdge(3, 1);
        dsg.addEdge(3, 2);
        dsg.addEdge(2, 3);
        MyDag<Integer> subGraph = Johnson.subGraphFrom(2, dsg);
        assertTrue(subGraph.containsVertex(2));
        assertTrue(subGraph.containsVertex(3));
        System.err.println(subGraph);
    }

    @Test
    public void testJohnson3() throws Johnson.JohnsonIllegalStateException {
        MyDag<Integer> dsg = new MyDag<>();
        dsg.addEdge(2, 1);
        dsg.addEdge(1, 2);
        dsg.addEdge(3, 2);
        dsg.addEdge(3, 1);
        Johnson j = new Johnson(dsg);
        j.findCircuits();
        assertTrue(j.circuits.size() == 1);
        System.err.println(j.circuits.get(0));
        assertTrue(j.circuits.get(0).contains(2));
        assertTrue(j.circuits.get(0).contains(1));
    }

    @Test
    public void testJohnson4() throws Johnson.JohnsonIllegalStateException {
        MyDag<Integer> dsg = new MyDag<>();
        dsg.addEdge(1, 3);
        dsg.addEdge(3, 2);
        dsg.addEdge(3, 1);
        dsg.addEdge(2, 1);
        Johnson j = new Johnson(dsg);
        j.findCircuits();
        System.err.println(j.circuits);
        assertTrue(j.circuits.size() == 2);
        assertTrue(j.circuits.get(0).contains(1));
        assertTrue(j.circuits.get(0).contains(3));
    }

    /**
     * Test for detection of strongly connected components.
     */

    @Test
    public void testTarjan3() throws Johnson.JohnsonIllegalStateException {
        MyDag<Integer> dsg = new MyDag<>();
        dsg.addEdge(1, 2);
        dsg.addEdge(2, 1);
        dsg.addEdge(2, 3);
        dsg.addEdge(2, 4);
        dsg.addEdge(4, 2);
        MyDag<Integer> leastScc = Johnson.leastSCC(dsg);
        System.err.println(leastScc);
        assertTrue(leastScc.getVertices().contains(1));
        assertTrue(leastScc.getVertices().contains(2));
        assertTrue(leastScc.getVertices().contains(4));
        assertTrue(leastScc.getVertexCount() == 3);
    }
    

    @Test
    public void testLargeGraph() throws Johnson.JohnsonIllegalStateException {
        MyDag<Integer> dg = new MyDag<>();
        Random r = new Random();
        int nodes = 30;
        int edges = 50;
        while (edges > 0) {
            int from = r.nextInt(nodes) + 1;
            int to = r.nextInt(nodes) + 1;
            if (! dg.hasEdge(from, to)) {
                dg.addEdge(from, to);
                edges--;
            }
        }
        System.err.println("edges: " + dg.getEdgeCount());
        System.err.println(dg);
        Johnson j = new Johnson(dg);
        j.findCircuits();
        System.err.println(j.circuits.size());
    }
}
