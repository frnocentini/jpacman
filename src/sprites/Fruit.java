package sprites;

import constants.Constants;
import structure.MazeManager;

import javax.swing.*;

public class Fruit extends Sprite{

    public Fruit(int points, ImageIcon image) {
        super(MazeManager.getObjCoord('S').getX(), MazeManager.getObjCoord('S').getY(), Constants.FRUIT_WIDTH, Constants.FRUIT_HEIGHT, points);
        setImage(image.getImage());
    }

}
