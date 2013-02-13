package net.sf.graphalgorithms.johnson;

import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.DirectedSparseGraph;

import java.util.*;

import net.sf.graphalgorithms.tarjan.Tarjan;

public class Johnson {

    Map<Integer, Boolean> blocked;
    Map<Integer, List<Integer>> blockedNodes;
    List<Stack<Integer>> circuits;
    DirectedGraph<Integer, WeightedEdge> dg;
    
    public Johnson(DirectedGraph<Integer, WeightedEdge> dg) {
        blocked = new HashMap<Integer, Boolean>();
        blockedNodes = new HashMap<Integer, List<Integer>>();
        circuits = new ArrayList<Stack<Integer>>();
        this.dg = dg;
    }
    
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
                this.circuits.add((Stack<Integer>) stack.clone());
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

    public static DirectedGraph<Integer, WeightedEdge> leastSCC(DirectedGraph<Integer,WeightedEdge> dg) throws JohnsonIllegalStateException {
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

    private static DirectedGraph<Integer, WeightedEdge> addEdges(List<Integer> list, DirectedGraph<Integer, WeightedEdge> dg) throws JohnsonIllegalStateException {
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

    public static DirectedGraph<Integer, WeightedEdge> subGraphFrom(Integer i, DirectedGraph<Integer, WeightedEdge> in) {
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
    
    public void findCircuits() throws JohnsonIllegalStateException {
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
