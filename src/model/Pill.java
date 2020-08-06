package model;

import image.Image;
import image.ImageFactory;

import javax.swing.*;

public class Pill extends Sprite{

    public Pill(int x, int y, int w, int h, int points) {
        super(x, y, w, h, points);
        ImageIcon imageIcon = ImageFactory.createImage(Image.PILL);
        setImage(imageIcon.getImage());
    }
}
