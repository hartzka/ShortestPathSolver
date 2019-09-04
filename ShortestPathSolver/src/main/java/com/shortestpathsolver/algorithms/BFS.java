package com.shortestpathsolver.algorithms;

import com.shortestpathsolver.domain.Node;
import com.shortestpathsolver.domain.ShortestRoute;
import com.shortestpathsolver.structures.CustomArrayList;
import com.shortestpathsolver.structures.Pair;
import com.shortestpathsolver.structures.Queue;

/**
 * BFS-algorithm
 *
 * @author kaihartz
 */
public class BFS extends Algorithm {

    private Queue<Node> queue;
    private boolean[] visited;
    private int[] dist;
    private CustomArrayList<Node> closedSet;
    private ShortestRoute sr;
    private int m;
    private int inf;

    public BFS(ShortestRoute sr) {
        super(sr);
        this.sr = sr;
        this.closedSet = new CustomArrayList<>();
        this.queue = new Queue<>();
        this.m = 20000;
        this.inf = 9999999;
    }

    /**
     * The main logic, which searches the shortest route with BFS
     *
     * @param initialNode Beginning of the search
     * @return List presenting the route
     */
    @Override
    public CustomArrayList<Node> calculatePath(Node initialNode) {
        closedSet.clear(); //initializations
        dist = new int[m];
        for (int i = 0; i < m; i++) {
            dist[i] = inf;
        }
        dist[initialNode.toInt()] = 0;
        super.getPath().clear();
        visited = new boolean[m];
        queue.clear();
        queue.enqueue(initialNode);
        visited[initialNode.toInt()] = true;

        while (queue.size() > 0) {
            Node node = queue.dequeue(); //The last added node

            if (sr.isFinalNode(node)) {
                //System.out.println("BFS Distance: " + node.getDist()); //The search is over and we've found the final Node! Be happy
                return getPath(node);
            }
            closedSet.add(node);
            Pair<Node, Integer>[] neighbours = getNeighbours(node); // Get all neighbours except blocks
            for (int i = 0; i < neighbours.length; i++) {
                if (neighbours[i] != null) {
                    Pair<Node, Integer> neighbourPair = neighbours[i];
                    Node neighbour = neighbourPair.getKey();
                    if (visited[neighbour.toInt()]) {
                        continue;
                    }
                    queue.enqueue(neighbour);
                    visited[neighbour.toInt()] = true;
                    int newDist = dist[node.toInt()] + 1;
                    dist[neighbour.toInt()] = newDist;
                    neighbour.setParent(node);
                    neighbour.setDist(newDist);
                }

            }
        }
        return new CustomArrayList<>();
    }

    @Override
    public CustomArrayList<Node> getClosedSet() {
        return closedSet;
    }
}
