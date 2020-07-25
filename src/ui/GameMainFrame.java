package ui;

import constants.Constants;
import image.Image;
import image.ImageFactory;

import javax.swing.*;
import java.awt.*;

public class GameMainFrame extends JFrame{

    public GameMainFrame(){
        initializeLayout();
    }

    private void initializeLayout(){

        //Sottoclasse di JPanel che attraverso un GridLayout crea il labirinto
        BGPanel bgPanel = new BGPanel(new GridLayout(21, 19),1);
        //Sottoclasse di JPanel dove posizioniamo le entità della mappa (Pac-Man, fantasmi, frutta, ecc...)
        GamePanel gamePanel = new GamePanel();
        this.setIconImage((ImageFactory.createImage(Image.PACMAN_LEFT).getImage()));
        //Creiamo il JPanel stratificato
        JLayeredPane layeredPane = new JLayeredPane();


        //bgPanel.setOpaque(false);
        //Imposto la grandezza del background e lo inserisco nello strato più basso del layeredPane
        bgPanel.setBounds(0, 0, Constants.BOARD_WIDTH * Constants.SCALE, Constants.BOARD_HEIGHT * Constants.SCALE);
        layeredPane.add(bgPanel, JLayeredPane.DEFAULT_LAYER);

        //Imposto la trasparenza la grandezza del gamePanel e lo inserisco nello strato successivo del layeredPane
        gamePanel.setOpaque(false);
        gamePanel.setBounds(0, 0, Constants.BOARD_WIDTH * Constants.SCALE, Constants.BOARD_HEIGHT * Constants.SCALE);
        layeredPane.add(gamePanel, JLayeredPane.PALETTE_LAYER);

        layeredPane.setPreferredSize( new Dimension(Constants.BOARD_WIDTH * Constants.SCALE, Constants.BOARD_HEIGHT * Constants.SCALE) );
        this.setContentPane(layeredPane);
        this.setVisible(true);
        this.setTitle(Constants.TITLE);

        this.pack();

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

    }

}