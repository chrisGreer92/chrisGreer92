package reversi;

// Model
// This is the data storage for the board
//
public interface IModel
{
	/**
	 * Initialise the board to a specified size, and store references to the view and controller. Note that the basic model doesn't use these references!
	 * @param width Width, in squares
	 * @param height Height, in squares
	 * @param view the view to use
	 * @param controller the controller to use
	 */
	void initialise( int width, int height, IView view, IController controller );
	
	/**
	 * Only used by controllers which have a concept of current player. Set the current player number - if the controller uses a concept of player number.
	 * @param player The player number, e.g. 1 or 2.
	 */
	void setPlayer(int player);
	
	/**
	 * Only used by controllers which have a concept of current player. Get the current player number - if the controller uses a concept of player number.
	 * @return the player number, e.g. 1 or 2.
	 */
	int getPlayer();
	
	/**
	 * Determine whether the game has finished or not - as set by setFinished();
	 * @return true if game has finished, false otherwise.
	 */
	boolean hasFinished();
	
	/**
	 * Store whether the game has finished or not.
	 * @param finished true if game has finished, galse otherwise
	 */
	void setFinished( boolean finished );
	

	/**
	 * Clear the board, setting all squares to the specified value
	 * @param value the value to set squares to
	 */
	void clear( int value );
	
	/**
	 * Get the board width, in squares
	 * @return The board width
	 */ 
	int getBoardWidth();
	
	/**
	 * Get the board height, in squares
	 * @return The board height
	 */ 
	int getBoardHeight();

	/**
	 * Get the contents of a square of the board
	 * @param x The x position (column) to access
	 * @param y The y position (row) to access
	 * @return The value of that square, as set by clear() or setBoardContents()
	 */
	int getBoardContents( int x, int y );
	
	/**
	 * Set the contents of a square of the board to a specified value
	 * @param x The x position (column) to access
	 * @param y The y position (row) to access
	 * @param value the new value for this square
	 */
	void setBoardContents( int x, int y, int value );
}
