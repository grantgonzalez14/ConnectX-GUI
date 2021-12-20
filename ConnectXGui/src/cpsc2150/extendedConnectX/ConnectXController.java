package cpsc2150.extendedConnectX;

/**
 * The controller class will handle communication between our View and our Model (IGameBoard)
 * <p>
 * This is where you will write code
 * <p>
 * You will need to include your IGameBoard interface
 * and both of the IGameBoard implementations from Project 4
 * If your code was correct you will not need to make any changes to your IGameBoard implementation class
 */
public class ConnectXController {

    //our current game that is being played
    private IGameBoard curGame;

    //The screen that provides our view
    private ConnectXView screen;

    public static final int MAX_PLAYERS = 10;
    //our play tokens are hard coded. We could make a screen to get those from the user, but

    public static final int START = 0;

    private int numPlayers;

    private int playerTurn = 0;

    private boolean newGame = false;

    private char [] playerTokens = {'X', 'O', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};

    /**
     * @param model the board implementation
     * @param view  the screen that is shown
     * @post the controller will respond to actions on the view using the model.
     */
    ConnectXController(IGameBoard model, ConnectXView view, int np) {
        this.curGame = model;
        this.screen = view;
        numPlayers = np;
    }

    /**
     * @param col the column of the activated button
     * @post will allow the player to place a token in the column if it is not full, otherwise it will display an error
     * and allow them to pick again. Will check for a win as well. If a player wins it will allow for them to play another
     * game hitting any button
     */
    public void processButtonClick(int col) {

        BoardPosition position;

        if (newGame){

            newGame();
            newGame = false;

        }

        if (curGame.checkIfFree(col)){

            for (int i = START; i < curGame.getNumRows(); i++){

                position = new BoardPosition(i, col);

                if (curGame.whatsAtPos(position) == ' '){

                    screen.setMarker(i, col, playerTokens[playerTurn]);
                    i = curGame.getNumRows();

                }

            }

            curGame.placeToken(playerTokens[playerTurn], col);

            if (curGame.checkForWin(col)){

                screen.setMessage("Player " + playerTokens[playerTurn] + " has won! Press any button to play again.");
                newGame = true;

            }
            else if (curGame.checkTie()){

                screen.setMessage("It's a tie! Press any button to play again.");
                newGame = true;

            }
            else {

                playerTurn++;

                if (playerTurn == numPlayers) playerTurn = START;

                screen.setMessage("It is " + playerTokens[playerTurn] + "'s turn.");

            }

        }
        else screen.setMessage("Column is full. It is " + playerTokens[playerTurn] + "'s turn.");

    }

    /**
     * This method will start a new game by returning to the setup screen and controller
     */
    private void newGame() {
        //close the current screen
        screen.dispose();
        //start back at the set up menu
        SetupView screen = new SetupView();
        SetupController controller = new SetupController(screen);
        screen.registerObserver(controller);
    }
}