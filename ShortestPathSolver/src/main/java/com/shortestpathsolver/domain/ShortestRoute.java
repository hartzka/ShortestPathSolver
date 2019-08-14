package com.shortestpathsolver.domain;

import com.shortestpathsolver.algorithms.AStar;
import com.shortestpathsolver.structures.CustomArrayList;
import com.shortestpathsolver.ui.DrawPad;
import com.shortestpathsolver.ui.Ui;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
    private boolean inserting;
    private Ui ui;
    private Node initialNode;
    private Node finalNode;
    private boolean[][] blocks;
    private Node[][] nodes;
    private int rows;
    private int cols;
    private Timeline timeline;
    private int timelineIterator;
    private Node prevNode;
    private KeyFrame frame;
    private boolean pathDrawing;
    private boolean aStarOn;
    private boolean initialNodeMoving;
    private boolean finalNodeMoving;

    public ShortestRoute() {
        this.inserting = true;
        this.startX = 0;
        this.startY = 0;
        this.goalX = 30;
        this.goalY = 30;
        this.writed = false;
        this.rows = 40;
        this.cols = 50;
        this.initialNodeMoving = false;
        this.finalNodeMoving = false;
        this.blocks = new boolean[rows][cols];
        this.nodes = new Node[rows][cols];
        this.initialNode = new Node(startX, startY);
        this.finalNode = new Node(goalX, goalY);
        this.aStar = new AStar(this);
        this.aStarOn = true;
        this.pathDrawing = false;
        this.timeline = new Timeline();
        this.timelineIterator = 0;
        setNodes();
        this.frame = new KeyFrame(Duration.seconds(0.002), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleAnimation();
            }
        });
        timeline.getKeyFrames().add(frame);
        timelineIterator = 0;
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    @Override
    public void start(Stage primaryStage) {
        ui = new Ui(this, cols * 20 + 1, rows * 20 + 1, rows, cols, Color.BURLYWOOD);
        ui.start(primaryStage);
    }

    /**
     * Hakee A*-algoritmilla lyhimmän reitin ja lähettää sen visualisoitavaksi.
     *
     * @return true, jos reitti löytyy, muuten false
     */
    public boolean calculateAStarPath() {
        CustomArrayList<Node> path = aStar.calculatePath(initialNode);
        CustomArrayList<Node> closedSet = aStar.getClosedSet();
        visualizeAStarPath(closedSet, path);
        if (path.isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * Logiikka Clear-painikkeelle
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
     * Logiikka A*-painikkeelle
     */
    public void handleAStarButtonActions() {
        aStar.setJPS(false);
    }

    /**
     * Logiikka JPS-painikkeelle
     */
    public void handleJpsButtonActions() {
        aStar.setJPS(true);
    }

    /**
     * Logiikka Insert-painikkeelle
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

    private void visualizeAStarPath(CustomArrayList<Node> closedSet, CustomArrayList<Node> path) {
        for (int i = 0; i < closedSet.size(); i++) {
            Node n = closedSet.get(i);
            n.setVisualizationColor(Color.WHITE);
        }
        for (int i = 0; i < path.size(); i++) {
            Node n = path.get(i);
            n.setVisualizationColor(Color.BLUEVIOLET);
        }
        timeline.play();
    }

    /**
     * Alustaa solmujen taulukon
     */
    public void setNodes() {
        for (int i = 0; i < nodes.length; i++) {
            for (int j = 0; j < nodes[0].length; j++) {
                Node node = new Node(i, j);
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
     */
    public void setBlock(int row, int col) {
        if (row >= 0 && col >= 0 && row < nodes.length && col < nodes[0].length && isNotStartOrGoalNode(col, row)) {
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

    public int getCols() {
        return cols;
    }

    public int getRows() {
        return rows;
    }

    public void setNodesMovementsOff() {
        initialNodeMoving = finalNodeMoving = false;
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public int getGoalX() {
        return goalX;
    }

    public int getGoalY() {
        return goalY;
    }

    /**
     * Kertoo, onko koordinaatti (y, x) vapaa, eli ei este eikä aloitus- tai
     * lopetussolmu
     *
     * @param y y-koordinaatti
     * @param x x-koordinaatti
     *
     * @return true, jos koordinaatti on vapaa
     */
    public boolean isAvailable(int y, int x) {
        return isNotStartOrGoalNode(x, y) && !blocks[y][x];
    }

    public boolean isInitialNode(Node node) {
        return node.equals(initialNode);
    }

    /**
     * Huolehtii hiiren liikkuttamisen toiminnallisuuksista, 
     * kuten esteiden piirtämisestä ja aloitus- ja lopetussolmujen liikuttamisesta.
     *
     * @param y y-koordinaatti
     * @param x x-koordinaatti
     */
    public void handleMouseAction(double x, double y) {
        int ux = (int) (x / 20);
        int uy = (int) (y / 20);
        DrawPad canvas;
        canvas = ui != null ? ui.getCanvas() : new DrawPad(this, null, uy, uy);
        if (initialNodeMoving) {
            if (isAvailable(uy, ux)) {
                canvas.reset();
                canvas.setFinalNode(finalNode.getRow(), finalNode.getColumn());
                canvas.fillBlocks(blocks);
                canvas.setInitialNode(uy, ux);
            }
        } else if (finalNodeMoving) {
            if (isAvailable(uy, ux)) {
                canvas.reset();
                canvas.setInitialNode(initialNode.getRow(), initialNode.getColumn());
                canvas.fillBlocks(getBlocks());
                canvas.setFinalNode(uy, ux);
            }
        } else if (initialNode.getColumn() == ux && initialNode.getRow() == uy) {
            initialNodeMoving = true;
        } else if (finalNode.getColumn() == ux && finalNode.getRow() == uy) {
            finalNodeMoving = true;
        } else if (!inserting) { //removing
            if (isNotStartOrGoalNode(ux, uy)) {
                canvas.removeBlock(y, x, ui == null ? Color.WHITE : ui.getBgColor());
            }
        } else {
            if (isNotStartOrGoalNode(ux, uy)) {
                try {
                    canvas.fillBlock(y, x);
                } catch (Exception ex) {
                    Logger.getLogger(Ui.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public boolean getInitialNodeMoving() {
        return initialNodeMoving;
    }

    public boolean getFinalNodeMoving() {
        return finalNodeMoving;
    }

    /**
     * Huolehtii solmujen ja reitin animaation toteuttamisesta, jota timeline ajoittaa.
     *
     */
    public void handleAnimation() {
        if (!pathDrawing && timelineIterator >= aStar.getClosedSet().size()) {
            pathDrawing = true;
            prevNode = null;
            timelineIterator = 0;
        } else if (pathDrawing) {
            if (timelineIterator >= aStar.getPath().size()) {
                pathDrawing = false;
                prevNode = null;
                aStar.reset();
                timelineIterator = 0;
                if (ui != null) {
                    ui.setGetPathDisable(false);
                }
                timeline.stop();
            } else {
                Node n = aStar.getPath().get(timelineIterator);
                if (prevNode == null) {
                    prevNode = n;
                    if (ui != null) {
                        ui.getCanvas().fillPathLine(initialNode.getRow(), initialNode.getColumn(), prevNode.getRow(), prevNode.getColumn());
                    }
                } else {
                    if (ui != null) {
                        ui.getCanvas().fillPathLine(prevNode.getRow(), prevNode.getColumn(), n.getRow(), n.getColumn());
                    }
                    prevNode = n;
                }
            }
        } else {
            Node n = aStar.getClosedSet().get(timelineIterator);
            if (isNotStartOrGoalNode(n.getColumn(), n.getRow()) && !n.isBlock()) {
                if (ui != null) {
                    ui.getCanvas().fillRect(n.getRow(), n.getColumn(), n.getVisualizationColor());
                }
            }
        }
        timelineIterator++;
    }

    public boolean getPathDrawing() {
        return pathDrawing;
    }

    public void setInserting(boolean b) {
        inserting = b;
    }
}
