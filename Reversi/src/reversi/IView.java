package reversi;

public interface IView
{
	/**
	 * Initialise the view
	 * @param model The model which has the information about the board
	 * @param controller The controller to inform when something happens - e.g. a space is selected
	 */
	void initialise( IModel model, IController controller );
	
	/**
	 * Refresh the display
	 */
	void refreshView();
	
	/**
	 * Display a feedback message to the user
	 * @param player Which player to tell, 1 (white) or 2 (black).
	 * @param message The message to display.
	 */
	void feedbackToUser( int player, String message );
}
