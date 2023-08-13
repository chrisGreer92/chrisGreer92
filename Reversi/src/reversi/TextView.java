package reversi;

public class TextView implements IView
{
	IModel model;
	IController controller;

	
	
	/**
	 * Constructor
	 */
	public TextView()
	{
	}
	
	
	
	@Override
	public void initialise(IModel model, IController controller)
	{
		this.model = model;
		this.controller = controller;		
		
		// Create the user interface - here there isn't anything for us to do, but a GUI would create the frame and components here.
		
		// Since Swing will create a worker thread to handle things, we will do the same thing here, and just handle lines starting '1'...
		// Don't worry about how the next line works - it uses an anonymous class (lecture 9) and a thread (optional lab at the end).
		new Thread() { public void run() { loopHandlingInput(); } }.start();
	}

	
	
	/**
	 * This will be executed in a new thread
	 */
	public void loopHandlingInput()
	{
		while ( true )
		{
			String input = PGPInput.nextString().toLowerCase();
			System.out.println("Handle command: '" + input + "'");
			if ( input.compareTo("exit") == 0)
			{
				System.out.println("End program - Goodbye");
				System.exit(0);
			}
			else if ( input.compareTo("reset") == 0)
			{
				controller.startup();
			}
			else if ( input.compareTo("auto1") == 0)
			{
				controller.doAutomatedMove(1);
			}
			else if ( input.compareTo("auto2") == 0)
			{
				controller.doAutomatedMove(2);
			}
			else if ( input.length() >= 6 && input.charAt(0)=='p')	// P1,3,4
			{
				String[] vals = input.substring(1).split(",");
				if ( vals.length == 3 )
				{
					try
					{
						int player = Integer.parseInt(vals[0]);
						int x = Integer.parseInt(vals[1]);
						int y = Integer.parseInt(vals[2]);
						// Try to deal with this operation:
						System.out.println("Player " + player + " selected position " + x + "," + y );
						controller.squareSelected(player, x, y);
					}
					catch( NumberFormatException e )
					{	// Gets called if any of the parseInt calls find value is not an integer
						System.out.println("Invalid input string: '" + input + "' - must have integer player number, x and y coordinates" );
					}
				}	
				int comma1Pos = input.indexOf(',');
				if ( comma1Pos > 0 )
				{
					int comma2Pos = input.indexOf(',',comma1Pos+1);
					if ( comma2Pos > 0 && comma2Pos > (comma1Pos+1) && comma2Pos < (input.length()-1))
					{
						String val1 = input.substring(comma1Pos+1, comma2Pos);
						String val2 = input.substring(comma1Pos+1, comma2Pos);
						
					}
				}
			}
			else
			{
				System.out.println("Invalid input string: '" + input + "'" );
			}
		}
	}
	
	
	
	/**
	 * Build the array of output strings
	 * @return The array of strings
	 */
	public String[] buildStrings()
	{
		int width = model.getBoardWidth();
		int height = model.getBoardHeight();
		String[] returnArray = new String[height+1];

		String s = "    "; // 4 spaces
		for ( int x = 0 ; x < width ; x++ )
			s = s + x + "  "; // New line for next line
		returnArray[0] = s;
		
		for ( int y = 0 ; y < height ; y++ )
		{
			s = y+" :"; 
			for ( int x = 0 ; x < width ; x++ )
			{
				switch( model.getBoardContents(x, y) )
				{
				case 1:		s = s + " W "; break;
				case 2:		s = s + " B "; break;
				default:	s = s + " . "; break;
				}
			}
			returnArray[y+1] = s;
		}
		return returnArray;
	}
	
	
	
	/**
	 * Build the array of output strings rotated for player 2
	 * @return The array of strings
	 */
	public String[] buildReverseStrings()
	{
		int width = model.getBoardWidth();
		int height = model.getBoardHeight();
		String[] returnArray = new String[height+1];

		String s = "    "; // 4 spaces
		for ( int x = 0 ; x < width ; x++ )
			s = s + (width-x-1) + "  "; // New line for next line
		returnArray[0] = s;
		
		for ( int y = height-1 ; y >= 0 ; y-- )
		{
			s = y+" :"; 
			for ( int x = width-1 ; x >= 0 ; x-- )
			{
				switch( model.getBoardContents(x, y) )
				{
				case 1:		s = s + " W "; break;
				case 2:		s = s + " B "; break;
				default:	s = s + " . "; break;
				}
			}
			returnArray[height-y] = s;
		}

		return returnArray;
	}

	
	
	@Override
	public void refreshView()
	{
		int width = model.getBoardWidth();
		int height = model.getBoardHeight();

		System.out.println("Player 1 view:" );
		String[] output = buildStrings();
		for ( int i = 0 ; i < output.length ; i++ )
			System.out.println(output[i]);
		System.out.println(); // Blank line at the end
		System.out.println(player1Message+"\n"); // Blank line at the end
		
		System.out.println("Player 2 view:" );
		output = buildReverseStrings();
		for ( int i = 0 ; i < output.length ; i++ )
			System.out.println(output[i]);
		System.out.println(); // Blank line at the end
		System.out.println(player2Message+"\n"); // Blank line at the end
		
		System.out.println("Enter command: e.g. 'P1,3,4', 'auto1', 'auto2', 'exit' or 'reset'" );
	}

	
	String player1Message = "";
	String player2Message = "";
	
	@Override
	public void feedbackToUser(int player, String message)
	{
		if ( player == 1 )
			player1Message = message;
		else if ( player == 2 )
			player2Message = message;
		
		System.out.println("\nMessage to player " + player + ":\n" + message + "\n" ); // Display message with some blank lines around it
	}
}

