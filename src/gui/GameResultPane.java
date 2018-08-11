package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.basic.BasicOptionPaneUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class GameResultPane extends JPanel{
    private BufferedImage blackWin, whiteWin;
    private JButton newGameButton;
    private Chessboard chessboard;

    public GameResultPane(Chessboard chessboard) throws Exception{
        /*
        * still need to do: centering, change image, button appearance
        * */
        /*
        newGameButton = new JButton("New Game");
        newGameButton.addActionListener(new buttonListener());
        //newGameButton.setBorder(null);
        newGameButton.setSize(new Dimension(30,30));
        this.add(newGameButton, BorderLayout.CENTER);
        */

        this.chessboard = chessboard;
        this.blackWin = ImageIO.read(new File("/Users/cirun/Documents/admin/code/java/project/src/gui/assets/blackWin.png"));
        this.whiteWin = ImageIO.read(new File("/Users/cirun/Documents/admin/code/java/project/src/gui/assets/whiteWin.png"));
        this.setPreferredSize(new Dimension(400, 300));
        this.setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g){
        g.drawImage(blackWin,0,0,null);
    }

    /*Inner buttonListener class*/
    private class buttonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == newGameButton){
                chessboard.resetGame();
            }
        }
    }
}

