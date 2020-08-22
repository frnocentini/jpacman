package image;

import constants.Constants;

import javax.swing.ImageIcon;

public class ImageFactory {

    // Prende un elemento dell'ENUM ImageList  e ne restituisce la relativa ImageIcon
    public static ImageIcon createImage(ImageList imageList) {

        ImageIcon imageIcon = null;

        switch (imageList){
            case EMPTY:
                imageIcon = getFile(Constants.EMPTY_IMAGE_URL);
                break;
            case WALL:
                imageIcon = getFile(Constants.WALL_IMAGE_URL);
                break;
            case BLINKY:
                imageIcon = getFile(Constants.BLINKY_IMAGE_URL);
                break;
            case PINKY:
                imageIcon = getFile(Constants.PINKY_IMAGE_URL);
                break;
            case PILL:
                imageIcon = getFile(Constants.PILL_IMAGE_URL);
                break;
            case POWERPILL:
                imageIcon = getFile(Constants.POWERPILL_IMAGE_URL);
                break;
            case FRIGHTENED_GHOST:
                imageIcon = getFile(Constants.FRIGHTENED_GHOST_IMAGE_URL);
                break;
            case EATEN_GHOST:
                imageIcon = getFile(Constants.EATEN_GHOST_IMAGE_URL);
                break;
            case MENU:
                imageIcon = getFile(Constants.MENU_IMAGE_URL);
                break;
            case LIFE:
                imageIcon = getFile(Constants.LIFE_IMAGE_URL);
                break;
            case PAUSE:
                imageIcon = getFile(Constants.PAUSE_IMAGE_URL);
                break;
            case SMALL_PANEL:
                imageIcon = getFile(Constants.SMALL_PANEL_URL);
                break;
            case BLUE_PORTAL:
                imageIcon = getFile(Constants.BLUE_PORTAL_IMAGE_URL);
                break;
            case RED_PORTAL:
                imageIcon = getFile(Constants.RED_PORTAL_IMAGE_URL);
                break;
            case PACMAN_A0:
                imageIcon = getFile(Constants.PACMAN_A0_URL);
                break;
            case PACMAN_R1:
                imageIcon = getFile(Constants.PACMAN_R1_URL);
                break;
            case PACMAN_R2:
                imageIcon = getFile(Constants.PACMAN_R2_URL);
                break;
            case PACMAN_U1:
                imageIcon = getFile(Constants.PACMAN_U1_URL);
                break;
            case PACMAN_U2:
                imageIcon = getFile(Constants.PACMAN_U2_URL);
                break;
            case PACMAN_L1:
                imageIcon = getFile(Constants.PACMAN_L1_URL);
                break;
            case PACMAN_L2:
                imageIcon = getFile(Constants.PACMAN_L2_URL);
                break;
            case PACMAN_D1:
                imageIcon = getFile(Constants.PACMAN_D1_URL);
                break;
            case PACMAN_D2:
                imageIcon = getFile(Constants.PACMAN_D2_URL);
                break;
            case PACMAN_DEATH0:
                imageIcon = getFile(Constants.PACMAN_DEATH0_URL);
                break;
            case PACMAN_DEATH1:
                imageIcon = getFile(Constants.PACMAN_DEATH1_URL);
                break;
            case PACMAN_DEATH2:
                imageIcon = getFile(Constants.PACMAN_DEATH2_URL);
                break;
            case PACMAN_DEATH3:
                imageIcon = getFile(Constants.PACMAN_DEATH3_URL);
                break;
            case PACMAN_DEATH4:
                imageIcon = getFile(Constants.PACMAN_DEATH4_URL);
                break;
            case PACMAN_DEATH5:
                imageIcon = getFile(Constants.PACMAN_DEATH5_URL);
                break;
            case PACMAN_DEATH6:
                imageIcon = getFile(Constants.PACMAN_DEATH6_URL);
                break;
            case PACMAN_DEATH7:
                imageIcon = getFile(Constants.PACMAN_DEATH7_URL);
                break;
            case PACMAN_DEATH8:
                imageIcon = getFile(Constants.PACMAN_DEATH8_URL);
                break;
            case PACMAN_DEATH9:
                imageIcon = getFile(Constants.PACMAN_DEATH9_URL);
                break;
            case PACMAN_DEATH10:
                imageIcon = getFile(Constants.PACMAN_DEATH10_URL);
                break;
            case BLINKY_R1:
                imageIcon = getFile(Constants.BLINKY_R1_URL);
                break;
            case BLINKY_R0:
                imageIcon = getFile(Constants.BLINKY_R0_URL);
                break;
            case BLINKY_U1:
                imageIcon = getFile(Constants.BLINKY_U1_URL);
                break;
            case BLINKY_U0:
                imageIcon = getFile(Constants.BLINKY_U0_URL);
                break;
            case BLINKY_L1:
                imageIcon = getFile(Constants.BLINKY_L1_URL);
                break;
            case BLINKY_L0:
                imageIcon = getFile(Constants.BLINKY_L0_URL);
                break;
            case BLINKY_D1:
                imageIcon = getFile(Constants.BLINKY_D1_URL);
                break;
            case BLINKY_D0:
                imageIcon = getFile(Constants.BLINKY_D0_URL);
                break;
            case PINKY_R1:
                imageIcon = getFile(Constants.PINKY_R1_URL);
                break;
            case PINKY_R0:
                imageIcon = getFile(Constants.PINKY_R0_URL);
                break;
            case PINKY_U1:
                imageIcon = getFile(Constants.PINKY_U1_URL);
                break;
            case PINKY_U0:
                imageIcon = getFile(Constants.PINKY_U0_URL);
                break;
            case PINKY_L1:
                imageIcon = getFile(Constants.PINKY_L1_URL);
                break;
            case PINKY_L0:
                imageIcon = getFile(Constants.PINKY_L0_URL);
                break;
            case PINKY_D1:
                imageIcon = getFile(Constants.PINKY_D1_URL);
                break;
            case PINKY_D0:
                imageIcon = getFile(Constants.PINKY_D0_URL);
                break;
            case INKY_R1:
                imageIcon = getFile(Constants.INKY_R1_URL);
                break;
            case INKY_R0:
                imageIcon = getFile(Constants.INKY_R0_URL);
                break;
            case INKY_U1:
                imageIcon = getFile(Constants.INKY_U1_URL);
                break;
            case INKY_U0:
                imageIcon = getFile(Constants.INKY_U0_URL);
                break;
            case INKY_L1:
                imageIcon = getFile(Constants.INKY_L1_URL);
                break;
            case INKY_L0:
                imageIcon = getFile(Constants.INKY_L0_URL);
                break;
            case INKY_D1:
                imageIcon = getFile(Constants.INKY_D1_URL);
                break;
            case INKY_D0:
                imageIcon = getFile(Constants.INKY_D0_URL);
                break;
            case CLYDE_R1:
                imageIcon = getFile(Constants.CLYDE_R1_URL);
                break;
            case CLYDE_R0:
                imageIcon = getFile(Constants.CLYDE_R0_URL);
                break;
            case CLYDE_U1:
                imageIcon = getFile(Constants.CLYDE_U1_URL);
                break;
            case CLYDE_U0:
                imageIcon = getFile(Constants.CLYDE_U0_URL);
                break;
            case CLYDE_L1:
                imageIcon = getFile(Constants.CLYDE_L1_URL);
                break;
            case CLYDE_L0:
                imageIcon = getFile(Constants.CLYDE_L0_URL);
                break;
            case CLYDE_D1:
                imageIcon = getFile(Constants.CLYDE_D1_URL);
                break;
            case CLYDE_D0:
                imageIcon = getFile(Constants.CLYDE_D0_URL);
                break;
            case FRIGHTENED0:
                imageIcon = getFile(Constants.FRIGHTENED0_URL);
                break;
            case FRIGHTENED1:
                imageIcon = getFile(Constants.FRIGHTENED1_URL);
                break;
            case FRIGHTENED2:
                imageIcon = getFile(Constants.FRIGHTENED2_URL);
                break;
            case FRIGHTENED3:
                imageIcon = getFile(Constants.FRIGHTENED3_URL);
                break;
            case EATEN_U:
                imageIcon = getFile(Constants.EATEN_U_URL);
                break;
            case EATEN_D:
                imageIcon = getFile(Constants.EATEN_D_URL);
                break;
            case EATEN_L:
                imageIcon = getFile(Constants.EATEN_L_URL);
                break;
            case EATEN_R:
                imageIcon = getFile(Constants.EATEN_R_URL);
                break;
            case FRUIT0:
                imageIcon = getFile(Constants.FRUIT0_URL);
                break;
            case FRUIT1:
                imageIcon = getFile(Constants.FRUIT1_URL);
                break;
            case FRUIT2:
                imageIcon = getFile(Constants.FRUIT2_URL);
                break;
            case FRUIT3:
                imageIcon = getFile(Constants.FRUIT3_URL);
                break;
            case FRUIT4:
                imageIcon = getFile(Constants.FRUIT4_URL);
                break;
            case FRUIT5:
                imageIcon = getFile(Constants.FRUIT5_URL);
                break;
            case FRUIT6:
                imageIcon = getFile(Constants.FRUIT6_URL);
                break;
            case FRUIT7:
                imageIcon = getFile(Constants.FRUIT7_URL);
                break;
            default:
                return null;
        }
        return imageIcon;
    }

    // Dato un filename recupera l'immagine e ci crea un ImageIcon
    private static ImageIcon getFile(String filename){
        // IDE
        return (new ImageIcon(filename));
        // JAR
        //return (new ImageIcon(ClassLoader.getSystemResource(filename)));
    }
}
