package gui;

import gui.constant.GuiConst;
import javax.swing.*;
import java.awt.*;

/**
 * This class is the the outer layer above Chessboard which
 * shows background image and other UI components. It is the
 * interface between chessboard and other classes.
 *
 * @author Chang ta'z jun
 * @version Version 1.1
 */
public class Background extends JPanel {
    private Image background, blackNext, whiteNext;

    /**
     * Chessboard is the component of this class
     */
    private Chessboard chessboard;

    /**
     * Text Area for showing game information
     */
    private static JTextArea textArea;

    /**
     * Scroll pane of the text area
     */
    private JScrollPane textAreaScrollPane;

    /**
     * Indicates which player should go in the next turn
     */
    static boolean blackTurn = true;

    /**
     * Indicates whether the game is over or not
     */
    //static boolean gameOnProgress = true;

    public Background() throws Exception {
        init();
    }

    private void init() throws Exception {
        //this.background = ImageIO.read(new File("src/gui/assets/backgroundAutumn.jpg"));
        this.background = new ImageIcon(this.getClass().getResource("/assets/backgroundAutumn.jpg")).getImage();
        //this.blackNext = ImageIO.read(new File("src/gui/assets/blackNext.png"));
        //this.whiteNext = ImageIO.read(new File("src/gui/assets/whiteNext.png"));
        this.setPreferredSize(new Dimension(GuiConst.FRAME_WIDTH, GuiConst.FRAME_HEIGHT));
        this.setLayout(null);
        addChessboard();
        addTextArea();
    }

    /**
     * Draws the background and user tips
     *
     * @param g The java.awt.Graphics class is the abstract base class for drawing components.
     */
    @Override public void paintComponent(Graphics g) {
        //g.drawImage(background, 0, 0, null);
        /*
        //draw the flag
        if (blackTurn) {
            g.drawImage(blackNext, 1000, 450, null);
        } else if (!blackTurn) {
            g.drawImage(whiteNext, 1000, 450, null);
        }
        */

    }

    private void addTextArea() {
        textArea = new JTextArea();
        textArea.setEditable(false);
        //textArea.setEditable(false);
        textArea.setText("JGomoku 1.0.1 @2018 copyright :-)\n\n");
        this.textAreaScrollPane = new JScrollPane(textArea);
        textAreaScrollPane.setBounds(800, 12, 340, 536);
        textAreaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.add(textAreaScrollPane);
    }

    private void addChessboard() throws Exception {
        chessboard = new Chessboard(this);
        chessboard.setBounds(100, 12, GuiConst.BOARD_WIDTH, GuiConst.BOARD_HEIGHT);
        this.add(chessboard);
    }

    public static void addMessage(String str) {
        textArea.append(str + "\n");
    }

    static void clearTextArea() {
        textArea.setText("");
    }

    void resetGame() {
        this.chessboard.resetGame();
    }

    void revertHistory(){
        this.chessboard.revertHistory();
    }
}
