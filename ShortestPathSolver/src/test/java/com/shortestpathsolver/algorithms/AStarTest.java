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
public class AStarTest {

    private ShortestRoute sr;
    private AStar aStar;

    @Before
    public void setUp() {
        sr = new ShortestRoute();
        sr.setInitialNode(new Node(0, 0));
        sr.setFinalNode(new Node(39, 39));
        aStar = sr.getAStar();
        aStar.setJPS(false);
    }

    @Test
    public void exists() {
        assertTrue(aStar != null);
    }

    @Test
    public void aStarPathIsReturned() {
        for (int i = 1; i < 39; i++) {
            sr.setBlock(i, (int) (Math.random() * 39));
        }
        assertTrue(sr.handleCalculatePathButtonActions() == true);
    }
    
    @Test
    public void aStarJPSPathIsReturned() {
        aStar.setJPS(true);
        for (int i = 1; i < 39; i++) {
            sr.setBlock(i, (int) (Math.random() * 39));
        }
        assertTrue(sr.calculateAStarPath() == true);
    }

    @Test
    public void aStarPathIsNotReturnedWhenDoesNotExist() {
        sr.setBlock(1, 0);
        sr.setBlock(1, 1);
        sr.setBlock(0, 1);
        assertTrue(sr.handleCalculatePathButtonActions() == false);
    }

    @Test
    public void isReseted() {
        sr.resetAStar();
        assertTrue(aStar.getOpenList().size() == 0);
        assertTrue(aStar.getClosedSet().isEmpty());
        assertTrue(aStar.getPath().isEmpty());
    }
}
