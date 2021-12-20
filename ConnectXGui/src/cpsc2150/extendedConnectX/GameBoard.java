package cpsc2150.extendedConnectX;

public class GameBoard extends AbsGameBoard {

    /**
     * @invariant       MIN_NUM_ROWS <= numRows <= MAX_NUM_ROWS AND
     *                  MIN_NUM_COLUMNS <= numColumns <= MAX_NUM_COLUMNS AND
     *                  MIN_NUM_TO_WIN <= numToWin <= MAX_NUM_TO_WIN AND
     *                  MIN_NUM_MOVES <= numMoves <= (numRows * numColumns) AND
     *                  MIN_ROW <= lastPlacedRow <= numRows
     * Correspondence   self.getNumRows() = numRows AND
     *                  self.getNumColumns() = numColumns AND
     *                  self = gameBoard[getNumRows() + 1][getNumColumns() + 1]
     */
    private char [][] gameBoard;
    private int numMoves;
    private int lastPlacedRow;
    private int numRows;
    private int numColumns;
    private int numToWin;
    private int maxMoves;

    /**
     * Constructor for GameBoard
     *
     *
     * @post    numRows = #numRows AND
     *          numColumns = #numColumns AND
     *          numToWin = #numToWin AND
     *          gameBoard = [empty numRows x numColumns game board] AND
     *          numMoves = 0 AND
     *          maxMoves = numRows * numColumns
     */
    public GameBoard(int numRows, int numColumns, int numToWin){

        this.numRows = numRows;
        this.numColumns = numColumns;
        this.numToWin = numToWin;
        maxMoves = this.numRows * this.numColumns;

        gameBoard = new char[getNumRows() + 1][getNumColumns() + 1];
        numMoves = MIN_NUM_MOVES;

        // For loops to initialize the game board array
        for (int i = START; i <= getNumRows(); i++){

            for (int j = START; j <= getNumColumns(); j++) gameBoard[i][j] = ' ';

        }

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

    public void placeToken(char p, int c){

        int r = 0;
        BoardPosition temp = new BoardPosition(r, c);

        while (whatsAtPos(temp) != ' ') {

            r++;
            temp = new BoardPosition(r, c);

        }

        gameBoard[r][c] = p;

        lastPlacedRow = r;

        numMoves++;

    }

    public char whatsAtPos(BoardPosition pos){

        int row = pos.getRow();
        int col = pos.getColumn();

        return gameBoard[row][col];

    }

    public boolean checkTie(){ return (numMoves == maxMoves); }

    public int getNumRows(){ return numRows; }

    public int getNumColumns(){ return numColumns; }

    public int getNumToWin(){ return numToWin; }

}