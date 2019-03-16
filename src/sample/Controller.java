package sample;
import javafx.fxml.FXML;
import javafx.scene.input.DragEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.net.URL;
import java.util.*;

import javafx.scene.Node;
import javafx.scene.text.Text;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.sound.midi.SysexMessage;
import java.util.Map.Entry;

public class Controller{
    private int playerturn = 0;
    @FXML
    private GridPane PieceGrids;

    public HashMap<Point, Node> allBoardPieces = new HashMap<>();
    public HashSet<Point> possibleMoves = new HashSet<>();



    public void boardClicked(MouseEvent e){
        getAllPieces();

        Node clickNode = e.getPickResult().getIntersectedNode();
        int colIndex = PieceGrids.getColumnIndex(clickNode);
        int rowIndex = PieceGrids.getRowIndex(clickNode);
        if(clickNode instanceof Circle || clickNode instanceof ImageView){

            movesPieces(clickNode, colIndex, rowIndex);
            for (Node n : PieceGrids.getChildren()) {
                n.setOnMouseClicked(ee -> {
                    int tempc = GridPane.getColumnIndex(n);
                    int tempr = GridPane.getRowIndex(n);

                    if (possibleMoves.contains(new Point(tempc, tempr))) {

                        if (kingRow(playerturn) == tempr) {
                            makeKing(clickNode, tempc,tempr);
                        }
                        else{
                            PieceGrids.setColumnIndex(clickNode, tempc);
                            PieceGrids.setRowIndex(clickNode, tempr);
                        }
                        deletePieceBetween(colIndex, rowIndex, tempc, tempr);
                        playerturn = getTurn(playerturn);
                    }
                    possibleMoves.clear();

                });
            }


        }

    }

    public void makeKing(Node nodec, int tempc, int tempr){

        Image img = new Image("/sample/RedCrown.png");
        if(playerturn == 0){
            img = new Image("/sample/WhiteCrown.png");
        }
        ImageView crownimage = new ImageView(img);
        crownimage.setFitHeight(50);
        crownimage.setFitWidth(50);
        PieceGrids.getChildren().remove(nodec);
        PieceGrids.add(crownimage, tempc, tempr);


    }
    public void deletePieceBetween(int fc, int fr, int sc, int sr){
        Point firstp = new Point(fc,fr);
        Point secp = new Point(sc,sr);
        Point midp = new Point((int)Math.ceil((fc+sc)/2),(int)Math.ceil((fr+sr)/2));

        if(!midp.equals(firstp) && !midp.equals(secp) &&
                allBoardPieces.containsKey(midp) && allBoardPieces.get(midp) instanceof Circle){
            PieceGrids.getChildren().remove(allBoardPieces.get(midp));
        }
    }

    public int getTurn(int t){
        if(t == 0){
            return 1;
        }
        return 0;
    }

    public int kingRow(int r){
        if(r == 0){
            return 0;
        }
        return 7;
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
    public void movesPieces(Node n, int c, int r){

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

    public void goUp(int c, int r){
        if(isValidRange(c-1) && isValidRange(r-1)&& checkMoves(c-1,r-1) == false){
            checkMoves(c-2,r-2);
        }
        if(isValidRange(c+1) && isValidRange(r-1)&& checkMoves(c+1,r-1)==false){
            checkMoves(c+2,r-2);
        }
    }

    public void goDown(int c, int r){
        if(isValidRange(c-1) && isValidRange(r+1)&& checkMoves(c-1,r+1) == false){
            checkMoves(c-2,r+2);
        }
        if(isValidRange(c+1) && isValidRange(r+1)&& checkMoves(c+1,r+1)==false){
            checkMoves(c+2,r+2);
        }
    }
    public boolean checkMoves(int newc, int newr) {

        if (checkFreeTile(newc , newr )) {
            Point temp = new Point(newc,newr);
            possibleMoves.add(temp);
            return true;
        }
        return false;
    }
    public boolean checkFreeTile(int c, int r){
        //check if diagonals of first click has other tiles
        if(allBoardPieces.keySet().contains(new Point(c, r))){
            return false;
        }
        return true;
    }
    public void getAllPieces(){
        //Point(x,y)
        //x is newc, y is newr
        allBoardPieces.clear();
        for(Node i: PieceGrids.getChildren()){

            if(i instanceof Circle || i instanceof StackPane) {

                int newc = GridPane.getColumnIndex(i);
                int newr = GridPane.getRowIndex(i);
                Point nc = new Point(newc, newr);
                allBoardPieces.put(nc, i);
            }

        }

    }
}