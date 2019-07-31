package com.shortestpathsolver.domain;

import com.shortestpathsolver.algorithms.AStar;
import com.shortestpathsolver.ui.Ui;
import java.util.List;
import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Sovelluksen logiikasta vastaava luokka
 *
 * @author kaihartz
 */
public class ShortestRoute extends Application {

    private AStar aStar;
    private boolean writed;
    private int startX;
    private int startY;
    private int goalX;
    private int goalY;
    private Ui ui;
    private Node initialNode;
    private Node finalNode;
    private boolean[][] blocks;
    private Node[][] nodes;
    private int rows;
    private int cols;
    private Node prevNode;

    public ShortestRoute() {
        startX = 0;
        startY = 0;
        goalX = 39;
        goalY = 39;
        writed = false;
        rows = 50;
        cols = 40;
        this.blocks = new boolean[rows][cols];
        this.nodes = new Node[rows][cols];
        this.initialNode = new Node(startX, startY, Color.RED);
        this.finalNode = new Node(goalX, goalY, Color.GREEN);
        this.aStar = new AStar(this);
        setNodes();
    }

    @Override
    public void start(Stage primaryStage) {
        ui = new Ui(this, 1001, 801, rows, cols, Color.LIGHTSKYBLUE, startX, startY, goalX, goalY);
        ui.start(primaryStage);
    }

    public void setWrited(boolean b) {
        this.writed = b;
    }

    /**
     * Hakee A*-algoritmilla lyhimmän reitin ja lähettää sen visualisoitavaksi.
     *
     * @return true, jos reitti löytyy, muuten false
     */
    public boolean calculateAStarPath() {
        List<Node> path = aStar.calculatePath(initialNode);
        visualizeAStarPath();
        if (path.isEmpty()) {
            return false;
        }
        return true;
    }

    public boolean getWrited() {
        return this.writed;
    }

    public AStar getAStar() {
        return this.aStar;
    }

    /**
     * Alustaa A*-algoritmiluokan.
     */
    public void resetAStar() {
        aStar.reset();
    }

    public Node getInitialNode() {
        return initialNode;
    }

    public void setInitialNode(Node initialNode) {
        this.initialNode = initialNode;
        this.startX = initialNode.getColumn();
        this.startY = initialNode.getRow();
    }

    public Node getFinalNode() {
        return finalNode;
    }

    public void setFinalNode(Node finalNode) {
        this.finalNode = finalNode;
        this.goalX = finalNode.getColumn();
        this.goalY = finalNode.getRow();
    }

    /**
     * Kertoo, onko solmu loppusolmu
     *
     * @param node Solmu
     * @return true, jos node on loppusolmu, muuten false
     */
    public boolean isFinalNode(Node node) {
        return node.equals(finalNode);
    }

    /**
     * Kertoo, onko tietyissä koordinaateissa sijaitseva solmu alku- tai
     * loppusolmu.
     *
     * @param x Solmun x-koordinaatti
     * @param y Solmun y-koordinaatti
     * @return true, jos koordinaateissa sijaitseva solmu ei ole alku- eikä
     * loppusolmu, muuten false
     */
    public boolean isNotStartOrGoalNode(int x, int y) {
        if (x == startX && y == startY) {
            return false;
        }
        if (x == goalX && y == goalY) {
            return false;
        }
        return true;
    }

    private void visualizeAStarPath() {
        List<Node> aStarPath = aStar.getPath();
        for (Node n : aStarPath) {
            if (prevNode == null) {
                prevNode = n;
                if (ui != null) {
                    ui.getCanvas().fillPathLine(initialNode.getRow(), initialNode.getColumn(), prevNode.getRow(), prevNode.getColumn());
                }
            } else {
                if (ui != null) {
                    ui.getCanvas().fillPathLine(prevNode.getRow(), prevNode.getColumn(), n.getRow(), n.getColumn());
                    prevNode = n;
                }
            }
        }
        prevNode = null;
    }

    /**
     * Alustaa solmujen taulukon
     */
    public void setNodes() {
        for (int i = 0; i < nodes.length; i++) {
            for (int j = 0; j < nodes[0].length; j++) {

                Node node = new Node(i, j, Color.WHITE);
                if (blocks[i][j]) {
                    node.setBlock(true);
                }
                node.calculateHeuristic(getFinalNode());
                this.nodes[i][j] = node;
            }
        }
    }

    public Node[][] getNodes() {
        return this.nodes;
    }

    /**
     * Asettaa esteen annettuihin koordinaatteihin.
     *
     * @param row Esteen rivi
     * @param col Esteen sarake
     */
    public void setBlock(int row, int col) {
        if (row >= 0 && col >= 0 && row < nodes.length && col < nodes[0].length) {
            this.nodes[row][col].setBlock(true);
            this.blocks[row][col] = true;
        }
    }

    public void setSearchArea(Node[][] searchArea) {
        this.nodes = searchArea;
    }

    public boolean[][] getBlocks() {
        return this.blocks;
    }
}
