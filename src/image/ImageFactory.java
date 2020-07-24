package image;

import constants.Constants;

import javax.swing.ImageIcon;

public class ImageFactory {

    public static ImageIcon createImage(Image image) {

        ImageIcon imageIcon = null;

        switch (image){
            case PACMAN:
                imageIcon = new ImageIcon(Constants.PACMAN_IMAGE_URL);
                break;
            case EMPTY:
                imageIcon = new ImageIcon(Constants.EMPTY_IMAGE_URL);
                break;
            case WALL:
                imageIcon = new ImageIcon(Constants.WALL_IMAGE_URL);
                break;
            case BLINKY:
                imageIcon = new ImageIcon(Constants.BLINKY_IMAGE_URL);
                break;
            case PILL:
                imageIcon = new ImageIcon(Constants.PILL_IMAGE_URL);
                break;
            case POWERPILL:
                imageIcon = new ImageIcon(Constants.POWERPILL_IMAGE_URL);
                break;
            default:
                return null;
        }

        return imageIcon;
    }
}
