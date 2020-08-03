package constants;

public class Constants {

    public static final String TITLE = "GayPacman";

    public static final int BOARD_WIDTH = 380;
    public static final int BOARD_HEIGHT = 420;
    public static final int BLOCK_DIM = 20;
    public static final int SCALE = 1;
    public static final int PACMAN_WIDTH = 20;
    public static final int PACMAN_HEIGHT = 20;
    public static final int PILL_WIDTH = 6;
    public static final int PILL_HEIGHT = 6;
    public static final int POWERPILL_WIDTH = 20;
    public static final int POWERPILL_HEIGHT = 20;

    //speed of the application
    public static final int GAME_SPEED = 10;
    public static final int PACMAN_SPEED = 1;
    public static final int GHOST_SPEED = 1;

    //Ghost related constants
    public static final int GHOST_HEIGHT = 20;
    public static final int GHOST_WIDTH = 20;

    //images for the objects
    public static final String PACMAN_UP_IMAGE_URL = "images/pacman_up.png";
    public static final String PACMAN_DOWN_IMAGE_URL = "images/pacman_down.png";
    public static final String PACMAN_LEFT_IMAGE_URL = "images/pacman_left.png";
    public static final String PACMAN_RIGHT_IMAGE_URL = "images/pacman_right.png";
    public static final String EMPTY_IMAGE_URL = "images/empty.png";
    public static final String WALL_IMAGE_URL = "images/wall.png";
    public static final String BLINKY_IMAGE_URL = "images/blinky.png";
    public static final String PILL_IMAGE_URL = "images/pill.png";
    public static final String POWERPILL_IMAGE_URL = "images/powerpill.png";
    public static final String FRIGHTENED_GHOST_IMAGE_URL = "images/frightened.png";
    public static final String EATEN_GHOST_IMAGE_URL = "images/eaten.png";

    //sounds
    public static final String CREDIT_URL = "sounds/credit.wav";
    public static final String DEATH_URL = "sounds/death.wav";
    public static final String EAT_FRUIT_URL = "sounds/eat_fruit.wav";
    public static final String EAT_GHOST_URL = "sounds/eat_ghost.wav";
    public static final String GAME_START_URL = "sounds/game_start.wav";
    public static final String MUNCH_1_URL = "sounds/munch_1.wav";
    public static final String MUNCH_2_URL = "sounds/munch_2.wav";
    public static final String FRIGHT_SOUND_URL = "sounds/fright_sound.wav";
    public static final String EATEN_SOUND_URL = "sounds/eaten_sound.wav";
    public static final String SIREN_1_URL = "sounds/siren_1.wav";
    public static final String SIREN_2_URL = "sounds/siren_2.wav";
    public static final String SIREN_3_URL = "sounds/siren_3.wav";
    public static final String SIREN_4_URL = "sounds/siren_4.wav";
    public static final String SIREN_5_URL = "sounds/siren_5.wav";

    //tempi chase-scatter dei livelli
    public static final long[] FIRST_TIMES = {7000,20000,7000,20000,5000,20000,5000};
    public static final long[] SECOND_TIMES = {7000,20000,7000,20000,5000,18314,1};
    public static final long[] FIFTH_TIMES = {5000,20000,5000,20000,5000,18314,1};
}