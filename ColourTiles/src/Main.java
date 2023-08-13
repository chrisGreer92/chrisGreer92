import ColorLabel.ColorLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Main implements ActionListener{

    JFrame guiFrame = new JFrame();
    ColorLabel[] arrayLabels = new ColorLabel[64];
    Random rand = new Random();
    JButton reMix = new JButton("Re-Mix");


    public static void main(String[] args) {

        Main thing = new Main();
        thing.createGUI();

    }

    //Below creates the main window, should not be static...
    void createGUI(){


        guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        guiFrame.setTitle("Look at the Colours!"); // Set a caption/title bar content for the frame
        guiFrame.setLocationRelativeTo(null); // centre on screen

        guiFrame.setLayout(new BorderLayout());

        //Set up the coloured tiles
        JPanel tilesPanel = new JPanel();
        tilesPanel.setLayout(new GridLayout(8,8));
        guiFrame.getContentPane().add(tilesPanel,BorderLayout.NORTH);


        for (int i=0;i<64;i++) {
            arrayLabels[i] = new ColorLabel(50, 50, randColour(rand), 0, Color.black);
            arrayLabels[i].setDrawColor(randColour(rand));
            tilesPanel.add(arrayLabels[i]);
        }

        // Set up the button
        reMix.addActionListener(this);
        guiFrame.getContentPane().add(reMix,BorderLayout.SOUTH);


        //Pack the frame
        guiFrame.pack();
        guiFrame.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

      for (ColorLabel label : arrayLabels ){
            System.out.println("Pressed");
            label.setDrawColor(randColour(rand));
        }
//        for (int i = 0 ; i < 64 ; i ++){
//            arrayLabels[i].setDrawColor(randColour(rand));
//            System.out.println("Pressed" + (i+1));
//            reMix.setBackground(Color.BLUE);
//        }
        guiFrame.repaint();

    }

    public Color randColour(Random rand){


        float r = rand.nextFloat();
        float g = rand.nextFloat();
        float b = rand.nextFloat();

        return new Color(r, g, b);

    }
}
