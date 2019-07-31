package tests;

import com.shortestpathsolver.domain.Node;
import javafx.scene.paint.Color;
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
        n = new Node(10, 10, Color.CORAL);
    }

    @Test
    public void exists() {
        assertTrue(n != null);
    }

    @Test
    public void stringRepresentationWorks() {
        String s = n.toString();
        assertTrue(s.equals("row: 10 col: 10"));
    }

    @Test
    public void betterPathIsChecked() {
        n.setG(100000);
        assertTrue(n.checkifBetterPathExistsAStar(new Node(0, 0, Color.CORAL), 10) == true);
    }
}
