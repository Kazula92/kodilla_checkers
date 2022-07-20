package com.kodilla.checkers2;

import java.io.Serializable;

public class Data implements Serializable {

    private int row;
    private int column;
    private boolean white;
    private boolean queen;
    private boolean whiteTurn;

    public Data(int row, int column, boolean white, boolean queen, boolean whiteTurn) {
        this.row = row;
        this.column = column;
        this.white = white;
        this.queen = queen;
        this.whiteTurn = whiteTurn;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public boolean isWhite() {
        return white;
    }

    public boolean isQueen() {
        return queen;
    }

    public boolean isWhiteTurn() {
        return whiteTurn;
    }
}
