package com.shortestpathsolver.performance;

import com.shortestpathsolver.algorithms.AStar;
import com.shortestpathsolver.algorithms.Dijkstra;
import com.shortestpathsolver.domain.Node;
import com.shortestpathsolver.domain.ShortestRoute;
import java.util.Random;
import org.junit.Before;
import org.junit.Test;

public class PerformanceTest {

    private AStar aStar;
    private Dijkstra dijkstra;
    private ShortestRoute sr;
    private boolean test;

    @Before
    public void setUp() {
        sr = new ShortestRoute();
        aStar = new AStar(sr);
        dijkstra = new Dijkstra(sr);
        test = true; // set this to false to disable testing!
    }

    private String testPerformance(int rows, int method) { //0 = A*, 1 = Jps, 2 = Dijkstra
        sr.updateRowsAndCols(rows);
        long processingTimeTotal = 0;
        long preProcessingTimeTotal = 0;
        int n = 1000;
        Random r = new Random();
        for (int i = 0; i < n; i++) {
            long t1 = System.nanoTime();
            sr.setInitialNode(new Node(r.nextInt(rows), r.nextInt(rows)));
            while (true) {
                int x = r.nextInt(rows);
                int y = r.nextInt(rows);
                if (sr.isNotInitialOrFinalNode(x, y)) {
                    sr.setFinalNode(new Node(x, y));
                    break;
                }
            }
            sr.randomizeBlocks();

            long t2 = System.nanoTime();

            if (method == 0 || method == 1) {
                aStar.calculatePath(sr.getInitialNode());
            } else if (method == 2) {
                dijkstra.calculatePath(sr.getInitialNode());
            }

            long t3 = System.nanoTime();
            preProcessingTimeTotal += t2 - t1;
            processingTimeTotal += t3 - t2;
        }
        long preProcessingTime = preProcessingTimeTotal / n;
        long processingTime = processingTimeTotal / n;
        String s = "rows: " + rows + " preprosessing time average: " + preProcessingTime / 1000 + " ms\n";
        if (method == 0) {
            s += "A* ";
        } else if (method == 1) {
            s += "JPS ";
        } else if (method == 2) {
            s += "Dijkstra ";
        }
        s += "running time average: " + processingTime / 1000 + " ms";
        return s;
    }

    @Test
    public void testPerformanceAStar() {
        if (test) {
            System.out.println("***********************************************************************************");
            System.out.println("Testing.......... this takes a moment");
            for (int i = 9; i < 100; i += 15) {
                System.out.println(testPerformance(i, 0)); //comment to disable output
            }
        }
    }

    @Test
    public void testPerformanceJps() {
        if (test) {
            System.out.println("***********************************************************************************");
            aStar.setJPS(true);
            System.out.println("\nJPS-search:\n");
            System.out.println("Testing.......... this takes a moment");
            for (int i = 9; i < 100; i += 15) {
                System.out.println(testPerformance(i, 1)); //comment to disable output
            }
        }
    }

    @Test
    public void testPerformanceDijkstra() {
        if (test) {
            System.out.println("***********************************************************************************");
            System.out.println("\nDijkstra:\n");
            System.out.println("Testing.......... this takes a moment");
            for (int i = 9; i < 100; i += 15) {
                System.out.println(testPerformance(i, 2)); //comment to disable output
            }
        }
    }
}
