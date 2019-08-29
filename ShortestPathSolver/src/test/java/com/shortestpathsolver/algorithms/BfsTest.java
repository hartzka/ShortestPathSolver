
package com.shortestpathsolver.algorithms;

import com.shortestpathsolver.domain.Node;
import com.shortestpathsolver.domain.ShortestRoute;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class BfsTest {
    private ShortestRoute sr;
    private BFS bfs;

    @Before
    public void setUp() {
        sr = new ShortestRoute();
        sr.setInitialNode(new Node(0, 0));
        sr.setFinalNode(new Node(39, 39));
        bfs = new BFS(sr);
    }

    @Test
    public void exists() {
        assertTrue(bfs != null);
    }

    @Test
    public void bfsPathIsReturned() {
        for (int i = 1; i < 39; i++) {
            sr.setBlock(i, (int) (Math.random() * 39));
        }
        sr.handleBfsButtonActions();
        assertTrue(sr.handleCalculatePathButtonActions() == true);
    }

    @Test
    public void bfsPathIsNotReturnedWhenDoesNotExist() {
        sr.setBlock(1, 0);
        sr.setBlock(1, 1);
        sr.setBlock(0, 1);
        sr.handleBfsButtonActions();
        assertTrue(sr.handleCalculatePathButtonActions() == false);
    }
}
