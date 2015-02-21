package jgraphalgos.johnson.performance;

import jgraphalgos.johnson.Johnson;
import jgraphalgos.tarjan.MyDag;
import org.junit.Test;

import java.util.Random;

public class PerformanceTest {

    @Test
    public void test1() throws Johnson.JohnsonIllegalStateException {
        measure(100, 170);
        measure(100, 180);
        measure(100, 190);
        measure(100, 195);
        measure(100, 197);
        measure(100, 200);
        measure(100, 202);
        measure(100, 203);
        measure(100, 204);
        measure(100, 205);
        measure(100, 206);
        measure(100, 207);
        measure(100, 208);
        measure(100, 209);
        measure(100, 210);
        measure(100, 211);
        measure(100, 212);
        measure(100, 213);
        measure(100, 214);
        measure(100, 215);
        measure(100, 216);
        measure(100, 215);
        measure(100, 217);
        measure(100, 218);
        measure(100, 219);
        measure(100, 220);
        measure(100, 222);
        measure(100, 225);
    }

    private void measure(int nodes, int edges) throws Johnson.JohnsonIllegalStateException {
        System.err.println(String.format("Nodes: %d, edges: %d", nodes, edges));
        MyDag<Integer> dg = new MyDag<>();
        Random r = new Random();
        long startConstruct = System.currentTimeMillis();
        for (int i = 0; i < edges; i++) {
            inner: while (true) {
                int from = r.nextInt(nodes);
                int to = r.nextInt(nodes);
                if (!dg.hasEdge(from, to)) {
                    dg.addEdge(from, to);
                    break inner;
                }
            }
        }
        Johnson j = new Johnson(dg);
        long endConstruct = System.currentTimeMillis();
        System.err.println("Constructing sample graph : " + (endConstruct - startConstruct));
        long start = System.currentTimeMillis();
        j.findCircuits();
        long end = System.currentTimeMillis();
        System.err.println("Finding circuits: " + (end - start));
    }
}

