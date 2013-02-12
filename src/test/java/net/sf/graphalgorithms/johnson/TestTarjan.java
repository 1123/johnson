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

}
