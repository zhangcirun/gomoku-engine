package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
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
        this.setLayout(null);
        this.chessboard = chessboard;
        this.blackWin = ImageIO.read(new File("/Users/cirun/Documents/admin/code/java/project/src/gui/assets/blackWin.png"));
        this.whiteWin = ImageIO.read(new File("/Users/cirun/Documents/admin/code/java/project/src/gui/assets/whiteWin.png"));

        newGameButton = new JButton("New Game");
        newGameButton.addActionListener(new buttonListener());
        newGameButton.setBounds(100,12,50,30);
        this.add(newGameButton);
        this.setPreferredSize(new Dimension(300, 300));
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

