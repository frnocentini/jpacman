package ui;

import keylisteners.GameEventListener;
import constants.Constants;
import logic.GameLogic;
import image.Image;
import image.ImageFactory;
import sprites.Fruit;
import sprites.Ghost;
import sprites.Pacman;
import sprites.Pill;
import sprites.Portal;
import sprites.PowerPill;
import structure.MazeManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
public class GamePanel extends JPanel {

    private GameMainFrame frame;                    // Riferimento al nostro JFrame
    private GameLogic logic;
    private GameEventListener gameEventListener;    // Listener della tastiera
    private ImageIcon smallPanel;                   // Immagine del pannelo inferiore
    private JLabel pointsLabel;                     //
    private JLabel readyLabel;
    private JLabel gameOverLabel;
    private JLabel levelLabel;
    private JLabel highScoreLabel;
    private JLabel livesLabel;
    private JLabel livesNumLabel;
    private ArrayList<ImageIcon> livesIcons;

    public GamePanel(GameMainFrame frame, int level, int highScore, int lives){
        this.frame = frame;
        this.logic = new GameLogic(frame,this,level,highScore,lives);
        initializeLabels();
        initializeLayout();
    }

    public void initializeLabels() {
        this.levelLabel = new JLabel(logic.getLevelString());
        levelLabel.setFont(new Font("PF Arma Five", Font.PLAIN, 12));
        levelLabel.setBounds(165,420,100,20);
        levelLabel.setForeground(Color.WHITE);
        add(levelLabel);
        int points = this.logic.getPoints();
        this.pointsLabel = new JLabel("Points: "+points);;
        pointsLabel.setFont(new Font("PF Arma Five", Font.PLAIN, 12));
        pointsLabel.setBounds(10,420,200,20);
        pointsLabel.setForeground(Color.WHITE);
        add(pointsLabel);
        this.highScoreLabel = new JLabel(logic.getHighScoreString());
        highScoreLabel.setFont(new Font("PF Arma Five", Font.PLAIN, 12));
        highScoreLabel.setBounds(10,438,200,20);
        highScoreLabel.setForeground(Color.WHITE);
        add(highScoreLabel);
        this.readyLabel = new JLabel(logic.getReadyString());
        readyLabel.setFont(new Font("PF Arma Five", Font.PLAIN, 12));
        readyLabel.setBounds(173,420,100,20);
        readyLabel.setForeground(Color.YELLOW);
        add(readyLabel);
        this.gameOverLabel = new JLabel(logic.getGameOverString());
        gameOverLabel.setFont(new Font("PF Arma Five", Font.PLAIN, 12));
        gameOverLabel.setBounds(155,420,100,20);
        gameOverLabel.setForeground(Color.RED);
        add(gameOverLabel);
        this.livesLabel = new JLabel("Lives:");
        livesLabel.setFont(new Font("PF Arma Five", Font.PLAIN, 12));
        livesLabel.setBounds(165,438,100,20);
        livesLabel.setForeground(Color.WHITE);
        add(livesLabel);
        this.livesNumLabel = new JLabel(logic.getLivesNumString());
        livesNumLabel.setFont(new Font("PF Arma Five", Font.PLAIN, 12));
        livesNumLabel.setBounds(220,438,100,20);
        livesNumLabel.setForeground(Color.WHITE);
        add(livesNumLabel);
        this.livesIcons = new ArrayList<>();
        ImageIcon lifeIcon = ImageFactory.createImage(image.Image.LIFE);
        for(int i=0;i<this.logic.getLives()-1;i++){
            livesIcons.add(lifeIcon);
        }
    }

    public void initializeLayout() {
        this.gameEventListener = new GameEventListener(this);
        addKeyListener(this.gameEventListener);
        this.smallPanel = ImageFactory.createImage(Image.SMALL_PANEL);
        setFocusable(true);
        setLayout(null);
        requestFocusInWindow();
        setPreferredSize(new Dimension(Constants.BOARD_WIDTH * Constants.SCALE, Constants.BOARD_HEIGHT * Constants.SCALE));
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        doDrawing(g);
    }

    public void doDrawing(Graphics g){
        drawSmallPanel(g);
        drawLives(g);
        drawPills(g);
        drawPowerPills(g);
        drawPortals(g);
        drawFruit(g);
        drawPacman(g);
        drawGhosts(g);
        drawLabels();
        //Metodo che si assicura che tutto si sia aggiornato
        Toolkit.getDefaultToolkit().sync();
    }

    public void drawLabels() {
        this.pointsLabel.setText("Points: "+this.logic.getPoints());
        this.highScoreLabel.setText(logic.getHighScoreString());
        this.readyLabel.setText(logic.getReadyString());
        this.levelLabel.setText(logic.getLevelString());
    }

    public void showGameOver(){
        this.gameOverLabel.setText(logic.getGameOverString());
        this.gameOverLabel.paintImmediately(this.gameOverLabel.getVisibleRect());
    }

    public void drawLives(Graphics g) {
        livesIcons.clear();
        for(int i=0;i<this.logic.getLives()-1;i++){
            livesIcons.add(ImageFactory.createImage(image.Image.LIFE));
        }
        if(this.logic.getLives() < 7){
            livesNumLabel.setText("");
            for(int i=0;i<this.livesIcons.size();i++){
                g.drawImage(this.livesIcons.get(i).getImage(),205+(i*12),442,this);
            }
        } else if (this.logic.getLives() != 0) {
            g.drawImage(this.livesIcons.get(0).getImage(),205,442,this);
            livesNumLabel.setText(""+(this.logic.getLives()-1));
        }
    }

    public void drawSmallPanel(Graphics g) {
        g.drawImage(this.smallPanel.getImage(), 0, 420, this);
    }

    public void drawFruit(Graphics g) {
        Fruit f = MazeManager.getMaze().getFruit();
        if(f != null){
            g.drawImage(f.getImage(), f.getX(), f.getY(), this);
        }
    }

    public void drawPortals(Graphics g) {
        Portal bluePortal = MazeManager.getMaze().getBluePortal();
        Portal redPortal = MazeManager.getMaze().getRedPortal();
        g.drawImage(bluePortal.getImage(), bluePortal.getX(), bluePortal.getY(), this);
        g.drawImage(redPortal.getImage(), redPortal.getX(), redPortal.getY(), this);
    }

    public void drawPowerPills(Graphics g) {
        for(int i = 0; i< MazeManager.getMaze().getPowerPillsNum(); i++){
            PowerPill pp = MazeManager.getMaze().getPowerPill(i);
            // rimuovere le pill direttamente dall'ArrayList causava una fastidiosa intermittenza delle altre
            if(!pp.isDead()){
                g.drawImage(pp.getImage(), pp.getX(), pp.getY(), this);
            }
        }
    }

    public void drawPills(Graphics g) {
        for(int i = 0; i< MazeManager.getMaze().getPillsNum(); i++){
            Pill p = MazeManager.getMaze().getPill(i);
            if(!p.isDead()){
                g.drawImage(p.getImage(), p.getX(), p.getY(), this);
            }
        }
    }

    public void drawPacman(Graphics g) {
        Pacman pacman = this.logic.getPacman();
        g.drawImage(pacman.getImage(), pacman.getX(), pacman.getY(), this);
    }

    public void drawGhosts(Graphics g) {
        ArrayList<Ghost> ghosts = this.logic.getGhosts();
        Pacman pacman = this.logic.getPacman();
        for(Ghost ghost : ghosts){
            if(!pacman.isDead()) {
                g.drawImage(ghost.getImage(), ghost.getX(), ghost.getY(), this);
            }
        }
    }

    public void keyPressed(KeyEvent e) {
        this.logic.keyPressed(e);
    }

    public void resumeGame(){
        this.logic.resumeGame();
    }

    public void restartApplication(){
        this.logic.restartApplication();
    }

}
