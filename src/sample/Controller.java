package sample;
import javafx.fxml.FXML;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.util.*;
import java.util.List;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Controller{
    private int playerturn = 0;
    @FXML
    private GridPane PieceGrids;

    private HashMap<Point, Node> allBoardPieces = new HashMap<>();
    private List<Node> allTilePieces = new ArrayList<>();
    private HashSet<Point> possibleMoves = new HashSet<>();



    public void boardClicked(MouseEvent e){
        getAllPieces();
        Node clickNode = e.getPickResult().getIntersectedNode();
        int colIndex = GridPane.getColumnIndex(clickNode);
        int rowIndex = GridPane.getRowIndex(clickNode);

        if((clickNode instanceof Circle || clickNode instanceof ImageView)&&
                clickNode.getId().equals(turnID(playerturn)) ){
            movesPieces(clickNode, colIndex, rowIndex);
            for (Node n : allTilePieces) {
                n.setOnMouseClicked(ee -> {
                    if (possibleMoves.contains(new Point(GridPane.getColumnIndex(n), GridPane.getRowIndex(n)))) {

                        if (kingRow(playerturn) == GridPane.getRowIndex(n)) {
                            makeKing(clickNode, GridPane.getColumnIndex(n),GridPane.getRowIndex(n));
                        }
                        else{
                            GridPane.setColumnIndex(clickNode, GridPane.getColumnIndex(n));
                            GridPane.setRowIndex(clickNode, GridPane.getRowIndex(n));
                        }
                        deletePieceBetween(colIndex, rowIndex, GridPane.getColumnIndex(n), GridPane.getRowIndex(n));
                        playerturn = switchTurn(playerturn);
                        possibleMoves.clear();
                    }
                });
            }

        }
    }

    private void deletePieceBetween(int fc, int fr, int sc, int sr){
        //If a piece hops over an opposing piece, this deletes that piece hopped
        Point firstp = new Point(fc,fr);
        Point secp = new Point(sc,sr);
        Point midp = new Point((int)Math.ceil((fc+sc)/2),(int)Math.ceil((fr+sr)/2));

        if(!midp.equals(firstp) && !midp.equals(secp) && allBoardPieces.containsKey(midp)){
            PieceGrids.getChildren().remove(allBoardPieces.get(midp));
        }
    }

    private String turnID(int t){
        //Finds the ID of the player turn
        if(t == 0){
            return "White";
        }
        return "Red";
    }

    private int switchTurn(int t){
        //After each proper turn the player turn is switched
        if(t == 0){
            return 1;
        }
        return 0;
    }

    private int kingRow(int r){
        //Sets the row that a player has to hit to be king
        if(r == 0){
            return 0;
        }
        return 7;
    }

    private boolean isValidRange(int number){
        //Checks if the row or column is within a moving range
        return number <= 7 && number >= 0;
    }
    private void movesPieces(Node n, int c, int r){
        //This method shows what move each tile from each player can do
        if (playerturn == 0) {
            if (n instanceof Circle){
                goUp(c,r);
            }
            if(n instanceof ImageView){
                goUp(c,r);
                goDown(c,r);
            }
        }
        if(playerturn == 1) {
            if (n instanceof Circle){
                goDown(c,r);
            }
            if(n instanceof ImageView){
                goUp(c,r);
                goDown(c,r);
            }
        }
    }

    private void goUp(int c, int r){
        //This allows tile to go up
        if(isValidRange(c-1) && isValidRange(r-1)&& !checkMoves(c-1,r-1)){
            checkMoves(c-2,r-2);
        }
        if(isValidRange(c+1) && isValidRange(r-1)&& !checkMoves(c+1,r-1)){
            checkMoves(c+2,r-2);
        }
    }

    private void goDown(int c, int r){
        //This allows the tile to go down
        if(isValidRange(c-1) && isValidRange(r+1)&& !checkMoves(c-1,r+1)){
            checkMoves(c-2,r+2);
        }
        if(isValidRange(c+1) && isValidRange(r+1)&& !checkMoves(c+1,r+1)){
            checkMoves(c+2,r+2);
        }
    }
    private boolean checkMoves(int newc, int newr) {
        //if the tile is free, add tile as possible move
        if (checkFreeTile(newc , newr )) {
            Point temp = new Point(newc,newr);
            possibleMoves.add(temp);
            return true;
        }
        return false;
    }
    private boolean checkFreeTile(int c, int r){
        //check if diagonals of first click has other tiles
        return !allBoardPieces.keySet().contains(new Point(c, r));
    }

    private void makeKing(Node nodec, int tempc, int tempr){
        //This method sets the crown image of the turn and sets its ID to the corresponding color
        Image img = new Image("/sample/RedCrown.png");
        if(playerturn == 0){
            img = new Image("/sample/WhiteCrown.png");
        }
        ImageView crown = new ImageView(img);
        crown.setFitHeight(50);
        crown.setFitWidth(50);
        crown.setId(turnID(playerturn));
        PieceGrids.getChildren().remove(nodec);
        PieceGrids.add(crown, tempc, tempr);
    }
    private void getAllPieces(){
        //This method saves all the pieces and tiles to be used later
        allBoardPieces.clear();
        for(Node i: PieceGrids.getChildren()){

            if(i instanceof Circle || i instanceof ImageView) {
                int newc = GridPane.getColumnIndex(i);
                int newr = GridPane.getRowIndex(i);
                Point nc = new Point(newc, newr);
                allBoardPieces.put(nc, i);
            }
            else{
                allTilePieces.add(i);
            }
        }
    }
}