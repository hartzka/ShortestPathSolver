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
public class Dijkstra extends Algorithm {

    private Heap heap;
    private boolean[] visited;
    private int[] dist;
    private CustomArrayList<Node> closedSet;
    private ShortestRoute sr;
    private int m;
    private int inf;

    public Dijkstra(ShortestRoute sr) {
        super(sr);
        this.sr = sr;
        this.closedSet = new CustomArrayList<>();
        this.heap = new Heap();
        this.m = 20000;
        this.inf = 9999999;
    }

    /**
     * The main logic, which searches the shortest route with Dijkstra
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
        heap.clear();
        heap.add(initialNode);
        while (heap.size() > 0) {
            Node node = heap.poll(); //Node with smallest distance
            if (visited[node.toInt()]) {
                continue;
            }
            if (sr.isFinalNode(node)) {
                System.out.println("Distance: " + node.getDist()); //The search is over and we've found the final Node!
                return getPath(node);
            }
            visited[node.toInt()] = true;
            closedSet.add(node);
            Pair<Node, Integer>[] neighbours = getNeighbours(node); // Get all neighbours except blocks
            for (int i = 0; i < neighbours.length; i++) {
                if (neighbours[i] != null) {
                    Pair<Node, Integer> neighbourPair = neighbours[i];
                    Node neighbour = neighbourPair.getKey();
                    int currentDist = dist[neighbour.toInt()];
                    int newDist = dist[node.toInt()] + neighbourPair.getValue();
                    if (newDist < currentDist) {
                        dist[neighbour.toInt()] = newDist; // Update dists
                        neighbour.setParent(node);
                        neighbour.setDist(newDist);
                        heap.add(neighbour); // Add to heap
                    }
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
