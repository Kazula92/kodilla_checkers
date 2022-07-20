package com.kodilla.checkers2;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import static com.kodilla.checkers2.Tools.getNode;

abstract class Piece extends Circle {

    private boolean queen = false;

    public Piece (Color pieceColor) {
        super(30, pieceColor);
    }

    abstract void mouseClick();
    abstract boolean possibleMove();
    abstract boolean movementOrBeating();

    public void setQueen() {

        queen= true;
        super.setStroke(Color.GOLD);
        super.setStrokeWidth(6);

    }

    public boolean queen() {

        return queen;
    }

    public void possibleBeating() {

        Board.alreadyClickedPiece= this;
        GridPane gridPane= (GridPane) this.getParent().getParent();
        int columnPiece= GridPane.getColumnIndex(this.getParent());
        int rowPiece= GridPane.getRowIndex(this.getParent());

        Field rightUp= (Field) getNode(gridPane,rowPiece + 1, columnPiece + 1);
        Field rightDown= (Field) getNode(gridPane, rowPiece - 1, columnPiece + 1);
        Field leftUp= (Field) getNode(gridPane, rowPiece + 1, columnPiece - 1);
        Field leftDown= (Field) getNode(gridPane, rowPiece - 1, columnPiece - 1);

        if (rightUp != null && rightUp.getChildren().size() > 0 && Board.alreadyClickedPiece.getClass() != rightUp.getChildren().get(0).getClass()) {
            if (getNode(gridPane, rowPiece + 2, columnPiece + 2) != null && ((Field) getNode(gridPane, rowPiece + 2,
                    columnPiece + 2)).getChildren().size() == 0) {

                rightUp.selectionColor("green");
                ((Field) getNode(gridPane, rowPiece + 2, columnPiece + 2)).selectionColor("blue");
                Board.possibleBeating.add((Field) getNode(gridPane, rowPiece + 2, columnPiece + 2));
            }
        }

        if (rightDown != null && rightDown.getChildren().size() > 0 && Board.alreadyClickedPiece.getClass() != rightDown.getChildren().get(0).getClass()) {
            if (getNode(gridPane, rowPiece - 2, columnPiece + 2) != null && ((Field) getNode(gridPane, rowPiece - 2,
                    columnPiece + 2)).getChildren().size() == 0) {

                rightDown.selectionColor("green");
                ((Field) getNode(gridPane, rowPiece - 2, columnPiece + 2)).selectionColor("blue");
                Board.possibleBeating.add((Field) getNode(gridPane, rowPiece - 2, columnPiece + 2));
            }
        }

        if (leftUp != null && leftUp.getChildren().size() > 0 && Board.alreadyClickedPiece.getClass() != leftUp.getChildren().get(0).getClass()) {
            if (getNode(gridPane, rowPiece + 2, columnPiece - 2) != null && ((Field) getNode(gridPane, rowPiece + 2,
                    columnPiece - 2)).getChildren().size() == 0) {

                leftUp.selectionColor("green");
                ((Field) getNode(gridPane, rowPiece + 2, columnPiece - 2)).selectionColor("blue");
                Board.possibleBeating.add((Field) getNode(gridPane, rowPiece + 2, columnPiece - 2));
            }
        }
        if (leftDown != null && leftDown.getChildren().size() > 0 && Board.alreadyClickedPiece.getClass() != leftDown.getChildren().get(0).getClass()) {
            if (getNode(gridPane, rowPiece - 2, columnPiece - 2) != null && ((Field) getNode(gridPane, rowPiece - 2,
                    columnPiece - 2)).getChildren().size() ==0) {

                leftDown.selectionColor("green");
                ((Field) getNode(gridPane, rowPiece - 2, columnPiece - 2)).selectionColor("blue");
                Board.possibleBeating.add((Field) getNode(gridPane, rowPiece - 2, columnPiece - 2));
            }
        }
    }
}
