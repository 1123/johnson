package jgraphalgos.tarjan;

import java.util.*;

/**
From Wikipedia (http://en.wikipedia.org/wiki/Tarjan%27s_strongly_connected_components_algorithm):
algorithm tarjan is
  input: graph G = (V, E)
  output: set of strongly connected components (sets of vertices)

  index := 0
  S := empty
  for each v in V do
    if (v.index is undefined) then
      strongconnect(v)
    end if
  repeat
  **/


public class Tarjan<N> {

	int index;
	MyDag<N> dg;
	Stack<N> stack;
	Map<N, Integer> indexMap;
	Map<N, Integer> lowLinkMap;

	public List<List<N>> tarjan() {
		this.index = 0;
		stack = new Stack<N>();
		List<List<N>> result = new ArrayList<List<N>>();
		for (N v : this.dg.getVertices()) {
			if (indexMap.get(v) == null) {
				result.addAll(this.strongConnect(v));
			}
		}
		return result;
	}

	public Tarjan(MyDag<N> dg) {
		this.index = 0;
		this.dg = dg;
		this.indexMap = new HashMap<N, Integer>();
		this.lowLinkMap = new HashMap<N, Integer>();
	}
	
	public List<List<N>> strongConnect(N v) {
		indexMap.put(v, index);
		lowLinkMap.put(v, index);
		index++;
		stack.push(v);
		for (N w : dg.getSuccessors(v)) {
			if (indexMap.get(w) == null) {
				strongConnect(w);
				lowLinkMap.put(v, Math.min(lowLinkMap.get(v), lowLinkMap.get(w)));
			} else {
				if (stack.contains(w)) {
					lowLinkMap.put(v, Math.min(lowLinkMap.get(v), indexMap.get(w)));
				}
			}
		}
		
		List<List<N>> result = new ArrayList<List<N>>();
		if (lowLinkMap.get(v).equals(indexMap.get(v))) {
			List<N> sccList = new ArrayList<N>();
			while (true) {
				N w = stack.pop();
				sccList.add(w);
				if (w.equals(v)) {
					break;
				}
			}
			if (sccList.size() > 1) { result.add(sccList); } // don't return trivial sccs in the form of single nodes.
		}
		return result;
	}
	
}

/*
function strongconnect(v)
// Set the depth index for v to the smallest unused index
v.index := index
v.lowlink := index
index := index + 1
S.push(v)

// Consider successors of v
for each (v, w) in E do
  if (w.index is undefined) then
    // Successor w has not yet been visited; recurse on it
    strongconnect(w)
    v.lowlink := min(v.lowlink, w.lowlink)
  else if (w is in S) then
    // Successor w is in stack S and hence in the current SCC
    v.lowlink := min(v.lowlink, w.index)
  end if
repeat

// If v is a root node, pop the stack and generate an SCC
if (v.lowlink = v.index) then
  start a new strongly connected component
  repeat
    w := S.pop()
    add w to current strongly connected component
  until (w = v)
  output the current strongly connected component
end if
end function *
*/
