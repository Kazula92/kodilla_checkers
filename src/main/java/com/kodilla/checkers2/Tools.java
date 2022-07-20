package com.kodilla.checkers2;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

public class Tools {

    public static Node getNode(GridPane gridPane, int row, int column) {
        for (Node node: gridPane.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                return node;
            }
        }
        return null;
    }

    public static void boardColors(GridPane grid) {
        for (Node node: grid.getChildren()) {
            ((Field) node).paintDefaultColour();
        }
    }

    public static boolean canBreak(Piece piece) {
        GridPane gridPane= (GridPane) piece.getParent().getParent();
        int columnPiece= GridPane.getColumnIndex(piece.getParent());
        int rowPiece= GridPane.getRowIndex(piece.getParent());

        Field rightUp= (Field) getNode(gridPane,rowPiece - 1, columnPiece + 1);
        Field rightDown= (Field) getNode(gridPane, rowPiece + 1, columnPiece + 1);
        Field leftUp= (Field) getNode(gridPane, rowPiece - 1, columnPiece - 1);
        Field leftDown= (Field) getNode(gridPane, rowPiece + 1, columnPiece - 1);

        if (rightUp != null && rightUp.getChildren().size() > 0 && rightUp.getChildren().get(0).getClass() != piece.getClass()
        && rightUp.getChildren().get(0) instanceof Piece) {
            if (getNode(gridPane, rowPiece -2, columnPiece + 2) != null && ((Field) getNode(gridPane, rowPiece - 2,
                    columnPiece + 2)).getChildren().size() == 0) {
                return true;
            }
        }

        if (rightDown != null && rightDown.getChildren().size() > 0 && rightDown.getChildren().get(0).getClass() != piece.getClass()
        && rightDown.getChildren().get(0) instanceof Piece) {
            if (getNode(gridPane, rowPiece + 2, columnPiece + 2) != null && ((Field) getNode(gridPane, rowPiece + 2,
                    columnPiece + 2)).getChildren().size() == 0) {
                return true;
            }
        }

        if (leftUp != null && leftUp.getChildren().size() > 0 && leftUp.getChildren().get(0).getClass() != piece.getClass()
        && leftUp.getChildren().get(0) instanceof Piece) {
            if (getNode(gridPane, rowPiece - 2, columnPiece - 2) != null && ((Field) getNode(gridPane, rowPiece - 2,
                    columnPiece - 2)).getChildren().size() == 0) {
                return true;
            }
        }
        if (leftDown != null && leftDown.getChildren().size() > 0 && leftDown.getChildren().get(0).getClass() != piece.getClass()
        && leftDown.getChildren().get(0) instanceof Piece) {
            if (getNode(gridPane, rowPiece + 2, columnPiece - 2) != null && ((Field) getNode(gridPane, rowPiece + 2,
                    columnPiece - 2)).getChildren().size() ==0) {
                return true;
            }
        }
        return false;
    }

    public static List<Piece> whichPieceCanBreak (GridPane gridPane) {
        List<Piece> pieceList = new ArrayList<>();
        for (Node node: gridPane.getChildren()) {
            if (Board.whiteTurn) {
                if (((Field) node).getChildren().size() > 0 && ((Field) node).getChildren().get(0) instanceof White) {
                    if (canBreak((Piece)((Field) node).getChildren().get(0))) {
                        pieceList.add((Piece) ((Field) node).getChildren().get(0));
                    }
                }
            }
            if (!Board.whiteTurn) {
                if (((Field) node).getChildren().size() > 0 && ((Field) node).getChildren().get(0) instanceof Black) {
                    if (canBreak((Piece) ((Field) node).getChildren().get(0))) {
                        pieceList.add((Piece) ((Field) node).getChildren().get(0));
                    }
                }
            }
        }
        return pieceList;
    }

    public static void scoreAndTurn() {
        if (Board.whiteTurn) {
            CheckersApp.result.setText("Black: " + (12 - Board.whitePiece.size()) + "    White: " + (12 - Board.blackPiece.size()) + "\nWhite Turn!");
        }
        if (!(Board.whiteTurn)) {
            CheckersApp.result.setText("Black: " + (12 - Board.whitePiece.size()) + "    White: " + (12 - Board.blackPiece.size()) + "\nBlack Turn!");
        }

        endGame();
    }

    public static void checkPossibilityToMakeMove (GridPane gridPane) {
        if (Board.whiteTurn) {
            Board.whitePieceCannotMove = true;
            for (Node field: gridPane.getChildren()) {
                for (Node piece: ((Field) field).getChildren()) {
                    if (piece instanceof White && ((Piece) piece).movementOrBeating()) {
                        Board.whitePieceCannotMove= false;
                    }
                }
            }
        }
        if (!(Board.whiteTurn)) {
            Board.blackPieceCannotMove = true;
            for (Node field: gridPane.getChildren()) {
                for (Node piece: ((Field) field).getChildren()) {
                    if (piece instanceof Black && ((Piece) piece).movementOrBeating()) {
                        Board.blackPieceCannotMove= false;
                    }
                }
            }
        }
    }
    private static void endGame() {
        if (Board.whitePiece.size() == 0) {
            CheckersApp.result.setText("Winner is Black");
        }
        if (Board.blackPiece.size() == 0) {
            CheckersApp.result.setText("Winner is White");
        }
    }
}
