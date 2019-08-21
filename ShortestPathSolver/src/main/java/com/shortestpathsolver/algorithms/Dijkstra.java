package com.shortestpathsolver.algorithms;

import com.shortestpathsolver.domain.Node;
import com.shortestpathsolver.domain.ShortestRoute;
import com.shortestpathsolver.structures.CustomArrayList;
import com.shortestpathsolver.structures.Heap;
import com.shortestpathsolver.structures.Pair;

/**
 * Dijkstra's algorithm
 *
 * @author kaihartz
 */
public class Dijkstra {

    private Heap heap;
    private boolean[] visited;
    private int[] dist;
    private CustomArrayList<Node> closedSet;
    private ShortestRoute sr;
    private CustomArrayList<Node> finalPath;
    private int m;
    private int inf;

    public Dijkstra(ShortestRoute sr) {
        this.sr = sr;
        this.closedSet = new CustomArrayList<>();
        this.finalPath = new CustomArrayList<>();
        this.heap = new Heap(1);
        this.m = 20000;
        this.inf = 9999999;
    }

    /**
     * The main method, which searches the shortest route with Dijkstra
     *
     * @param initialNode Beginning of the search
     * @return List presenting the route
     */
    public CustomArrayList<Node> calculatePath(Node initialNode) {
        closedSet.clear();
        dist = new int[m];
        for (int i = 0; i < m; i++) {
            dist[i] = inf;
        }
        dist[initialNode.toInt()] = 0;
        finalPath.clear();
        visited = new boolean[m];
        heap.clear();
        heap.add(initialNode);
        while (heap.size() > 0) {
            Node node = heap.poll();
            if (visited[node.toInt()]) {
                continue;
            }
            if (sr.isFinalNode(node)) {
                System.out.println("Distance: " + node.getDist());
                return getPath(node);
            }
            visited[node.toInt()] = true;
            closedSet.add(node);
            Pair<Node, Integer>[] neighbours = getNeighbours(node);
            for (int i = 0; i < neighbours.length; i++) {
                if (neighbours[i] != null) {
                    Pair<Node, Integer> neighbourPair = neighbours[i];
                    Node neighbour = neighbourPair.getKey();
                    int currentDist = dist[neighbour.toInt()];
                    int newDist = dist[node.toInt()] + neighbourPair.getValue();
                    if (newDist < currentDist) {
                        dist[neighbour.toInt()] = newDist;
                        neighbour.setParent(node);
                        neighbour.setDist(newDist);
                        heap.add(neighbour);
                    }
                }

            }
        }
        return new CustomArrayList<>();
    }

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

    private CustomArrayList<Node> getPath(Node currentNode) {
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

    public CustomArrayList<Node> getClosedSet() {
        return closedSet;
    }

    public CustomArrayList<Node> getPath() {
        return this.finalPath;
    }

}
