package com.shortestpathsolver.domain;

import com.shortestpathsolver.structures.CustomArrayList;
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
        sr.setInitialNode(new Node(0, 0));
        sr.setFinalNode(new Node(20, 20));
    }

    @Test
    public void exists() {
        assertTrue(sr != null);
    }

    @Test
    public void isStartNode() {
        assertTrue(sr.isNotInitialOrFinalNode(0, 0) == false);
    }

    @Test
    public void isFinalNode() {
        assertTrue(sr.isNotInitialOrFinalNode(20, 20) == false);
    }

    @Test
    public void isNotStartOrFinalNodeWorks() {
        assertTrue(sr.isNotInitialOrFinalNode(2, 3) == true);
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
        sr.setInitialNode(new Node(20, 10));
        assertTrue(sr.getStartX() == 20);
        assertTrue(sr.getStartY() == 10);
    }

    @Test
    public void finalNodeIsSetCorrectly() {
        sr.setFinalNode(new Node(20, 10));
        assertTrue(sr.getGoalX() == 20);
        assertTrue(sr.getGoalY() == 10);
    }

    @Test
    public void searchAreaIsSetAndNodesAreSaved() {
        Node[][] n = new Node[2][2];
        Node test = new Node(0, 0);
        test.setH(10);
        test.setRow(8);
        test.setColumn(9);
        n[0][0] = test;
        sr.setSearchArea(n);
        Node saved = sr.getNodes()[0][0];
        assertTrue(saved.getColumn() == 9 && saved.getRow() == 8 && saved.getH() == 10);
    }

    @Test
    public void testClearButtonActions() {
        sr.handleClearButtonActions();
        assertTrue(sr.getInserting() == false && sr.getWrited() == false);
    }

    @Test
    public void testClearAllButtonActions() {
        sr.handleClearAllButtonActions();
        assertTrue(sr.getInserting() == true && sr.getWrited() == false);
    }

    @Test
    public void testInsertButtonActions() {
        sr.handleInsertButtonActions();
        assertTrue(sr.getInserting() == true);
    }

    @Test
    public void testRemoveBlock() {
        sr.setBlock(4, 4);
        assertTrue(sr.getBlocks()[4][4] == true);
        sr.removeBlock(4, 4);
        assertTrue(sr.getBlocks()[4][4] == false);
    }

    @Test
    public void testMouse() {
        sr.handleMouseAction(0, 0);
        assertTrue(sr.getInitialNodeMoving() == true);
        sr.handleMouseAction(30, 30);
        assertTrue(sr.getInitialNode().getColumn() == 0);
        sr.setNodesMovementsOff();

        sr.handleMouseAction(20 * 20, 20 * 20);
        assertTrue(sr.getFinalNodeMoving() == true);
        sr.handleMouseAction(80, 80);
        sr.setNodesMovementsOff();

        sr.handleMouseAction(30 * 20, 30 * 20);
        sr.setInserting(false);
        sr.handleMouseAction(5 * 20, 5 * 20);
    }

    @Test
    public void testAnimation1() {
        CustomArrayList<Node> cs = new CustomArrayList<>();
        cs.add(new Node(3, 3));
        cs.add(new Node(4, 44));
        sr.getAStar().setClosedSet(cs);

        CustomArrayList<Node> path = new CustomArrayList<>();
        path.add(new Node(0, 44));
        path.add(new Node(4, 44));

        sr.handleAnimation();
        sr.handleAnimation();
        assertTrue(sr.getPathDrawing() == false);
        sr.handleAnimation();
        assertTrue(sr.getPathDrawing() == true);
        sr.handleAnimation();
        assertTrue(sr.getPathVisualize() == false);
    }
    
    @Test
    public void testAnimation2() {
        sr.handleBfsButtonActions();
        testAnimation1();
    }
    
    @Test
    public void testAnimation3() {
        sr.handleDijkstraButtonActions();
        testAnimation1();
    }

    @Test
    public void testPathLineFilling() {
        sr.handleAStarButtonActions();
        sr.fillPathLine();
        assertTrue(sr.getPrevNode() == null);

        sr.handleDijkstraButtonActions();
        sr.fillPathLine();
        assertTrue(sr.getPrevNode() == null);

        sr.handleBfsButtonActions();
        sr.fillPathLine();
        assertTrue(sr.getPrevNode() == null);
    }

    @Test
    public void toggleJps() {
        sr.handleJpsButtonActions();
        assertTrue(sr.getAStar().getJps() == true);
        sr.handleAStarButtonActions();
        assertTrue(sr.getAStar().getJps() == false);
    }
    
    @Test
    public void testBackgroundChange() {
        sr.setBackGround(Color.BLUE);
        assertTrue(sr.getBgColor() == Color.BLUE);
        sr.handleAStarButtonActions();
        assertTrue(sr.getAStar().getJps() == false);
    }

    @Test
    public void testDijkstraButtonActions() {
        sr.handleDijkstraButtonActions();
        assertTrue(sr.getDijkstraInUse().get() == true);
    }

    @Test
    public void testBfsButtonActions() {
        sr.handleBfsButtonActions();
        assertTrue(sr.getBfsInUse().get() == true);
    }
    
    @Test
    public void testAStarButtonActions() {
        sr.handleAStarButtonActions();
        assertTrue(sr.getAStarInUse().get() == true);
    }

    @Test
    public void testJpsButtonActions() {
        sr.handleJpsButtonActions();
        assertTrue(sr.getJpsInUse().get() == true);
    }
    
    @Test
    public void testSetButtonsDisable() {
        assertTrue(sr.getButtonsDisable().get() == false);
        sr.setButtonsDisable(true);
        assertTrue(sr.getButtonsDisable().get() == true);
    }

    @Test
    public void updateRowsAndColsTest() {
        sr.updateRowsAndCols(80);
        assertTrue(sr.getRows() == 80);
        assertTrue(sr.getCols() == 100);
    }

    @Test
    public void getContentTest() {
        sr.initializeBlocks();
        String s = sr.getContent();
        assertFalse(s.contains("@"));
        sr.randomizeBlocks();
        s = sr.getContent();
        assertTrue(s.contains("@"));
    }
}
