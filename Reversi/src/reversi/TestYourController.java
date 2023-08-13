package reversi;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.JPanel;

/*
 * This is a very simple controller just to give you a graphical option, and illustrate how things like buttons would work on the graphics interface.
 */
public class TestYourController extends SimpleModel implements IView
{
    IModel model;
    IController controller;

    /**
     * Constructor
     */
    public TestYourController()
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

        // Create a dummy user interface - you need to do a proper one in your
        // implementation
        // You will need 2 frames but I put only one into the demo
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame1.setTitle("Test program - based upon FakeTextView and SimpleTestModel");
        frame1.setLocationRelativeTo(null); // centre on screen

        frame1.getContentPane().setLayout(new GridLayout(1, 2));

        board1.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        JPanel p1Panel = new JPanel();
        p1Panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 3));
        p1Panel.setLayout(new BorderLayout());
        frame1.add(p1Panel);

        board2.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        JPanel p2Panel = new JPanel();
        p2Panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        p2Panel.setLayout(new BorderLayout());
        frame1.getContentPane().add(p2Panel);

        message1.setFont(new Font("Arial", Font.BOLD, 20));
        message2.setFont(new Font("Arial", Font.BOLD, 20));

        board1.setFont(new Font("Consolas", Font.BOLD, 20));
        board2.setFont(new Font("Consolas", Font.BOLD, 20));
        board1.setPreferredSize(new Dimension(400, 330));
        board2.setPreferredSize(new Dimension(400, 330));

        // Now we add the 'stuff' for each player to the panel for that player...
        message1.setText("Initial text goes here");
        p1Panel.add(message1, BorderLayout.NORTH);
        board1.setText("XXXXXXXXXXXXXXXXXXXX\nX\r\nX\r\nX\r\nX\r\nX\r\nX\r\nX\r\nX\r\nXXXXXXXXXXXXXXXXXXXX\r\n");
        p1Panel.add(board1, BorderLayout.CENTER);

        // AI button
        JButton butAI1 = new JButton("AI (1)");
        butAI1.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                controller.doAutomatedMove(1);
            }
        });
        p1Panel.add(butAI1, BorderLayout.SOUTH);

        message2.setText("Initial text goes here");
        p2Panel.add(message2, BorderLayout.NORTH);
        board2.setText("XXXXXXXXXXXXXXXXXXXX\nX\r\nX\r\nX\r\nX\r\nX\r\nX\r\nX\r\nX\r\nXXXXXXXXXXXXXXXXXXXX\r\n");
        p2Panel.add(board2, BorderLayout.CENTER);

        // AI button
        JButton butAI2 = new JButton("AI (2)");
        butAI2.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                controller.doAutomatedMove(2);
            }
        });
        p2Panel.add(butAI2, BorderLayout.SOUTH);

        frame1.pack();
        frame1.setVisible(true);
    }

    /**
     * Build the array of output strings
     *
     * @return The array of strings
     */
    public String[] buildStrings()
    {
        int width = model.getBoardWidth();
        int height = model.getBoardHeight();
        String[] returnArray = new String[height + 1];

        String s = "    "; // 4 spaces
        for (int x = 0; x < width; x++)
            s = s + x + "  "; // New line for next line
        returnArray[0] = s;

        for (int y = 0; y < height; y++)
        {
            s = y + " :";
            for (int x = 0; x < width; x++)
            {
                switch (model.getBoardContents(x, y))
                {
                    case 1:
                        s = s + " W ";
                        break;
                    case 2:
                        s = s + " B ";
                        break;
                    default:
                        s = s + " . ";
                        break;
                }
            }
            returnArray[y + 1] = s;
        }
        return returnArray;
    }

    /**
     * Build the array of output strings rotated for player 2
     *
     * @return The array of strings
     */
    public String[] buildReverseStrings()
    {
        int width = model.getBoardWidth();
        int height = model.getBoardHeight();
        String[] returnArray = new String[height + 1];

        String s = "    "; // 4 spaces
        for (int x = 0; x < width; x++)
            s = s + (width - x - 1) + "  "; // New line for next line
        returnArray[0] = s;

        for (int y = height - 1; y >= 0; y--)
        {
            s = y + " :";
            for (int x = width - 1; x >= 0; x--)
            {
                switch (model.getBoardContents(x, y))
                {
                    case 1:
                        s = s + " W ";
                        break;
                    case 2:
                        s = s + " B ";
                        break;
                    default:
                        s = s + " . ";
                        break;
                }
            }
            returnArray[height - y] = s;
        }

        return returnArray;
    }

    @Override
    public void refreshView()
    {
        int width = model.getBoardWidth();
        int height = model.getBoardHeight();

        StringBuilder p1 = new StringBuilder();
        p1.append("Player 1 view:\r\n");
        String[] output = buildStrings();
        for (int i = 0; i < output.length; i++)
            p1.append(output[i] + "\r\n");
        p1.append("Dummy interface supports only AI,\nnot reset or manual selection of\npositions");
        board1.setText(p1.toString());

        StringBuilder p2 = new StringBuilder();
        p2.append("Player 2 view:\r\n");
        output = buildReverseStrings();
        for (int i = 0; i < output.length; i++)
            p2.append(output[i] + "\r\n");
        p2.append("Dummy interface supports only AI,\nnot reset or manual selection of\npositions");
        board2.setText(p2.toString());

        frame1.repaint();
    }


    // Note: since we are using a UI here it is tricky to ensure that messages come out
    // For automated testing I will drop the UI so can also test that
    // Here we just ensure that messages that we do get are correctly formatted:
    @Override
    public void feedbackToUser(int player, String message)
    {
        if (player == 1)
            message1.setText(message);
        else if (player == 2)
            message2.setText(message);

        // Validate the message text against the state of the program:

        message = message.replace('–', '-'); // Fix for copy-paste from pdf puts a different - into the program

        // Could be text saying it IS their turn...
        String myTurn = playerName[player] + " player - choose where to put your piece";
        if ( this.player == player && myTurn.compareToIgnoreCase(message) == 0 )
            return; // Valid message if it really was their turn

        // Could be text saying it is NOT their turn...
        String notMyTurn = playerName[player] + " player - not your turn";
        if ( this.player != player && notMyTurn.compareToIgnoreCase(message) == 0 )
            return; // Valid message if it was NOT their turn

        // Could be text saying not to move now...
        String stopTryingToPlay = "It is not your turn!";
        if ( this.player != player && stopTryingToPlay.compareToIgnoreCase(message) == 0 )
            return; // Also a valid message if it was NOT their turn

        // Could be text saying game finished...
        int black = 0; int white = 0;
        for (int y = 0; y < 8; y++)
            for (int x = 0; x < 8; x++)
                switch( boardContents[x][y] )
                {
                    case 1: white++; break;
                    case 2: black++; break;
                }

        // Note: here I noticed that the requirements say 'Reset the game' but my demo said 'Reset game' erroneously, so I allow both in case students copied the text from the demo
        String finalMessage1 = "Black won. Black " + black + " to White " + white + ". Reset the game to replay.";
        String finalMessage2 = "Black won. Black " + black + " to White " + white + ". Reset game to replay.";
        if ( this.hasFinished() )
        {
            if ( white == black )
            {
                finalMessage1 = "Draw. Both players ended with " + white + " pieces. Reset the game to replay.";
                finalMessage2 = "Draw. Both players ended with " + white + " pieces. Reset game to replay.";
            }
            else if ( white > black )
            {
                finalMessage1 = "White won. White " + white + " to Black " + black + ". Reset the game to replay.";
                finalMessage2 = "White won. White " + white + " to Black " + black + ". Reset game to replay.";
            }
            if ( finalMessage1.compareToIgnoreCase(message) == 0 )
                return; // Expected
            // Also allow this one, as the demo showed this in error...
            if ( finalMessage2.compareToIgnoreCase(message) == 0 )
                return; // Expected
        }

        System.err.println("\nFEEDBACK STRINGS ERROR: Unexpected feedback message for player " + playerName[player] + " : '" + message + "'");
        if ( this.hasFinished() )
            System.err.println("Potential valid message would have been: " + finalMessage1 +"\n" ); // Don't suggest the erroneous one I did
        else // Not finished, so message to tell player if it is their turn
        {
            if ( this.player == player )
                System.err.println("Potential valid message would have been: " + myTurn +"\n" );
            if ( this.player != player )
                System.err.println("Potential valid message would have been: " + notMyTurn + "\n   or " + stopTryingToPlay +"\n" );
        }
    }

    Random rand = new Random();

    /**
     * Check that board state matches this string.
     *
     * @param expectedStateAsDotOneTwo String of characters
     */
    public void verifyBoardState(String expectedStateAsDotOneTwo)
    {
        for (int y = 0; y < 8; y++)
            for (int x = 0; x < 8; x++)
                switch (expectedStateAsDotOneTwo.charAt(y * 9 + x))
                { // Note: char is a number, so can be used in a switch statement
                    case '.':
                        if (boardContents[x][y] != 0)
                            System.err.println("Failed: Expected empty square at position (" + x + "," + y + ")");
                        break;
                    case '1':
                        if (boardContents[x][y] != 1)
                            System.err.println("Failed: Expected empty player 1 piece at position (" + x + "," + y + ")");
                        break;
                    case '2':
                        if (boardContents[x][y] != 2)
                            System.err.println("Failed: Expected empty player 2 piece at position (" + x + "," + y + ")");
                        break;
                    default:
                        System.err.println(
                                "Error: give verifyBoardState() a string to validate against with 64+7 characters which are . 1 or 2 with an extra separator every 9th character");
                        return; // don't carry on since this needs fixing!
                }
    }


    public static String[] playerName = { "None", "White", "Black" };


    public void fillBoardAndFinished(int playerNumberOrEmpty)
    {
        System.out.println("\nTest controller update() with a filled/empty board for player " + playerName[player] + " and a finished game");
        clear(playerNumberOrEmpty);
        setFinished(false);
        view.refreshView();
        controller.update();

        if (!finished)	// Should be finished!
            System.err.println("Test FAILED: full/empty board: game has finished but controller update() failed to identify this - see description of update() on IController!");
        else
            System.out.println("Test PASSED: update() realised that no player had a move so game has finished");
        System.out.println( "MANUAL CHECK NEEDED: Please check that the message on the view correctly shows that the game has finished and counts the pieces correctly." );
    }

    public void almostFillAndFinishedAsPlayer(int playerNumber)
    {
        System.out.println("\nTest controller update() with almost full board - should set finished flag " + playerName[player] );
        clear(playerNumber);
        setBoardContents(0, 0, 0);
        setBoardContents(1, 1, 0);
        setFinished(false);
        view.refreshView();
        controller.update();
        if ( !finished )
            System.err.println( "Test FAILED: game has not finised, but update() failed to clear the flag!" );
        else
            System.out.println( "Test PASSED: game was correctly recorded as not finished by update()" );
    }


    public void almostFillButBePlayableAsPlayer(int playerNumber)
    {
        clear(playerNumber);
        setBoardContents(0, 0, 0);
        setBoardContents(1, 1, 3 - playerNumber);
        setFinished(false);
        setPlayer(3-playerNumber); // Switch to other player to check that update switches it back
        view.refreshView();
        controller.update();
        if ( finished )	// Game should not be finished
            System.err.println( "Test FAILED: game has not finised, but update() failed to clear the finished flag!" );
        else
            System.out.println( "Test PASSED: game was correctly recorded as not finished by update()" );
        if ( player != playerNumber )	// Only this player could actually play
            System.err.println( "Test FAILED: update has not realised that it needs to be player " + playerName[player] + "'s turn as only they can play" );
        else
            System.out.println( "Test PASSED: game correctly realised that player " + playerName[player] + " can player" );
        System.out.println( "MANUAL CHECK NEEDED: Please check that the message on the view correctly shows that player " + playerName[player] + " can play and " + playerName[3-player] + " cannot play.");
    }




    public void randomFillWhiteAndBlack()
    {
        System.out.println("\nTest controller update() with filled board - should realise game has finished and report it" );
        setFinished(false);
        for (int x = 0; x < getBoardWidth(); x++)
            for (int y = 0; y < getBoardHeight(); y++)
                setBoardContents(x, y, 1 + rand.nextInt(2));
        view.refreshView();
        // We are testing the controller.update() here
        controller.update();
        if (!finished)	// Should be finished!
            System.err.println("Test FAILED: Random fill: game has finished but controller update() failed to identify this - see description of update() on IController!");
        else
            System.out.println("Test PASSED: Random fill: controller update() correctly identified game has finished and set flag.");
        System.out.println( "MANUAL CHECK NEEDED: Please check that the message on the view correctly shows that the game has finished and counts the pieces correctly." );
    }

    public void alternateFillWhiteAndBlack()
    {
        System.out.println("\nTest controller update() with filled board - should report a draw (32 each)" );
        for (int x = 0; x < getBoardWidth(); x++)
            for (int y = 0; y < getBoardHeight(); y++)
                setBoardContents(x, y, 1 + (x+y)%2);
        setFinished(false);
        view.refreshView();
        // We are testing the controller.update() here
        controller.update();
        if (!finished)	// Should be finished!
            System.err.println("Test FAILED: alternate fill: game has finished but controller update() failed to identify this - see description of update() on IController!");
        else
            System.out.println("Test PASSED: alternate fill: controller update() correctly identified game has finished and set flag.");
        System.out.println( "MANUAL CHECK NEEDED: Please check that the message on the view correctly shows that the game finished with a draw! 32 pieces each." );
    }

    public void setRandomContents()
    {
        System.out.println("\nTest controller update() with random contents (not filled) and player " + playerName[player] );
        int oldPlayer = player;
        for (int x = 0; x < getBoardWidth(); x++)
            for (int y = 0; y < getBoardHeight(); y++)
                setBoardContents(x, y, rand.nextInt(3));
        // Now manually add something to ensure that there is a place to play...
        setBoardContents( 0, 0, player ); // Set corner to current player
        setBoardContents( 1, 0, 3-player ); // Set next to it to other player
        setBoardContents( 2, 0, 0 ); // Give current player a valid place to move to
        setFinished(true);	// Finished should be cleared as there is a valid move
        //System.out.println( "Testing random board and controller update() : player was " + player + " before update() and should not change. Also finished flag should be cleared by update()." );
        view.refreshView();
        controller.update();
        if ( player != oldPlayer ) // Player should not have changed
            System.err.println( "Test FAILED: player " + oldPlayer + " had a valid move so game should not have switched to the other player!" );
        else
            System.out.println( "Test PASSED: player had a valid move and game remained with this player!" );
        if ( finished )	// Game should not be finished
            System.err.println( "Test FAILED: game has not finised, but update() failed to clear the finished flag!" );
        else
            System.out.println( "Test PASSED: game was correctly recorded as not finished by update()" );
        System.out.println( "MANUAL CHECK NEEDED: Please check that the message on the view correctly shows that player " + playerName[player] + " can play and " + playerName[3-player] + " cannot play.");
    }


    int testCase[][]= { {2,4,1}, {4,5,1}, {5,4,1}, {2,3,2}, {4,2,2}, {4,1,3}, {5,1,1}, {6,5,1}, {6,4,3}, {2,5,2}, {1,4,2}, {6,3,2}, {7,4,2}, {6,1,1}, {3,6,2}, {2,7,3}, {5,2,2}, {0,4,4}, {-1,-1,2} };

    // This is convoluted because I can't just give you the code for the automated move and calculation as it would give you the answers.
    public void testAI( int testStage )
    {
        System.out.println("\nTest AI on known configuration " + testStage + " so that I don't have to give you the coursework code to be able to test this..." );

        // Clear the controller to get to initial state first
        controller.startup();
        this.refreshView();
        controller.update();

        int nextCapture = 0;
        int pl = 1; // Start with white
        // Play all of the moves to get to the test stage
        for ( int ts = 0; ts < testStage ; ts++ )
        {
            controller.squareSelected(pl, testCase[ts][0], testCase[ts][1]);
            nextCapture = testCase[ts+1][2];
            pl = 3 - pl; // Switch player for next move
        }

        // Count squares for player pl before move
        int before = 0;
        for ( int x = 0 ; x < 8 ; x++ )
            for ( int y = 0 ; y < 8 ; y++ )
                if ( boardContents[x][y] == pl )
                    before++;

        // Now try to run the automated move
        controller.doAutomatedMove(pl);

        int after = 0;
        for ( int x = 0 ; x < 8 ; x++ )
            for ( int y = 0 ; y < 8 ; y++ )
                if ( boardContents[x][y] == pl )
                    after++;

        if ( before + 1 + nextCapture != after )
            System.err.println("Test FAILED: automated move stage " + testStage + " captured " + (after-before-1) + " rather than " + nextCapture + "\nThis means that your code to capture the most doesn't always work.");
        else
            System.out.println("Test PASSED: automated move stage " + testStage + " correctly found a move capturing " + nextCapture + " pieces");
    }



    public void createTestButtons()
    {
        JFrame testFrame = new JFrame();
        testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        testFrame.setTitle("TEST FACILITY");

        testFrame.getContentPane().setLayout(new GridLayout(12, 3, 5/* hgap */, 5/* vgap */));

        testFrame.getContentPane().add(new JLabel("--- Controller update() tests: --- ", SwingConstants.CENTER));
        testFrame.getContentPane().add(
                new JLabel("--- Manual place pieces tests (use them in order 1,2,3 etc): --- ", SwingConstants.CENTER));
        testFrame.getContentPane().add(new JLabel("--- AI tests: --- ", SwingConstants.CENTER));


        JButton button1a = new JButton("Test empty board sets finished (draw, 0 pieces each)");
        button1a.addActionListener(e -> {
            fillBoardAndFinished(0);
        });
        testFrame.getContentPane().add(button1a);

        // test manually placing pieces, as a view
        JButton buttonCtrl1 = new JButton("1. Test placing pieces - setup board");
        buttonCtrl1.addActionListener(e -> {
            System.out.println("\nTest initial board setup..." );
            // Initialise state before test, regardless of current state
            setFinished(true); // Not finished the game - just in case they missed it in startup!
            this.setPlayer(2); // Change to white player
            controller.startup();
            // Check startup() worked...
            if ( this.getPlayer() != 1 )
            {
                System.out.println("Test FAILED: startup() did not set player to 1");
                setPlayer(1);
            }
            if ( this.hasFinished() )
            {
                System.out.println("Test FAILED: startup() did not clear the finished flag");
                setFinished(false);
            }
            view.refreshView();
            controller.update();
            // Now check that the initial board state is correct
            System.out.println("Testing board state before move...");
            verifyBoardState("........|........|........|...12...|...21...|........|........|........");
        });
        testFrame.getContentPane().add(buttonCtrl1);

        JButton button1c = new JButton("Play AI to the end");
        button1c.addActionListener(e -> {
            System.out.println( "Test: keep playing AI moves until your controller says that the game has finished.");
            while (!hasFinished())
            {
                System.out.println("Playing a move by white then a move by black...");
                controller.doAutomatedMove(1);
                controller.doAutomatedMove(2);
                view.refreshView();
                controller.update();
            }
            System.out.println( "MANUAL CHECK NEEDED: Please check that at this point neither player can play. I can't do this automatically here without giving you the coursework answers.");
            System.out.println( "IMPORTANT: MANUALLY CHECK the board now and ensure that all empty spaces could not have either White OR black play in them. If they could then you set finished incorrectly. I can check this automatically but can't give you the code to do it without answering the coursework for you.");
        });
        testFrame.getContentPane().add(button1c);


        JButton button2a = new JButton("Test white board sets finished (white won with 64 pieces)");
        button2a.addActionListener(e -> { fillBoardAndFinished(1); });
        testFrame.getContentPane().add(button2a);

        JButton buttonCtrl2 = new JButton("2. White captures piece ...");
        buttonCtrl2.addActionListener(e -> {
            System.out.println("\nTest player plays a piece..." );
            controller.squareSelected(1/* player */, 5, 3);
            System.out.println("Testing board state after white move...");
            verifyBoardState("........|........|........|...111..|...21...|........|........|........");
            if (this.getPlayer() != 2)
                System.err.println("Your controller should have set player to 2 after player 1 made a move!");
        });
        testFrame.getContentPane().add(buttonCtrl2);

        testFrame.getContentPane().add( new JLabel("I cannot include AI test function for random board state so here are some examples:") );


        JButton button3a = new JButton("Test black board sets finished (black won with 64 pieces)");
        button3a.addActionListener(e -> { fillBoardAndFinished(2); });
        testFrame.getContentPane().add(button3a);

        JButton buttonCtrl3 = new JButton("3. Black captures other row...");
        buttonCtrl3.addActionListener(e -> {
            System.out.println("\nTest player plays a piece..." );
            controller.squareSelected(2/* player */, 5, 4);
            System.out.println("Testing board state after black move...");
            verifyBoardState("........|........|........|...111..|...222..|........|........|........");
            if (this.getPlayer() != 1)
                System.err.println("Your controller should have set player to 1 after player 2 made a move!");
        });
        testFrame.getContentPane().add(buttonCtrl3);

        JButton button3c = new JButton("Simple test of AI : 1 and 2");
        button3c.addActionListener(e -> { testAI(1); testAI(2);} );
        testFrame.getContentPane().add(button3c);



        JButton button4a = new JButton("Test almost full white board sets finished (white won with 62 pieces)");
        button4a.addActionListener(e -> { almostFillAndFinishedAsPlayer(1); });
        testFrame.getContentPane().add(button4a);


        JButton buttonCtrl4 = new JButton("4. White captures most of board...");
        buttonCtrl4.addActionListener(e -> {
            System.out.println("\nTest player plays a piece..." );
            controller.squareSelected(1/* player */, 5, 5);
            System.out.println("Testing board state after white move...");
            verifyBoardState("........|........|........|...111..|...211..|.....1..|........|........");
            if (this.getPlayer() != 2)
                System.err.println("Your controller should have set player to 2 after player 1 made a move!");
        });
        testFrame.getContentPane().add(buttonCtrl4);

        JButton button4c = new JButton("Simple test of AI : 3 and 4");
        button4c.addActionListener(e -> { testAI(3); testAI(4);} );
        testFrame.getContentPane().add(button4c);





        JButton button5a = new JButton("Test almost full black board sets finished (black won with 62 pieces)");
        button5a.addActionListener(e -> { almostFillAndFinishedAsPlayer(2); });
        testFrame.getContentPane().add(button5a);


        JButton buttonCtrl5 = new JButton("5. Black captures diagonal...");
        buttonCtrl5.addActionListener(e -> {
            System.out.println("\nTest player plays a piece..." );
            controller.squareSelected(2/* player */, 5, 2);
            System.out.println("Testing board state after black move...");
            verifyBoardState("........|........|.....2..|...121..|...211..|.....1..|........|........");
            if (this.getPlayer() != 1)
                System.err.println("Your controller should have set player to 1 after player 2 made a move!");
        });
        testFrame.getContentPane().add(buttonCtrl5);

        JButton button5c = new JButton("Simple test of AI : 5 and 6");
        button5c.addActionListener(e -> { testAI(5); testAI(6); } );
        testFrame.getContentPane().add(button5c);




        JButton button6a = new JButton("Test almost full black board where white can play (not finished)");
        button6a.addActionListener(e -> { almostFillButBePlayableAsPlayer(1); });
        testFrame.getContentPane().add(button6a);

        JButton buttonCtrl6 = new JButton("6. White recaptures the middle one...");
        buttonCtrl6.addActionListener(e -> {
            System.out.println("\nTest player plays a piece..." );
            controller.squareSelected(1/* player */, 4, 2);
            System.out.println("Testing board state after white move...");
            verifyBoardState("........|........|....12..|...111..|...211..|.....1..|........|........");
            if (this.getPlayer() != 2)
                System.err.println("Your controller should have set player to 2 after player 1 made a move!");
        });
        testFrame.getContentPane().add(buttonCtrl6);

        JButton button6c = new JButton("Simple test of AI : 7 and 8");
        button6c.addActionListener(e -> { testAI(7); testAI(8);} );
        testFrame.getContentPane().add(button6c);




        JButton button7a = new JButton("Test almost full black board where black can play (not finished)");
        button7a.addActionListener(e -> { almostFillButBePlayableAsPlayer(2); });
        testFrame.getContentPane().add(button7a);

        JButton buttonCtrl7 = new JButton("7. Black takes the top and left...");
        buttonCtrl7.addActionListener(e -> {
            System.out.println("\nTest player plays a piece..." );
            controller.squareSelected(2/* player */, 3, 2);
            System.out.println("Testing board state after black move...");
            verifyBoardState("........|........|...222..|...211..|...211..|.....1..|........|........");
            if (this.getPlayer() != 1)
                System.err.println("Your controller should have set player to 1 after player 2 made a move!");
        });
        testFrame.getContentPane().add(buttonCtrl7);

        JButton button7c = new JButton("Simple test of AI : 9 and 10");
        button7c.addActionListener(e -> { testAI(9); testAI(10); } );
        testFrame.getContentPane().add(button7c);



        JButton button8a = new JButton("Random fill white and black - test finished (finished)");
        button8a.addActionListener(e -> { randomFillWhiteAndBlack(); });
        testFrame.getContentPane().add(button8a);

        JButton buttonCtrl8 = new JButton("8. White likes diagonals...");
        buttonCtrl8.addActionListener(e -> {
            System.out.println("\nTest player plays a piece..." );
            controller.squareSelected(1/* player */, 2, 1);
            System.out.println("Testing board state after white move...");
            verifyBoardState("........|..1.....|...122..|...211..|...211..|.....1..|........|........");
            if (this.getPlayer() != 2)
                System.err.println("Your controller should have set player to 2 after player 1 made a move!");
        });
        testFrame.getContentPane().add(buttonCtrl8);

        JButton button8c = new JButton("Simple test of AI : 11 and 12");
        button8c.addActionListener(e -> { testAI(11); testAI(12);} );
        testFrame.getContentPane().add(button8c);




        JButton button9a = new JButton("Random fill so current player can still play");
        button9a.addActionListener(e -> { setRandomContents(); });
        testFrame.getContentPane().add(button9a);

        JButton buttonCtrl9 = new JButton("9. Black captures multiple directions, multiple pieces...");
        buttonCtrl9.addActionListener(e -> {
            System.out.println("\nTest player plays a piece..." );
            controller.squareSelected(2/* player */, 6, 4);
            System.out.println("Testing board state after black move...");
            verifyBoardState("........|..1.....|...122..|...212..|...2222.|.....1..|........|........");
            if (this.getPlayer() != 1)
                System.err.println("Your controller should have set player to 1 after player 2 made a move!");
        });
        testFrame.getContentPane().add(buttonCtrl9);

        JButton button9c = new JButton("Simple test of AI : 13 and 14");
        button9c.addActionListener(e -> { testAI(13); testAI(14);} );
        testFrame.getContentPane().add(button9c);




        JButton button10a = new JButton("Altenate colours, resulting in a draw! (32 pieces each)");
        button10a.addActionListener(e -> { alternateFillWhiteAndBlack(); });
        testFrame.getContentPane().add(button10a);

        JButton buttonCtrl10 = new JButton("10. White captures just one piece...");
        buttonCtrl10.addActionListener(e -> {
            System.out.println("\nTest player plays a piece..." );
            controller.squareSelected(1/* player */, 4, 1);
            System.out.println("Testing board state after white move...");
            verifyBoardState("........|..1.1...|...112..|...212..|...2222.|.....1..|........|........");
            if (this.getPlayer() != 2)
                System.err.println("Your controller should have set player to 2 after player 1 made a move!");
        });
        testFrame.getContentPane().add(buttonCtrl10);

        JButton button10c = new JButton("Simple test of AI : 15 and 16");
        button10c.addActionListener(e -> { testAI(15); testAI(16); } );
        testFrame.getContentPane().add(button10c);




        testFrame.getContentPane().add( new JLabel("Note: These will all fail if you did not implement update() on controller") );

        JButton buttonCtrl11 = new JButton("11. Black multidirection capture...");
        buttonCtrl11.addActionListener(e -> {
            System.out.println("\nTest player plays a piece..." );
            controller.squareSelected(2/* player */, 3, 1);
            System.out.println("Testing board state after black move...");
            verifyBoardState("........|..121...|...222..|...212..|...2222.|.....1..|........|........");
            if (this.getPlayer() != 1)
                System.err.println("Your controller should have set player to 1 after player 2 made a move!");
        });
        testFrame.getContentPane().add(buttonCtrl11);

        JButton button11c = new JButton("Simple test of AI : 17 and 18");
        button11c.addActionListener(e -> { testAI(17); testAI(18); } );
        testFrame.getContentPane().add(button11c);

        testFrame.pack();
        testFrame.setVisible(true);
    }

    public static void main(String[] args)
    {
        TestYourController testObject = new TestYourController();
        testObject.createTestButtons();

        // Note that this object acts as both model and view so that we can test the
        // controller...
        IModel model = testObject;
        IView view = testObject;

        IController controller = new ReversiController();

        // Initialise everything...
        model.initialise(8, 8, view, controller);
        controller.initialise(model, view);
        view.initialise(model, controller);

        // Now start the game - set up the board
        controller.startup();
    }

}