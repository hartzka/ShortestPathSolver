package com.shortestpathsolver.performance;

import com.shortestpathsolver.algorithms.AStar;
import com.shortestpathsolver.algorithms.BFS;
import com.shortestpathsolver.algorithms.Dijkstra;
import com.shortestpathsolver.domain.Node;
import com.shortestpathsolver.domain.ShortestRoute;
import java.util.Random;
import org.junit.Before;
import org.junit.Test;

public class PerformanceTest {

    private AStar aStar;
    private Dijkstra dijkstra;
    private BFS bfs;
    private ShortestRoute sr;
    private boolean test;
    private boolean testTimeOrNodes;

    @Before
    public void setUp() {
        sr = new ShortestRoute();
        aStar = new AStar(sr);
        dijkstra = new Dijkstra(sr);
        bfs = new BFS(sr);
        test = false; // set this to false to disable testing! True = enabled
        testTimeOrNodes = true; // true = test time, false = test processed nodes
    }

    private String testPerformance(int rows, int method) { //0 = A*, 1 = Jps, 2 = Dijkstra, 3 = BFS
        sr.updateRowsAndCols(rows);
        int cols = sr.getCols();
        long processingTimeTotal = 0;
        long preProcessingTimeTotal = 0;
        long processedNodes = 0;
        int n = 1000;
        if (!testTimeOrNodes) {
            n /= 10;
        }
        Random r = new Random();
        for (int i = 0; i < n; i++) {
            long t1 = System.nanoTime();
            sr.setInitialNode(new Node(r.nextInt(cols), r.nextInt(rows)));
            while (true) {
                int x = r.nextInt(cols);
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
                processedNodes += aStar.getClosedSet().size();
            } else if (method == 2) {
                dijkstra.calculatePath(sr.getInitialNode());
                processedNodes += dijkstra.getClosedSet().size();
            } else if (method == 3) {
                bfs.calculatePath(sr.getInitialNode());
                processedNodes += bfs.getClosedSet().size();
            }

            long t3 = System.nanoTime();
            preProcessingTimeTotal += t2 - t1;
            processingTimeTotal += t3 - t2;
            if (!testTimeOrNodes && (method == 0 || method == 1)) {
                aStar.getClosedSet().clear();
            }
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
        } else if (method == 3) {
            s += "BFS  ";
        }
        if (testTimeOrNodes) {
            s += "running time average: " + processingTime / 1000 + " ms\n";
        } else {
            s += "processed nodes average: " + processedNodes / n + "\n";
        }
        return s;
    }

    @Test
    public void testPerformanceAStar() {
        if (test) {
            System.out.println("***********************************************************************************");
            System.out.println("\nA*:\n");
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

    @Test
    public void testPerformanceBfs() {
        if (test) {
            System.out.println("***********************************************************************************");
            System.out.println("\nBFS:\n");
            System.out.println("Testing.......... this takes a moment");
            for (int i = 9; i < 100; i += 15) {
                System.out.println(testPerformance(i, 3)); //comment to disable output
            }
        }
    }
}
