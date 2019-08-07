package com.shortestpathsolver.algorithms;

import com.shortestpathsolver.domain.Node;
import com.shortestpathsolver.domain.ShortestRoute;
import com.shortestpathsolver.structures.CustomArrayList;
import java.util.*;

/**
 * A*-algoritmin ominaisuudet toteuttava luokka
 *
 * @author kaihartz
 */
public class AStar {

    private int hCost = 10;
    private int diagonalCost = 14;
    private PriorityQueue<Node> openList;
    private CustomArrayList<Node> closedSet;
    private ShortestRoute sr;
    private CustomArrayList<Node> finalPath;
    private boolean jps; //JPS-algorithm

    public AStar(ShortestRoute sr) {
        this.sr = sr;
        this.jps = true;
        this.openList = new PriorityQueue<Node>(new Comparator<Node>() {
            @Override
            public int compare(Node node1, Node node2) {
                return Integer.compare(node1.getF(), node2.getF());
            }
        });
        this.closedSet = new CustomArrayList<>();
        this.finalPath = new CustomArrayList<>();
    }

    private void checkNode(Node currentNode, int col, int row, int cost) {
        Node neighbour = sr.getNodes()[row][col];
        int gCost = currentNode.getG() + cost;
        if (!(neighbour.isBlock() || getClosedSet().contains(neighbour))) {
            if (!getOpenList().contains(neighbour)) {
                neighbour.setAStarInformation(currentNode, gCost);
                getOpenList().add(neighbour);
            } else {
                checkifBetterPathExists(currentNode, neighbour, gCost);
            }
        }
    }

    /**
     * Pääalgoritmi, joka hakee lyhimmän reitin joko tavallisella
     * A*-algoritmilla tai JPS-lisätoiminnolla
     *
     * @param initialNode Alkusolmu, josta haku lähtee liikkeelle
     * @return Listan reitistä
     */
    public CustomArrayList<Node> calculatePath(Node initialNode) {
        openList.add(initialNode);
        while (!openList.isEmpty()) {
            Node currentNode = openList.poll();
            closedSet.add(currentNode);
            if (sr.isFinalNode(currentNode)) {
                return getPath(currentNode);
            } else {
                if (!jps) {
                    addNeighbourNodes(currentNode); //A*-algoritmi
                } else { //A* with JPS
                    Node[] jumpPoints = calculateJumpPoints(currentNode);
                    for (int i = 0; i < jumpPoints.length; i++) {
                        if (jumpPoints[i] != null) {
                            openList.add(jumpPoints[i]);
                        }
                    }
                }
            }
        }
        return new CustomArrayList<Node>();
    }

    /**
     * Palauttaa mahdolliset jump pointit tietylle solmulle. Tämä nopeuttaa
     * hakua huomattavasti tietyissä ruudukoissa, joissa on suoraviivainen
     * reitti.
     *
     * @param node Solmu
     * @return mahdolliset jump pointit
     */
    public Node[] calculateJumpPoints(Node node) {
        Node[] jumpPoints = new Node[8];
        int[][] neighbors = getNeighbors(node);
        for (int i = 0; i < neighbors.length; i++) {
            int[] point = jump(neighbors[i][0], neighbors[i][1], node.getColumn(), node.getRow());
            if (point[0] != -1) {
                int x = point[0];
                int y = point[1];

                if (!(x != node.getColumn() && y != node.getRow() && Math.abs(x - node.getColumn()) != Math.abs(y - node.getRow()))) {
                    int newG = (approxG(x, y, node.getColumn(), node.getRow()) + node.getG());
                    Node[][] nodes = sr.getNodes();
                    if (nodes[y][x].getF() == 0 || nodes[y][x].getG() > newG) {
                        nodes[y][x].setAStarInformation(node, approxG(x, y, node.getColumn(), node.getRow()) + node.getG());
                        jumpPoints[i] = nodes[y][x];
                    }
                }
            }
        }
        return jumpPoints;
    }

    /**
     * Rekursiivinen metodi, joka etsii hyppypistettä solmun ja sen vanhemman
     * tietojen perusteella.
     *
     * @param x solmun sarake
     * @param y solmun rivi
     * @param px vanhemman sarake
     * @param py vanhemman rivi
     * @return löydetyn hyppypisteen koordinaatit.
     */
    public int[] jump(int x, int y, int px, int py) {

        int dx = (x - px) / Math.max(Math.abs(x - px), 1);
        int dy = (y - py) / Math.max(Math.abs(y - py), 1);

        if (!allowed(x, y)) {
            return createTable(-1, -1);
        }
        if (x == sr.getFinalNode().getColumn() && y == sr.getFinalNode().getRow()) {
            return createTable(x, y);
        }
        if (dx != 0 && dy != 0) {
            if ((allowed(x - dx, y + dy) && !allowed(x - dx, y)) || (allowed(x + dx, y - dy) && !allowed(x, y - dy))) {
                return createTable(x, y);
            }
            int[] jumpx = jump(x + dx, y, x, y);
            int[] jumpy = jump(x, y + dy, x, y);
            if (jumpx[0] != -1 || jumpy[0] != -1) {
                return createTable(x, y);
            }
        } else {
            if (dx == 0) {
                if ((allowed(x + 1, y + dy) && !allowed(x + 1, y)) || (allowed(x - 1, y + dy) && !allowed(x - 1, y))) {
                    return createTable(x, y);
                }
            } else {
                if ((allowed(x + dx, y + 1) && !allowed(x, y + 1)) || (allowed(x + dx, y - 1) && !allowed(x, y - 1))) {
                    return createTable(x, y);
                }
            }
        }

        if (x == 0 && y == 0 && dx == 0 && dy == 0) {
            return createTable(-1, -1);
        }
        int[] table = jump(x + dx, y + dy, x, y);
        return table;
    }

    /**
     * Palauttaa solmun kaikki naapurit, joihin on mahdollista liikkua
     *
     * @param node solmu
     * @return taulukon naapureista
     */
    public int[][] getNeighbors(Node node) {
        int[][] neighbors = new int[8][2];
        int x = node.getColumn();
        int y = node.getRow();

        neighbors[0] = allowed(x, y - 1) ? createTable(x, y - 1) : createTable(-1, -1);
        neighbors[1] = allowed(x + 1, y) ? createTable(x + 1, y) : createTable(-1, -1);
        neighbors[2] = allowed(x, y + 1) ? createTable(x, y + 1) : createTable(-1, -1);
        neighbors[3] = allowed(x - 1, y) ? createTable(x - 1, y) : createTable(-1, -1);
        neighbors[4] = allowed(x - 1, y - 1) ? createTable(x - 1, y - 1) : createTable(-1, -1);
        neighbors[5] = allowed(x + 1, y - 1) ? createTable(x + 1, y - 1) : createTable(-1, -1);
        neighbors[6] = allowed(x + 1, y + 1) ? createTable(x + 1, y + 1) : createTable(-1, -1);
        neighbors[7] = allowed(x - 1, y + 1) ? createTable(x - 1, y + 1) : createTable(-1, -1);
        
        return neighbors;
    }

    /**
     * Muodostaa x- ja y-koordinaateista taulukon, jota tarvitaan käsittelyssä
     *
     * @param x x-koordinaatti
     * @param y y-koordinaatti
     * @return taulukko {x, y}
     */
    public int[] createTable(int x, int y) {
        int[] table = {x, y};
        return table;
    }

    private CustomArrayList<Node> getPath(Node currentNode) {
        CustomArrayList<Node> path = new CustomArrayList<Node>();
        path.add(currentNode);
        Node parent;
        while ((parent = currentNode.getParent()) != null) {
            path.add(0, parent);
            currentNode = parent;
        }
        this.finalPath = path;
        return path;
    }

    private void addNeighbourNodes(Node currentNode) {
        int row = currentNode.getRow();
        int col = currentNode.getColumn();
        Node[][] nodes = sr.getNodes();
        if (row - 1 >= 0) { //ylin rivi
            checkNode(currentNode, col, row - 1, hCost); //ylä
            if (col - 1 >= 0) {
                checkNode(currentNode, col - 1, row - 1, diagonalCost); //vasen ylä
            }
            if (col + 1 < nodes[0].length) {
                checkNode(currentNode, col + 1, row - 1, diagonalCost); //oikea ylä
            }
        }
        if (col - 1 >= 0) {
            checkNode(currentNode, col - 1, row, hCost); //vasen
        }
        if (col + 1 < nodes[0].length) {
            checkNode(currentNode, col + 1, row, hCost); //oikea
        }
        if (row + 1 < nodes.length) { //lower row
            checkNode(currentNode, col, row + 1, hCost); //ala
            if (col - 1 >= 0) {
                checkNode(currentNode, col - 1, row + 1, diagonalCost); //vasen ala
            }
            if (col + 1 < nodes[0].length) {
                checkNode(currentNode, col + 1, row + 1, diagonalCost); //oikea ala
            }
        }
    }

    public PriorityQueue<Node> getOpenList() {
        return openList;
    }

    public CustomArrayList<Node> getClosedSet() {
        return closedSet;
    }

    /**
     * Alustaa solmut, avoimen ja suljetun listan ja reitin
     */
    public void reset() {
        sr.setNodes();
        openList.clear();
        closedSet.clear();
        finalPath.clear();
    }

    public CustomArrayList<Node> getPath() {
        return this.finalPath;
    }

    /**
     *
     * Laskee arvion etäisyydestä loppusolmuun
     *
     * @param current Käsiteltävä solmu
     * @param finalNode Loppusolmu
     */
    public void calculateHeuristic(Node current, Node finalNode) {
        int h = Math.abs(finalNode.getColumn() - current.getColumn()) + Math.abs(finalNode.getRow() - current.getRow());
        current.setH(h);
    }

    /**
     *
     * Tarkistaa, onko olemassa parempaa polkua ja tekee tarvittavat
     * toimenpiteet
     *
     * @param currentNode Tämänhetkinen solmu
     * @param neighbour Naapurisolmu
     * @param gCost Kustannus
     * @return true, jos parempi polku on olemassa, muuten false
     */
    public boolean checkifBetterPathExists(Node currentNode, Node neighbour, int gCost) {
        if (gCost < currentNode.getG()) {
            neighbour.setAStarInformation(currentNode, gCost);
            return true;
        }
        return false;
    }

    private int approxG(int x1, int y1, int x2, int y2) {
        if (x1 == x2) {
            return Math.abs(y1 - y2) * hCost;
        }
        if (y1 == y2) {
            return Math.abs(x1 - x2) * hCost;
        }
        return Math.abs(x1 - x2) * diagonalCost;
    }

    /**
     * Testaa, onko solmuun sallittua mennä
     *
     * @param x solmun x-koordinaatti
     * @param y solmun y-koordinaatti
     * @return true, jos solmuun voi mennä, eli se on kartalla eikä ole este.
     * Muuten false.
     */
    public boolean allowed(int x, int y) {
        return (x < sr.getCols() && y < sr.getRows() && x >= 0 && y >= 0 && sr.getBlocks()[y][x] == false);
    }

    public void setJPS(boolean b) {
        this.jps = b;
    }
}
