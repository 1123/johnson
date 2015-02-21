package jgraphalgos.johnson;

import jgraphalgos.tarjan.MyDag;
import jgraphalgos.tarjan.Tarjan;

import java.util.*;

public class Johnson {

    Map<Integer, Boolean> blocked;
    Map<Integer, List<Integer>> blockedNodes;
    List<Stack<Integer>> circuits;
    MyDag<Integer> dg;

    public Johnson(MyDag<Integer> dg) {
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

    public boolean circuit(MyDag<Integer> dg, Integer v, Integer s, Stack<Integer> stack) throws JohnsonIllegalStateException {
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

    /**
     * Find the graph for the strongly connected component with the smallest vertex.
     * @param dg the entire DAG
     * @return the graph of the srongly connected component with the least vertex.
     * @throws JohnsonIllegalStateException
     */

    public static MyDag<Integer> leastSCC(MyDag<Integer> dg) throws JohnsonIllegalStateException {
        Tarjan<Integer> t = new Tarjan<>(dg);
    	List<List<Integer>> sccs = t.tarjan();
        Integer min = Integer.MAX_VALUE;
        List<Integer> minScc = new ArrayList<>();
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

    public Integer leastVertex(MyDag<Integer> in) {
        Integer result = Integer.MAX_VALUE;
        for (Integer i : in.getVertices()) {
            if (i < result) {
                result = i;
            }
        };
        return result;
    }

    private static MyDag<Integer> addEdges(List<Integer> list, MyDag<Integer> dg) throws JohnsonIllegalStateException {
        if (list == null) { throw new JohnsonIllegalStateException(); }
        if (dg == null) { throw new JohnsonIllegalStateException(); }
        MyDag<Integer> result = new MyDag<Integer>();
        for (Integer from : list) {
            for (Integer to : dg.getSuccessors(from)) {
                if (list.contains(to)) { // only add the edge, if the sink is also part of the SCC.
                    result.addEdge(from, to);
                }
            }
        }
        return result;
    }

    public static MyDag<Integer> subGraphFrom(Integer i, MyDag<Integer> in) {
        MyDag<Integer> result = new MyDag<Integer>();
        for (Integer from : in.getVertices()) {
            if (from >= i) {
                for (Integer to : in.getSuccessors(from)) {
                    if (to >= i) {
                        result.addEdge(from, to);
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
            MyDag<Integer> subGraph = subGraphFrom(s,dg);
            MyDag<Integer> leastScc = leastSCC(subGraph);
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
