package reversi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReversiTile extends JButton {

    IController controller;
    Color tileColor = null, tileBackground = null;
    int xAxis, yAxis, playerNo;
    public ReversiTile(int x, int y, int player, IController controller )
    {
        this.controller = controller;
        xAxis = x;
        yAxis = y;
        this.playerNo = player;

        setMinimumSize( new Dimension(50, 50) );
        setPreferredSize( new Dimension(50, 50) );

        //this.setText("(" + xAxis + "," + yAxis + ")");

        this.addActionListener(new TilePressed());
    }

    public void SetIt(int player){
        if (player == 0) {
            tileBackground = null;
            tileColor = null;
        }else if (player == 1) {
            tileBackground = Color.black;
            tileColor = Color.white;
        } else if (player ==2 ){
            tileBackground = Color.white;
            tileColor = Color.black;
        }

        this.repaint();
    }

    public class TilePressed implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            controller.squareSelected(playerNo,xAxis,yAxis);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        //super.paintComponent(g);

        g.setColor(Color.black);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.green);
        g.fillRect(1, 1, 48, 48);

        if (tileColor != null) {
            g.setColor(tileBackground);
            g.fillOval(1, 1, 46, 46);

            g.setColor(tileColor);
            g.fillOval(2, 2, 44, 44);
        }

    }
}
