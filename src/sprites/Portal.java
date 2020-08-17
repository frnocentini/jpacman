package sprites;

import image.Image;
import image.ImageFactory;

import javax.swing.*;

public class Portal extends Sprite{

    String color;
    Portal other;

    public Portal(int x, int y, int w, int h, String color){
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.color = color;
        if(color.equals("BLUE")){
            ImageIcon imageIcon = ImageFactory.createImage(Image.BLUE_PORTAL);
            setImage(imageIcon.getImage());
        } else {
            ImageIcon imageIcon = ImageFactory.createImage(Image.RED_PORTAL);
            setImage(imageIcon.getImage());
        }
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Portal getOther() {
        return other;
    }

    public void setOther(Portal other) {
        this.other = other;
    }
}
