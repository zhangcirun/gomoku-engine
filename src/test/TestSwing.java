package test;

import javax.swing.*;
import java.awt.*;

/**
 * Created by cirun on 2018/8/9.
 */
public class TestSwing {
    private static JFrame frame = new JFrame("Gomoku");

    public static void main(String[] args) {

        JPanel p1 = new JPanel();
        p1.setBackground(Color.black);
        p1.setPreferredSize(new Dimension(40,40));
        p1.add(new JButton());
        p1.add(new JButton());
        p1.add(new JButton());
        p1.add(new JDesktopPane());

        frame.setSize(1024, 576);
        frame.add(p1);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocation(200, 95);
        frame.setVisible(true);


    }
}

