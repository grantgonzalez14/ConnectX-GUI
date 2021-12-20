package cpsc2150.extendedConnectX;

/**
 * This object will hold all the information about the game board, and will handle
 * all checks and calculations about the board.
 *
 * Defines: GameBoard - The game board object
 *          NumMoves - The number of tokens that have been placed
 *          LastPlacedRows - The row that the last token was placed in
 *          NumRows - The number of rows in the game board
 *          NumColumns - The number of columns in the game board
 *          NumToWin - The number of tokens need in a row to win
 *          MaxMoves - The maximum amount of moves that can be done
 *
 *
 * Initialization Ensures: [Game Board is full of blank space characters or is empty and
 *                          is numRows x numColumns]
 *
 * Constraints: 0 <= numMoves <= (numRows * numColumns) AND
 *              0 <= lastPlacedRow <= numRows
 *
 */
public interface IGameBoard {

    public static final int MIN_ROW = 0;
    public static final int MIN_COLUMN = 0;
    public static final int START = 0;
    public static final int STEP = 1;
    public static final int MIN_NUM_ROWS = 3;
    public static final int MIN_NUM_COLUMNS = 3;
    public static final int MIN_NUM_TO_WIN = 3;
    public static final int MIN_NUM_MOVES = 0;
    public static final int MAX_NUM_ROWS = 100;
    public static final int MAX_NUM_COLUMNS = 100;
    public static final int MAX_NUM_TO_WIN = 25;

    /**
     * Checks the board to see if the last token placed in column see resulted
     * in a vertical, horizontal, or diagonal win.
     *
     * @param c     Column to check for win.
     * @return      true if the last token placed (which was placed in column c)
     *              resulted in the player winning the game.  Otherwise, it returns
     *              false.
     * @pre         c >= MIN_COLUMN AND
     *              c <= numColumns
     * @post        If checkHorizWin = true OR
     *              checkVertWin = true OR
     *              checkDiagWin = true then checkForWin = true
     *              else checkForWin = false
     *
     */
    public boolean checkForWin(int c);
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
    public void placeToken(char p, int c);

    /**
     * Returns the char that is in position pos of the game board.  If there is no token a blank
     * space character is returned.
     *
     * @param pos       The position that is being looked at to determine what is at that position.
     * @return          the char that is in position pos of the game board.  If there is no token at
     *                  the spot it returns a blank space character: ' '.
     * @pre             MIN_ROW <= pos.getRow() <= numRows, MIN_COLUMN <= column <= numRows.
     * @post            whatsAtPos = [Player Token] OR
     *                  whatsAtPos = ' '
     *                  #board = board
     */
    public char whatsAtPos(BoardPosition pos);

    /**
     * Checks the board to see if there is a tie.
     *
     * @return      true if the game board results in a tie game, otherwise it returns false.
     * @pre         checkForWin(int c) == false
     * @post        checkTie = true if numMoves == maxMoves
     *              checkTie = false if numMoves != maxMoves
     *              #board = board
     */
    public boolean checkTie();
    /**
     * Returns the number of rows in the board
     * @return      Returns the number of rows in the board
     * @post        getNumRows = numRows
     *
     */
    public int getNumRows();

    /**
     * Returns the number of columns in the board
     * @return      Returns the number of columns in the board
     * @post        getNumColumns = numColumns
     */
    public int getNumColumns();

    /**
     * Returns the number of tokens needed to win
     * @return      Returns the number of tokens needed to win
     * @post        getNumToWin = numToWin
     */
    public int getNumToWin();

    /**
     * Overrided toString function used to convert a 2D array or Map (gameBoard) into a string
     * to be printed to the screen.
     *
     * @return      A gameBoard string to be printed to the screen
     * @post        toString = board
     */
    @Override
    public String toString();

    /**
     * Checks column c to determine if that column is available to be played.
     *
     * @param c     Column to check if free.
     * @return      true if column is able to accept another token,
     *              false otherwise.
     * @pre         c >= MIN_COLUMNS and c <= NUM_COLUMNS
     * @post        checkIfFree = true || checkIfFree = false
     */

    /**
     * Checks column c to determine if that column is available to be played.
     *
     * @param c     Column to check if free.
     * @return      true if column is able to accept another token,
     *              false otherwise.
     * @pre         c >= MIN_COLUMNS and c <= NUM_COLUMNS
     * @post        checkIfFree = true || checkIfFree = false
     */
    default public boolean checkIfFree(int c){

        BoardPosition temp = new BoardPosition(MIN_ROW, c);

        for (int i = 1; i <= getNumRows(); i++){

            if (whatsAtPos(temp) == ' ') return true;

            temp = new BoardPosition(i, c);

        }

        return false;

    }

    /**
     * Returns true if player player is at position pos.
     *
     * @param pos       The position that is being looked at to determine if a player token
     *                  is at that position.
     * @param player    Which player token is being checked.
     * @return          true if the player is at that position, otherwise it returns false.
     * @pre             MIN_ROW <= row <= numRows AND
     *                  MIN_COLUMN <= column <= numColumns
     * @post            Returns true || false AND #board = board
     */
    default public boolean isPlayerAtPos(BoardPosition pos, char player){ return (whatsAtPos(pos) == player); }

    /**
     * Checks the game board to see if the last token placed, p, resulted
     * in the number of tokens needed to win in a row horizontally.
     *
     * @param pos   The position at which the most recent token was placed used to check for
     *              a horizontal win.
     * @param p     The player token that is being checked for a horizontal win.
     * @return      true if the last token placed (which was placed in position pos and player p)
     *              resulted in the player winning by getting the number of tokens need to win in a row
     *              horizontally.  Otherwise, it returns false.
     * @pre         MIN_ROW <= row <= numRows AND
     *              MIN_COLUMN <= column <= numColumns
     * @post        If numTokensInARow >= numToWin then checkHorizWin = true else
     *              checkHorizWin = false AND
     *              #board = board
     */
    default public boolean checkHorizWin(BoardPosition pos, char p){

        // Variables
        int numTokensInARow = 1;
        int col = pos.getColumn();
        int row = pos.getRow();
        boolean done = false;
        BoardPosition temp;

        // If the player token is not in the first column and
        // not in the last column this if statement runs
        if (col > MIN_COLUMN && col < getNumColumns()) {

            // While loop to iterate through the game board
            // horizontally to the left to determine if
            // equivalent tokens are in a row
            while (!done) {

                // Temporary BoardPosition variable used to
                // iterate through the game board
                temp = new BoardPosition(row, col - STEP);

                // If the token found at the temporary board
                // position variable is equivalent to the
                // player token passed then numTokensInARow
                // is increased by 1 otherwise the while loop
                // is finished
                if (isPlayerAtPos(temp, p)) numTokensInARow++;
                else done = true;

                col = temp.getColumn();

                // If the temporary column position is less
                // than or equal to the 1st column then
                // the while loop is quit
                if (col <= MIN_COLUMN) done = true;

            }

            // Variable are reset
            done = false;
            col = pos.getColumn();

            // While loop to iterate through the game board
            // horizontally to the right to determine if
            // equivalent tokens are in a row
            while (!done) {

                // Temporary BoardPosition variable used to
                // iterate through the game board
                temp = new BoardPosition(row, col + STEP);

                // If the token found at the temporary board
                // position variable is equivalent to the
                // player token passed then numTokensInARow
                // is increased by 1 otherwise the while loop
                // is finished
                if (isPlayerAtPos(temp, p)) numTokensInARow++;
                else done = true;

                col = temp.getColumn();

                // If the temporary column position is greater
                // than or equal to the last column then
                // the while loop is quit
                if (col >= getNumColumns()) done = true;

            }

        }
        // If the player token is in the 1st column this if statement runs
        else if (col < getNumColumns()){

            // While loop to iterate through the game board
            // horizontally to the right to determine if
            // equivalent tokens are in a row
            while (!done) {

                // Temporary BoardPosition variable used to
                // iterate through the game board
                temp = new BoardPosition(row, col + STEP);

                // If the token found at the temporary board
                // position variable is equivalent to the
                // player token passed then numTokensInARow
                // is increased by 1 otherwise the while loop
                // is finished
                if (isPlayerAtPos(temp, p)) numTokensInARow++;
                else done = true;

                col = temp.getColumn();

                // If the temporary column position is greater
                // than or equal to the last column then
                // the while loop is quit
                if (col >= getNumColumns()) done = true;

            }

        }
        // If the player token is in the last column this else statement runs
        else{

            // While loop to iterate through the game board
            // horizontally to the left to determine if
            // equivalent tokens are in a row
            while (!done){

                // Temporary BoardPosition variable used to
                // iterate through the game board
                temp = new BoardPosition(row, col - STEP);

                // If the token found at the temporary board
                // position variable is equivalent to the
                // player token passed then numTokensInARow
                // is increased by 1 otherwise the while loop
                // is finished
                if (isPlayerAtPos(temp, p)) numTokensInARow++;
                else done = true;

                col = temp.getColumn();

                // If the temporary column position is less
                // than or equal to the 1st column then
                // the while loop is quit
                if (col <= MIN_COLUMN) done = true;

            }

        }

        // Returns true if the number of tokens in a row is
        // greater than or equal to the number needed in a row
        // to win
        return (numTokensInARow >= getNumToWin());


    }

    /**
     * Checks the game board to see if the last token placed, p, resulted
     * in the number of tokens needed to win in a row vertically.
     *
     * @param pos   The position at which the most recent token was placed used to check for
     *              a vertical win.
     * @param p     The player token that is being checked for a vertical win.
     * @return      true if the last token placed (which was placed in position pos and player p)
     *              resulted in the player getting the number of tokens need to win in a row vertically.
     *              Otherwise, it returns false.
     * @pre         MIN_ROW <= row <= numRows AND
     *              MIN_COLUMN <= column <= numColumns
     * @post        if numTokensInARow >= numToWin then checkVertWin = true else
     *              checkVertWin = false AND
     *              #board = board
     */
    default public boolean checkVertWin(BoardPosition pos, char p){

        // Variables
        int numTokensInARow = 1;
        int col = pos.getColumn();
        int row = pos.getRow();
        boolean done = false;
        BoardPosition temp;

        // If the player token is not in the first row
        // or the last row this if statement is run
        if (row > MIN_ROW && row < getNumRows()) {

            // While loop to iterate through the game board
            // vertically down to determine if equivalent
            // tokens are in a row
            while (!done) {

                // Temporary BoardPosition variable used to
                // iterate through the game board
                temp = new BoardPosition(row - STEP, col);

                // If the token found at the temporary board
                // position variable is equivalent to the
                // player token passed then numTokensInARow
                // is increased by 1 otherwise the while loop
                // is finished
                if (isPlayerAtPos(temp, p)) numTokensInARow++;
                else done = true;

                row = temp.getRow();

                // If the temporary row position is less
                // than or equal to the 1st row then
                // the while loop is quit
                if (row <= MIN_ROW) done = true;

            }

            // Variables are reset
            done = false;
            row = pos.getRow();

            // While loop to iterate through the game board
            // vertically up to determine if equivalent
            // tokens are in a row
            while (!done) {

                // Temporary BoardPosition variable used to
                // iterate through the game board
                temp = new BoardPosition(row + STEP, col);

                // If the token found at the temporary board
                // position variable is equivalent to the
                // player token passed then numTokensInARow
                // is increased by 1 otherwise the while loop
                // is finished
                if (isPlayerAtPos(temp, p)) numTokensInARow++;
                else done = true;

                row = temp.getRow();

                // If the temporary row position is greater
                // than or equal to the last row then
                // the while loop is quit
                if (row >= getNumRows()) done = true;

            }

        }
        // If the player token is on the first row this if else
        // statement is run
        else if (row == MIN_ROW){

            // While loop to iterate through the game board
            // vertically up to determine if equivalent
            // tokens are in a row
            while (!done) {

                // Temporary BoardPosition variable used to
                // iterate through the game board
                temp = new BoardPosition(row + STEP, col);

                // If the token found at the temporary board
                // position variable is equivalent to the
                // player token passed then numTokensInARow
                // is increased by 1 otherwise the while loop
                // is finished
                if (isPlayerAtPos(temp, p)) numTokensInARow++;
                else done = true;

                row = temp.getRow();

                // If the temporary row position is greater
                // than or equal to the last row then
                // the while loop is quit
                if (row >= getNumRows()) done = true;

            }

        }
        // If the player token is in the last row this
        // else statement is run
        else{

            // While loop to iterate through the game board
            // vertically down to determine if equivalent
            // tokens are in a row
            while (!done){

                // Temporary BoardPosition variable used to
                // iterate through the game board
                temp = new BoardPosition(row - STEP, col);

                // If the token found at the temporary board
                // position variable is equivalent to the
                // player token passed then numTokensInARow
                // is increased by 1 otherwise the while loop
                // is finished
                if (isPlayerAtPos(temp, p)) numTokensInARow++;
                else done = true;

                row = temp.getRow();

                // If the temporary row position is less
                // than or equal to the 1st row then
                // the while loop is quit
                if (row <= MIN_ROW) done = true;

            }

        }

        // Returns true if the number of tokens in a row is
        // greater than or equal to the number needed in a row
        // to win
        return (numTokensInARow >= getNumToWin());

    }

    /**
     * Checks the game board to see if the last token placed, p, resulted
     * in the number of tokens needed to win in a row diagonally.
     *
     * @param pos   The position at which the most recent token was placed used to check for
     *              a diagonal win.
     * @param p     The player token that is being checked for a diagonal win.
     * @return      true if the last token placed (which was placed in position pos and player p)
     *              resulted in the player getting the number of tokens needed to win in a row diagonally.
     *              Otherwise, it returns false.
     * @pre         MIN_ROW <= row <= numRows AND
     *              MIN_COLUMN <= column <= numColumns
     * @post        If numTokensInARow >= numToWin then checkDiagWin = true else
     *              checkDiagWin = false AND
     *              #board = board
     */
    default public boolean checkDiagWin(BoardPosition pos, char p){

        int numTokensInARow = 1;
        int col = pos.getColumn();
        int row = pos.getRow();
        boolean done = false;
        BoardPosition temp;

        // If the player token is somewhere on the board that is not the edges
        if (col > MIN_COLUMN && row > MIN_ROW && col < getNumColumns() && row < getNumRows()){

            // While loop to iterate through the game board
            // vertically down to and to the left to determine
            // if equivalent tokens are in a row
            while (!done) {

                // Temporary BoardPosition variable used to
                // iterate through the game board
                temp = new BoardPosition(row - STEP, col - STEP);

                // If the token found at the temporary board
                // position variable is equivalent to the
                // player token passed then numTokensInARow
                // is increased by 1 otherwise the while loop
                // is finished
                if (isPlayerAtPos(temp, p)) numTokensInARow++;
                else done = true;

                col = temp.getColumn();
                row = temp.getRow();

                // If the temporary row position is less
                // than or equal to the 1st row or the
                // temporary column position is less than
                // or equal to the 1st row then the while loop
                // is quit
                if (col <= MIN_COLUMN || row <= MIN_ROW) done = true;

            }

            // Variables are reset
            done = false;
            col = pos.getColumn();
            row = pos.getRow();

            // While loop to iterate through the game board
            // vertically up and to the right to determine
            // if equivalent tokens are in a row
            while (!done){

                // Temporary BoardPosition variable used to
                // iterate through the game board
                temp = new BoardPosition(row + STEP, col + STEP);

                // If the token found at the temporary board
                // position variable is equivalent to the
                // player token passed then numTokensInARow
                // is increased by 1 otherwise the while loop
                // is finished
                if (isPlayerAtPos(temp, p)) numTokensInARow++;
                else done = true;

                col = temp.getColumn();
                row = temp.getRow();

                // If the temporary row position is greater
                // than or equal to the last row or the
                // temporary column position is greater than
                // or equal to the last column then the while loop
                // is quit
                if (col >= getNumColumns() || row >= getNumRows()) done = true;

            }

            // Returns true if the number of tokens in a row is
            // greater than or equal to the number needed in a row
            // to win
            if (numTokensInARow >= getNumToWin()) return true;

            // Variables are reset
            numTokensInARow = 1;
            done = false;
            col = pos.getColumn();
            row = pos.getRow();

            // While loop to iterate through the game board
            // vertically up and to the left to determine
            // if equivalent tokens are in a row
            while (!done){

                // Temporary BoardPosition variable used to
                // iterate through the game board
                temp = new BoardPosition(row + STEP, col - STEP);

                // If the token found at the temporary board
                // position variable is equivalent to the
                // player token passed then numTokensInARow
                // is increased by 1 otherwise the while loop
                // is finished
                if (isPlayerAtPos(temp, p)) numTokensInARow++;
                else done = true;

                col = temp.getColumn();
                row = temp.getRow();

                // If the temporary row position is greater
                // than or equal to the last row or the
                // temporary column position is less than
                // or equal to the 1st column then the while loop
                // is quit
                if (col <= MIN_COLUMN || row >= getNumRows()) done = true;

            }

            // Variables are reset
            done = false;
            col = pos.getColumn();
            row = pos.getRow();

            // While loop to iterate through the game board
            // vertically down and to the right to determine
            // if equivalent tokens are in a row
            while (!done){

                // Temporary BoardPosition variable used to
                // iterate through the game board
                temp = new BoardPosition(row - STEP, col + STEP);

                // If the token found at the temporary board
                // position variable is equivalent to the
                // player token passed then numTokensInARow
                // is increased by 1 otherwise the while loop
                // is finished
                if (isPlayerAtPos(temp, p)) numTokensInARow++;
                else done = true;

                col = temp.getColumn();
                row = temp.getRow();

                // If the temporary row position is less
                // than or equal to the 1st row or the
                // temporary column position is greater than
                // or equal to the last column then the while loop
                // is quit
                if (col >= getNumColumns() || row <= MIN_ROW) done = true;

            }

        }
        // if the player token is on the right edge and not the bottom or top row
        else if (col > MIN_COLUMN && row > MIN_ROW && row < getNumRows()){

            // While loop to iterate through the game board
            // vertically down and to the left to determine
            // if equivalent tokens are in a row
            while (!done){

                // Temporary BoardPosition variable used to
                // iterate through the game board
                temp = new BoardPosition(row - STEP, col - STEP);

                // If the token found at the temporary board
                // position variable is equivalent to the
                // player token passed then numTokensInARow
                // is increased by 1 otherwise the while loop
                // is finished
                if (isPlayerAtPos(temp, p)) numTokensInARow++;
                else done = true;

                col = temp.getColumn();
                row = temp.getRow();

                // If the temporary row position is less
                // than or equal to the 1st row or the
                // temporary column position is less than
                // or equal to the 1st column then the while loop
                // is quit
                if (col <= MIN_COLUMN || row <= MIN_ROW) done = true;

            }

            // Returns true if the number of tokens in a row is
            // greater than or equal to the number needed in a row
            // to win
            if (numTokensInARow >= getNumToWin()) return true;

            // Variables are reset
            numTokensInARow = 1;
            done = false;
            col = pos.getColumn();
            row = pos.getRow();

            // While loop to iterate through the game board
            // vertically up and to the left to determine
            // if equivalent tokens are in a row
            while (!done){

                // Temporary BoardPosition variable used to
                // iterate through the game board
                temp = new BoardPosition(row + STEP, col - STEP);

                // If the token found at the temporary board
                // position variable is equivalent to the
                // player token passed then numTokensInARow
                // is increased by 1 otherwise the while loop
                // is finished
                if (isPlayerAtPos(temp, p)) numTokensInARow++;
                else done = true;

                col = temp.getColumn();
                row = temp.getRow();

                // If the temporary row position is greater
                // than or equal to the last row or the
                // temporary column position is less than
                // or equal to the 1st column then the while loop
                // is quit
                if (col <= MIN_COLUMN || row >= getNumRows()) done = true;

            }

        }
        // If the player token is on left edge and not on bottom or top row
        else if (row > MIN_ROW && col == MIN_COLUMN && row < getNumRows()){

            // While loop to iterate through the game board
            // vertically down and to the right to determine
            // if equivalent tokens are in a row
            while (!done) {

                // Temporary BoardPosition variable used to
                // iterate through the game board
                temp = new BoardPosition(row - STEP, col + STEP);

                // If the token found at the temporary board
                // position variable is equivalent to the
                // player token passed then numTokensInARow
                // is increased by 1 otherwise the while loop
                // is finished
                if (isPlayerAtPos(temp, p)) numTokensInARow++;
                else done = true;

                col = temp.getColumn();
                row = temp.getRow();

                // If the temporary row position is less
                // than or equal to the 1st row or the
                // temporary column position is greater than
                // or equal to the last column then the while loop
                // is quit
                if (col >= getNumColumns() || row <= MIN_ROW) done = true;

            }

            // Returns true if the number of tokens in a row is
            // greater than or equal to the number needed in a row
            // to win
            if (numTokensInARow >= getNumToWin()) return true;

            // Variables are reset
            numTokensInARow = 1;
            done = false;
            col = pos.getColumn();
            row = pos.getRow();

            // While loop to iterate through the game board
            // vertically up and to the right to determine
            // if equivalent tokens are in a row
            while (!done){

                // Temporary BoardPosition variable used to
                // iterate through the game board
                temp = new BoardPosition(row + STEP, col + STEP);

                // If the token found at the temporary board
                // position variable is equivalent to the
                // player token passed then numTokensInARow
                // is increased by 1 otherwise the while loop
                // is finished
                if (isPlayerAtPos(temp, p)) numTokensInARow++;
                else done = true;

                col = temp.getColumn();
                row = temp.getRow();

                // If the temporary row position is greater
                // than or equal to the last row or the
                // temporary column position is greater than
                // or equal to the last column then the while loop
                // is quit
                if (col >= getNumColumns() || row >= getNumRows()) done = true;

            }

        }
        // If player token is on bottom row and not in corners
        else if (col < getNumColumns() && col > MIN_COLUMN && row == MIN_ROW){

            // While loop to iterate through the game board
            // vertically up and to the right to determine
            // if equivalent tokens are in a row
            while (!done) {

                // Temporary BoardPosition variable used to
                // iterate through the game board
                temp = new BoardPosition(row + STEP, col + STEP);

                // If the token found at the temporary board
                // position variable is equivalent to the
                // player token passed then numTokensInARow
                // is increased by 1 otherwise the while loop
                // is finished
                if (isPlayerAtPos(temp, p)) numTokensInARow++;
                else done = true;

                col = temp.getColumn();
                row = temp.getRow();

                // If the temporary row position is greater
                // than or equal to the last row or the
                // temporary column position is greater than
                // or equal to the last column then the while loop
                // is quit
                if (col >= getNumColumns() || row >= getNumRows()) done = true;

            }

            // Returns true if the number of tokens in a row is
            // greater than or equal to the number needed in a row
            // to win
            if (numTokensInARow >= getNumToWin()) return true;

            // Variables are reset
            numTokensInARow = 1;
            done = false;
            col = pos.getColumn();
            row = pos.getRow();

            // While loop to iterate through the game board
            // vertically up and to the left to determine
            // if equivalent tokens are in a row
            while (!done){

                // Temporary BoardPosition variable used to
                // iterate through the game board
                temp = new BoardPosition(row + STEP, col - STEP);

                // If the token found at the temporary board
                // position variable is equivalent to the
                // player token passed then numTokensInARow
                // is increased by 1 otherwise the while loop
                // is finished
                if (isPlayerAtPos(temp, p)) numTokensInARow++;
                else done = true;

                col = temp.getColumn();
                row = temp.getRow();

                // If the temporary row position is greater
                // than or equal to the last row or the
                // temporary column position is less than
                // or equal to the 1st column then the while loop
                // is quit
                if (col <= MIN_COLUMN || row >= getNumRows()) done = true;

            }

        }
        // If player token is on top row and not in corners
        else if (col < getNumColumns() && col > MIN_COLUMN && row == getNumRows()){

            // While loop to iterate through the game board
            // vertically down and to the right to determine
            // if equivalent tokens are in a row
            while (!done){

                // Temporary BoardPosition variable used to
                // iterate through the game board
                temp = new BoardPosition(row - STEP, col + STEP);

                // If the token found at the temporary board
                // position variable is equivalent to the
                // player token passed then numTokensInARow
                // is increased by 1 otherwise the while loop
                // is finished
                if (isPlayerAtPos(temp, p)) numTokensInARow++;
                else done = true;


                col = temp.getColumn();
                row = temp.getRow();

                // If the temporary row position is less
                // than or equal to the 1st row or the
                // temporary column position is greater than
                // or equal to the last column then the while loop
                // is quit
                if (col >= getNumColumns() || row <= MIN_ROW) done = true;

            }

            // Returns true if the number of tokens in a row is
            // greater than or equal to the number needed in a row
            // to win
            if (numTokensInARow >= getNumToWin()) return true;

            // Variables are rest
            numTokensInARow = 1;
            done = false;
            col = pos.getColumn();
            row = pos.getRow();

            // While loop to iterate through the game board
            // vertically down and to the left to determine
            // if equivalent tokens are in a row
            while (!done){

                // Temporary BoardPosition variable used to
                // iterate through the game board
                temp = new BoardPosition(row - STEP, col - STEP);

                // If the token found at the temporary board
                // position variable is equivalent to the
                // player token passed then numTokensInARow
                // is increased by 1 otherwise the while loop
                // is finished
                if (isPlayerAtPos(temp, p)) numTokensInARow++;
                else done = true;

                col = temp.getColumn();
                row = temp.getRow();

                // If the temporary row position is less
                // than or equal to the 1st row or the
                // temporary column position is less than
                // or equal to the 1st column then the while loop
                // is quit
                if (col <= MIN_COLUMN || row <= MIN_ROW) done = true;

            }

        }
        // If player token is in bottom left corner
        else if (col == MIN_COLUMN && row == MIN_ROW){

            // While loop to iterate through the game board
            // vertically up and to the right to determine
            // if equivalent tokens are in a row
            while (!done){

                // Temporary BoardPosition variable used to
                // iterate through the game board
                temp = new BoardPosition(row + STEP, col + STEP);

                // If the token found at the temporary board
                // position variable is equivalent to the
                // player token passed then numTokensInARow
                // is increased by 1 otherwise the while loop
                // is finished
                if (isPlayerAtPos(temp, p)) numTokensInARow++;
                else done = true;

                col = temp.getColumn();
                row = temp.getRow();

                // If the temporary row position is greater
                // than or equal to the last row or the
                // temporary column position is greater than
                // or equal to the last column then the while loop
                // is quit
                if (col >= getNumColumns() || row >= getNumRows()) done = true;

            }

        }
        // If token is in bottom right corner
        else if (col == getNumColumns() && row == MIN_ROW){

            // While loop to iterate through the game board
            // vertically up and to the left to determine
            // if equivalent tokens are in a row
            while (!done) {

                // Temporary BoardPosition variable used to
                // iterate through the game board
                temp = new BoardPosition(row + STEP, col - STEP);

                // If the token found at the temporary board
                // position variable is equivalent to the
                // player token passed then numTokensInARow
                // is increased by 1 otherwise the while loop
                // is finished
                if (isPlayerAtPos(temp, p)) numTokensInARow++;
                else done = true;

                col = temp.getColumn();
                row = temp.getRow();

                // If the temporary row position is greater
                // than or equal to the last row or the
                // temporary column position is less than
                // or equal to the 1st column then the while loop
                // is quit
                if (col <= MIN_COLUMN || row >= getNumRows()) done = true;

            }

        }
        // If player token is in top left corner
        else if (col == MIN_COLUMN && row == getNumRows()){

            // While loop to iterate through the game board
            // vertically down and to the right to determine
            // if equivalent tokens are in a row
            while (!done) {

                // Temporary BoardPosition variable used to
                // iterate through the game board
                temp = new BoardPosition(row - STEP, col + STEP);

                // If the token found at the temporary board
                // position variable is equivalent to the
                // player token passed then numTokensInARow
                // is increased by 1 otherwise the while loop
                // is finished
                if (isPlayerAtPos(temp, p)) numTokensInARow++;
                else done = true;

                col = temp.getColumn();
                row = temp.getRow();

                // If the temporary row position is less
                // than or equal to the 1st row or the
                // temporary column position is greater than
                // or equal to the last column then the while loop
                // is quit
                if (col >= getNumColumns() || row <= MIN_ROW) done = true;

            }

        }
        // If player token is in top right corner
        else{

            // While loop to iterate through the game board
            // vertically down and to the left to determine
            // if equivalent tokens are in a row
            while (!done){

                // Temporary BoardPosition variable used to
                // iterate through the game board
                temp = new BoardPosition(row - STEP, col - STEP);

                // If the token found at the temporary board
                // position variable is equivalent to the
                // player token passed then numTokensInARow
                // is increased by 1 otherwise the while loop
                // is finished
                if (isPlayerAtPos(temp, p)) numTokensInARow++;
                else done = true;

                col = temp.getColumn();
                row = temp.getRow();

                // If the temporary row position is less
                // than or equal to the 1st row or the
                // temporary column position is less than
                // or equal to the 1st column then the while loop
                // is quit
                if (col <= MIN_COLUMN || row <= MIN_ROW) done = true;

            }

        }

        // Returns true if the number of tokens in a row is
        // greater than or equal to the number needed in a row
        // to win
        return (numTokensInARow >= getNumToWin());

    }

}