package net.sf.graphalgorithms.johnson;

import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.DirectedSparseGraph;

import java.util.*;

import net.sf.graphalgorithms.tarjan.Tarjan;

/**
 * Created with IntelliJ IDEA.
 * User: benediktlinse
 * Date: 09.02.13
 * Time: 22:02
 * To change this template use File | Settings | File Templates.
 */
public class Johnson {

    static Map<Integer, Boolean> blocked = new HashMap<Integer, Boolean>();
    static Map<Integer, List<Integer>> blockedNodes = new HashMap<Integer, List<Integer>>();

    public void unblock(Integer u) {
        blocked.put(u, false);
        while (blockedNodes.get(u).size() > 0) {
            Integer w = blockedNodes.get(u).remove(0);
            if (blocked.get(w)) {
                unblock(w);
            }
        }
    }

    public boolean circuit(DirectedGraph<Integer, WeightedEdge> dg, Integer v, Integer s, Stack<Integer> stack) throws JohnsonIllegalStateException {
        if (dg == null) { throw new JohnsonIllegalStateException(); }
        if (dg.getVertexCount() == 0) { return false; }
        boolean f = false;
        stack.push(v);
        blocked.put(v, true);
        for (Integer w : dg.getSuccessors(v)) {
            if (w == s) {
                stack.push(s);
                System.err.println("found circuit: " + stack);
                stack.pop();
                f = true;
            }
            else {
                if (! blocked.get(w)) {
                    if (circuit(dg, w, s, stack)) { f = true; }
                }
            }
        }
        if (f) { unblock(v); }
        else {
            for (Integer w : dg.getSuccessors(v)) {
                if (! blockedNodes.get(w).contains(v)) {
                    blockedNodes.get(w).add(v);
                }
            }
        }
        stack.pop();
        return f;
    }

    public DirectedGraph<Integer, WeightedEdge> leastSCC(DirectedGraph<Integer,WeightedEdge> dg) throws JohnsonIllegalStateException {
        Tarjan<Integer, WeightedEdge> t = new Tarjan<Integer, WeightedEdge>(dg);
    	List<List<Integer>> sccs = t.tarjan();
        Integer min = Integer.MAX_VALUE;
        List<Integer> minScc = new ArrayList<Integer>();
        for (List<Integer> scc : sccs) {
            if (scc.size() == 1) { continue; }
            for (Integer i : scc) {
                if (i < min) {
                    minScc = scc;
                    min = i;
                }
            }
        }
        return addEdges(minScc, dg);
    }

    public Integer leastVertex(DirectedGraph<Integer, WeightedEdge> in) {
        Integer result = Integer.MAX_VALUE;
        for (Integer i : in.getVertices()) {
            if (i < result) {
                result = i;
            }
        };
        return result;
    }

    private DirectedGraph<Integer, WeightedEdge> addEdges(List<Integer> list, DirectedGraph<Integer, WeightedEdge> dg) throws JohnsonIllegalStateException {
        if (list == null) { throw new JohnsonIllegalStateException(); }
        if (dg == null) { throw new JohnsonIllegalStateException(); }
        DirectedGraph<Integer, WeightedEdge> result = new DirectedSparseGraph<Integer, WeightedEdge>();
        for (Integer i : list) {
            for (WeightedEdge edge : dg.getOutEdges(i)) {
                Integer to = dg.getOpposite(i, edge);
                if (list.contains(to)) {
                    result.addEdge(edge, i, to);
                }
            }
        }
        return result;
    }

    public DirectedGraph<Integer, WeightedEdge> subGraphFrom(Integer i, DirectedGraph<Integer, WeightedEdge> in) {
        DirectedGraph<Integer, WeightedEdge> result = new DirectedSparseGraph<Integer, WeightedEdge>();
        for (Integer from : in.getVertices()) {
            if (from >= i) {
                for (Integer to : in.getSuccessors(from)) {
                    if (to >= i) {
                        result.addEdge(in.findEdge(from, to), from, to);
                    }
                }
            }
        }
        return result;
    }


    
    public  void findCircuits(DirectedGraph<Integer, WeightedEdge> dg) throws JohnsonIllegalStateException {
        blocked = new HashMap<Integer, Boolean>();
        blockedNodes = new HashMap<Integer, List<Integer>>();
        Stack<Integer> stack = new Stack<Integer>();
        Integer s = 1;
        while (s < dg.getVertexCount()) {
            DirectedGraph<Integer, WeightedEdge> subGraph = subGraphFrom(s,dg);
            DirectedGraph<Integer, WeightedEdge> leastScc = leastSCC(subGraph);
            if (leastScc.getVertices().size() > 0) {
                s = leastVertex(leastScc);
                for (Integer i : leastScc.getVertices()) {
                    blocked.put(i, false);
                    blockedNodes.put(i, new ArrayList<Integer>());
                }
                boolean dummy = circuit(leastScc, s, s, stack);
                s++;
            }
            else {
                s = dg.getVertexCount();
            }
        }
    }

    public static class JohnsonIllegalStateException extends Throwable {
    }
}
