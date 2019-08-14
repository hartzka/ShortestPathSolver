package com.shortestpathsolver.ui;

import com.shortestpathsolver.domain.ShortestRoute;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
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
    private Text notFound;
    private final Button clear;
    private final Button clearAll;
    private final Button insert;
    private final Button getPath;
    private final Button aStar;
    private final Button jps;
    private Pane pane;

    public Ui(ShortestRoute sr, int width, int height, int rows, int cols, Color color) {
        this.sr = sr;
        this.width = width;
        this.height = height;
        this.rows = rows;
        this.cols = cols;
        this.bgColor = color;
        this.colorpickerColor = Color.BROWN;
        this.canvas = new DrawPad(sr, this, width, height);
        this.getPath = new Button("Calculate path");
        this.insert = new Button("Insert");
        this.clear = new Button("Clear");
        this.clearAll = new Button("Clear all");
        this.aStar = new Button("A*");
        this.jps = new Button("JPS");
    }

    /**
     * Start-metodi, joka käynnistää käyttöliittymän
     *
     * @param primaryStage Käytettävä stage
     */
    public void start(Stage primaryStage) {
        pane = createPane();
        pane.setStyle("-fx-background-color: #" + bgColor.toString().substring(2, 8));

        Scene scene = new Scene(pane);
        scene.getStylesheets().add("/styles/Styles.css");
        canvas.fillGrid(width, height);
        canvas.setInitialNode(sr.getStartY(), sr.getStartX());
        canvas.setFinalNode(sr.getGoalY(), sr.getGoalX());
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Shortest path solver");
        primaryStage.show();
    }

    private Pane createPane() {
        colorpicker = new ColorPicker();
        colorpicker.valueProperty().setValue(bgColor);
        BorderPane layout = new BorderPane();
        layout.setCenter(canvas);

        insert.setDisable(true);
        clear.setDisable(true);
        clearAll.setDisable(true);

        final Text insertText = new Text("Inserting");
        final Text clearText = new Text("Clearing");

        final Text aStarText = new Text("A*");
        final Text jpsText = new Text("JPS");
        notFound = new Text("No path found");

        insertText.setVisible(true);
        clearText.setVisible(false);
        jpsText.setVisible(false);
        notFound.setVisible(false);
        aStar.setDisable(true);

        aStar.getStyleClass().add("orbutton");
        jps.getStyleClass().add("yebutton");
        insert.getStyleClass().add("blbutton");
        clear.getStyleClass().add("orbutton");
        clearAll.getStyleClass().add("redbutton");
        getPath.getStyleClass().add("grbutton");
        aStarText.getStyleClass().add("text");
        jpsText.getStyleClass().add("text");
        insertText.getStyleClass().add("text");
        clearText.getStyleClass().add("text");
        notFound.getStyleClass().add("notfound");
        aStar.setStyle("-fx-text-fill: white");
        jps.setStyle("-fx-text-fill: white");
        insert.setStyle("-fx-text-fill: white");
        clear.setStyle("-fx-text-fill: white");
        clearAll.setStyle("-fx-text-fill: white");
        getPath.setStyle("-fx-text-fill: white");

        colorpicker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                pane.setStyle("-fx-background-color: #" + colorpicker.getValue().toString().substring(2, 8));
            }
        });

        VBox vb = new VBox(10);
        VBox vb2 = new VBox(0);
        vb2.getChildren().addAll(insertText, clearText);
        vb2.setPadding(new Insets(20, 0, 20, 5));
        vb.getChildren().addAll(colorpicker, insert, clear, clearAll, vb2, getPath, notFound);
        vb.setPadding(new Insets(10, 10, 10, 10));

        HBox hb = new HBox(10);
        HBox hb2 = new HBox(0);
        hb2.getChildren().addAll(aStarText, jpsText);
        hb.getChildren().addAll(aStar, jps, hb2);
        hb2.setAlignment(Pos.CENTER);
        hb2.setPadding(new Insets(0, 0, 0, 30));
        hb.setPadding(new Insets(10, 10, 10, 10));

        layout.setRight(vb);
        layout.setBottom(hb);

        getPath.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                getPath.setDisable(true);
                if (!sr.getWrited()) {
                    notWritedActions();
                }
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
                canvas.setInitialNode(sr.getStartY(), sr.getStartX());
                canvas.setFinalNode(sr.getGoalY(), sr.getGoalX());
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

        aStar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sr.handleAStarButtonActions();
                aStar.setDisable(true);
                jps.setDisable(false);
                jpsText.setVisible(false);
                aStarText.setVisible(true);
            }
        });

        jps.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sr.handleJpsButtonActions();
                aStar.setDisable(false);
                jps.setDisable(true);
                jpsText.setVisible(true);
                aStarText.setVisible(false);
            }
        });

        EventHandler<MouseEvent> mouseReleaseHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                sr.setNodesMovementsOff();
            }
        };

        EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double x = event.getX();
                double y = event.getY();
                colorpickerColor = colorpicker.getValue();
                canvas.updateFill(colorpickerColor);

                if (x > 0 && x < width && y > 0 && y < height) {
                    if (!sr.getWrited()) {
                        notWritedActions();
                    }
                    sr.handleMouseAction(x, y);
                }
            }
        };

        canvas.setOnMousePressed(mouseHandler);
        canvas.setOnMouseDragged(mouseHandler);
        canvas.setOnMouseReleased(mouseReleaseHandler);

        return new Pane(layout);
    }

    private void notWritedActions() {
        notFound.setVisible(false);
        sr.setWrited(true);
        canvas.clearArea();
        canvas.fillGrid(width, height);
        canvas.setInitialNode(sr.getStartY(), sr.getStartX());
        canvas.setFinalNode(sr.getGoalY(), sr.getGoalX());
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

    public DrawPad getCanvas() {
        return this.canvas;
    }

    public ColorPicker getColorpicker() {
        return this.colorpicker;
    }

    public void setGetPathDisable(boolean b) {
        getPath.setDisable(b);
    }

    public Color getBgColor() {
        return bgColor;
    }
}
