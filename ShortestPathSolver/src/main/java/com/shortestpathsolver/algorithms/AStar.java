package com.shortestpathsolver.algorithms;

import com.shortestpathsolver.domain.Node;
import com.shortestpathsolver.domain.ShortestRoute;
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
    private ArrayList<Node> closedSet;
    private ShortestRoute sr;
    private List<Node> finalPath;

    public AStar(ShortestRoute sr) {
        this.sr = sr;

        this.openList = new PriorityQueue<Node>(new Comparator<Node>() {
            @Override
            public int compare(Node node1, Node node2) {
                return Integer.compare(node1.getF(), node2.getF());
            }
        });
        this.closedSet = new ArrayList<>();
        this.finalPath = new ArrayList<>();
    }

    private void checkNode(Node currentNode, int col, int row, int cost) {
        Node neighbour = sr.getNodes()[row][col];
        if (!(neighbour.isBlock() || getClosedSet().contains(neighbour))) {
            if (!getOpenList().contains(neighbour)) {
                neighbour.setAStarInformation(currentNode, cost);
                getOpenList().add(neighbour);
            } else {
                boolean changed = neighbour.checkifBetterPathExistsAStar(currentNode, cost);
                if (changed) {
                    getOpenList().remove(neighbour);
                    getOpenList().add(neighbour);
                }
            }
        }
    }

    /**
     * Pääalgoritmi, joka hakee lyhimmän reitin
     *
     * @param initialNode Alkusolmu, josta haku lähtee liikkeelle
     * @return Listan reitistä
     */
    public List<Node> calculatePath(Node initialNode) {
        openList.add(initialNode);
        while (!openList.isEmpty()) {
            Node currentNode = openList.poll();
            closedSet.add(currentNode);
            if (sr.isFinalNode(currentNode)) {
                return getPath(currentNode);
            } else {
                addNeighbourNodes(currentNode);
            }
        }
        return new ArrayList<Node>();
    }

    private List<Node> getPath(Node currentNode) {
        List<Node> path = new ArrayList<Node>();
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
        if (row - 1 >= 0) { //upper row
            checkNode(currentNode, col, row - 1, hCost); //upper
            if (col - 1 >= 0) {
                checkNode(currentNode, col - 1, row - 1, diagonalCost); //upper left
            }
            if (col + 1 < nodes[0].length) {
                checkNode(currentNode, col + 1, row - 1, diagonalCost); //upper right
            }
        }
        if (col - 1 >= 0) {
            checkNode(currentNode, col - 1, row, hCost); //left
        }
        if (col + 1 < nodes[0].length) {
            checkNode(currentNode, col + 1, row, hCost); //right
        }
        if (row + 1 < nodes.length) { //lower row
            checkNode(currentNode, col, row + 1, hCost); //lower
            if (col - 1 >= 0) {
                checkNode(currentNode, col - 1, row + 1, diagonalCost); //lower left
            }
            if (col + 1 < nodes[0].length) {
                checkNode(currentNode, col + 1, row + 1, diagonalCost); //lower right
            }
        }
    }

    public PriorityQueue<Node> getOpenList() {
        return openList;
    }

    public List<Node> getClosedSet() {
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

    public List<Node> getPath() {
        return this.finalPath;
    }
}
