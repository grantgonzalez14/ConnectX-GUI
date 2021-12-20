package cpsc2150.extendedConnectX;

public abstract class AbsGameBoard implements IGameBoard {

    @Override
    public String toString(){

        // Variables
        String board = "| ";
        BoardPosition pos;
        int endOfSingleDigitNumbers = 9;

        // For loop to create the top row with number labels
        for (int i = START; i < getNumColumns(); i++){

            // If i < 9 then "|" is added with a space behind it
            // otherwise only "|" is added, this is done otherwise
            // the game board is not created correctly
            if (i < endOfSingleDigitNumbers) board += i + "| ";
            else board += i + "|";

        }
        board += "\n|";

        // For loop to add the tokens or blank spaces
        // for every row except for the last row
        for (int i = getNumRows() - 1; i >= START; i--){

            // For loop to add the tokens or blank spaces for each column
            for (int j = START; j < getNumColumns(); j++){

                pos = new BoardPosition(i, j);
                board += whatsAtPos(pos) + " |";

            }

            board += "\n|";

        }

        board = board.substring(0, board.length() - 1);

        return board;

    }

}
