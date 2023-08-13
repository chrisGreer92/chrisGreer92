package reversi;

public interface IController
{
	/**
	 * Initialise the controller for a model and view
	 * @param model The model to use
	 * @param view The view to use.
	 */
	void initialise( IModel model, IView view );

	
	/**
	 * Start the game again - set up view and board appropriately
	 * See requirements - don't forget to set the player number and finished flag to the correct values as well as clearing the board.
	 */
	void startup();
	
	/**
	 * Implement this to update any state information.
	 * This is really used to simplify your testing (so that SimpleTestModel will work for you) and to simplify my testing.
	 * 
	 * Ideally this needs to do three things:
	 * If the controller uses the finished flag, then it should look at the board and set the finished flag or not according to whether the game has finished.
	 * If the controller use the player number, then it should check it in case it changed. (Probably nothing to do for this.) 
	 * Also set the feedback to the user according to which player is the current player (model.getPlayer()) and whether the game has finished or not.
	 * 
	 * Once you have implemented this, you may find it easy to just call it after each time you add a piece to the board and have changed the player, to update the player feedback and the finished status accordingly. 
	 * e.g. mine for Reversi does the following
	 * If current player cannot play, change to other player.
	 * If this player also cannot play then end the game - count the number of pieces, set the labels for players, and set the finished flag.
	 * If the game has not finished, send the correct feedback messages to players to tell them whose turn it is.
	 */
	void update();
	
	/**
	 * Called by view when some position on the board is selected
	 * @param player Which player clicked the square, 1 (white) or 2 (black).
	 * @param x The x coordinate of the square which is clicked
	 * @param y The y coordinate of the square which is clicked
	 */
	void squareSelected( int player, int x, int y );
	
	
	/**
	 * Called when view requests an automated move.
	 * @param player Which player the automated move should happen for, 1 (white) or 2 (black).
	 */
	void doAutomatedMove( int player );
}
