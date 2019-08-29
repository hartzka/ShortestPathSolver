package com.shortestpathsolver.algorithms;

import com.shortestpathsolver.domain.Node;
import com.shortestpathsolver.domain.ShortestRoute;
import com.shortestpathsolver.structures.CustomArrayList;
import com.shortestpathsolver.structures.Pair;

/**
 * Abstract algorithm class. Contains implemented and unimplemented methods used
 * in algorithm classes.
 *
 * @author kaihartz
 */
public abstract class Algorithm {

    private ShortestRoute sr;
    private CustomArrayList<Node> finalPath;

    public Algorithm(ShortestRoute sr) {
        this.sr = sr;
        this.finalPath = new CustomArrayList<>();
    }

    /**
     * The main logic, which searches the shortest route
     *
     * @param initialNode Beginning of the search
     * @return List presenting the route
     */
    public abstract CustomArrayList<Node> calculatePath(Node initialNode);

    /**
     * Returns all allowed node's neighbours
     *
     * @param node
     * @return table presenting neighbours
     */
    public Pair<Node, Integer>[] getNeighbours(Node node) {
        Pair<Node, Integer>[] neighbors = new Pair[8];
        int x = node.getColumn();
        int y = node.getRow();
        Node[][] nodes = sr.getNodes();

        neighbors[0] = allowed(x, y - 1) ? new Pair(nodes[y - 1][x], 10) : null;
        neighbors[1] = allowed(x + 1, y) ? new Pair(nodes[y][x + 1], 10) : null;
        neighbors[2] = allowed(x, y + 1) ? new Pair(nodes[y + 1][x], 10) : null;
        neighbors[3] = allowed(x - 1, y) ? new Pair(nodes[y][x - 1], 10) : null;
        neighbors[4] = allowed(x - 1, y - 1) ? new Pair(nodes[y - 1][x - 1], 14) : null;
        neighbors[5] = allowed(x + 1, y - 1) ? new Pair(nodes[y - 1][x + 1], 14) : null;
        neighbors[6] = allowed(x + 1, y + 1) ? new Pair(nodes[y + 1][x + 1], 14) : null;
        neighbors[7] = allowed(x - 1, y + 1) ? new Pair(nodes[y + 1][x - 1], 14) : null;

        return neighbors;
    }

    public CustomArrayList<Node> getPath(Node currentNode) {
        CustomArrayList<Node> path = new CustomArrayList<>();
        path.add(currentNode);
        Node parent;
        while ((parent = currentNode.getParent()) != null) {
            path.add(0, parent);
            currentNode = parent;
        }
        this.finalPath = path;
        return path;
    }

    public boolean allowed(int x, int y) {
        return (x < sr.getCols() && y < sr.getRows() && x >= 0 && y >= 0 && sr.getBlocks()[y][x] == false);
    }

    public abstract CustomArrayList<Node> getClosedSet();

    public CustomArrayList<Node> getPath() {
        return this.finalPath;
    }
}
