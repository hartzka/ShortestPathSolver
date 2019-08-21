package com.shortestpathsolver.domain;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author kaihartz
 */
public class NodeTest {

    private Node n;

    @Before
    public void setUp() {
        n = new Node(10, 10);
    }

    @Test
    public void exists() {
        assertTrue(n != null);
    }

    @Test
    public void stringRepresentationWorks() {
        String s = n.toString();
        assertTrue(s.equals("row: 10 col: 10 g: 0 dist: 0"));
        n.setAStarInformation(new Node(1, 0), 10);
        s = n.toString();
        assertTrue(s.equals("row: 10 col: 10 g: 10 dist: 10 parent: row: 0 col: 1"));
    }
}
