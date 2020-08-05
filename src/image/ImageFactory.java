package image;

import constants.Constants;

import javax.swing.ImageIcon;

public class ImageFactory {

    public static ImageIcon createImage(Image image) {

        ImageIcon imageIcon = null;

        switch (image){
            case PACMAN_UP:
                imageIcon = new ImageIcon(Constants.PACMAN_UP_IMAGE_URL);
                break;
            case PACMAN_DOWN:
                imageIcon = new ImageIcon(Constants.PACMAN_DOWN_IMAGE_URL);
                break;
            case PACMAN_LEFT:
                imageIcon = new ImageIcon(Constants.PACMAN_LEFT_IMAGE_URL);
                break;
            case PACMAN_RIGHT:
                imageIcon = new ImageIcon(Constants.PACMAN_RIGHT_IMAGE_URL);
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
            case PINKY:
                imageIcon = new ImageIcon(Constants.PINKY_IMAGE_URL);
                break;
            case PILL:
                imageIcon = new ImageIcon(Constants.PILL_IMAGE_URL);
                break;
            case POWERPILL:
                imageIcon = new ImageIcon(Constants.POWERPILL_IMAGE_URL);
                break;
            case FRIGHTENED_GHOST:
                imageIcon = new ImageIcon(Constants.FRIGHTENED_GHOST_IMAGE_URL);
                break;
            case EATEN_GHOST:
                imageIcon = new ImageIcon(Constants.EATEN_GHOST_IMAGE_URL);
                break;
            case MENU:
                imageIcon = new ImageIcon(Constants.MENU_IMAGE_URL);
                break;
            case BLUE_PORTAL:
                imageIcon = new ImageIcon(Constants.BLUE_PORTAL_IMAGE_URL);
                break;
            case RED_PORTAL:
                imageIcon = new ImageIcon(Constants.RED_PORTAL_IMAGE_URL);
                break;
            default:
                return null;
        }

        return imageIcon;
    }
}
