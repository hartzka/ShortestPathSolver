package com.shortestpathsolver.algorithms;

import com.shortestpathsolver.domain.Node;
import com.shortestpathsolver.domain.ShortestRoute;
import com.shortestpathsolver.structures.CustomArrayList;
import com.shortestpathsolver.structures.Heap;
import com.shortestpathsolver.structures.Pair;

/**
 * A*-algorithm
 *
 * @author kaihartz
 */
public class AStar extends Algorithm {

    private int hCost = 10;
    private int diagonalCost = 14;
    private Heap openList;
    private CustomArrayList<Node> closedSet;
    private ShortestRoute sr;
    private boolean jps; //JPS-algorithm

    public AStar(ShortestRoute sr) {
        super(sr);
        this.sr = sr;
        this.jps = false;
        this.openList = new Heap();
        this.closedSet = new CustomArrayList<>();
    }

    /**
     * The main logic in A* algorithm. Checks if currentNode's neighbour is
     * found in closed set and open list. Updates node's information and checks
     * if a better path exists from currentNode to its neighbour.
     *
     * @param currentNode Current node
     * @param col Neighbour's column
     * @param row Neighbour's row
     * @param cost Movement cost
     */
    private void checkNode(Node currentNode, int col, int row, int cost) {
        Node neighbour = sr.getNodes()[row][col];
        if (!neighbour.isBlock()) {
            if (!closedSet.contains(neighbour) && !openList.contains(neighbour)) {
                neighbour.setAStarInformation(currentNode, cost);
                openList.add(neighbour);
            } else if (closedSet.contains(neighbour)) { // in case the algorithm doesn't work. Otherwise nodes in closed set shouldn't be processed.
                if (checkifBetterPathExists(currentNode, neighbour, cost)) {
                    openList.add(neighbour);
                }
            } else {
                checkifBetterPathExists(currentNode, neighbour, cost);
            }
        }
    }

    /**
     * The main method, which searches the shortest route with either A* or JPS
     *
     * @param initialNode Beginning of the search
     * @return List presenting the route
     */
    @Override
    public CustomArrayList<Node> calculatePath(Node initialNode) {
        openList.add(initialNode);
        while (openList.size() != 0) {
            Node currentNode = (Node) openList.poll(); // The node with smallest distance (f-value)
            closedSet.add(currentNode);
            if (sr.isFinalNode(currentNode)) {
                System.out.println("G: " + currentNode.getG());
                return getPath(currentNode);
            } else {
                if (!jps) {
                    addNeighbourNodes(currentNode); //A*-algorithm
                } else { //A* with JPS
                    Node[] jumpPoints = calculateJumpPoints(currentNode); //Possible jump points in JPS
                    for (int i = 0; i < jumpPoints.length; i++) {
                        Node jumpPoint = jumpPoints[i];
                        if (!(jumpPoint == null || jumpPoint.isBlock())) {
                            if (!closedSet.contains(jumpPoint) && !openList.contains(jumpPoint)) { // Logic in JPS
                                setAStarInformationRange(currentNode, jumpPoint);
                                openList.add(jumpPoint);
                            } else if (closedSet.contains(jumpPoint)) {
                                if (checkifBetterPathExists(currentNode, jumpPoint, approxG(currentNode.getColumn(), currentNode.getRow(), jumpPoint.getColumn(), jumpPoint.getRow()))) {
                                    openList.add(jumpPoint);
                                }
                            } else {
                                checkifBetterPathExists(currentNode, jumpPoint, approxG(currentNode.getColumn(), currentNode.getRow(), jumpPoint.getColumn(), jumpPoint.getRow()));
                            }
                        }
                    }
                }
            }
        }
        return new CustomArrayList<>();
    }

    /**
     * Returns node's jumppoints for JPS. This speeds up the search remarkably.
     *
     * @param node
     * @return possible jump points
     */
    public Node[] calculateJumpPoints(Node node) {
        Node[] jumpPoints = new Node[8];
        Pair[] neighbors = getNeighbours(node); // All neighbours
        for (int i = 0; i < neighbors.length; i++) {

            Pair<Integer, Integer> point = jump((int) neighbors[i].getKey(), (int) neighbors[i].getValue(), node.getColumn(), node.getRow()); //Pair presenting jump point
            if (point.getKey() != -1) {
                int x = point.getKey();
                int y = point.getValue();

                int newG = (approxG(x, y, node.getColumn(), node.getRow()));
                if (sr.getNodes()[y][x].getDist() == 0 || sr.getNodes()[y][x].getG() > newG) {
                    jumpPoints[i] = sr.getNodes()[y][x]; // Jump point is added
                }
            }
        }
        return jumpPoints;
    }

    /**
     * Recursive method, which searches a jump point based on a node and its
     * parent.
     *
     * @param x node's column
     * @param y node's row
     * @param px parent's column
     * @param py parent's row
     * @return coordinates of the possibly found jump point
     */
    public Pair jump(int x, int y, int px, int py) {

        int dx = (x - px) / Math.max(Math.abs(x - px), 1);
        int dy = (y - py) / Math.max(Math.abs(y - py), 1);

        if (!allowed(x, y)) {
            return new Pair(-1, -1);
        }
        if (x == sr.getFinalNode().getColumn() && y == sr.getFinalNode().getRow()) {
            return new Pair(x, y);
        }
        if (dx != 0 && dy != 0) {
            if ((allowed(x - dx, y + dy) && !allowed(x - dx, y)) || (allowed(x + dx, y - dy) && !allowed(x, y - dy))) {
                return new Pair(x, y);
            }
            Pair jumpx = jump(x + dx, y, x, y);
            Pair jumpy = jump(x, y + dy, x, y);
            if ((int) jumpx.getKey() != -1 || (int) jumpy.getKey() != -1) {
                return new Pair(x, y);
            }
        } else {
            if (dx == 0) {
                if ((allowed(x + 1, y + dy) && !allowed(x + 1, y)) || (allowed(x - 1, y + dy) && !allowed(x - 1, y))) {
                    return new Pair(x, y);
                }
            } else {
                if ((allowed(x + dx, y + 1) && !allowed(x, y + 1)) || (allowed(x + dx, y - 1) && !allowed(x, y - 1))) {
                    return new Pair(x, y);
                }
            }
        }

        if (x == 0 && y == 0 && dx == 0 && dy == 0) {
            return new Pair(-1, -1);
        }
        Pair table = jump(x + dx, y + dy, x, y);
        return table;
    }

    /**
     * Returns all allowed node's neighbours
     *
     * @param node
     * @return table presenting neighbours
     */
    @Override
    public Pair[] getNeighbours(Node node) {
        Pair[] neighbors = new Pair[8];
        int x = node.getColumn();
        int y = node.getRow();

        neighbors[0] = allowed(x, y - 1) ? new Pair(x, y - 1) : new Pair(-1, -1);
        neighbors[1] = allowed(x + 1, y) ? new Pair(x + 1, y) : new Pair(-1, -1);
        neighbors[2] = allowed(x, y + 1) ? new Pair(x, y + 1) : new Pair(-1, -1);
        neighbors[3] = allowed(x - 1, y) ? new Pair(x - 1, y) : new Pair(-1, -1);
        neighbors[4] = allowed(x - 1, y - 1) ? new Pair(x - 1, y - 1) : new Pair(-1, -1);
        neighbors[5] = allowed(x + 1, y - 1) ? new Pair(x + 1, y - 1) : new Pair(-1, -1);
        neighbors[6] = allowed(x + 1, y + 1) ? new Pair(x + 1, y + 1) : new Pair(-1, -1);
        neighbors[7] = allowed(x - 1, y + 1) ? new Pair(x - 1, y + 1) : new Pair(-1, -1);

        return neighbors;
    }

    private void addNeighbourNodes(Node currentNode) {
        int row = currentNode.getRow();
        int col = currentNode.getColumn();
        if (row - 1 >= 0) { //ylin rivi
            checkNode(currentNode, col, row - 1, hCost); //upper
            if (col - 1 >= 0) {
                checkNode(currentNode, col - 1, row - 1, diagonalCost); //upper left
            }
            if (col + 1 < sr.getCols()) {
                checkNode(currentNode, col + 1, row - 1, diagonalCost); //upper right
            }
        }
        if (col - 1 >= 0) {
            checkNode(currentNode, col - 1, row, hCost); //left
        }
        if (col + 1 < sr.getCols()) {
            checkNode(currentNode, col + 1, row, hCost); //right
        }
        if (row + 1 < sr.getRows()) { //lower row
            checkNode(currentNode, col, row + 1, hCost); //bottom
            if (col - 1 >= 0) {
                checkNode(currentNode, col - 1, row + 1, diagonalCost); //bottom left
            }
            if (col + 1 < sr.getCols()) {
                checkNode(currentNode, col + 1, row + 1, diagonalCost); //bottom right
            }
        }
    }

    public Heap getOpenList() {
        return openList;
    }

    @Override
    public CustomArrayList<Node> getClosedSet() {
        return closedSet;
    }

    /**
     * Resets nodes, open list, closed set and path
     */
    public void reset() {
        sr.setNodes();
        openList.clear();
        closedSet.clear();
        super.getPath().clear();
    }

    /**
     *
     * Calculates a heuristic from current to finalNode. The pattern may need
     * updating depending on input. The heuristic may yield wrong results if not
     * defined correctly.
     *
     * @param current
     * @param finalNode
     */
    public void calculateHeuristic(Node current, Node finalNode) {
        int h = (int) Math.sqrt(Math.pow(finalNode.getColumn() - current.getColumn(), 3) + Math.pow(finalNode.getRow() - current.getRow(), 3));
        int euclidian = (int) Math.sqrt(Math.pow(finalNode.getColumn() - current.getColumn(), 2) + Math.pow(finalNode.getRow() - current.getRow(), 2));
        int manhattan = (int) (Math.abs(finalNode.getColumn() - current.getColumn()) + Math.abs(finalNode.getRow() - current.getRow()));

        current.setH(euclidian);
    }

    /**
     *
     * Checks if a better path exists between currentNode and neighbour, and if
     * exists, updates neighbour's information.
     *
     * @param currentNode
     * @param neighbour
     * @param cost Movement cost
     * @return true if a better path exists, false otherwise
     */
    public boolean checkifBetterPathExists(Node currentNode, Node neighbour, int cost) {
        int gCost = currentNode.getG() + cost;
        if (gCost < neighbour.getG()) {
            neighbour.setAStarInformation(currentNode, cost);
            return true;
        }
        return false;
    }

    /**
     * Gives an approximation of g value between two nodes.
     *
     * @param x1 x-coordinate of the first node
     * @param y1 y-coordinate of the first node
     * @param x2 x-coordinate of the second node
     * @param y2 y-coordinate of the second node
     *
     * @return Approximation of g cost
     */
    private int approxG(int x1, int y1, int x2, int y2) {
        if (x1 == x2) {
            return Math.abs(y1 - y2) * hCost;
        } else if (y1 == y2) {
            return Math.abs(x1 - x2) * hCost;
        } else {
            return Math.abs(x1 - x2) * diagonalCost;
        }
    }

    public void setJPS(boolean b) {
        this.jps = b;
    }

    /**
     * Updates all Node informations in a range between currentNode and
     * jumpPoint. Used in JPS.
     *
     * @param currentNode
     * @param jumpPoint
     *
     */
    private void setAStarInformationRange(Node currentNode, Node jumpPoint) {
        int col1 = currentNode.getColumn();
        int col2 = jumpPoint.getColumn();
        int row1 = currentNode.getRow();
        int row2 = jumpPoint.getRow();
        int i = 0, j = 0;
        if (col2 < col1) {
            i = -1;
        } else if (col1 < col2) {
            i = 1;
        }
        if (row2 < row1) {
            j = -1;
        } else if (row1 < row2) {
            j = 1;
        }

        int a = col1, b = row1;
        while (true) {
            a += i;
            b += j;

            Node n = sr.getNodes()[b][a];
            int newG = approxG(currentNode.getColumn(), currentNode.getRow(), n.getColumn(), n.getRow());
            if (n.getG() == 0 || currentNode.getG() + newG < n.getG()) {
                if (!sr.isInitialNode(n)) {
                    sr.getNodes()[b][a].setAStarInformation(currentNode, newG);
                }
            }

            if (a == col2 && b == row2) {
                break;
            }
        }
    }

    public boolean getJps() {
        return jps;
    }

    public void setClosedSet(CustomArrayList<Node> cs) {
        this.closedSet = cs;
    }
}
