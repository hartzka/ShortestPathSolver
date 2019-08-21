package com.shortestpathsolver.domain;

import javafx.scene.paint.Color;

/**
 * Node class
 *
 * @author kaihartz
 */
public class Node {

    private int hValue; //heuristic in A*
    private int gValue; //movement cost in A*
    private int dist;  // h+g=f in A* and distance in dijkstra
    private int row;
    private int column;
    private boolean isBlock;
    private Node parent;
    private Color visualizationColor;

    public Node(int col, int row) {
        this.row = row;
        this.column = col;
        this.visualizationColor = Color.WHITE;
    }

    public int getH() {
        return hValue;
    }

    public void setH(int h) {
        this.hValue = h;
    }

    public int getG() {
        return gValue;
    }

    public int getDist() {
        return dist;
    }

    public void setDist(int newDist) {
        this.dist = newDist;
    }

    /**
     *
     * Updates node's information in A*
     *
     * @param currentNode Current node in A*
     * @param gCost Movement cost
     */
    public void setAStarInformation(Node currentNode, int gCost) {
        this.gValue = currentNode.getG() + gCost;
        this.parent = currentNode;
        this.dist = gValue + hValue;
    }

    public Node getParent() {
        return parent;
    }

    /**
     *
     * @return if node is block
     */
    public boolean isBlock() {
        return isBlock;
    }

    public void setBlock(boolean isBlock) {
        this.isBlock = isBlock;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int col) {
        this.column = col;
    }

    public void setVisualizationColor(Color c) {
        this.visualizationColor = c;
    }

    public Color getVisualizationColor() {
        return this.visualizationColor;
    }

    @Override
    public boolean equals(Object n) {
        Node node2 = (Node) n;
        return this.getRow() == node2.getRow() && this.getColumn() == node2.getColumn();
    }

    @Override
    public String toString() {
        if (parent != null) {
            return "row: " + row + " col: " + column + " g: " + gValue + " dist: " + dist + " parent: row: " + parent.getRow() + " col: " + parent.getColumn();
        } else {
            return "row: " + row + " col: " + column + " g: " + gValue + " dist: " + dist;
        }
    }

    /**
     *
     * @return int representation of node
     */
    public int toInt() {
        return row * 150 + column;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }
}
