package jgraphalgos.tarjan;

import java.util.*;

public class HashMapDag<N> {

    Map<N, List<N>> forward = new HashMap<>();
    Map<N, List<N>> backward = new HashMap<>();

    public void addEdge(N from, N to) {
        if (! this.forward.containsKey(from)) {
            this.forward.put(from, new ArrayList<N>());
        }
        if (this.forward.get(from).contains(to)) { throw new RuntimeException("This edge already exists."); }
        this.forward.get(from).add(to);
        if (! this.backward.containsKey(to)) {
            this.backward.put(to, new ArrayList<N>());
        }
        this.backward.get(to).add(from);
    }

    public int getVertexCount() {
        return this.getVertices().size();
    }

    public Set<N> getVertices() {
        Set<N> result = new HashSet<N>(this.forward.keySet());
        result.addAll(this.backward.keySet());
        return result;
    }

    public List<N> getSuccessors(N v) {
        return (this.forward.get(v) == null) ? new ArrayList<N>() : this.forward.get(v);
    }

    public boolean containsVertex(N i) {
        return this.forward.containsKey(i) || this.backward.containsKey(i);
    }

    public int getEdgeCount() {
        return this.forward.keySet().stream().map(
                e -> this.forward.get(e).size()
        ).mapToInt(
                e -> e
        ).sum();
    }

    public boolean hasEdge(N from, N to) {
        return this.forward.get(from) != null && this.forward.get(from).contains(to);
    }
}
