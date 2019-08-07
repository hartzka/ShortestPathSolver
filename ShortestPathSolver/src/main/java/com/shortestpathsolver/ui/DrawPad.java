package com.shortestpathsolver.ui;

import com.shortestpathsolver.domain.Node;
import com.shortestpathsolver.domain.ShortestRoute;
import java.io.FileNotFoundException;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Piirtoalusta, joka huolehtii graafisten elementtien piirtämisestä
 *
 * @author kaihartz
 */
public class DrawPad extends Canvas {

    private GraphicsContext gc;
    private int width;
    private int height;
    private Ui ui;
    private ShortestRoute sr;

    public DrawPad(ShortestRoute sr, Ui ui, int width, int height) {
        super(width, height);
        this.sr = sr;
        this.ui = ui;
        this.width = width;
        this.height = height;
        gc = super.getGraphicsContext2D();
    }

    /**
     * Tyhjentää ruudukon
     *
     */
    public void clearArea() {
        gc.clearRect(0, 0, width, height);
    }

    /**
     * Päivittää käytettävän värin
     *
     * @param color Väri
     */
    public void updateFill(Color color) {
        gc.setFill(color);
    }

    /**
     * Piirtää polun solmusta toiseen
     *
     * @param y1 Solmun 1 y-koordinaatti
     * @param x1 Solmun 1 x-koordinaatti
     * @param y2 Solmun 2 y-koordinaatti
     * @param x2 Solmun 2 x-koordinaatti
     */
    public void fillPathLine(int y1, int x1, int y2, int x2) {
        gc.setStroke(Color.ORANGE);
        gc.setLineWidth(3.0);
        gc.strokeLine(x1 * 20 + 10, y1 * 20 + 10, x2 * 20 + 10, y2 * 20 + 10);
    }

    /**
     * Piirtää esteen
     *
     * @param positionY Hiiren y-koordinaatti
     * @param positionX Hiiren x-koordinaatti
     * @param color Esteen väri
     *
     * @throws FileNotFoundException
     */
    public void fillBlock(double positionY, double positionX, Color color) throws FileNotFoundException {
        int x = (int) positionX / 20;
        int y = (int) positionY / 20;
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        gc.fillText("X", x * 20 + 1, y * 20 + 19);
        sr.setBlock(y, x, color);
    }

    /**
     * Poistaa esteen
     *
     * @param positionY Hiiren y-koordinaatti
     * @param positionX Hiiren x-koordinaatti
     * @param color Ruudun väri
     */
    public void removeBlock(double positionY, double positionX, Color color) {
        int x = (int) positionX / 20;
        int y = (int) positionY / 20;

        gc.setFill(color);
        gc.fillRect(x * 20 + 1, y * 20 + 1, 18, 18);
        sr.removeBlock(y, x);
    }

    /**
     * Asettaa alkusolmun
     *
     * @param row Alkusolmun rivi
     * @param column Alkusolmun sarake
     * @return Alkusolmun
     */
    public Node setInitialNode(int row, int column) {
        Node n = new Node(row, column, ui.getColorpicker().getValue());
        gc.setFill(Color.BLUE);
        gc.fillRect(column * 20 + 1, row * 20 + 1, 18, 18);
        sr.setInitialNode(n);
        return n;
    }

    /**
     * Asettaa loppusolmun
     *
     * @param row Loppuusolmun rivi
     * @param column Loppusolmun sarake
     * @return Loppusolmun
     */
    public Node setFinalNode(int row, int column) {
        Node n = new Node(row, column, ui.getColorpicker().getValue());
        gc.setFill(Color.GREEN);
        gc.fillRect(column * 20 + 1, row * 20 + 1, 18, 18);
        sr.setFinalNode(n);
        return n;
    }

    /**
     * Piirtää ruudukon
     *
     * @param width Ruudukon leveys
     * @param height Ruudukon korkeus
     */
    public void fillGrid(int width, int height) {
        gc.setLineWidth(1.0);
        gc.setStroke(Color.BLACK);
        for (int x = 0; x <= width; x += 20) {
            gc.strokeLine(x, 0, x, height);
        }

        for (int y = 0; y <= height; y += 20) {
            gc.strokeLine(0, y, width, y);
        }
    }

    /**
     * Piirtää kaikki esteet
     *
     * @param blocks Taulukko esteistä
     */
    public void fillBlocks(boolean[][] blocks) {
        for (int y = 0; y < blocks.length; y++) {
            for (int x = 0; x < blocks[0].length; x++) {
                if (blocks[y][x]) {
                    gc.setFill(sr.getBlockColor(y, x));
                    gc.setFont(Font.font("Arial", FontWeight.BOLD, 24));
                    gc.fillText("X", x * 20 + 1, y * 20 + 19);
                }
            }
        }
        gc.setFill(ui.getColorpicker().getValue());
    }

    /**
     * Piirtää neliön
     *
     * @param y Neliön y-koordinaatti
     * @param x Neliön x-koordinaatti
     * @param c Väri
     */
    public void fillRect(int y, int x, Color c) {
        gc.setFill(c);
        gc.fillRect(x * 20 + 1, y * 20 + 1, 18, 18);
    }
}
