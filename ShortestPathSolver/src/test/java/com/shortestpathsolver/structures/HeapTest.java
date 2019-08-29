package com.shortestpathsolver.structures;

import com.shortestpathsolver.domain.Node;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author kaihartz
 */
public class HeapTest {

    private Heap heap;

    @Before
    public void setUp() {
        heap = new Heap();
    }

    @Test
    public void exists() {
        assertTrue(heap != null);
    }

    @Test
    public void testInsertingAndRemoving() {
        Node n = new Node(1, 1);
        heap.add(n);
        for (int i = 1; i <= 200; i++) {
            heap.add(new Node(i, i));
        }
        assertTrue(heap.size() == 201);
        heap.poll();
        assertTrue(heap.size() == 200);
        heap.clear();
        assertTrue(heap.size() == 0);
    }

    @Test
    public void testListIsIncreased() {
        for (int i = 0; i <= 100; i++) {
            heap.add(new Node(i, i));
        }
        assertTrue(heap.size() == 101);
    }

    @Test
    public void testContains() {
        Node n = new Node(1, 1);
        for (int i = 0; i <= 100; i++) {
            heap.add(new Node(i, i));
        }
        assertTrue(heap.contains(n));
        assertFalse(heap.contains(new Node(5, 99)));
    }
}
