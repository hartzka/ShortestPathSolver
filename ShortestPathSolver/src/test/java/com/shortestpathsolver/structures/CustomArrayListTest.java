package com.shortestpathsolver.structures;

import com.shortestpathsolver.domain.Node;
import javafx.scene.paint.Color;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author kaihartz
 */
public class CustomArrayListTest {

    private CustomArrayList<Node> list;

    @Before
    public void setUp() {
        list = new CustomArrayList<>();
    }

    @Test
    public void exists() {
        assertTrue(list != null);
    }

    @Test
    public void ArrayIndexOutOfBoundsExceptionWhenInserting() {
        try {
            list.add(list.size()+1, new Node(0, 0, Color.CORAL));
            fail("Expected an ArrayIndexOutOfBoundsException to be thrown");
        } catch (ArrayIndexOutOfBoundsException e) {
            assertTrue(e.getMessage().equals("ArrayIndexOutOfBounds"));
        }
    }
    
    @Test
    public void ArrayIndexOutOfBoundsExceptionWhenRemoving() {
        list.add(0, new Node(0, 0, Color.CORAL));
        try {
            list.remove(list.size()+1);
            fail("Expected an ArrayIndexOutOfBoundsException to be thrown");
        } catch (ArrayIndexOutOfBoundsException e) {
            assertTrue(e.getMessage().equals("ArrayIndexOutOfBounds"));
        }
    }
    
    @Test
    public void testInsertingAndRemoving() {
        Node n = new Node(1, 1, Color.AQUA);
        list.add(n);
        for (int i = 1; i <= 200; i++) {
            list.add(new Node(i, i, Color.CORAL));
        }
        assertTrue(list.size() == 201);
        list.remove(n);
        assertTrue(list.size() == 200);
        list.clear();
        assertTrue(list.size() == 0);
    }
    
    @Test
    public void testListIsIncreased() {
        for (int i = 0; i <= 100; i++) {
            list.add(i, new Node(i, i, Color.CORAL));
        }
        assertTrue(list.size() == 101);
    }
    
    @Test
    public void testContains() {
        Node n = new Node(1, 1, Color.AQUA);
        for (int i = 0; i <= 100; i++) {
            list.add(i, new Node(i, i, Color.CORAL));
        }
        assertTrue(list.contains(n));
        assertFalse(list.contains(new Node(5, 99, Color.AZURE)));
    }
}
