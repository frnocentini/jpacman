package image;

import constants.Constants;

import javax.swing.ImageIcon;

public class ImageFactory {

    public static ImageIcon createImage(Image image) {

        ImageIcon imageIcon = null;

        switch (image){
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
            case PACMAN_A0:
                imageIcon = new ImageIcon(Constants.PACMAN_A0_URL);
                break;
            case PACMAN_R1:
                imageIcon = new ImageIcon(Constants.PACMAN_R1_URL);
                break;
            case PACMAN_R2:
                imageIcon = new ImageIcon(Constants.PACMAN_R2_URL);
                break;
            case PACMAN_U1:
                imageIcon = new ImageIcon(Constants.PACMAN_U1_URL);
                break;
            case PACMAN_U2:
                imageIcon = new ImageIcon(Constants.PACMAN_U2_URL);
                break;
            case PACMAN_L1:
                imageIcon = new ImageIcon(Constants.PACMAN_L1_URL);
                break;
            case PACMAN_L2:
                imageIcon = new ImageIcon(Constants.PACMAN_L2_URL);
                break;
            case PACMAN_D1:
                imageIcon = new ImageIcon(Constants.PACMAN_D1_URL);
                break;
            case PACMAN_D2:
                imageIcon = new ImageIcon(Constants.PACMAN_D2_URL);
                break;
            case PACMAN_DEATH0:
                imageIcon = new ImageIcon(Constants.PACMAN_DEATH0_URL);
                break;
            case PACMAN_DEATH1:
                imageIcon = new ImageIcon(Constants.PACMAN_DEATH1_URL);
                break;
            case PACMAN_DEATH2:
                imageIcon = new ImageIcon(Constants.PACMAN_DEATH2_URL);
                break;
            case PACMAN_DEATH3:
                imageIcon = new ImageIcon(Constants.PACMAN_DEATH3_URL);
                break;
            case PACMAN_DEATH4:
                imageIcon = new ImageIcon(Constants.PACMAN_DEATH4_URL);
                break;
            case PACMAN_DEATH5:
                imageIcon = new ImageIcon(Constants.PACMAN_DEATH5_URL);
                break;
            case PACMAN_DEATH6:
                imageIcon = new ImageIcon(Constants.PACMAN_DEATH6_URL);
                break;
            case PACMAN_DEATH7:
                imageIcon = new ImageIcon(Constants.PACMAN_DEATH7_URL);
                break;
            case PACMAN_DEATH8:
                imageIcon = new ImageIcon(Constants.PACMAN_DEATH8_URL);
                break;
            case PACMAN_DEATH9:
                imageIcon = new ImageIcon(Constants.PACMAN_DEATH9_URL);
                break;
            case PACMAN_DEATH10:
                imageIcon = new ImageIcon(Constants.PACMAN_DEATH10_URL);
                break;
            case BLINKY_R1:
                imageIcon = new ImageIcon(Constants.BLINKY_R1_URL);
                break;
            case BLINKY_R0:
                imageIcon = new ImageIcon(Constants.BLINKY_R0_URL);
                break;
            case BLINKY_U1:
                imageIcon = new ImageIcon(Constants.BLINKY_U1_URL);
                break;
            case BLINKY_U0:
                imageIcon = new ImageIcon(Constants.BLINKY_U0_URL);
                break;
            case BLINKY_L1:
                imageIcon = new ImageIcon(Constants.BLINKY_L1_URL);
                break;
            case BLINKY_L0:
                imageIcon = new ImageIcon(Constants.BLINKY_L0_URL);
                break;
            case BLINKY_D1:
                imageIcon = new ImageIcon(Constants.BLINKY_D1_URL);
                break;
            case BLINKY_D0:
                imageIcon = new ImageIcon(Constants.BLINKY_D0_URL);
                break;
            case PINKY_R1:
                imageIcon = new ImageIcon(Constants.PINKY_R1_URL);
                break;
            case PINKY_R0:
                imageIcon = new ImageIcon(Constants.PINKY_R0_URL);
                break;
            case PINKY_U1:
                imageIcon = new ImageIcon(Constants.PINKY_U1_URL);
                break;
            case PINKY_U0:
                imageIcon = new ImageIcon(Constants.PINKY_U0_URL);
                break;
            case PINKY_L1:
                imageIcon = new ImageIcon(Constants.PINKY_L1_URL);
                break;
            case PINKY_L0:
                imageIcon = new ImageIcon(Constants.PINKY_L0_URL);
                break;
            case PINKY_D1:
                imageIcon = new ImageIcon(Constants.PINKY_D1_URL);
                break;
            case PINKY_D0:
                imageIcon = new ImageIcon(Constants.PINKY_D0_URL);
                break;
            case FRIGHTENED0:
                imageIcon = new ImageIcon(Constants.FRIGHTENED0_URL);
                break;
            case FRIGHTENED1:
                imageIcon = new ImageIcon(Constants.FRIGHTENED1_URL);
                break;
            case FRIGHTENED2:
                imageIcon = new ImageIcon(Constants.FRIGHTENED2_URL);
                break;
            case FRIGHTENED3:
                imageIcon = new ImageIcon(Constants.FRIGHTENED3_URL);
                break;
            case EATEN_U:
                imageIcon = new ImageIcon(Constants.EATEN_U_URL);
                break;
            case EATEN_D:
                imageIcon = new ImageIcon(Constants.EATEN_D_URL);
                break;
            case EATEN_L:
                imageIcon = new ImageIcon(Constants.EATEN_L_URL);
                break;
            case EATEN_R:
                imageIcon = new ImageIcon(Constants.EATEN_R_URL);
                break;
            default:
                return null;
        }
        return imageIcon;
    }
}
