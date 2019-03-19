package sample;
import java.util.ArrayList;
import java.util.List;

public class Board{
    private List<List<String>> board = new ArrayList<List<String>>();
    private int rows;
    private int cols;


    // constructing the Board with the specified rows and columns
    public Board(int r, int c)
    {
        rows = r;
        cols = c;

        for(int rIndex = 0; rIndex < rows; rIndex++)
        {
            List<String> row = new ArrayList<String>();
            for(int cIndex = 0; cIndex < cols; cIndex++)
            {
                row.add("'");
            }
            board.add(row);
        }
    }


    // returns num of rows
    int getRows()
    {
        return rows;
    }


    // returns num of columns
    int getCols()
    {
        return cols;
    }

    // returns array that represents the Board
    public List<List<String>> getBoard()
    {
        return board;
    }


    // pass in new array to update the Board
    void updateBoard( List<List<String>> updatedBoard)
    {
        board = updatedBoard;
    }

    void updateBoard(int row ,  int col , String value)
    {
        board.get(row).set(col, value);
    }


    // prints the contents of the Board
    void printBoard()
    {
        System.out.println("PRINTING BOARD");
        for(int rIndex = 0; rIndex < rows; rIndex++)
        {

            for(int cIndex = 0; cIndex < cols; cIndex++) {
                System.out.print(board.get(rIndex).get(cIndex) + " ");
            }
            System.out.println();
        }


    }


}