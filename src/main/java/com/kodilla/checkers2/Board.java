package com.kodilla.checkers2;

import javafx.event.Event;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.layout.*;

import java.util.ArrayList;

public class Board {

   public static ArrayList<Field> possibleMove= new ArrayList<>();
   public static ArrayList<Field> possibleBeating= new ArrayList<>();
   public static Piece alreadyClickedPiece;
   public static boolean whiteTurn;
   public static ArrayList<Piece> whitePiece= new ArrayList<>();
   public static ArrayList<Piece> blackPiece= new ArrayList<>();
   public static boolean whitePieceCannotMove;
   public static boolean blackPieceCannotMove;

   public GridPane createCleanBoard() {

      whitePiece.clear();
      blackPiece.clear();

      GridPane grid= new GridPane();
      grid.setAlignment(Pos.CENTER);
      grid.setPadding(new Insets(0,0,0,0));

      for( int row= 0; row < 8; row++) {
         for ( int column= 0; column < 8; column++) {
            Field field;
            if ((row + column) % 2 == 0) {
               field= new Field("yellow");
            } else {
               field= new Field("green");
            }
            grid.add(field,row,column);
            whiteTurn= true;
         }
      }
      for( int i= 0; i < 8; i++) {
         grid.getColumnConstraints().add(new ColumnConstraints(5, Control.USE_COMPUTED_SIZE, Double.MAX_VALUE, Priority.ALWAYS, HPos.CENTER,true));
         grid.getRowConstraints().add(new RowConstraints(5,Control.USE_COMPUTED_SIZE, Double.MAX_VALUE, Priority.ALWAYS, VPos.CENTER, true));
      }
      possibleMove.clear();
      possibleBeating.clear();
      alreadyClickedPiece= null;

      grid.setOnMouseClicked(event -> mouseEvent(event));

      return grid;
   }

   public GridPane createBoardWithPiece() {

      whitePiece.clear();
      blackPiece.clear();
      GridPane grid= createCleanBoard();

      for (Node node: grid.getChildren()) {
         if (GridPane.getRowIndex(node) < 3 && (GridPane.getRowIndex(node) + GridPane.getColumnIndex(node)) % 2 !=0) {
            ((Field) node).addBlackPiece();
         }
         if (GridPane.getRowIndex(node) >= 5 && (GridPane.getRowIndex(node) + GridPane.getColumnIndex(node)) % 2 !=0) {
            ((Field) node).addWhitePiece();
         }
         whiteTurn= true;
      }
      return grid;
   }

   public void mouseEvent(Event event) {
      if (Board.whiteTurn) {
         if (event.getTarget() instanceof Piece) {
            ((Piece) event.getTarget()).mouseClick();
         }
         if (event.getTarget() instanceof Field) {
            ((Field) event.getTarget()).clickMouse();
         }
      }
   }
}
