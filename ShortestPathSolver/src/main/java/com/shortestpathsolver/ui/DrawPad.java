package com.shortestpathsolver.ui;

import com.shortestpathsolver.domain.Node;
import com.shortestpathsolver.domain.ShortestRoute;
import java.io.FileNotFoundException;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 * Drawpad, takes care of drawing and filling graphical components.
 *
 * @author kaihartz
 */
public class DrawPad extends Canvas {

    private GraphicsContext gc;
    private int width;
    private int height;
    private int rows;
    private int cols;
    private Ui ui;
    private ShortestRoute sr;
    private int rowGap;

    public DrawPad(ShortestRoute sr, Ui ui, int width, int height, int rows, int cols) {
        super(width, height);
        this.sr = sr;
        this.ui = ui;
        this.width = width;
        this.height = height;
        this.rows = rows;
        this.cols = cols;
        this.rowGap = width / cols;
        this.gc = super.getGraphicsContext2D();
    }

    /**
     * Clears the grid area.
     *
     */
    public void clearArea() {
        gc.clearRect(0, 0, width, height);
    }

    /**
     * Updates the fill.
     *
     * @param color Fill
     */
    public void updateFill(Color color) {
        gc.setFill(color);
    }

    /**
     * Fills a path from node1 to node2.
     *
     * @param y1 node1's y-coordinate
     * @param x1 node1's x-coordinate
     * @param y2 node2's y-coordinate
     * @param x2 node2's x-coordinate
     */
    public void fillPathLine(int y1, int x1, int y2, int x2) {
        gc.setStroke(Color.ORANGE);
        gc.setLineWidth(3.0);
        gc.strokeLine(x1 * rowGap + rowGap / 2, y1 * rowGap + rowGap / 2, x2 * rowGap + rowGap / 2, y2 * rowGap + rowGap / 2);
    }

    /**
     * Fills a block.
     *
     * @param positionY y-coordinate of mouse
     * @param positionX x-coordinate of mouse
     *
     * @throws FileNotFoundException
     */
    public void fillBlock(double positionY, double positionX) throws FileNotFoundException {
        int x = (int) positionX / rowGap;
        int y = (int) positionY / rowGap;
        if (x < cols && y < rows) {
            Image img = new Image(getClass().getResourceAsStream("/images/block.png"));
            gc.drawImage(img, x * rowGap + 1, y * rowGap + 1, rowGap - 2, rowGap - 2);
            sr.setBlock(y, x);
        }
    }

    /**
     * Removes a block.
     *
     * @param positionY y-coordinate of mouse
     * @param positionX x-coordinate of mouse
     * @param color Block's color
     */
    public void removeBlock(double positionY, double positionX, Color color) {
        int x = (int) positionX / rowGap;
        int y = (int) positionY / rowGap;

        gc.setFill(color);
        gc.fillRect(x * rowGap + 1, y * rowGap + 1, rowGap - 2, rowGap - 2);
        sr.removeBlock(y, x);
    }

    /**
     * Sets up the initial node.
     *
     * @param row
     * @param column
     * @return Initial node
     */
    public Node setInitialNode(int row, int column) {
        if (row >= height / rowGap || column >= width / rowGap) {
            row = 0;
            column = 0;
            sr.setInitialNode(row, column);
        }
        Node n = new Node(column, row);
        gc.setFill(Color.BLUE);
        gc.fillRect(column * rowGap + 1, row * rowGap + 1, rowGap - 2, rowGap - 2);
        sr.setInitialNode(n);
        return n;
    }

    /**
     * Sets up the final node.
     *
     * @param row
     * @param column
     * @return Final node
     */
    public Node setFinalNode(int row, int column) {
        if (row >= height / rowGap || column >= width / rowGap) {
            row = rows - 1;
            column = cols - 1;
            sr.setFinalNode(column, row);
        }
        Node n = new Node(column, row);
        gc.setFill(Color.GREEN);
        gc.fillRect(column * rowGap + 1, row * rowGap + 1, rowGap - 2, rowGap - 2);
        sr.setFinalNode(n);
        return n;
    }

    /**
     * Fills the grid.
     *
     * @param width Width of the grid
     * @param height Height of the grid
     */
    public void fillGrid(int width, int height) {
        gc.setLineWidth(1.0);
        gc.setStroke(Color.BLACK);
        for (int x = 0; x <= width; x += rowGap) {
            gc.strokeLine(x, 0, x, rows * rowGap);
        }

        for (int y = 0; y <= rows * rowGap; y += rowGap) {
            gc.strokeLine(0, y, cols * rowGap, y);
        }
    }

    /**
     * Fills all blocks.
     *
     * @param blocks Array of blocks
     */
    public void fillBlocks(boolean[][] blocks) {
        for (int y = 0; y < blocks.length; y++) {
            for (int x = 0; x < blocks[0].length; x++) {
                if (blocks[y][x] && x < cols && y < rows) {
                    Image img = new Image(getClass().getResourceAsStream("/images/block.png"));
                    gc.drawImage(img, x * rowGap + 1, y * rowGap + 1, rowGap - 2, rowGap - 2);
                }
            }
        }
        if (ui != null) {
            gc.setFill(ui.getColorpicker().getValue());
        }
    }

    /**
     * Fills a rectangle
     *
     * @param y Rectangle's y-coordinate
     * @param x Rectangle's x-coordinate
     * @param c Color
     */
    public void fillRect(int y, int x, Color c) {
        if (x < cols && y < rows) {
            gc.setFill(c);
            gc.fillRect(x * rowGap + 1, y * rowGap + 1, rowGap - 2, rowGap - 2);
        }
    }

    /**
     * Resets the grid
     *
     */
    public void reset() {
        clearArea();
        fillGrid(width, height);
        setInitialNode(sr.getStartY(), sr.getStartX());
        setFinalNode(sr.getGoalY(), sr.getGoalX());
        fillBlocks(sr.getBlocks());
    }

    /**
     * Updates rows and columns
     *
     * @param rows
     *
     */
    public void updateRowsAndCols(int rows) {
        this.rowGap = height / rows;
        this.rows = rows;
        this.cols = width / rowGap;
        reset();

    }
}
