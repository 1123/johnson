package net.sf.graphalgorithms.johnson;

import static org.junit.Assert.*;

import java.util.List;

import net.sf.graphalgorithms.tarjan.Tarjan;

import org.junit.Test;

import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.DirectedSparseGraph;

/**
 * Unit test for simple App.
 */

public class TestTarjan {

	@Test
	public void test1() {
		DirectedGraph<Integer, WeightedEdge> dg = new DirectedSparseGraph<Integer, WeightedEdge>();
		dg.addEdge(new WeightedEdge(1.0f), 1, 2);
		dg.addEdge(new WeightedEdge(1.0f), 2, 1);
		Tarjan<Integer, WeightedEdge> t = new Tarjan<Integer, WeightedEdge>(dg);
		List<List<Integer>> sccs = t.tarjan();
		assertTrue(sccs.size() == 1);
		assertTrue(sccs.get(0).contains(1));
		assertTrue(sccs.get(0).contains(2));
	}

	@Test
	public void test2() {
		// TODO: should sccs of size 1 be returned? Seems like some are returned, others not.
		DirectedGraph<Integer, WeightedEdge> dg = new DirectedSparseGraph<Integer, WeightedEdge>();
		dg.addEdge(new WeightedEdge(1.0f), 5, 6);
		Tarjan<Integer, WeightedEdge> t = new Tarjan<Integer, WeightedEdge>(dg);
		List<List<Integer>> sccs = t.tarjan();
		assertTrue(sccs.size() == 0);
	}

	@Test
	public void test3() {
		// TODO: should sccs of size 1 be returned? Seems like some are returned, others not.
		DirectedGraph<Integer, WeightedEdge> dg = new DirectedSparseGraph<Integer, WeightedEdge>();
		dg.addEdge(new WeightedEdge(1.0f), 5, 6);
		dg.addEdge(new WeightedEdge(1.0f), 6, 7);
		dg.addEdge(new WeightedEdge(1.0f), 7, 8);
		dg.addEdge(new WeightedEdge(1.0f), 8, 5);
		Tarjan<Integer, WeightedEdge> t = new Tarjan<Integer, WeightedEdge>(dg);
		List<List<Integer>> sccs = t.tarjan();
		assertTrue(sccs.size() == 1);
		assertTrue(sccs.get(0).contains(5));
		assertTrue(sccs.get(0).contains(6));
		assertTrue(sccs.get(0).contains(7));
		assertTrue(sccs.get(0).contains(8));
	}

	@Test
	public void test4() {
		// TODO: should sccs of size 1 be returned? Seems like some are returned, others not.
		DirectedGraph<Integer, WeightedEdge> dg = new DirectedSparseGraph<Integer, WeightedEdge>();
		dg.addEdge(new WeightedEdge(1.0f), 5, 6);
		dg.addEdge(new WeightedEdge(1.0f), 6, 5);
		dg.addEdge(new WeightedEdge(1.0f), 7, 8);
		dg.addEdge(new WeightedEdge(1.0f), 8, 7);
		Tarjan<Integer, WeightedEdge> t = new Tarjan<Integer, WeightedEdge>(dg);
		List<List<Integer>> sccs = t.tarjan();
		assertTrue(sccs.size() == 2);
		assertTrue(sccs.get(0).contains(5));
		assertTrue(sccs.get(0).contains(6));
		assertTrue(sccs.get(1).contains(7));
		assertTrue(sccs.get(1).contains(8));
	}
	
	
}
