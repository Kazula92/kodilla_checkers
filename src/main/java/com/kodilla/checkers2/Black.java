package com.kodilla.checkers2;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class Black extends Piece {

    public Black() {
        super(Color.BLACK);
    }

    @Override
    void mouseClick() {

        if (Board.alreadyClickedPiece != null) {
            Tools.boardColors((GridPane) this.getParent().getParent());
            Board.possibleMove.clear();
            Board.alreadyClickedPiece = null;
        }
        if (!(Board.whiteTurn)) {
            Board.alreadyClickedPiece = this;
            if (Tools.canBreak(this)) {
                possibleBeating();
            } else if (Tools.whichPieceCanBreak((GridPane) this.getParent().getParent()).size() == 0) {
                this.possibleMove();
            }
        }
    }

    @Override
    boolean possibleMove() {

        int row = GridPane.getRowIndex(this.getParent());
        int column = GridPane.getColumnIndex(this.getParent());

        Field rightDown = (Field) Tools.getNode((GridPane) this.getParent().getParent(), (row + 1), (column - 1));
        Field leftDown = (Field) Tools.getNode((GridPane) this.getParent().getParent(), (row + 1), (column + 1));

        if (rightDown != null && rightDown.getChildren().size() == 0) {
            Board.possibleMove.add(rightDown);
        }
        if (leftDown != null && leftDown.getChildren().size() == 0) {
            Board.possibleMove.add(leftDown);
        }

        if (queen()) {
            Field rightUp = (Field) Tools.getNode((GridPane) this.getParent().getParent(), (row - 1), (column - 1));
            Field leftUp = (Field) Tools.getNode((GridPane) this.getParent().getParent(), (row - 1), (column + 1));

            if (rightUp != null && rightUp.getChildren().size() == 0) {
                Board.possibleMove.add(rightUp);
            }
            if (leftUp != null && leftUp.getChildren().size() == 0) {
                Board.possibleMove.add(leftUp);
            }
        }
        return false;
    }


    @Override
    boolean movementOrBeating() {

        int row= GridPane.getRowIndex(this.getParent());
        int column= GridPane.getColumnIndex(this.getParent());

        if (Tools.canBreak(this)) {
            return true;
        }

        Field rightDown = (Field) Tools.getNode((GridPane) this.getParent().getParent(), (row + 1), (column - 1));
        Field leftDown = (Field) Tools.getNode((GridPane) this.getParent().getParent(), (row + 1), (column + 1));

        if (rightDown != null && rightDown.getChildren().size() == 0) {
            return true;
        }
        if (leftDown != null && leftDown.getChildren().size() == 0) {
            return true;
        }

        if (queen()) {
            Field rightUp = (Field) Tools.getNode((GridPane) this.getParent().getParent(), (row - 1), (column - 1));
            Field leftUp = (Field) Tools.getNode((GridPane) this.getParent().getParent(), (row - 1), (column + 1));

            if (rightUp != null && rightUp.getChildren().size() == 0) {
                return true;
            }
            if (leftUp != null && leftUp.getChildren().size() == 0) {
                return true;
            }
        }
        return false;
    }

    boolean movePossible() {

        int row = GridPane.getRowIndex(this.getParent());
        int column = GridPane.getColumnIndex(this.getParent());

        Field rightDown = (Field) Tools.getNode((GridPane) this.getParent().getParent(), (row + 1), (column - 1));
        Field leftDown = (Field) Tools.getNode((GridPane) this.getParent().getParent(), (row + 1), (column + 1));

        if (rightDown != null && rightDown.getChildren().size() == 0) {
            return true;
        }
        if (leftDown != null && leftDown.getChildren().size() == 0) {
            return true;
        }

        if (queen()) {
            Field rightUp = (Field) Tools.getNode((GridPane) this.getParent().getParent(), (row - 1), (column - 1));
            Field leftUp = (Field) Tools.getNode((GridPane) this.getParent().getParent(), (row - 1), (column + 1));

            if (rightUp != null && rightUp.getChildren().size() == 0) {
                return true;
            }
            if (leftUp != null && leftUp.getChildren().size() == 0) {
                return true;
            }
        }
        return false;
    }
}
