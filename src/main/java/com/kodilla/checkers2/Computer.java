package com.kodilla.checkers2;

import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Computer {

    Random random= new Random();
    ArrayList<Piece> move= new ArrayList<>();
    List<Piece> beating;
    Field selectedField;


    public void logic(GridPane gridPane) {

        Board.alreadyClickedPiece = new Black();
        beating = Tools.whichPieceCanBreak(gridPane);

        if (beating.size() > 0) {
            Board.alreadyClickedPiece = beating.get(random.nextInt(beating.size()));
            Board.alreadyClickedPiece.possibleBeating();

            Task<Void> pause = new Task<Void>() {
                @Override
                protected Void call() throws Exception {

                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {

                    }
                    return null;
                }

            };

            pause.setOnSucceeded(event -> {

                if (Board.possibleBeating.size() > 0) {
                    selectedField = Board.possibleBeating.get(random.nextInt(Board.possibleBeating.size()));
                    selectedField.clickMouse();
                }
            });

            new Thread(pause).start();

        } else {
            checkPieceMove(gridPane);

            if (move.size() > 0) {
                Board.alreadyClickedPiece = move.get(random.nextInt(move.size()));
                Board.alreadyClickedPiece.possibleMove();

                Task<Void> pause = new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {

                        try {
                            Thread.sleep(600);
                        } catch (InterruptedException e) {

                        }
                        return null;
                    }
                };

                pause.setOnSucceeded(event -> {

                    if (Board.possibleMove.size() > 0) {
                        assignField();
                        selectedField.clickMouse();
                    }
                });
                new Thread(pause).start();
            }
        }
        selectedField= null;
        move.clear();
        beating.clear();
    }

    public void checkPieceMove(GridPane gridPane) {
        {
            for (Node node :gridPane.getChildren()) {
                if (((Field) node).getChildren().size() > 0 && ((Field) node).getChildren().get(0) instanceof Black) {

                    if (((Black) ((Field) node).getChildren().get(0)).movePossible()) {
                        move.add((Piece) ((Field) node).getChildren().get(0));
                    }
                }
            }
        }
    }

    public void assignField() {
        selectedField= Board.possibleMove.get(random.nextInt(Board.possibleMove.size()));
    }
}
