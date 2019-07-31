package tests;

import com.shortestpathsolver.domain.Node;
import com.shortestpathsolver.domain.ShortestRoute;
import javafx.scene.paint.Color;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author kaihartz
 */
public class ShortestRouteTest {

    private ShortestRoute sr;

    @Before
    public void setUp() {
        sr = new ShortestRoute();
        sr.setBlock(5, 5);
        sr.setNodes();
        sr.setInitialNode(new Node(0, 0, Color.CORAL));
        sr.setFinalNode(new Node(20, 20, Color.CORAL));
    }

    @Test
    public void exists() {
        assertTrue(sr != null);
    }

    @Test
    public void isStartNode() {
        assertTrue(sr.isNotStartOrGoalNode(0, 0) == false);
    }

    @Test
    public void isFinalNode() {
        assertTrue(sr.isNotStartOrGoalNode(20, 20) == false);
    }

    @Test
    public void isNotStartOrFinalNodeWorks() {
        assertTrue(sr.isNotStartOrGoalNode(2, 3) == true);
    }

    @Test
    public void setsWrited() {
        sr.setWrited(true);
        assertTrue(sr.getWrited() == true);
    }

    @Test
    public void setBlockWorks() {
        sr.setBlock(5, 6);
        assertTrue(sr.getBlocks()[5][6] == true);
        assertTrue(sr.getNodes()[5][6].isBlock());
    }

    @Test
    public void initialNodeIsSetCorrectly() {
        Node n = sr.getInitialNode();
        assertTrue(n.getColumn() == 0 && n.getRow() == 0);
    }

    @Test
    public void searchAreaIsSetAndNodesAreSaved() {
        Node[][] n = new Node[2][2];
        Node test = new Node(0, 0, Color.CORAL);
        test.setH(10);
        test.setRow(8);
        test.setColumn(9);
        n[0][0] = test;
        sr.setSearchArea(n);
        Node saved = sr.getNodes()[0][0];
        assertTrue(saved.getColumn() == 9 && saved.getRow() == 8 && saved.getH() == 10);
    }

}
