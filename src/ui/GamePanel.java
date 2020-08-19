package ui;

import image.ImageList;
import keylisteners.GameEventListener;
import logic.GameLogic;
import image.ImageFactory;
import sprites.Fruit;
import sprites.Ghost;
import sprites.Pacman;
import sprites.Pill;
import sprites.Portal;
import sprites.PowerPill;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
public class GamePanel extends JPanel {

    private GameMainFrame frame;                    // Riferimento al nostro JFrame
    private GameLogic logic;                        // Oggetto che gestisce il ciclo update-repaint e
                                                    // si occupa della parte logica del gioco che il
                                                    // GamePanel dovrà solo stampare su schermo
    private GameEventListener gameEventListener;    // Listener della tastiera
    private ImageIcon smallPanel;                   // Immagine del pannelo inferiore
    private JLabel pointsLabel;                     // Label dei punti
    private JLabel readyLabel;                      // Label della scritta Raedy
    private JLabel gameOverLabel;                   // Label della scritta Game Over
    private JLabel levelLabel;                      // Label del numero del livello
    private JLabel highScoreLabel;                  // Label del massimo punteggio ottenuto
    private JLabel livesLabel;                      // Label della scritta Lives
    private JLabel livesNumLabel;                   // Label delle vite rimanenti
    private ArrayList<ImageIcon> livesIcons;        // ArrayList di ImageIcon da stampare accanto
                                                    // alla label delle vite

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
        ImageIcon lifeIcon = ImageFactory.createImage(ImageList.LIFE);
        for(int i=0;i<this.logic.getLives()-1;i++){
            livesIcons.add(lifeIcon);
        }
    }

    public void initializeLayout() {
        // Aggiungiamo il listener della tastiera
        this.gameEventListener = new GameEventListener(this);
        this.addKeyListener(this.gameEventListener);
        // Creiamo l'immagine del pannellino inferiore
        this.smallPanel = ImageFactory.createImage(ImageList.SMALL_PANEL);
        setFocusable(true);
        setLayout(null);
        requestFocusInWindow();
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        doDrawing(g);
    }

    public void doDrawing(Graphics g){
        // Disegna il pannellino
        drawSmallPanel(g);
        // Disegna le vite rimanenti nel pannellino
        drawLives(g);
        // Disegna le pillole rimanenti
        drawPills(g);
        // Disegna le powerpill rimanenti
        drawPowerPills(g);
        // Disegna i portali di teletrasporto
        drawPortals(g);
        // Disegna la frutta
        drawFruit(g);
        // Disegna Pacman
        drawPacman(g);
        // Disegna i fantasmi
        drawGhosts(g);
        // Aggiorna le JLabel
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

    // Aggiorna la label per mostrare Game Over
    public void showGameOver(){
        this.gameOverLabel.setText(logic.getGameOverString());
        this.gameOverLabel.paintImmediately(this.gameOverLabel.getVisibleRect());
    }

    public void drawLives(Graphics g) {
        livesIcons.clear();
        for(int i=0;i<this.logic.getLives()-1;i++){
            livesIcons.add(ImageFactory.createImage(ImageList.LIFE));
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
        Fruit f = this.logic.getMaze().getFruit();
        if(f != null){
            g.drawImage(f.getImage(), f.getX(), f.getY(), this);
        }
    }

    public void drawPortals(Graphics g) {
        Portal bluePortal = this.logic.getMaze().getBluePortal();
        Portal redPortal = this.logic.getMaze().getRedPortal();
        g.drawImage(bluePortal.getImage(), bluePortal.getX(), bluePortal.getY(), this);
        g.drawImage(redPortal.getImage(), redPortal.getX(), redPortal.getY(), this);
    }

    public void drawPowerPills(Graphics g) {
        for(int i = 0; i< this.logic.getMaze().getPowerPillsNum(); i++){
            PowerPill pp = this.logic.getMaze().getPowerPill(i);
            // rimuovere le pill direttamente dall'ArrayList causava una fastidiosa intermittenza delle altre
            if(!pp.isDead()){
                g.drawImage(pp.getImage(), pp.getX(), pp.getY(), this);
            }
        }
    }

    public void drawPills(Graphics g) {
        for(int i = 0; i< this.logic.getMaze().getPillsNum(); i++){
            Pill p = this.logic.getMaze().getPill(i);
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
