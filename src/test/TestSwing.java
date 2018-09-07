package test;

import gui.Chessboard;
import gui.constant.GuiConst;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class TestSwing extends JFrame {
    private BufferedImage background, blackNext, whiteNext, boardImage;

    public TestSwing() throws Exception{

        this.boardImage = ImageIO.read(new File("src/gui/assets/chessboard.jpg"));
        this.background = ImageIO.read(new File("src/gui/assets/backgroundAutumn.jpg"));
        this.setPreferredSize(new Dimension(GuiConst.FRAME_WIDTH, GuiConst.FRAME_HEIGHT));
        this.setLayout(null);

        JPanel chessboard = new JPanel();
        chessboard.setBounds(245 ,12, 535, 536);
        chessboard.setBackground(Color.red);



        //5 rows, 30 columns
        JTextArea textArea = new JTextArea(3, 3);
        //textArea.setBounds(900, 12, 240,60);
        //textArea.setEditable(false);
        textArea.setAutoscrolls(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        //设置矩形大小.参数依次为(矩形左上角横坐标x,矩形左上角纵坐标y，矩形长度，矩形宽度)
        scrollPane.setBounds(900, 12, 240, 536);
        //默认的设置是超过文本框才会显示滚动条，以下设置让滚动条一直显示
        scrollPane.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        this.add(chessboard);
        //this.add(textArea);
        this.add(scrollPane);
        this.setSize(GuiConst.FRAME_WIDTH, GuiConst.FRAME_HEIGHT);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);

    }

    public static void main(String[] args) throws Exception {
        new TestSwing();
    }

}

