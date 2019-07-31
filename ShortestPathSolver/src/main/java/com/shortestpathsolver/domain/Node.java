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
    private Color blockColor;

    /**
     * Konstuktori
     *
     * @param row Solmun rivi
     * @param col Solmun sarake
     * @param color Solmun väri
     */
    public Node(int row, int col, Color color) {
        this.row = row;
        this.column = col;
        this.blockColor = color;
    }

    /**
     *
     * Laskee A*-algoritmissa tarvittavan arvion etäisyydestä loppusolmuun
     *
     * @param finalNode Loppusolmu
     */
    public void calculateHeuristic(Node finalNode) {
        this.hValue = Math.abs(finalNode.getColumn() - getColumn()) + Math.abs(finalNode.getRow() - getRow());
    }

    private void calculateFinalCost() {
        int finalCost = getG() + getH();
        setF(finalCost);
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

    public void setG(int g) {
        this.gValue = g;
    }

    public int getF() {
        return fValue;
    }

    public void setF(int f) {
        this.fValue = f;
    }

    /**
     *
     * Asettaa solmuun A*-algoritmissa vaadittavia tietoja
     *
     * @param currentNode Tämänhetkinen solmu
     * @param cost Kustannus
     */
    public void setAStarInformation(Node currentNode, int cost) {
        int gCost = currentNode.getG() + cost;
        setParent(currentNode);
        setG(gCost);
        calculateFinalCost();
    }

    /**
     *
     * Tarkistaa, onko olemassa parempaa polkua A*-algoritmissa ja tekee
     * tarvittavat toimenpiteet
     *
     * @param currentNode Tämänhetkinen solmu
     * @param cost Kustannus
     * @return true, jos parempi polku on olemassa, muuten false
     */
    public boolean checkifBetterPathExistsAStar(Node currentNode, int cost) {
        int gCost = currentNode.getG() + cost;
        if (gCost < this.gValue) {
            setAStarInformation(currentNode, cost);
            return true;
        }
        return false;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

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

    @Override
    public boolean equals(Object n) {
        Node node2 = (Node) n;
        return this.getRow() == node2.getRow() && this.getColumn() == node2.getColumn();
    }

    @Override
    public String toString() {
        return "row: " + row + " col: " + column;
    }
}
