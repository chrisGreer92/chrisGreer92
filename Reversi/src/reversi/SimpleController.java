package reversi;


/**
 * Simple controller
 * All this does is allow any player to play in any space that they wish
 */
public class SimpleController implements IController
{
	IModel model;
	IView view;
	
	java.util.Random rand = new java.util.Random();

	
	
	@Override
	public void initialise(IModel model, IView view)
	{
		this.model = model;
		this.view = view;
	}

	
	
	@Override
	public void startup()
	{
		// Initialise board
		int width = model.getBoardWidth();
		int height = model.getBoardHeight();
		for ( int x = 0 ; x < width ; x++ )
			for ( int y = 0 ; y < height ; y++ )
				model.setBoardContents(x, y, 0);
		// Consider setting up any initial pieces here in your own controller
		
		// Refresh all messages and frames
		view.feedbackToUser(1, "Simple controller - choose a square");
		view.feedbackToUser(2, "Simple controller - choose a square");
		view.refreshView();
	}

	
	
	@Override
	public void squareSelected(int player, int x, int y)
	{
		// The finished flag never gets set by this controller, but the SimpleTestModel could set it
		if ( model.hasFinished() )
		{
			view.feedbackToUser(player, "Somehow the game has finished!" );
			return; // Don't do the set board contents
		}
		
		model.setBoardContents(x, y, player);		
		view.feedbackToUser(player, "You last played in location " + x + "," + y);
		view.refreshView();
	}
	
	
	@Override
	public void doAutomatedMove(int player)
	{
		int x = rand.nextInt(model.getBoardWidth());
		int y = rand.nextInt(model.getBoardHeight());
		model.setBoardContents(x, y, player);
		view.feedbackToUser(player, "You last played in location " + x + "," + y );
		view.refreshView();
	}



	@Override
	public void update()
	{
		// Here we will set finished based upon whether there is any space on the board or not...
		boolean finished = true;
		for ( int x = 0 ; x < model.getBoardWidth() ; x++ )
			for ( int y = 0 ; y < model.getBoardHeight() ; y++ )
				if ( model.getBoardContents(x, y) == 0 )
					finished = false; // There is an empty square
		model.setFinished(finished);
		
		// We assume that something might have changed so update the labels accordingly, then tell view to update itself
		// In this controller though we don't use the finished flag or player number, so we probably just tell the user what was set, for debug purposes
		view.feedbackToUser(1, "I just updated: finished = " + model.hasFinished() + ", current player = " + model.getPlayer() );
		view.feedbackToUser(2, "I just updated: finished = " + model.hasFinished() + ", current player = " + model.getPlayer() );
		view.refreshView();
	}
}
