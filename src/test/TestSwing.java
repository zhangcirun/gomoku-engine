package test;

import gui.Chessboard;
import gui.SettingPane;
import gui.constant.GuiConst;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

public class TestSwing extends JFrame {
    private BufferedImage image1;
    public TestSwing() throws Exception{

        image1 = ImageIO.read(new File(this.getClass().getResource("/assets/black.png").getFile()));
        ImageIcon i1 = new ImageIcon(this.getClass().getResource("/assets/black.png"));
        //InputStream inp = ClassLoader.getSystemClassLoader().getResourceAsStream("src/assets/black.png");
        this.setSize(GuiConst.FRAME_WIDTH, GuiConst.FRAME_HEIGHT);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);

    }

}

