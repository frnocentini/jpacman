package ui;

import constants.Constants;
import image.ImageList;
import logic.MainFrameLogic;
import image.ImageFactory;
import sound.SoundPlayer;

import javax.swing.*;
import java.awt.*;

public class GameMainFrame extends JFrame{

    private int level;                          // Numero del livello da cui si parte
    private int lives;                          // Numero delle vite con cui si parte
    private int highScore;                      // Punteggio massimo raggiunto
    private MainFrameLogic logic;               // Oggetto contenente metodi logici di supporto al GameMainFrame
    private JLayeredPane layeredPane;           // Pannello stratificato su cui inserire gli altri JPanel
    private MenuPanel menuPanel;                // Pannello del Menù principale
    private BGPanel bgPanel;                    // Pannello del labirinto
    private GamePanel gamePanel;                // Pannello in cui vengono disegnati gli sprite
    private PausePanel pausePanel;              // Pannello del menù di pausa

    public GameMainFrame(){
        this.logic = new MainFrameLogic(this);
        // Registriamo nell'ambiente grafico un font preso da un file .ttf
        this.logic.registerFont();
        // Inizializiamo la classe che si occupa dei suoni
        SoundPlayer.initialize();
        level = Constants.START_LEVEL;
        lives = Constants.START_LIVES;
        // Creiamo un pannello stratificato
        this.layeredPane = new JLayeredPane();
        // Legge il file dell'highscore e se non esiste lo crea
        this.highScore = this.readHighScore();
        // Avviamo il metodo
        initializeGameMenu();
    }

    public void initializeGameMenu() {
        layeredPane.removeAll();
        System.gc();
        // Creiamo il pannello del Menù e lo aggiungiamo al LayeredPane
        this.menuPanel = new MenuPanel(this);
        menuPanel.setBounds( 0,0,Constants.BOARD_WIDTH * Constants.SCALE, Constants.BOARD_HEIGHT * Constants.SCALE + 40);

        layeredPane.add(menuPanel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.setPreferredSize( new Dimension(Constants.BOARD_WIDTH * Constants.SCALE, Constants.BOARD_HEIGHT * Constants.SCALE + 40) );

        this.setIconImage((ImageFactory.createImage(ImageList.PACMAN_R1).getImage()));
        this.setContentPane(layeredPane);
        this.setVisible(true);
        this.setTitle(Constants.TITLE);

        this.pack();

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
    }

    public void initializeLayout(int mazeNum){
        layeredPane.removeAll();
        System.gc();
        //Sottoclasse di JPanel che attraverso un GridLayout crea il labirinto
        this.bgPanel = new BGPanel(new GridLayout(21, 19),mazeNum);
        //Sottoclasse di JPanel dove posizioniamo le entità della mappa (Pac-Man, fantasmi, frutta, ecc...)
        this.gamePanel = new GamePanel(this, this.level, this.highScore, this.lives);
        //Imposto la grandezza del background e lo inserisco nello strato più basso del layeredPane
        bgPanel.setBounds(0, 0, Constants.BOARD_WIDTH * Constants.SCALE, Constants.BOARD_HEIGHT * Constants.SCALE);
        layeredPane.add(bgPanel, JLayeredPane.DEFAULT_LAYER);

        //Imposto la trasparenza  e la grandezza del gamePanel e lo inserisco nello strato successivo del layeredPane
        gamePanel.setOpaque(false);
        gamePanel.setBounds(0, 0, Constants.BOARD_WIDTH * Constants.SCALE, Constants.BOARD_HEIGHT * Constants.SCALE + 40);
        layeredPane.add(gamePanel, JLayeredPane.PALETTE_LAYER);

        layeredPane.setPreferredSize( new Dimension(Constants.BOARD_WIDTH * Constants.SCALE, Constants.BOARD_HEIGHT * Constants.SCALE + 40) );
        this.setContentPane(layeredPane);
        this.setVisible(true);
        this.setTitle(Constants.TITLE);

        this.pack();

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);

    }

    public void showPauseMenu(){
        this.pausePanel = new PausePanel(this);
        pausePanel.setBounds( 0,0,Constants.BOARD_WIDTH * Constants.SCALE, Constants.BOARD_HEIGHT * Constants.SCALE + 40);

        layeredPane.add(pausePanel, JLayeredPane.MODAL_LAYER);
        layeredPane.setPreferredSize( new Dimension(Constants.BOARD_WIDTH * Constants.SCALE, Constants.BOARD_HEIGHT * Constants.SCALE + 40) );

        this.setContentPane(layeredPane);
        this.setVisible(true);
        this.setTitle(Constants.TITLE);

        this.pack();

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
    }

    public void resumeGame() {
        layeredPane.remove(this.pausePanel);
        this.gamePanel.resumeGame();
    }

    public void quitGame() {
        this.gamePanel.restartApplication();
    }

    public int readHighScore(){
        return this.logic.readHighScore();
    }

    public void writeHighScore(int points){
        this.logic.writeHighScore(points);
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }
}