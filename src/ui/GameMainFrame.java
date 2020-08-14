package ui;

import constants.Constants;
import image.Image;
import image.ImageFactory;
import sound.SoundPlayer;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Scanner;

public class GameMainFrame extends JFrame{

    private int level;
    private int gamePoints;
    private int lives;
    private int highScore;
    private JLayeredPane layeredPane;
    private MenuPanel menuPanel;
    private BGPanel bgPanel;
    private GamePanel gamePanel;
    private PausePanel pausePanel;

    public GameMainFrame(){
        Font f = null;
        try {
            f = Font.createFont(Font.TRUETYPE_FONT, new File(Constants.ARMA_FONT));
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(f);
        SoundPlayer.initialize();
        level = 1;
        gamePoints = 0;
        lives = 3;
        this.layeredPane = new JLayeredPane();
        layeredPane.add(new JPanel(), JLayeredPane.DEFAULT_LAYER);
        this.highScore = this.readHighScore();
        initializeGameMenu();
    }

    public void initializeGameMenu() {
        layeredPane.removeAll();
        System.gc();
        this.menuPanel = new MenuPanel(this);
        menuPanel.setBounds( 0,0,Constants.BOARD_WIDTH * Constants.SCALE, Constants.BOARD_HEIGHT * Constants.SCALE + 40);

        layeredPane.add(menuPanel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.setPreferredSize( new Dimension(Constants.BOARD_WIDTH * Constants.SCALE, Constants.BOARD_HEIGHT * Constants.SCALE + 40) );

        this.setIconImage((ImageFactory.createImage(Image.PACMAN_R1).getImage()));
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
        this.setIconImage((ImageFactory.createImage(Image.PACMAN_R1).getImage()));
        //Creiamo il JPanel stratificato



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
        int score = 0;
        File f = new File(Constants.HIGHSCORES);
        try {
            Scanner sc = new Scanner(f);
            score = sc.nextInt();
        } catch (FileNotFoundException e) {
            try {
                f.createNewFile();
                PrintWriter pw = new PrintWriter(f);
                pw.print(0);
                pw.flush();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        return score;
    }

    public int writeHighScore(int points){
        if(points > this.highScore) {
            this.highScore = points;
            PrintWriter pw = null;
            try {
                pw = new PrintWriter(new File(Constants.HIGHSCORES));
                pw.print(points);
                pw.flush();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return this.highScore;
    }
}