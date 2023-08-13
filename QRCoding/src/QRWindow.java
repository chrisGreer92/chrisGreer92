import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class QRWindow extends JFrame {

    private QRFormatter formatter = new QRFormatter();
    private JLabel qrSpace;
    private BufferedImage image=null;
    private JTextField textField;

    public QRWindow(){

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Enter Text for QR Code");
        initialise();

    }

    public void initialise(){
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        setUpText();
        setUpQRSpace();
        setUpButtons();

        this.pack();
        this.setVisible(true);
    }


    private void setUpText() {
        //Set up top text
        textField = new JTextField();
        getContentPane().add(textField,BorderLayout.NORTH);
    }

    private void setUpQRSpace() {
        qrSpace = new JLabel();
        qrSpace.setPreferredSize(new Dimension(400,400));
        getContentPane().add(qrSpace,BorderLayout.CENTER);
    }


    public void setUpButtons(){
        //Set up the buttons panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(2,1));

        JButton createCode = new JButton("Create");
        JButton saveCode = new JButton("Save");

        saveCode.addActionListener(new SaveCode());
        createCode.addActionListener(new CreateCode());

        buttonsPanel.add(createCode);
        buttonsPanel.add(saveCode);
        getContentPane().add(buttonsPanel,BorderLayout.SOUTH);
    }

    private void saveCode(){
        if (image == null)
            return;

        JFileChooser fc = new JFileChooser(System.getProperty("user.home") + "/Desktop");
        fc.setSelectedFile(new File("NameMe"));

        int returnVal = fc.showSaveDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION) {

            String newPath = fc.getSelectedFile().getAbsolutePath();
            if( !newPath.toLowerCase().endsWith( ".png" ) )
                newPath += ".png";
            Path newFile = new File(newPath).toPath();

            try {
                Files.copy(formatter.pathAsPath(),newFile,StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                setTitle("Problem Saving File");
            }
        }



    }

    private void createCode(){
        String str = textField.getText();
        formatter.setData(str);
        try {
            QRCreator.generateQR(formatter);
            image = ImageIO.read(new File(formatter.getPath()));
            qrSpace.setIcon(new ImageIcon(image));
        } catch (Exception ex) {
            setTitle("Problem Creating Code");
        }
    }

    public class CreateCode implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {createCode();}
    }

    public class SaveCode implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            saveCode();}
    }

}
