package cpsc2150.extendedConnectX;

import java.util.*;

public class GameBoardMem extends AbsGameBoard{

    /**
     * @invariant       MIN_NUM_ROWS <= numRows <= MAX_NUM_ROWS AND
     *                  MIN_NUM_COLUMNS <= numColumns <= MAX_NUM_COLUMNS AND
     *                  MIN_NUM_TO_WIN <= numToWin <= MAX_NUM_TO_WIN AND
     *                  MIN_NUM_MOVES <= numMoves <= (numRows * numColumns) AND
     *                  MIN_ROW <= lastPlacedRow <= numRows
     * Correspondence   self.getNumRows() = numRows AND
     *                  self.getNumColumns() = numColumns AND
     *                  self = gameBoard
     */
    private Map<Character, List<BoardPosition>> gameBoard;
    private int numMoves;
    private int lastPlacedRow;
    private int numRows;
    private int numColumns;
    private int numToWin;
    private int maxMoves;

    /**
     * Constructor for GameBoardMem
     *
     *
     * @post    numRows = #numRows AND
     *          numColumns = #numColumns AND
     *          numToWin = #numToWin AND
     *          gameBoard = [Empty HashMap] AND
     *          numMoves = 0 AND
     *          maxMoves = numRows * numColumns
     */
    public GameBoardMem(int numRows, int numColumns, int numToWin){

        this.numRows = numRows;
        this.numColumns = numColumns;
        this.numToWin = numToWin;
        maxMoves = this.numRows * this.numColumns;
        numMoves = MIN_NUM_MOVES;

        gameBoard = new HashMap<>();

    }

    public boolean checkForWin(int c){

        // Temporary BoardPosition variable used to represent
        // the position of the last placed player token
        BoardPosition temp = new BoardPosition(lastPlacedRow, c);
        char p = whatsAtPos(temp);

        if (checkHorizWin(temp, p)) return true;
        else{

            if (checkVertWin(temp, p)) return true;
            else{

                return checkDiagWin(temp, p);

            }

        }

    }

    /**
     * Places a token p in column c on the game board.  The token will be placed
     * in the lowest available row in column c.
     *
     * @param p     token to be placed on the game board.
     * @param c     column on the game board where the token is placed.
     * @pre         <row, column> are empty AND
     *              p == 'X' or p == 'X' AND
     *              gameBoard != [Empty]
     * @post        <row, column> = p
     */
    public void placeToken(char p, int c){

        int r = 0;
        BoardPosition temp = new BoardPosition(r, c);
        List<BoardPosition> tempList;

        if (gameBoard.containsKey(p)) tempList = gameBoard.get(p);
        else tempList = new ArrayList<>();

        while (whatsAtPos(temp) != ' '){

            r++;
            temp = new BoardPosition(r, c);

        }

        tempList.add(temp);

        if (gameBoard.containsKey(p)) {

            gameBoard.remove(p);
            gameBoard.put(p, tempList);

        }
        else gameBoard.put(p, tempList);

        lastPlacedRow = r;

        numMoves++;

    }

    public char whatsAtPos(BoardPosition pos){

        List<BoardPosition> temp;

        for (Map.Entry<Character, List<BoardPosition>> m : gameBoard.entrySet()){

            temp = m.getValue();

            for (int i = START; i < temp.size(); i++){

                if (temp.get(i).getColumn() == pos.getColumn() && temp.get(i).getRow() == pos.getRow()) return m.getKey();

            }

        }

        return ' ';

    }

    @Override
    public boolean isPlayerAtPos(BoardPosition pos, char player){

        List<BoardPosition> temp = gameBoard.get(player);

        for (int i = START; i < temp.size(); i++){

            if (temp.get(i).equals(pos)) return true;

        }

        return false;

    }

    public boolean checkTie(){ return numMoves == maxMoves; }

    public int getNumRows(){ return numRows; }

    public int getNumColumns(){ return numColumns; }

    public int getNumToWin(){ return numToWin; }

}
