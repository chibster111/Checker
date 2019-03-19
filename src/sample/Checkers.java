package sample;

import jdk.nashorn.internal.runtime.linker.LinkerCallSite;

import javax.sound.midi.SysexMessage;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Checkers {

    private String redplayer;
    private String whiteplayer;
    private String currentplayer;
    private Board board;
    private List<Point> possibleMoves = new ArrayList<>();


    public static void main(String[] args){
        Checkers cn = new Checkers();
        while (!cn.gameOver()){
            System.out.println(cn.currentplayer);
            List<Integer> piece = cn.selectPiece();
            int row = piece.get(0);
            int col = piece.get(1);
            cn.getAllPossibleMoves(row,col);
            List<Integer> tile = cn.selectPiece();
            int tr = tile.get(0);
            int tc = tile.get(1);
            if(cn.checkMove(tr,tc)){
                cn.makeMove(row, col, tr, tc);
            }
        }
    }
    public Checkers(){

        board = new Board(8,8);
        redplayer = "R";
        whiteplayer = "W";
        currentplayer = whiteplayer;
        makeBoard();
    }

    public void makeBoard(){
        List<List<String>> state = board.getBoard();
        for (int x = 0; x < state.size(); x++){
            for(int y = 0; y < state.size(); y++){
                if (y <= 2 && (x + y) % 2 != 0) {
                    state.get(y).set(x, redplayer);
                }
                if (y >= 5 && (x + y) % 2 != 0) {
                    state.get(y).set(x, whiteplayer);
                }
            }
        }
        this.board.updateBoard(state);

    }




    public void switchPlayer() {
        if (this.currentplayer.equals(whiteplayer)) {
            currentplayer = redplayer;
        } else {
            currentplayer = whiteplayer;

        }
    }
    public int kingRow(){
        if(this.currentplayer.equals(whiteplayer)){
            return 0;
        }
        return 7;
    }

    public boolean isValidRange(int number){
        //Checks if the row or column is within a moving range
        return number <= 7 && number >= 0;
    }

    public String playerKing(String player){
        if(player.equals(whiteplayer)){
            return "WK";
        }
        return "RK";
    }
    public boolean gameOver(){
        return getNumberOfPieces(whiteplayer) == 0 || getNumberOfPieces(redplayer) == 0;
    }
    public int getNumberOfPieces(String player){
        int count = 0;
        for (int x = 0; x < this.board.getRows(); x++){
            for(int y = 0; y < this.board.getCols(); y++){
                if (this.board.getBoard().get(y).get(x).equals(player) || this.board.getBoard().get(y).get(x).equals(playerKing(player))){
                    count+=1;
                }
            }
        }

        return count;
    }

    public void makeMove(int fr, int fc, int sr, int sc){
        this.board.getBoard().get(fr).set(fc, "'");
        if(sr == kingRow()){
            this.board.getBoard().get(sr).set(sc, playerKing(currentplayer));
        }
        else {
            this.board.getBoard().get(sr).set(sc, currentplayer);
        }
        possibleMoves.clear();
        switchPlayer();
    }

    public boolean checkMove(int r, int c){
        if(possibleMoves.contains(new Point(r,c))){
            return true;
        }
        return false;
    }

    List<Integer> selectPiece() {
        this.board.printBoard();
        List<Integer> res = new ArrayList<Integer>();
        Scanner row = new Scanner(System.in);
        Scanner col = new Scanner(System.in);

        System.out.println("Enter row: ");
        int row_val = row.nextInt();
        System.out.println("Enter col: ");
        int col_val = col.nextInt();

        res.add(row_val);
        res.add(col_val);

        return res;
    }



    public void getAllPossibleMoves(int r, int c){
        if (currentplayer == whiteplayer){
            if(this.board.getBoard().get(r).get(c).equals(whiteplayer)){
                goUp(r,c);
            }
            if(this.board.getBoard().get(r).get(c).equals(playerKing(whiteplayer))){
                goDown(r,c);
                goUp(r,c);
            }
        }
        if (currentplayer == redplayer){
            if(this.board.getBoard().get(r).get(c).equals(redplayer)){
                goDown(r,c);
            }
            if(this.board.getBoard().get(r).get(c).equals(playerKing(redplayer))){
                goDown(r,c);
                goUp(r,c);
            }
        }
    }

    private void goDown(int r, int c){
        //This allows the tile to go down
        if(isValidRange(c-1) && isValidRange(r+1)&& !addFreeTile(r+1,c-1)){

            addFreeTile(r+2,c-2);
        }
        if(isValidRange(c+1) && isValidRange(r+1)&& !addFreeTile(r+1,c+1)){

            addFreeTile(r+2,c+2);
        }
    }

    private void goUp(int r, int c){
        //This allows tile to go up

        if(isValidRange(c-1) && isValidRange(r-1)&& !addFreeTile(r-1,c-1)){
            addFreeTile(r-2,c-2);
        }
        if(isValidRange(c+1) && isValidRange(r-1)&& !addFreeTile(r-1,c+1)){
            addFreeTile(r-2,c+2);
        }
    }

    public boolean addFreeTile(int r, int c){
        if(freeTile(r,c)){

            possibleMoves.add(new Point(r,c));
            return true;
        }
        return false;
    }
    public boolean freeTile(int r,int c){

        return this.board.getBoard().get(r).get(c).equals("'");
    }














}
