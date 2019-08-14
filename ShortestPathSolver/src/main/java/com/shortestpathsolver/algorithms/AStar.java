package com.shortestpathsolver.algorithms;

import com.shortestpathsolver.domain.Node;
import com.shortestpathsolver.domain.ShortestRoute;
import com.shortestpathsolver.structures.CustomArrayList;
import com.shortestpathsolver.structures.Heap;
import com.shortestpathsolver.structures.Pair;

/**
 * A*-algoritmin ominaisuudet toteuttava luokka
 *
 * @author kaihartz
 */
public class AStar {

    private int hCost = 10;
    private int diagonalCost = 14;
    private Heap openList;
    private CustomArrayList<Node> closedSet;
    private ShortestRoute sr;
    private CustomArrayList<Node> finalPath;
    private boolean jps; //JPS-algorithm

    public AStar(ShortestRoute sr) {
        this.sr = sr;
        this.jps = false;
        this.openList = new Heap();
        this.closedSet = new CustomArrayList<>();
        this.finalPath = new CustomArrayList<>();
    }

    private void checkNode(Node currentNode, int col, int row, int cost) {
        Node neighbour = sr.getNodes()[row][col];
        if (!(neighbour.isBlock() || closedSet.contains(neighbour))) {
            if (!openList.contains(neighbour)) {
                neighbour.setAStarInformation(currentNode, cost);
                openList.add(neighbour);
            } else {
                checkifBetterPathExists(currentNode, neighbour, cost);
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
        while (openList.size() != 0) {
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
                        Node jumpPoint = jumpPoints[i];
                        if (!(jumpPoint == null || jumpPoint.isBlock())) {
                            if (!closedSet.contains(jumpPoint)) {
                                if (!openList.contains(jumpPoint)) {
                                    setAStarInformationRange(currentNode, jumpPoint);
                                    openList.add(jumpPoint);
                                } else {
                                    checkifBetterPathExists(currentNode, jumpPoint, approxG(currentNode.getColumn(), currentNode.getRow(), jumpPoint.getColumn(), jumpPoint.getRow()));
                                }
                            } else {
                                setAStarInformationRange(currentNode, jumpPoint);
                            }
                        }
                    }
                }
            }
        }
        return new CustomArrayList<Node>();
    }

    /**
     * Palauttaa JPS-haun mahdolliset jump pointit tietylle solmulle. Tämä
     * nopeuttaa hakua huomattavasti tietyissä ruudukoissa, joissa on
     * suoraviivainen reitti.
     *
     * @param node Solmu
     * @return mahdolliset jump pointit
     */
    public Node[] calculateJumpPoints(Node node) {
        Node[] jumpPoints = new Node[8];
        Pair[] neighbors = getNeighbors(node);
        for (int i = 0; i < neighbors.length; i++) {

            Pair point = jump(neighbors[i].getX(), neighbors[i].getY(), node.getColumn(), node.getRow());
            if (point.getX() != -1) {
                int x = point.getX();
                int y = point.getY();

                int newG = (approxG(x, y, node.getColumn(), node.getRow()));
                if (sr.getNodes()[y][x].getF() == 0 || sr.getNodes()[y][x].getG() > newG) {
                    jumpPoints[i] = sr.getNodes()[y][x];
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
            if (jumpx.getX() != -1 || jumpy.getX() != -1) {
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
     * Palauttaa solmun kaikki naapurit, joihin on mahdollista liikkua
     *
     * @param node solmu
     * @return taulukon naapureista
     */
    public Pair[] getNeighbors(Node node) {
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

    public Heap getOpenList() {
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
     * Laskee arvion etäisyydestä loppusolmuun. Käytettävää laskukaavaa voi olla
     * tarpeen muuttaa riippuen syötteestä, mutta nykyisellään tämän on katsottu
     * toimivan.
     *
     * @param current Käsiteltävä solmu
     * @param finalNode Loppusolmu
     */
    public void calculateHeuristic(Node current, Node finalNode) {
        int h = (int) Math.sqrt(Math.pow(finalNode.getColumn() - current.getColumn(), 3) + Math.pow(finalNode.getRow() - current.getRow(), 3));
        current.setH(h);
    }

    /**
     *
     * Tarkistaa, onko olemassa parempaa polkua solmujen välillä, ja mikäli on,
     * tekee tarvittavat toimenpiteet, mm. päivittää solmun g-arvon ja
     * vanhemman.
     *
     * @param currentNode Tämänhetkinen solmu
     * @param neighbour Naapurisolmu
     * @param cost Kustannus
     * @return true, jos parempi polku on olemassa, muuten false
     */
    public boolean checkifBetterPathExists(Node currentNode, Node neighbour, int cost) {
        int gCost = currentNode.getG() + cost;
        if (gCost < neighbour.getG()) {
            neighbour.setAStarInformation(currentNode, cost);
            return true;
        }
        return false;
    }

    private int approxG(int x1, int y1, int x2, int y2) {
        if (x1 == x2) {
            return Math.abs(y1 - y2) * hCost;
        } else if (y1 == y2) {
            return Math.abs(x1 - x2) * hCost;
        } else {
            return Math.abs(x1 - x2) * diagonalCost;
        }
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

    public void setPath(CustomArrayList<Node> path) {
        this.finalPath = path;
    }
}
