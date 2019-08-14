package com.shortestpathsolver.domain;

import javafx.scene.paint.Color;

/**
 * Solmuluokka
 *
 * @author kaihartz
 */
public class Node {

    private int hValue; //heuristic
    private int gValue; //movement cost
    private int fValue;  // h+g
    private int row;
    private int column;
    private boolean isBlock;
    private Node parent;
    private Color visualizationColor;

    /**
     * Konstuktori
     *
     * @param row Solmun rivi
     * @param col Solmun sarake
     */
    public Node(int row, int col) {
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

    public int getF() {
        return fValue;
    }

    /**
     *
     * Asettaa solmuun A*-algoritmissa vaadittavia tietoja
     *
     * @param currentNode Tämänhetkinen solmu
     * @param gCost Kustannus
     */
    public void setAStarInformation(Node currentNode, int gCost) {
        this.gValue = currentNode.getG() + gCost;
        this.parent = currentNode;
        this.fValue = gValue + hValue;
    }

    public Node getParent() {
        return parent;
    }

    /**
     *
     * @return onko solmu este
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
            return "row: " + row + " col: " + column + " g: " + gValue + " f: " + fValue + " parent: row: " + parent.getRow() + " col: " + parent.getColumn();
        } else {
            return "row: " + row + " col: " + column + " g: " + gValue + " f: " + fValue;
        }
    }
}