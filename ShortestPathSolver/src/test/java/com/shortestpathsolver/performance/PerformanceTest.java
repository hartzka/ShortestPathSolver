package com.shortestpathsolver.performance;

import com.shortestpathsolver.algorithms.AStar;
import com.shortestpathsolver.domain.Node;
import com.shortestpathsolver.domain.ShortestRoute;
import java.util.Random;
import org.junit.Before;
import org.junit.Test;

public class PerformanceTest {

    private AStar aStar;
    private ShortestRoute sr;

    @Before
    public void setUp() {
        sr = new ShortestRoute();
        aStar = new AStar(sr);
    }

    @Test
    public void testPerformanceAStar() {
        long processingTimeTotal = 0;
        long preProcessingTimeTotal = 0;
        int n = 1000;
        Random r = new Random();
        for (int i = 0; i < n; i++) {
            long t1 = System.nanoTime();
            sr.setInitialNode(new Node(r.nextInt(40), r.nextInt(40)));
            while (true) {
                int x = r.nextInt(40);
                int y = r.nextInt(40);
                if (sr.isNotStartOrGoalNode(x, y)) {
                    sr.setFinalNode(new Node(x, y));
                    break;
                }
            }
            int range = r.nextInt(300);
            for (int j = 0; j < range; j++) {
                sr.setBlock(r.nextInt(40), r.nextInt(40));
            }
            
            long t2 = System.nanoTime();
            
            aStar.calculatePath(sr.getInitialNode());
            
            long t3 = System.nanoTime();
            preProcessingTimeTotal += t2 - t1;
            processingTimeTotal += t3 - t2;
        }
        long preProcessingTime = preProcessingTimeTotal / n;
        long processingTime = processingTimeTotal / n;
        System.out.println("A* preprosessing time average: " + preProcessingTime + " ns");
        System.out.println("A* running time: " + processingTime + " ns");
    }
    
    @Test
    public void testPerformanceAStarJps() {
        aStar.setJPS(true);
        System.out.println("\nJPS-search:");
        testPerformanceAStar();
    }
}
