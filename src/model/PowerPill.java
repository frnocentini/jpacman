package model;

import image.Image;
import image.ImageFactory;

import javax.swing.*;

public class PowerPill extends Eatable{

    public PowerPill(int x, int y, int w, int h, int points) {
        super(x, y, w, h, points);
        ImageIcon imageIcon = ImageFactory.createImage(Image.POWERPILL);
        setImage(imageIcon.getImage());
    }

}
