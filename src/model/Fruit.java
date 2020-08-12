package model;

import constants.Constants;
import utility.CoordManager;

import javax.swing.*;

public class Fruit extends Sprite{

    public Fruit(int points, ImageIcon image) {
        super(CoordManager.getObjCoord('S').getX(), CoordManager.getObjCoord('S').getY(), Constants.FRUIT_WIDTH, Constants.FRUIT_HEIGHT, points);
        setImage(image.getImage());
    }

}
