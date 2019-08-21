package com.shortestpathsolver.algorithms;

import com.shortestpathsolver.domain.Node;
import com.shortestpathsolver.domain.ShortestRoute;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author kaihartz
 */
public class DijkstraTest {

    private ShortestRoute sr;
    private Dijkstra dijkstra;

    @Before
    public void setUp() {
        sr = new ShortestRoute();
        sr.setInitialNode(new Node(0, 0));
        sr.setFinalNode(new Node(39, 39));
        dijkstra = new Dijkstra(sr);
    }

    @Test
    public void exists() {
        assertTrue(dijkstra != null);
    }

    @Test
    public void dijkstraPathIsReturned() {
        for (int i = 1; i < 39; i++) {
            sr.setBlock(i, (int) (Math.random() * 39));
        }
        sr.handleDijkstraButtonActions();
        assertTrue(sr.handleGetPathButtonActions() == true);
    }

    @Test
    public void dijkstraPathIsNotReturnedWhenDoesNotExist() {
        sr.setBlock(1, 0);
        sr.setBlock(1, 1);
        sr.setBlock(0, 1);
        sr.handleDijkstraButtonActions();
        assertTrue(sr.handleGetPathButtonActions() == false);
    }
}
