package com.kodilla.checkers2;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;

public class CheckersApp extends Application {

    Board board = new Board();
    GridPane gridPane= board.createBoardWithPiece();
    static TextArea result= new TextArea();
    FlowPane flowPane= new FlowPane();


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        HBox hBox = new HBox();
        hBox.setPadding(new Insets(0, 0, 0, 0));

        Button save= new Button();
        save.setText("Save game");
        save.setMinSize(150, 50);
        save.setOnAction(event -> saveGame());

        Button load= new Button();
        load.setText("Load game");
        load.setMinSize(150, 50);
        load.setOnAction(event -> loadGame());

        Button newg= new Button();
        newg.setText("New game");
        newg.setMinSize(150, 50);
        newg.setOnAction(event -> newGame());

        result.setPrefSize(300,50);
        result.setText("Black: " + (0) + "White: " + (0) + "\nWhite Turn");
        result.setEditable(false);

        hBox.getChildren().addAll(save, load, newg, result);

        flowPane.getChildren().addAll(hBox);
        flowPane.getChildren().addAll(gridPane);

        Scene scene= new Scene(flowPane, 800, 850, Color.BLACK);

        primaryStage.setResizable(false);
        primaryStage.setTitle("Checkers");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void newGame() {
        flowPane.getChildren().removeAll(gridPane);
        gridPane = board.createBoardWithPiece();
        flowPane.getChildren().addAll(gridPane);
        Tools.scoreAndTurn();

    }

    private void saveGame() {
        ArrayList<Data> saveData = new ArrayList<>();

        for (Node node : gridPane.getChildren()) {
            if (((Field) node).getChildren().size() > 0) {
                Data data = new Data(
                        GridPane.getRowIndex(node),
                        GridPane.getColumnIndex(node),
                        ((Field) node).getChildren().get(0) instanceof White,
                        ((Piece) ((Field) node).getChildren().get(0)).queen(),
                        Board.whiteTurn);

                saveData.add(data);

            }
        }

        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("save.txt"));
            out.writeObject(saveData);
            out.close();
            result.setText("Saved");
        } catch (IOException ex) {
            result.setText("Cannot create save file!");
        }
    }

    private void loadGame() {
        try {
            ObjectInputStream stream = stream = new ObjectInputStream(new FileInputStream("save.txt"));
            ArrayList<Data> loadData = (ArrayList<Data>) stream.readObject();

            flowPane.getChildren().removeAll(gridPane);
            gridPane = board.createCleanBoard();
            flowPane.getChildren().addAll(gridPane);

            for (Data data : loadData) {
                if (data.isWhite()) {
                    Piece piece = new White();
                    if (data.isQueen()) {
                        piece.setQueen();
                    }
                    ((Field) Tools.getNode(gridPane, data.getRow(), data.getColumn())).addPiece(piece);
                    Board.whitePiece.add(piece);
                }

                if (!(data.isWhite())) {
                    Piece piece = new Black();
                    if (data.isQueen()) {
                        piece.setQueen();
                    }
                    ((Field) Tools.getNode(gridPane, data.getRow(), data.getColumn())).addPiece(piece);
                    Board.blackPiece.add(piece);
                }

                Board.whiteTurn = data.isWhiteTurn();
                Tools.scoreAndTurn();
            }
        } catch (IOException e) {
            result.setText("Save file not found!");
        } catch (ClassNotFoundException e) {
            result.setText("Save file crashed");
        }
    }
}
