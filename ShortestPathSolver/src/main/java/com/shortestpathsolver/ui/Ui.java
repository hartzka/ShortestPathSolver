package com.shortestpathsolver.ui;

import com.shortestpathsolver.domain.ShortestRoute;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Käyttöliittymäluokka
 *
 * @author kaihartz
 */
public class Ui {

    private ShortestRoute sr;
    private DrawPad canvas;
    private int width;
    private int height;
    private int rows;
    private int cols;
    private Color bgColor;
    private int startX;
    private int startY;
    private int goalX;
    private int goalY;

    public Ui(ShortestRoute sr, int width, int height, int rows, int cols, Color color, int startX, int startY, int goalX, int goalY) {
        this.sr = sr;
        this.width = width;
        this.height = height;
        this.rows = rows;
        this.cols = cols;
        this.bgColor = color;
        this.startX = startX;
        this.startY = startY;
        this.goalX = goalX;
        this.goalY = goalY;
        this.canvas = new DrawPad(sr, this, width, height);
    }

    /**
     * Start-metodi, joka käynnistää käyttöliittymän
     *
     * @param primaryStage Käytettävä stage
     */
    public void start(Stage primaryStage) {
        Pane pane = createPane();
        pane.setStyle("-fx-background-color: #" + bgColor.toString().substring(2, 8));
        Scene scene = new Scene(pane);
        scene.getStylesheets().add("/styles/Styles.css");
        canvas.fillGrid(width, height);
        canvas.setInitialNode(startY, startX);
        canvas.setFinalNode(goalY, goalX);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private Pane createPane() {

        BorderPane layout = new BorderPane();
        layout.setCenter(canvas);

        final Button getPath = new Button("Calculate path");

        VBox vb = new VBox(10);
        vb.getChildren().addAll(getPath);
        layout.setRight(vb);
        getPath.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                getPath.setDisable(true);
                sr.setWrited(false);
                sr.calculateAStarPath();
            }
        });

        EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double x = event.getX();
                double y = event.getY();

                if (x > 0 && x < width && y > 0 && y < height) {
                    if (!sr.getWrited()) {
                        sr.setWrited(true);
                        getPath.setDisable(false);
                        canvas.clearArea();
                        canvas.fillGrid(width, height);
                        canvas.setInitialNode(startY, startX);
                        canvas.setFinalNode(goalY, goalX);
                        canvas.fillBlocks(sr.getBlocks());
                        sr.resetAStar();
                    }

                    if (sr.isNotStartOrGoalNode((int) x / 20, (int) y / 20)) {
                        try {
                            canvas.fillBlock(y, x, Color.BLACK);
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(Ui.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                }
            }
        };

        canvas.setOnMousePressed(mouseHandler);
        canvas.setOnMouseDragged(mouseHandler);

        return new Pane(layout);
    }

    /**
     * Asettaa Alkusolmun
     *
     * @param startY Alkusolmun y-koordinaatti
     * @param startX Alkusolmun x-koordinaatti
     */
    public void setInitialNode(int startY, int startX) {
        canvas.setInitialNode(startY, startX);
    }

    /**
     * Asettaa Loppusolmun
     *
     * @param goalY Loppusolmun y-koordinaatti
     * @param goalX Loppusolmun x-koordinaatti
     */
    public void setFinalNode(int goalY, int goalX) {
        canvas.setFinalNode(goalY, goalX);
    }

    public DrawPad getCanvas() {
        return this.canvas;
    }
}
