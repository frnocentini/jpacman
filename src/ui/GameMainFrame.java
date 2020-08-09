package ui;

import constants.Constants;
import image.Image;
import image.ImageFactory;
import sound.SoundPlayer;

import javax.swing.*;
import java.awt.*;

public class GameMainFrame extends JFrame{

    public static int level;
    public static int gamePoints;

    public GameMainFrame(){
        level = 1;
        gamePoints = 0;
        SoundPlayer.initialize();
        initializeGameMenu();
    }

    public void initializeGameMenu() {

        MenuPanel menuPanel = new MenuPanel(this);
        menuPanel.setPreferredSize( new Dimension(Constants.BOARD_WIDTH * Constants.SCALE, Constants.BOARD_HEIGHT * Constants.SCALE + 40) );
        this.setIconImage((ImageFactory.createImage(Image.PACMAN_R1).getImage()));
        this.setContentPane(menuPanel);
        this.setVisible(true);
        this.setTitle(Constants.TITLE);

        this.pack();

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

    }

    public void initializeLayout(){

        //Sottoclasse di JPanel che attraverso un GridLayout crea il labirinto
        BGPanel bgPanel = new BGPanel(new GridLayout(21, 19),1);
        //Sottoclasse di JPanel dove posizioniamo le entità della mappa (Pac-Man, fantasmi, frutta, ecc...)
        GamePanel gamePanel = new GamePanel(this, this.level, this.gamePoints);
        this.setIconImage((ImageFactory.createImage(Image.PACMAN_R1).getImage()));
        //Creiamo il JPanel stratificato
        JLayeredPane layeredPane = new JLayeredPane();


        //bgPanel.setOpaque(false);
        //Imposto la grandezza del background e lo inserisco nello strato più basso del layeredPane
        bgPanel.setBounds(0, 0, Constants.BOARD_WIDTH * Constants.SCALE, Constants.BOARD_HEIGHT * Constants.SCALE);
        layeredPane.add(bgPanel, JLayeredPane.DEFAULT_LAYER);

        //Imposto la trasparenza la grandezza del gamePanel e lo inserisco nello strato successivo del layeredPane
        gamePanel.setOpaque(false);
        gamePanel.setBounds(0, 0, Constants.BOARD_WIDTH * Constants.SCALE, Constants.BOARD_HEIGHT * Constants.SCALE + 40);
        layeredPane.add(gamePanel, JLayeredPane.PALETTE_LAYER);

        layeredPane.setPreferredSize( new Dimension(Constants.BOARD_WIDTH * Constants.SCALE, Constants.BOARD_HEIGHT * Constants.SCALE + 40) );
        this.setContentPane(layeredPane);
        this.setVisible(true);
        this.setTitle(Constants.TITLE);

        this.pack();

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

    }

}