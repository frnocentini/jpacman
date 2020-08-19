package sprites;

import image.ImageList;
import image.ImageFactory;

import javax.swing.*;

public class Pill extends Sprite{

    public Pill(int x, int y, int w, int h, int points) {
        super(x, y, w, h, points);
        ImageIcon imageIcon = ImageFactory.createImage(ImageList.PILL);
        setImage(imageIcon.getImage());
    }
}
