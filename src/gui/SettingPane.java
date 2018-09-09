package gui;

import ai.AdvancedAgent;

import javax.swing.JFrame;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.SpinnerNumberModel;
import javax.swing.WindowConstants;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingPane extends JFrame {
    public SettingPane(){
        Box boxLayout = Box.createVerticalBox();

        JLabel tip1 = new JLabel("Search Tree Depth");

        JSpinner depthSpinner = new JSpinner(new SpinnerNumberModel(7,2,7,1));
        JPanel tmp1 = new JPanel();
        tmp1.add(tip1);
        tmp1.add(depthSpinner);

        JPanel tmp2 = new JPanel();
        JButton confirmButton = new JButton("ok");
        confirmButton.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                int depth = (int)depthSpinner.getValue();
                AdvancedAgent.setMaximumSearchDepth(depth);
                Background.addMessage("Reset search tree depth to " + depth);
                dispose();
            }
        });
        tmp2.add(confirmButton);

        boxLayout.add(tmp1);
        boxLayout.add(tmp2);
        this.setSize(200,150);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.add(boxLayout);
        this.setVisible(false);
    }
}
