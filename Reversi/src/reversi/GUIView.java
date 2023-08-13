package reversi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIView implements IView {

    GUIView thisGUI;
    PlayerFrame playerOne, playerTwo;
    IModel model;
    IController controller;
    int width, height, area;

    @Override
    public void initialise(IModel model, IController controller) {

        this.model = model;
        this.controller = controller;
        thisGUI = this;

        width = model.getBoardWidth();
        height = model.getBoardHeight();
        area = width*height;

        playerOne = new PlayerFrame(1);
        playerTwo = new PlayerFrame(2);

        //Pack and show the frames
        playerOne.pack();
        playerOne.setVisible(true);
        playerTwo.pack();
        playerTwo.setVisible(true);

    }

    public class PlayerFrame extends JFrame {

        int playerNo;
        String playerColor;
        JPanel tilesPanel;
        ReversiTile[] arrayTiles = new ReversiTile[area];
        JLabel frameTitle = new JLabel("Not Set Yet");
        JButton greedyAI;
        JButton refreshBoard;

        public PlayerFrame(int player){

        playerNo = player;

        if (playerNo == 1)
            playerColor = "white";
        else
            playerColor = "black";

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Reversi - " + playerColor + " player");
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        setUpText();

        setUpTiles();

        setUpButtons();

        }

        public void SetMessage(String message){
            frameTitle.setText(message);
        }

        public void setUpText() {
            //Set up top text
            JPanel textPanel = new JPanel();
            textPanel.add(frameTitle);
            getContentPane().add(textPanel,BorderLayout.NORTH);
        }

        public void setUpTiles(){

            //Set up the coloured tiles
            tilesPanel = new JPanel();
            tilesPanel.setLayout(new GridLayout(width,height));
            getContentPane().add(tilesPanel,BorderLayout.CENTER);

            // Set up the tiles
            for (int i=0;i<area;i++) {
                int x = i % width;
                int y = i / width;

                arrayTiles[i] = new ReversiTile(x, y, playerNo, controller);
            }
            for (int i=0;i<area;i++) {
                if (playerNo == 1)
                    tilesPanel.add(arrayTiles[i]);
                else if (playerNo == 2)
                    tilesPanel.add(arrayTiles[((area-1) - i)]);
            }

        }

        public void setUpButtons(){
            //Set up the buttons panel
            JPanel buttonsPanel = new JPanel();
            buttonsPanel.setLayout(new GridLayout(2,1));

            refreshBoard = new JButton("Restart");
            greedyAI = new JButton("Greedy AI (play " +playerColor+ ")");

            refreshBoard.addActionListener(new RefreshBoard());
            greedyAI.addActionListener(new GreedyAI());

            buttonsPanel.add(greedyAI);
            buttonsPanel.add(refreshBoard);
            getContentPane().add(buttonsPanel,BorderLayout.SOUTH);
        }

        public void refreshTiles(){

            // Refresh the tiles
            for (int i=0;i<area;i++) {
                int x = i % width;
                int y = i / width;

                int player = model.getBoardContents(x,y);
                arrayTiles[i].SetIt(player);
            }

        }

        public class GreedyAI implements ActionListener{

            @Override
            public void actionPerformed(ActionEvent e) {
                controller.doAutomatedMove(playerNo);
            }
        }

    }

    @Override
    public void refreshView() {

        playerOne.refreshTiles();
        playerTwo.refreshTiles();

    }

    public class RefreshBoard implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            controller.startup();
        }
    }



    @Override
    public void feedbackToUser(int player, String message) {
        if (player == 1)
            playerOne.SetMessage(message);
        else if (player == 2)
            playerTwo.SetMessage(message);
        //Otherwise it's invalid
    }
}
