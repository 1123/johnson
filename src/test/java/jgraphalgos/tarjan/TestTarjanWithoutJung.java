package jgraphalgos.tarjan;

import static org.junit.Assert.*;

import java.util.List;


import org.junit.Test;

/**
 * Unit test the Tarjan algorithm.
 */

public class TestTarjanWithoutJung {

    /**
     * Test for a binary strongly connected component.
     */

	@Test
	public void test1() {
		MyDag<Integer> dg = new MyDag<Integer>();
		dg.addEdge(1, 2);
		dg.addEdge(2, 1);
		Tarjan<Integer> t = new Tarjan<Integer>(dg);
		List<List<Integer>> sccs = t.tarjan();
		assertTrue(sccs.size() == 1);
		assertTrue(sccs.get(0).contains(1));
		assertTrue(sccs.get(0).contains(2));
	}

    /**
     * This test asserts that a graph with a sinlge edge does not contain strongly connected components.
     */

	@Test
	public void test2() {
		MyDag<Integer> dg = new MyDag<Integer>();
		dg.addEdge(5, 6);
		Tarjan<Integer> t = new Tarjan<>(dg);
		List<List<Integer>> sccs = t.tarjan();
		assertTrue(sccs.size() == 0);
	}

    /**
     * Test for discovery of a strongly connected component with 4 nodes (a cycle).
     */

	@Test
	public void test3() {
        MyDag<Integer> dg = new MyDag<Integer>();
		dg.addEdge(5, 6);
		dg.addEdge(6, 7);
		dg.addEdge(7, 8);
		dg.addEdge(8, 5);
		Tarjan<Integer> t = new Tarjan<Integer>(dg);
		List<List<Integer>> sccs = t.tarjan();
		assertTrue(sccs.size() == 1);
		assertTrue(sccs.get(0).contains(5));
		assertTrue(sccs.get(0).contains(6));
		assertTrue(sccs.get(0).contains(7));
		assertTrue(sccs.get(0).contains(8));
	}

    /**
     * Test for discovery of two binary strongly connected components.
     */

	@Test
	public void test4() {
        MyDag<Integer> dg = new MyDag<Integer>();
		dg.addEdge(5, 6);
		dg.addEdge(6, 5);
		dg.addEdge(7, 8);
		dg.addEdge(8, 7);
        Tarjan<Integer> t = new Tarjan<Integer>(dg);
		List<List<Integer>> sccs = t.tarjan();
		assertTrue(sccs.size() == 2);
		assertTrue(sccs.get(0).contains(5));
		assertTrue(sccs.get(0).contains(6));
		assertTrue(sccs.get(1).contains(7));
		assertTrue(sccs.get(1).contains(8));
	}
	
	
}
