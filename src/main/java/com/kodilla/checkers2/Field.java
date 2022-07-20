package com.kodilla.checkers2;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class Field extends StackPane {

    private Piece piece;
    public boolean possibleSubsequentBeating;
    public String defaultColour;
    Computer computer= new Computer();

    public Field (String defaultColour) {
        this.setMaxSize(100.0,100.0);
        this.setMinSize(100.0,100.0);
        this.defaultColour= defaultColour;
        this.setStyle("-fx-background-color: " + defaultColour + ";");
    }

    public void selectionColor(String color) {
        this.setStyle("-fx-background-color: " + color + ";");
    }

    public void paintDefaultColour() {
        this.setStyle("-fx-background-color: " + defaultColour + ";");
    }

    public void addBlackPiece() {
        piece = new Black();
        this.getChildren().addAll(piece);
        Board.blackPiece.add(piece);
    }

    public void addWhitePiece() {
        piece = new White();
        this.getChildren().addAll(piece);
        Board.whitePiece.add(piece);
    }

    public void addPiece (Piece piece) {
        this.getChildren().addAll(piece);
        this.piece= piece;
    }

    public void removePiece() {
        this.getChildren().removeAll(piece);
    }

    public void clickMouse() {
        if (Board.possibleBeating.contains(this)) {

            possibleSubsequentBeating= false;
            beatPiece();
            System.out.println("Successful capture of a pawn");
            System.out.println(possibleSubsequentBeating);

            if (!(possibleSubsequentBeating)) {
                blockedMoveLogic();
            }
        } else if (Board.possibleMove.contains(this)) {

            movePiece();
            blockedMoveLogic();
        }
        Tools.boardColors((GridPane) this.getParent());

        if(!(Board.whiteTurn)) {
            computer.logic((GridPane) this.getParent());
        }
    }

    private void beatPiece() {

        int row= GridPane.getRowIndex(this);
        int column= GridPane.getColumnIndex(this);
        int rowAlreadyClickedPiece= GridPane.getRowIndex(Board.alreadyClickedPiece.getParent());
        int columnAlreadyClickedPiece= GridPane.getColumnIndex(Board.alreadyClickedPiece.getParent());
        int rowBeatingPiece= (row + rowAlreadyClickedPiece) / 2;
        int columnBeatingPiece= (column + columnAlreadyClickedPiece) / 2;

        Piece kickPiece= ((Field) Tools.getNode(((GridPane) this.getParent()),rowBeatingPiece, columnBeatingPiece)).piece;
        ((Field) Board.alreadyClickedPiece.getParent()).removePiece();
        this.addPiece(Board.alreadyClickedPiece);
        ((Field) Tools.getNode(((GridPane) this.getParent()),rowBeatingPiece, columnBeatingPiece)).removePiece();

        Board.blackPiece.remove(kickPiece);
        Board.whitePiece.remove(kickPiece);

        queenLogic();
        System.out.println("Verification...");
        if (Tools.canBreak(Board.alreadyClickedPiece)) {
            possibleSubsequentBeating= true;
        }

        Board.possibleMove.clear();
        Board.possibleBeating.clear();
    }

    private void movePiece() {

        ((Field) Board.alreadyClickedPiece.getParent()).removePiece();
        this.addPiece(Board.alreadyClickedPiece);
        queenLogic();
        Board.possibleMove.clear();
    }

    private void blockedMoveLogic() {

        Board.whiteTurn= !(Board.whiteTurn);
        Tools.checkPossibilityToMakeMove((GridPane) this.getParent());
        Tools.scoreAndTurn();

        if (!(Board.whitePiece.isEmpty()) && !(Board.blackPiece.isEmpty())) {

            if (Board.whiteTurn && Board.whitePieceCannotMove) {
                CheckersApp.result.setText("White player blocked. \nWinner is Black!!!");
            } else if (!(Board.whiteTurn) && Board.blackPieceCannotMove) {
                CheckersApp.result.setText("Black player blocked. \nWinner is White!!!");
            }
        }
        Board.alreadyClickedPiece= null;
    }

    private void queenLogic() {

        if (Board.alreadyClickedPiece instanceof White && GridPane.getRowIndex(this) == 0) {
            Board.alreadyClickedPiece.setQueen();
        }
        if (Board.alreadyClickedPiece instanceof Black && GridPane.getColumnIndex(this) == 7) {
            Board.alreadyClickedPiece.setQueen();
        }
    }
}
