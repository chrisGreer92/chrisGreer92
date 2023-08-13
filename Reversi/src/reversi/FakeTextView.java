package reversi;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JPanel;

/*
 * This is a very simple controller just to give you a graphical option, and illustrate how things like buttons would work on the graphics interface.
 */
public class FakeTextView implements IView
{
	IModel model;
	IController controller;

	
	
	/**
	 * Constructor
	 */
	public FakeTextView()
	{
	}
	
	
	JLabel message1 = new JLabel();
	JLabel message2 = new JLabel();
	JTextArea board1 = new JTextArea();
	JTextArea board2 = new JTextArea();
	JFrame frame1 = new JFrame();
	
	@Override
	public void initialise(IModel model, IController controller)
	{
		this.model = model;
		this.controller = controller;		
		
		// Create a dummy user interface - you need to do a proper one in your implementation
		// You will need 2 frames but I put only one into the demo
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame1.setTitle("Reversi Dummy Interface - you need two frames though!");
		frame1.setLocationRelativeTo(null); // centre on screen
		
		frame1.getContentPane().setLayout(new GridLayout(1,2));

		board1.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5) );
		JPanel p1Panel = new JPanel();
		p1Panel.setBorder( BorderFactory.createLineBorder(Color.LIGHT_GRAY,3) );
		p1Panel.setLayout( new BorderLayout() );
		frame1.add(p1Panel);
		
		board2.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5) );
		JPanel p2Panel = new JPanel();
		p2Panel.setBorder( BorderFactory.createLineBorder(Color.BLACK,3) );
		p2Panel.setLayout( new BorderLayout() );
		frame1.getContentPane().add(p2Panel);

		message1.setFont( new Font( "Arial", Font.BOLD, 20 ));
		message2.setFont( new Font( "Arial", Font.BOLD, 20 ));

		board1.setFont( new Font( "Consolas", Font.BOLD, 20 ));
		board2.setFont( new Font( "Consolas", Font.BOLD, 20 ));
		board1.setPreferredSize(new Dimension(400,330));
		board2.setPreferredSize(new Dimension(400,330));
		
		// Now we add the 'stuff' for each player to the panel for that player...
		message1.setText("Initial text goes here");
		p1Panel.add(message1,BorderLayout.NORTH);
		board1.setText("XXXXXXXXXXXXXXXXXXXX\nX\r\nX\r\nX\r\nX\r\nX\r\nX\r\nX\r\nX\r\nXXXXXXXXXXXXXXXXXXXX\r\n");
		p1Panel.add(board1, BorderLayout.CENTER );
		
		// AI button
		JButton butAI1 = new JButton("AI (1)");
		butAI1.addActionListener( new ActionListener() 
					{ public void actionPerformed(ActionEvent e) { controller.doAutomatedMove(1); } } );
		p1Panel.add(butAI1,BorderLayout.SOUTH);

		message2.setText("Initial text goes here");
		p2Panel.add(message2,BorderLayout.NORTH);
		board2.setText("XXXXXXXXXXXXXXXXXXXX\nX\r\nX\r\nX\r\nX\r\nX\r\nX\r\nX\r\nX\r\nXXXXXXXXXXXXXXXXXXXX\r\n");
		p2Panel.add(board2, BorderLayout.CENTER );
		
		// AI button
		JButton butAI2 = new JButton("AI (2)");
		butAI2.addActionListener( new ActionListener() 
					{ public void actionPerformed(ActionEvent e) { controller.doAutomatedMove(2); } } );
		p2Panel.add(butAI2,BorderLayout.SOUTH);
		
		frame1.pack();
		frame1.setVisible(true);
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

		StringBuilder p1 = new StringBuilder();
		p1.append("Player 1 view:\r\n" );
		String[] output = buildStrings();
		for ( int i = 0 ; i < output.length ; i++ )
			p1.append( output[i] + "\r\n" );
		p1.append("Dummy interface supports only AI,\nnot reset or manual selection of\npositions");
		board1.setText( p1.toString() );
		
		StringBuilder p2 = new StringBuilder();
		p2.append("Player 2 view:\r\n" );
		output = buildReverseStrings();
		for ( int i = 0 ; i < output.length ; i++ )
			p2.append( output[i] + "\r\n" );
		p2.append("Dummy interface supports only AI,\nnot reset or manual selection of\npositions");
		board2.setText( p2.toString() );

		frame1.repaint();
	}


	@Override
	public void feedbackToUser(int player, String message)
	{
		if ( player == 1 )
			message1.setText(message);
		else if ( player == 2 )
			message2.setText(message);
	}
}

