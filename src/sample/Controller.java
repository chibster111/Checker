package sample;
import javafx.fxml.FXML;
import javafx.scene.input.DragEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.net.URL;
import java.util.*;

import javafx.scene.Node;

import java.util.Map.Entry;

public class Controller{
    private int playerturn = 0;
    @FXML
    private GridPane PieceGrids;

    public HashMap<Point, String> allBoardPieces = new HashMap<>();
    public HashSet<Point> possibleMoves = new HashSet<>();



    public void boardClicked(MouseEvent e){
        System.out.println("First click");
        getAllPieces();

        Node clickNode = e.getPickResult().getIntersectedNode();
        int colIndex = GridPane.getColumnIndex(clickNode);
        int rowIndex = GridPane.getRowIndex(clickNode);
        if(clickNode instanceof Circle){

            Circle nodeC = (Circle)clickNode;
            System.out.println(playerturn);
            System.out.println(getColor(playerturn));
            System.out.println(nodeC.getFill().toString());
            if(nodeC.getFill().toString().equals(getColor(playerturn))) {
                movesPieces(colIndex, rowIndex);
                for (Point er : possibleMoves) {
                    System.out.println(er.y + " " + er.x);
                }
                for (Node n : PieceGrids.getChildren()) {
                    int tempc = GridPane.getColumnIndex(n);
                    int tempr = GridPane.getRowIndex(n);
                    Point temppoint = new Point(tempc, tempr);

                    n.setOnMouseClicked(ee -> {
                        System.out.println("Second click");

                        if (possibleMoves.contains(temppoint)) {
                            Circle newcircle = new Circle();
                            newcircle.setFill(nodeC.getFill());
                            newcircle.setRadius(25.0);
                            PieceGrids.getChildren().remove(clickNode);
                            PieceGrids.add(newcircle, tempc, tempr);
                        }
                        possibleMoves.clear();


                    });
                    playerturn = getTurn(playerturn);
                }
            }
        }

    }

    public int getTurn(int t){
        if(t == 0){
            return 1;
        }
        return 0;
    }

    public String getColor(int t){
        if(t == 0){
            return "0xffffffff";
        }
        else{
            return "0xff1f1fff";
        }
    }

    public boolean isGameOver(){
        if(allBoardPieces.size() == 0){
            return true;
        }
        return false;
    }

    public boolean isValidRange(int number){
        if(number <=7 && number>=0){
            return true;
        }
        return false;
    }
    public void movesPieces(int c, int r){

        if (playerturn == 0) {
            if(isValidRange(c-1) && isValidRange(r-1)&& checkMoves(c-1,r-1) == false){
                checkMoves(c-2,r-2);
            }
            if(isValidRange(c+1) && isValidRange(r-1)&& checkMoves(c+1,r-1)==false){
                checkMoves(c+2,r-2);
            }
        }
        if(playerturn == 1) {
            if(isValidRange(c-1) && isValidRange(r+1)&& checkMoves(c-1,r+1) == false){
                System.out.println(c-2 +" "+ (r+2));
                checkMoves(c-2,r+2);
            }
            if(isValidRange(c+1) && isValidRange(r+1)&& checkMoves(c+1,r+1)==false){
                System.out.println(c+2 +" "+(r+2));
                checkMoves(c+2,r+2);
            }
        }
    }
    public boolean checkMoves(int newc, int newr) {

        if (checkTileCollision(newc , newr )) {
            System.out.println("In checkMoves");
            Point temp = new Point(newc,newr);
            possibleMoves.add(temp);
            return true;
        }
        return false;
    }
    public boolean checkTileCollision(int c, int r){
        //check if diagonals of first click has other tiles

        Point nc = new Point(c, r);
        if(allBoardPieces.keySet().contains(nc)){

            return false;
        }
        return true;
    }
    public void getAllPieces(){
        //Point(x,y)
        //x is newc, y is newr
        allBoardPieces.clear();
        for(Node i: PieceGrids.getChildren()){

            if(i instanceof Circle) {
                int newc = GridPane.getColumnIndex(i);
                int newr = GridPane.getRowIndex(i);
                Point nc = new Point(newc, newr);
                allBoardPieces.put(nc, ((Circle) i).getFill().toString());
            }
        }

    }







}
