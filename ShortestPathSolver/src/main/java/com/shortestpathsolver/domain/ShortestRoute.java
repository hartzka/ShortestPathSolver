package com.shortestpathsolver.domain;

import com.shortestpathsolver.algorithms.AStar;
import com.shortestpathsolver.structures.CustomArrayList;
import com.shortestpathsolver.ui.Ui;
import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
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
    private boolean inserting;
    private Ui ui;
    private Node initialNode;
    private Node finalNode;
    private boolean[][] blocks;
    private Node[][] nodes;
    private int rows;
    private int cols;
    private Node prevNode;
    private boolean aStarOn;

    public ShortestRoute() {
        inserting = true;
        startX = 0;
        startY = 0;
        goalX = 30;
        goalY = 30;
        writed = false;
        rows = 40;
        cols = 50;
        this.blocks = new boolean[rows][cols];
        this.nodes = new Node[rows][cols];
        this.initialNode = new Node(startX, startY, Color.RED);
        this.finalNode = new Node(goalX, goalY, Color.GREEN);
        this.aStar = new AStar(this);
        this.aStarOn = true;
        setNodes();
    }

    @Override
    public void start(Stage primaryStage) {
        ui = new Ui(this, cols * 20 + 1, rows * 20 + 1, rows, cols, Color.LIGHTSKYBLUE, startX, startY, goalX, goalY);
        ui.start(primaryStage);
    }

    /**
     * Hakee A*-algoritmilla lyhimmän reitin ja lähettää sen visualisoitavaksi.
     *
     * @return true, jos reitti löytyy, muuten false
     */
    public boolean calculateAStarPath() {
        CustomArrayList<Node> path = aStar.calculatePath(initialNode);
        visualizeAStarPath();
        if (path.isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * Logiikka Clear -painikkeelle
     */
    public void handleClearButtonActions() {
        inserting = false;
        writed = false;
    }

    /**
     * Logiikka Clear All -painikkeelle
     */
    public void handleClearAllButtonActions() {
        inserting = true;
        writed = false;
        clearBlocks();
        aStar.reset();
    }

    /**
     * Logiikka Insert -painikkeelle
     */
    public void handleInsertButtonActions() {
        inserting = true;
    }

    public void setWrited(boolean b) {
        this.writed = b;
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

    public boolean getInserting() {
        return this.inserting;
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
        CustomArrayList<Node> aStarPath = aStar.getPath();
        for (int i = 0; i < aStarPath.size(); i++) {
            Node n = aStarPath.get(i);
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
                Color color = Color.WHITE;
                if (nodes[i][j] != null) {
                    color = nodes[i][j].getBlockColor();
                }
                Node node = new Node(i, j, color);
                if (blocks[i][j]) {
                    node.setBlock(true);
                }
                if (aStarOn) {
                    aStar.calculateHeuristic(node, this.finalNode);
                }
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
     * @param color Väri
     */
    public void setBlock(int row, int col, Color color) {
        if (row >= 0 && col >= 0 && row < nodes.length && col < nodes[0].length) {
            this.nodes[row][col].setBlock(true);
            this.nodes[row][col].setBlockColor(color);
            this.blocks[row][col] = true;
        }
    }

    public void setSearchArea(Node[][] searchArea) {
        this.nodes = searchArea;
    }

    public boolean[][] getBlocks() {
        return this.blocks;
    }

    /**
     * Poistaa esteen tietyistä koordinaateista.
     *
     * @param y y-koordinaatti
     * @param x x-koordinaatti
     */
    public void removeBlock(int y, int x) {
        this.nodes[y][x].setBlock(false);
        this.blocks[y][x] = false;
    }

    /**
     * Tyhjentää esteet
     */
    public void clearBlocks() {
        this.blocks = new boolean[rows][cols];
    }

    public Paint getBlockColor(int y, int x) {
        return nodes[y][x].getBlockColor();
    }

    public int getCols() {
        return cols;
    }

    public int getRows() {
        return rows;
    }
}
