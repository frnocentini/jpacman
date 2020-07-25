package model;

import image.Image;
import image.ImageFactory;

import javax.swing.*;

public class Pill extends Eatable{

    public Pill(int x, int y, int w, int h, int points, String audioFilename) {
        super(x, y, w, h, points, audioFilename);
        ImageIcon imageIcon = ImageFactory.createImage(Image.PILL);
        setImage(imageIcon.getImage());
    }
}
