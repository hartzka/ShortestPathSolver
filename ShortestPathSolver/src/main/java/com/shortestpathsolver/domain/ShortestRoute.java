package com.shortestpathsolver.domain;

import com.shortestpathsolver.algorithms.AStar;
import com.shortestpathsolver.algorithms.BFS;
import com.shortestpathsolver.algorithms.Dijkstra;
import com.shortestpathsolver.structures.CustomArrayList;
import com.shortestpathsolver.ui.DrawPad;
import com.shortestpathsolver.ui.Ui;
import java.util.Random;
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
 * Main logic of the application
 *
 * @author kaihartz
 */
public class ShortestRoute extends Application {

    private AStar aStar;
    private Dijkstra dijkstra;
    private BFS bfs;
    private boolean writed;
    private int startX;
    private int startY;
    private int goalX;
    private int goalY;
    private boolean inserting;
    private Ui ui;
    private DrawPad canvas;
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
    private int algorithmInUse; // 0 = A*, 1 = JPS, 2 = Dijkstra, 3 = BFS
    private boolean initialNodeMoving;
    private boolean finalNodeMoving;
    private int rowGap;
    private int height;
    private int width;
    private Color bgColor;
    private Random rand;
    private boolean pathVisualize;

    public ShortestRoute() {
        this.inserting = true;
        this.startX = 0;
        this.startY = 0;
        this.goalX = 30;
        this.goalY = 30;
        this.writed = false;
        this.rows = 40;
        this.cols = 50;
        this.height = 801;
        this.width = 1001;
        this.initialNodeMoving = false;
        this.finalNodeMoving = false;
        this.blocks = new boolean[101][126];
        this.nodes = new Node[101][126];
        this.initialNode = new Node(startX, startY);
        this.finalNode = new Node(goalX, goalY);
        this.aStar = new AStar(this);
        this.dijkstra = new Dijkstra(this);
        this.bfs = new BFS(this);
        this.algorithmInUse = 0;
        this.pathDrawing = false;
        this.timeline = new Timeline();
        this.timelineIterator = 0;
        this.bgColor = Color.BURLYWOOD;
        this.rowGap = height / rows;
        this.rand = new Random();
        this.pathVisualize = false;
        this.canvas = new DrawPad(this, null, 1, 1, 1, 1);
        setNodes();
        this.frame = new KeyFrame(Duration.seconds(0.001), new EventHandler<ActionEvent>() {
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
        this.ui = new Ui(this, width, height, rows, cols, bgColor);
        ui.start(primaryStage);
        this.canvas = ui.getCanvas();
    }

    /**
     * Searches the shortest route with A* and sends it to visualization method.
     *
     * @return true, if a route exists, otherwise false
     */
    public boolean calculateAStarPath() {
        CustomArrayList<Node> path = aStar.calculatePath(initialNode);
        CustomArrayList<Node> closedSet = aStar.getClosedSet();
        visualizePath(closedSet, path);
        if (path.isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * Searches the shortest route with Dijkstra and sends it to visualization
     * method.
     *
     * @return true, if a route exists, otherwise false
     */
    public boolean calculateDijkstraPath() {
        CustomArrayList<Node> path = dijkstra.calculatePath(initialNode);
        CustomArrayList<Node> closedSet = dijkstra.getClosedSet();
        visualizePath(closedSet, path);
        if (path.isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * Searches the shortest route with BFS and sends it to visualization
     * method.
     *
     * @return true, if a route exists, otherwise false
     */
    public boolean calculateBFSPath() {
        CustomArrayList<Node> path = bfs.calculatePath(initialNode);
        CustomArrayList<Node> closedSet = bfs.getClosedSet();
        visualizePath(closedSet, path);
        if (path.isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * Logic for Clear-button
     */
    public void handleClearButtonActions() {
        inserting = false;
        writed = false;
    }

    /**
     * Logic for Clear all -button
     */
    public void handleClearAllButtonActions() {
        inserting = true;
        writed = false;
        initializeBlocks();
        aStar.reset();
    }

    /**
     * Logic for A*-button
     */
    public void handleAStarButtonActions() {
        aStar.setJPS(false);
        algorithmInUse = 0;
    }

    /**
     * Logic for Jps-button
     */
    public void handleJpsButtonActions() {
        aStar.setJPS(true);
        algorithmInUse = 1;
    }

    /**
     * Logic for BFS-button
     */
    public void handleBfsButtonActions() {
        algorithmInUse = 3;
    }

    /**
     * Logic for Dijkstra-button
     */
    public void handleDijkstraButtonActions() {
        algorithmInUse = 2;
    }

    /**
     * Logic for Insert-button
     */
    public void handleInsertButtonActions() {
        inserting = true;
    }

    /**
     * Logic for Calculate path -button
     *
     * @return true, if path is found, false otherwise
     */
    public boolean handleCalculatePathButtonActions() {
        boolean found = false;
        pathVisualize = true;
        if (algorithmInUse <= 1) {
            found = calculateAStarPath();
        } else if (algorithmInUse == 2) {
            found = calculateDijkstraPath();
        } else if (algorithmInUse == 3) {
            found = calculateBFSPath();
        }
        return found;
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
     * Resets A*.
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
     * Tells if node is final node.
     *
     * @param node
     * @return true, if node is final node, otherwise false.
     */
    public boolean isFinalNode(Node node) {
        return node.equals(finalNode);
    }

    /**
     * Tells if a node is initial- or final node.
     *
     * @param x Node's x-coordinate
     * @param y Node's y-coordinate
     * @return true, if node is neither initial node nor final node.
     */
    public boolean isNotInitialOrFinalNode(int x, int y) {
        if (x == startX && y == startY) {
            return false;
        }
        if (x == goalX && y == goalY) {
            return false;
        }
        return true;
    }

    /**
     * Updates visualization colors.
     */
    private void visualizePath(CustomArrayList<Node> closedSet, CustomArrayList<Node> path) {
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
     * Initializes the node array.
     */
    public void setNodes() {
        for (int i = 0; i < nodes.length; i++) {
            for (int j = 0; j < nodes[0].length; j++) {
                Node node = new Node(j, i);
                if (blocks[i][j]) {
                    node.setBlock(true);
                }
                if (algorithmInUse <= 1) {
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
     * Sets a block in given coordinates.
     *
     * @param row Block's row
     * @param col Block's column
     */
    public void setBlock(int row, int col) {
        if (row >= 0 && col >= 0 && row < nodes.length && col < nodes[0].length && isNotInitialOrFinalNode(col, row)) {
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
     * Removes a block from given coordinates.
     *
     * @param y y-coordinate
     * @param x x-coordinate
     */
    public void removeBlock(int y, int x) {
        this.nodes[y][x].setBlock(false);
        this.blocks[y][x] = false;
    }

    /**
     * Initializes blocks.
     */
    public void initializeBlocks() {
        this.blocks = new boolean[nodes.length][nodes[0].length];
    }

    public int getCols() {
        return cols;
    }

    public int getRows() {
        return rows;
    }

    public void setNodesMovementsOff() {
        initialNodeMoving = false;
        finalNodeMoving = false;
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
     * Tells if a node in given coordinates is available i.e. not initial- or
     * final node and not block.
     *
     * @param y y-coordinate
     * @param x x-coordinate
     *
     * @return true, if node is available, false otherwise
     */
    public boolean isAvailable(int y, int x) {
        return isNotInitialOrFinalNode(x, y) && !blocks[y][x];
    }

    public boolean isInitialNode(Node node) {
        return node.equals(initialNode);
    }

    /**
     * Takes care of different mouse actions such as initial- and final node
     * movement and filling blocks.
     *
     * @param y y-coordinate
     * @param x x-coordinate
     */
    public void handleMouseAction(double x, double y) {
        if (!pathVisualize) {
            int ux = (int) (x / rowGap);
            int uy = (int) (y / rowGap);
            if (initialNodeMoving) {
                if (isAvailable(uy, ux) && uy < rows && ux < cols) { // Now initial Node is moving
                    canvas.fillRect(startY, startX, bgColor);
                    startX = ux;
                    startY = uy;
                    canvas.setInitialNode(uy, ux);
                }
            } else if (finalNodeMoving) {
                if (isAvailable(uy, ux) && uy < rows && ux < cols) { // Now final Node is moving
                    canvas.fillRect(goalY, goalX, bgColor);
                    goalX = ux;
                    goalY = uy;
                    canvas.setFinalNode(uy, ux);
                }
            } else if (initialNode.getColumn() == ux && initialNode.getRow() == uy) {
                initialNodeMoving = true; // Initial Node starts to move
            } else if (finalNode.getColumn() == ux && finalNode.getRow() == uy) {
                finalNodeMoving = true; // Final Node starts to move
            } else if (!inserting) { //Removing
                if (isNotInitialOrFinalNode(ux, uy)) {
                    canvas.removeBlock(y, x, bgColor);
                }
            } else {
                if (isNotInitialOrFinalNode(ux, uy)) {
                    try {
                        canvas.fillBlock(y, x); //Inserting blocks
                    } catch (Exception ex) {
                        Logger.getLogger(Ui.class.getName()).log(Level.SEVERE, null, ex);
                    }
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
     * Takes care of animations and path visualization timed by timeline.
     *
     */
    public void handleAnimation() {
        if (!pathDrawing && ((algorithmInUse <= 1 && timelineIterator >= aStar.getClosedSet().size()) || (algorithmInUse == 2 && timelineIterator >= dijkstra.getClosedSet().size())
                || (algorithmInUse == 3 && timelineIterator >= bfs.getClosedSet().size()))) {
            pathDrawing = true;
            prevNode = null;
            timelineIterator = 0;
        } else if (pathDrawing) {
            handlePathDrawingActions();
        } else {
            Node n = null;
            if (algorithmInUse == 2) {
                n = dijkstra.getClosedSet().get(timelineIterator);
            } else if (algorithmInUse <= 1) {
                n = aStar.getClosedSet().get(timelineIterator);
            } else if (algorithmInUse == 3) {
                n = bfs.getClosedSet().get(timelineIterator);
            }
            if (isNotInitialOrFinalNode(n.getColumn(), n.getRow()) && !n.isBlock()) {
                canvas.fillRect(n.getRow(), n.getColumn(), n.getVisualizationColor());
            }
        }
        timelineIterator++;
    }

    private void handlePathDrawingActions() {
        if ((algorithmInUse == 2 && timelineIterator >= dijkstra.getPath().size()) || (algorithmInUse <= 1 && timelineIterator >= aStar.getPath().size())
                || (algorithmInUse == 3 && timelineIterator >= bfs.getPath().size())) {
            pathDrawing = false;
            prevNode = null;
            if (algorithmInUse <= 1) {
                aStar.reset();
            }
            timelineIterator = 0;
            timeline.stop();
            pathVisualize = false;
            if (ui != null) {
                ui.setButtonsOn();
            }
        } else {
            fillPathLine();
        }
    }

    public void fillPathLine() {
        Node n = null;
        if (algorithmInUse == 2) {
            n = dijkstra.getPath().get(timelineIterator);
        } else if (algorithmInUse <= 1) {
            n = aStar.getPath().get(timelineIterator);
        } else if (algorithmInUse == 3) {
            n = bfs.getPath().get(timelineIterator);
        }
        if (prevNode == null) {
            if (n != null) {
                prevNode = n;
                canvas.fillPathLine(initialNode.getRow(), initialNode.getColumn(), prevNode.getRow(), prevNode.getColumn());
            }
        } else {
            canvas.fillPathLine(prevNode.getRow(), prevNode.getColumn(), n.getRow(), n.getColumn());
            prevNode = n;
        }
    }

    public boolean getPathDrawing() {
        return pathDrawing;
    }

    public void setInserting(boolean b) {
        inserting = b;
    }

    /**
     * Updates rows and columns.
     *
     * @param rows
     */
    public void updateRowsAndCols(int rows) {
        this.rows = rows;
        this.rowGap = height / rows;
        this.cols = width / rowGap;
    }

    /**
     * Updates initial node.
     *
     * @param x x-coordinate
     * @param y y-coordinate
     */
    public void setInitialNode(int x, int y) {
        blocks[y][x] = false;
        initialNode = new Node(x, y);
        startX = x;
        startY = y;
    }

    /**
     * Updates final node.
     *
     * @param x x-coordinate
     * @param y y-coordinate
     */
    public void setFinalNode(int x, int y) {
        blocks[y][x] = false;
        finalNode = new Node(x, y);
        goalX = x;
        goalY = y;
    }

    /**
     * Constructs the map content. This is needed in file saving.
     *
     * @return content
     */
    public String getContent() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (blocks[i][j]) {
                    s.append("@");
                } else {
                    s.append(".");
                }
            }
            s.append("\n");
        }
        return s.toString();
    }

    /**
     * Randomizes blocks.
     *
     * @return Array presenting randomized blocks.
     */
    public boolean[][] randomizeBlocks() {
        inserting = true;
        writed = false;
        initializeBlocks();
        aStar.reset();
        int k = rand.nextInt(15) + 3;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int r = rand.nextInt(k);
                if (r == 2 && isNotInitialOrFinalNode(j, i)) {
                    blocks[i][j] = true;
                } else {
                    blocks[i][j] = false;
                }
            }
        }
        return blocks;
    }

    public void setBackGround(Color value) {
        this.bgColor = value;
    }

    public boolean getPathVisualize() {
        return pathVisualize;
    }

    public int getAlgorithmInUse() {
        return algorithmInUse;
    }

    public Node getPrevNode() {
        return prevNode;
    }
}
