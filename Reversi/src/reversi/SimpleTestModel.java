package reversi;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Same as the SimpleModel but adds a simple GUI with some buttons to test features
 */
public class SimpleTestModel extends SimpleModel
{
	Random rand = new Random();
	
	public SimpleTestModel()
	{
		JFrame testFrame = new JFrame();
		testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		testFrame.setTitle("TEST FACILITY");
		
		testFrame.getContentPane().setLayout(new GridLayout(11,1));
		// Don't try to work out the following line until we have covered Lambda Expressions!
		JButton button1 = new JButton("Almost Fill Model, clear finished flag and call update");
		button1.addActionListener( e->{ clear(1); setBoardContents( 0, 0, 0 ); setBoardContents( 1, 1, 0 ); setFinished(false); view.refreshView(); controller.update(); } );
		testFrame.getContentPane().add(button1);
		
		JButton button2 = new JButton("Almost fill, with ability to play, clear finished flag and call update");
		button2.addActionListener( e->{ clear(1); setBoardContents( 0, 0, 0 ); setBoardContents( 1, 1, 2 ); setFinished(false); view.refreshView(); controller.update(); } );
		testFrame.getContentPane().add(button2);
		
		JButton button3 = new JButton("Clear totally, clear finished flag and call update");
		button3.addActionListener( e->{ clear(0); setFinished(false); view.refreshView(); controller.update(); } ); 
		testFrame.getContentPane().add(button3);
		
		JButton button4 = new JButton("Fill black, clear finished flag and call update");
		button4.addActionListener( e->{ clear(2); setFinished(false); view.refreshView(); controller.update(); } ); 
		testFrame.getContentPane().add(button4);
		
		JButton button5 = new JButton("Random fill white or black, clear finished flag and call update");
		button5.addActionListener( e->{ for ( int x = 0 ; x < getBoardWidth(); x++ ) for ( int y = 0 ; y < getBoardHeight(); y++ ) setBoardContents(  x, y, 1+rand.nextInt(2) ); setFinished(false); view.refreshView(); controller.update(); } );
		testFrame.getContentPane().add(button5);
		
		JButton button6 = new JButton("Totally random, empty/white/black, clear finished flag and call update");
		button6.addActionListener( e->{ for ( int x = 0 ; x < getBoardWidth(); x++ ) for ( int y = 0 ; y < getBoardHeight(); y++ ) setBoardContents(  x, y, rand.nextInt(3) ); setFinished(false); view.refreshView(); controller.update(); } );
		testFrame.getContentPane().add(button6);
		
		JButton button7 = new JButton("Set player 1 and call update, clear finished flag and call update");
		button7.addActionListener( e->{ setPlayer(1); setFinished(false); view.refreshView(); controller.update(); } );
		testFrame.getContentPane().add(button7);

		JButton button8 = new JButton("Set player 2 and call update, clear finished flag and call update");
		button8.addActionListener( e->{ setPlayer(2); setFinished(false); view.refreshView(); controller.update(); } );
		testFrame.getContentPane().add(button8);

		JButton button9 = new JButton("Set finished and call update");
		button9.addActionListener( e->{ setFinished(true); view.refreshView(); controller.update(); } );
		testFrame.getContentPane().add(button9);

		JButton button10 = new JButton("Set not finished and call update (to see whether it gets set again)");
		button10.addActionListener( e->{ setFinished(false); view.refreshView(); controller.update(); } );
		testFrame.getContentPane().add(button10);
		
		JButton button11 = new JButton("Play AI to the end");
		button11.addActionListener( e->{ while( !hasFinished() ) { controller.doAutomatedMove(1); controller.doAutomatedMove(2); view.refreshView(); controller.update(); } } );
		testFrame.getContentPane().add(button11);
		
		testFrame.pack();
		testFrame.setVisible(true);
	}
}
