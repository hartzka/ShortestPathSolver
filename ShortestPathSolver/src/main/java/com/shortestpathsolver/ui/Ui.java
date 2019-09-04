package com.shortestpathsolver.ui;

import com.shortestpathsolver.domain.ShortestRoute;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * UI class
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
    private final Button dijkstra;
    private final Button bfs;
    private final Button randomize;
    private final Button read;
    private final Button save;
    private final Button update;
    private Pane pane;
    private int maxRows;
    private Stage primaryStage;

    public Ui(ShortestRoute sr, int width, int height, int rows, int cols, Color color) {
        this.sr = sr;
        this.width = width;
        this.height = height;
        this.rows = rows;
        this.cols = cols;
        this.bgColor = color;
        this.maxRows = 100;
        this.colorpickerColor = Color.BROWN;
        this.canvas = new DrawPad(sr, this, width, height, rows, cols);
        this.insert = new Button("Insert");
        this.clear = new Button("Clear");
        this.clearAll = new Button("Clear all");
        this.randomize = new Button("Randomize blocks");
        this.read = new Button("Read");
        this.save = new Button("Save");
        this.update = new Button("Update");
        this.getPath = new Button("Calculate path");
        this.aStar = new Button("A*");
        this.jps = new Button("JPS");
        this.bfs = new Button("BFS");
        this.dijkstra = new Button("Dijkstra");
    }

    /**
     * Start-metodi, joka käynnistää käyttöliittymän
     *
     * @param primaryStage Käytettävä stage
     */
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
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
        final Text dijkstraText = new Text("Dijkstra");
        final Text bfsText = new Text("BFS");
        notFound = new Text("No path found");

        insertText.setVisible(true);
        clearText.setVisible(false);
        jpsText.setVisible(false);
        dijkstraText.setVisible(false);
        bfsText.setVisible(false);
        notFound.setVisible(false);
        aStar.setDisable(true);

        //Event handlers
        colorpicker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                pane.setStyle("-fx-background-color: #" + colorpicker.getValue().toString().substring(2, 8));
                sr.setBackGround(colorpicker.getValue());
                canvas.reset();
            }
        });

        save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fc = new FileChooser();
                FileChooser.ExtensionFilter ef = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
                fc.getExtensionFilters().add(ef);
                File fileToSave = new File("maps");
                if (!fileToSave.exists()) {
                    fileToSave.mkdirs();
                }
                fc.setInitialDirectory(fileToSave);

                File file = fc.showSaveDialog(primaryStage);
                if (file != null && !file.getName().endsWith(".txt")) {
                    file = new File(file.getAbsolutePath() + ".txt");
                }

                if (file != null) {
                    saveFile(file);
                }
            }
        });

        read.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                FileChooser.ExtensionFilter extentionFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
                fileChooser.getExtensionFilters().add(extentionFilter);
                File userDirectory = new File("maps");
                if (!userDirectory.canRead()) {
                    userDirectory = new File("maps");
                }
                fileChooser.setInitialDirectory(userDirectory);
                File chosenFile = null;
                try {
                    chosenFile = fileChooser.showOpenDialog(null);
                } catch (Exception e) {
                    System.out.println("No records");
                }
                if (chosenFile != null) {
                    try (BufferedReader reader = new BufferedReader(new FileReader(chosenFile))) {
                        String line;
                        int i = 0;
                        while ((line = reader.readLine()) != null) {
                            for (int j = 0; j < cols; j++) {
                                if (j >= line.length()) {
                                    break;
                                }
                                char c = line.charAt(j);
                                if (c != '.') {
                                    sr.setBlock(i, j);
                                } else {
                                    sr.removeBlock(i, j);
                                }
                            }
                            i++;
                            if (i >= rows) {
                                break;
                            }
                        }
                        canvas.reset();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        );

        //Styles
        aStar.getStyleClass().add("orbutton");
        jps.getStyleClass().add("yebutton");
        dijkstra.getStyleClass().add("blbutton");
        bfs.getStyleClass().add("grbutton");
        insert.getStyleClass().add("blbutton");
        clear.getStyleClass().add("orbutton");
        clearAll.getStyleClass().add("redbutton");
        getPath.getStyleClass().add("grbutton");
        randomize.getStyleClass().add("orbutton");
        save.getStyleClass().add("orbutton");
        read.getStyleClass().add("blbutton");
        aStarText.getStyleClass().add("text");
        jpsText.getStyleClass().add("text");
        dijkstraText.getStyleClass().add("text");
        bfsText.getStyleClass().add("text");
        insertText.getStyleClass().add("text");
        clearText.getStyleClass().add("text");
        notFound.getStyleClass().add("notfound");
        aStar.setStyle("-fx-text-fill: white");
        jps.setStyle("-fx-text-fill: white");
        dijkstra.setStyle("-fx-text-fill: white");
        bfs.setStyle("-fx-text-fill: white");
        insert.setStyle("-fx-text-fill: white");
        clear.setStyle("-fx-text-fill: white");
        clearAll.setStyle("-fx-text-fill: white");
        getPath.setStyle("-fx-text-fill: white");
        randomize.setStyle("-fx-text-fill: white");
        save.setStyle("-fx-text-fill: white");
        read.setStyle("-fx-text-fill: white");
        randomize.disableProperty().bind(sr.getButtonsDisable());
        colorpicker.disableProperty().bind(sr.getButtonsDisable());
        getPath.disableProperty().bind(sr.getButtonsDisable());
        save.disableProperty().bind(sr.getButtonsDisable());
        read.disableProperty().bind(sr.getButtonsDisable());
        update.disableProperty().bind(sr.getButtonsDisable());
        clearAll.disableProperty().bind(sr.getButtonsDisable());
        aStar.disableProperty().bind(sr.getAStarInUse());
        dijkstra.disableProperty().bind(sr.getDijkstraInUse());
        jps.disableProperty().bind(sr.getJpsInUse());
        bfs.disableProperty().bind(sr.getBfsInUse());
        GridPane rowsColumns = new GridPane();
        String cssLayout = "-fx-border-color: grey;\n"
                + "-fx-border-insets: 5;\n"
                + "-fx-border-width: 1;\n"
                + "-fx-border-style: solid;\n";
        rowsColumns.setStyle(cssLayout);
        rowsColumns.setAlignment(Pos.CENTER);
        rowsColumns.setHgap(10);
        rowsColumns.setVgap(10);
        rowsColumns.setPadding(new Insets(20, 5, 20, 5));
        final Label rowLabel = new Label("Rows: (3-99)");
        rowsColumns.add(rowLabel, 0, 1);
        final TextField rowsField = new TextField();
        rowsColumns.add(rowsField, 1, 1);
        update.getStyleClass().add("yebutton");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_LEFT);
        hbBtn.getChildren().add(update);
        rowsColumns.add(hbBtn, 1, 4);

        HBox saveRead = new HBox(5);
        saveRead.getChildren().addAll(save, read);

        VBox vb = new VBox(10);
        VBox vb2 = new VBox(0);

        vb2.getChildren().addAll(insertText, clearText);
        vb2.setPadding(new Insets(20, 0, 20, 5));
        vb.getChildren().addAll(colorpicker, insert, clear, clearAll, vb2, randomize, notFound, rowsColumns, saveRead);
        vb.setPadding(new Insets(10, 10, 10, 10));

        HBox hb = new HBox(10);
        HBox hb2 = new HBox(0);

        hb2.getChildren().addAll(aStarText, jpsText, dijkstraText, bfsText);
        hb.getChildren().addAll(aStar, jps, dijkstra, bfs, hb2, getPath);
        hb2.setAlignment(Pos.CENTER);

        hb2.setPadding(new Insets(0, 0, 0, 30));
        hb.setPadding(new Insets(10, 10, 10, 10));

        layout.setRight(vb);

        layout.setBottom(hb);

        //Event handlers
        update.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event
            ) {
                String rowsText = rowsField.getText();
                if (isNumeric(rowsText)) {
                    int rows = Integer.parseInt(rowsText);
                    if (rows > 2 && rows < maxRows) {
                        canvas.updateRowsAndCols(rows);
                        sr.updateRowsAndCols(rows);
                        updateRowsAndCols(rows);
                    }
                }
            }

            private boolean isNumeric(String text) {
                try {
                    int d = Integer.parseInt(text);
                } catch (NumberFormatException | NullPointerException e) {
                    return false;
                }
                return true;
            }
        }
        );
        getPath.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sr.setButtonsDisable(true);
                if (!sr.getWrited()) {
                    notWritedActions();
                }
                sr.setWrited(false);
                boolean found = sr.handleCalculatePathButtonActions();
                if (!found) {
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
                canvas.clearArea();
                canvas.fillGrid(width, height);
                canvas.setInitialNode(sr.getStartY(), sr.getStartX());
                canvas.setFinalNode(sr.getGoalY(), sr.getGoalX());
                canvas.updateFill(colorpickerColor);
                notFound.setVisible(false);
                insert.setDisable(true);
                clear.setDisable(true);
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

        randomize.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                boolean[][] blocks = sr.randomizeBlocks();
                canvas.reset();
                canvas.fillBlocks(blocks);
            }
        });

        aStar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (sr.getButtonsDisable().get() == false) {
                    sr.handleAStarButtonActions();
                    aStarText.setVisible(true);
                    jpsText.setVisible(false);
                    dijkstraText.setVisible(false);
                    bfsText.setVisible(false);
                }
            }
        });

        jps.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (sr.getButtonsDisable().get() == false) {
                    sr.handleJpsButtonActions();
                    jpsText.setVisible(true);
                    aStarText.setVisible(false);
                    dijkstraText.setVisible(false);
                    bfsText.setVisible(false);
                }
            }
        });

        dijkstra.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (sr.getButtonsDisable().get() == false) {
                    sr.handleDijkstraButtonActions();
                    jpsText.setVisible(false);
                    aStarText.setVisible(false);
                    dijkstraText.setVisible(true);
                    bfsText.setVisible(false);
                }
            }
        });

        bfs.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (sr.getButtonsDisable().get() == false) {
                    sr.handleBfsButtonActions();
                    jpsText.setVisible(false);
                    aStarText.setVisible(false);
                    dijkstraText.setVisible(false);
                    bfsText.setVisible(true);
                }
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
                if (!sr.getPathVisualize()) {
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
            }
        };

        canvas.setOnMousePressed(mouseHandler);
        canvas.setOnMouseDragged(mouseHandler);
        canvas.setOnMouseReleased(mouseReleaseHandler);

        return new Pane(layout);
    }

    /**
     *
     * The actions when user has not yet filled blocks
     *
     */
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

    /**
     *
     * File saving
     *
     * @param file The file to save
     */
    private void saveFile(File file) {
        try {
            PrintWriter writer;
            writer = new PrintWriter(file);
            writer.println(sr.getContent());
            writer.close();

        } catch (IOException ex) {
            Logger.getLogger(Ui.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    private void updateRowsAndCols(int rows) {
        this.rows = rows;
        int rowGap = height / rows;
        this.cols = width / rowGap;
    }
}
