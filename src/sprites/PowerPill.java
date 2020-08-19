package sprites;

import image.ImageList;
import image.ImageFactory;

import javax.swing.*;

public class PowerPill extends Sprite{

    public PowerPill(int x, int y, int w, int h, int points) {
        super(x, y, w, h, points);
        ImageIcon imageIcon = ImageFactory.createImage(ImageList.POWERPILL);
        setImage(imageIcon.getImage());
    }

}
