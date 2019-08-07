package com.shortestpathsolver.ui;

import com.shortestpathsolver.domain.ShortestRoute;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
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
    private ColorPicker colorpicker;
    private Color colorpickerColor;
    private int startX;
    private int startY;
    private int goalX;
    private int goalY;
    private Text notFound;

    public Ui(ShortestRoute sr, int width, int height, int rows, int cols, Color color, int startX, int startY, int goalX, int goalY) {
        this.sr = sr;
        this.width = width;
        this.height = height;
        this.rows = rows;
        this.cols = cols;
        this.bgColor = color;
        this.colorpickerColor = Color.BROWN;
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
        primaryStage.setTitle("Shortest path solver");
        primaryStage.show();
    }

    private Pane createPane() {
        colorpicker = new ColorPicker();
        colorpicker.valueProperty().setValue(Color.BROWN);
        BorderPane layout = new BorderPane();
        layout.setCenter(canvas);

        final Button getPath = new Button("Calculate path");
        final Button insert = new Button("Insert");
        final Button clear = new Button("Clear");
        final Button clearAll = new Button("Clear all");

        insert.setDisable(true);
        clear.setDisable(true);
        clearAll.setDisable(true);

        final Text insertText = new Text("Inserting");
        final Text clearText = new Text("Clearing");
        notFound = new Text("No path found");

        insertText.setVisible(true);
        clearText.setVisible(false);
        notFound.setVisible(false);

        VBox vb = new VBox(10);
        vb.getChildren().addAll(colorpicker, insert, clear, clearAll, insertText, clearText, getPath, notFound);
        layout.setRight(vb);

        getPath.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                getPath.setDisable(true);
                sr.setWrited(false);
                clearAll.setDisable(false);
                if (!sr.calculateAStarPath()) {
                    notFound.setVisible(true);
                }
            }
        });

        clear.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                notFound.setVisible(false);
                insertText.setVisible(false);
                clearText.setVisible(true);
                clear.setDisable(true);
                insert.setDisable(false);
                sr.handleClearButtonActions();
            }
        });

        clearAll.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                getPath.setDisable(false);
                canvas.clearArea();
                canvas.fillGrid(width, height);
                canvas.setInitialNode(startY, startX);
                canvas.setFinalNode(goalY, goalX);
                canvas.updateFill(colorpickerColor);
                notFound.setVisible(false);
                insert.setDisable(true);
                clear.setDisable(true);
                clearAll.setDisable(true);
                insertText.setVisible(true);
                clearText.setVisible(false);
                sr.handleClearAllButtonActions();
            }
        });

        insert.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                notFound.setVisible(false);
                sr.handleInsertButtonActions();
                insert.setDisable(true);
                clear.setDisable(false);
                clearText.setVisible(false);
                insertText.setVisible(true);
            }
        });

        EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double x = event.getX();
                double y = event.getY();
                colorpickerColor = colorpicker.getValue();
                canvas.updateFill(colorpickerColor);

                if (x > 0 && x < width && y > 0 && y < height) {
                    if (!sr.getWrited()) {
                        notFound.setVisible(false);
                        sr.setWrited(true);
                        getPath.setDisable(false);
                        canvas.clearArea();
                        canvas.fillGrid(width, height);
                        canvas.setInitialNode(startY, startX);
                        canvas.setFinalNode(goalY, goalX);
                        canvas.updateFill(colorpickerColor);
                        canvas.fillBlocks(sr.getBlocks());
                        sr.resetAStar();
                        clearAll.setDisable(false);
                        if (sr.getInserting()) {
                            clear.setDisable(false);
                        } else {
                            insert.setDisable(false);
                        }
                    }
                    if (!sr.getInserting()) { //removing
                        if (sr.isNotStartOrGoalNode((int) x / 20, (int) y / 20)) {
                            canvas.removeBlock(y, x, bgColor);
                        }
                    } else {
                        if (sr.isNotStartOrGoalNode((int) x / 20, (int) y / 20)) {
                            try {
                                canvas.fillBlock(y, x, colorpicker.getValue());
                            } catch (FileNotFoundException ex) {
                                Logger.getLogger(Ui.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }
            }
        };

        canvas.setOnMousePressed(mouseHandler);
        canvas.setOnMouseDragged(mouseHandler);

        return new Pane(layout);
    }

    public DrawPad getCanvas() {
        return this.canvas;
    }

    public ColorPicker getColorpicker() {
        return this.colorpicker;
    }
}
