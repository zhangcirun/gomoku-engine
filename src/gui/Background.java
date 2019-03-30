package gui;

import gui.constant.GuiConst;

import javax.swing.*;
import java.awt.*;

/**
 * This class is the most outer layer of the GUI structure, which is the interface between Chessboard component
 * and other GUI components.
 *
 * @author Cirun Zhang
 * @version Version 1.1
 */
public class Background extends JPanel {
    /**
     * Chessboard is the component of this class
     */
    public Chessboard chessboard;

    /**
     * Text Area for showing game information
     */
    private static JTextArea textArea;

    /**
     * Scroll pane of the text area
     */
    private JScrollPane textAreaScrollPane;


    Background() {
        init();
    }

    /**
     * Initialize the Background component
     */
    private void init() {
        this.setPreferredSize(new Dimension(GuiConst.FRAME_WIDTH, GuiConst.FRAME_HEIGHT));
        this.setLayout(null);
        addTextArea();
        addChessboard();
    }

    /**
     * Adds text area component to the background
     */
    private void addTextArea() {
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setText("JGomoku 1.0.1 @2018 copyright :-)\n\n");
        this.textAreaScrollPane = new JScrollPane(textArea);
        textAreaScrollPane.setBounds(800, 12, 340, 536);
        textAreaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.add(textAreaScrollPane);
    }

    /**
     * Add Chessboard component to the background
     */
    private void addChessboard() {
        chessboard = new Chessboard(this);
        chessboard.setBounds(100, 12, GuiConst.BOARD_WIDTH, GuiConst.BOARD_HEIGHT);
        this.add(chessboard);
    }

    /**
     * Passes message to the text area
     *
     * @param str Message body
     */
    public static void addMessage(String str) {
        if (textArea != null) {
            textArea.append(str + "\n");
            scrollDown();
        }
    }

    /**
     * Scrolls down the text area
     */
    private static void scrollDown() {
        textArea.setCaretPosition(textArea.getText().length());
    }

    /**
     * Clear all the history messages
     */
    static void clearTextArea() {
        textArea.setText("");
    }

    void resetGame() {
        this.chessboard.resetGame();
    }

    void revertHistory() {
        this.chessboard.revertHistory();
    }
}
