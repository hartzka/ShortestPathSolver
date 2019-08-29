package com.shortestpathsolver.structures;

import com.shortestpathsolver.domain.Node;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author kaihartz
 */
public class QueueTest {

    private Queue<Node> queue;

    @Before
    public void setUp() {
        queue = new Queue<>();
    }

    @Test
    public void exists() {
        assertTrue(queue != null);
    }

    @Test
    public void testInsertingAndRemoving() {
        Node n = new Node(1, 1);
        queue.enqueue(n);
        for (int i = 1; i <= 200; i++) {
            queue.enqueue(new Node(i, i));
        }
        assertTrue(queue.size() == 201);
        queue.dequeue();
        assertTrue(queue.size() == 200);
        queue.clear();
        assertTrue(queue.size() == 0);
    }

    @Test
    public void testIsIncreased() {
        for (int i = 0; i <= 100; i++) {
            queue.enqueue(new Node(i, i));
        }
        assertTrue(queue.size() == 101);
    }
}
