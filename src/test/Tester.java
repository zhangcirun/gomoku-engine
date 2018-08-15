package test;

import gui.Game;
import gui.constant.GuiConst;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class Tester {
    public static void main(String[] args) throws Exception{
        int a = GuiConst.TILE_NUM_PER_ROW;
        Game game = new Game();
        game.start();
    }
}
