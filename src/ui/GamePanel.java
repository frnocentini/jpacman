package ui;

import callbacks.GameEventListener;
import constants.Constants;
import model.*;
import sound.Sound;
import sound.SoundFactory;
import sound.SoundPlayer;
import utility.CoordManager;
import utility.State;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import static java.awt.event.KeyEvent.VK_ENTER;
import static sound.Sound.*;
import static utility.Direction.*;
import static utility.State.*;

public class GamePanel extends JPanel {

    private GameMainFrame frame;
    private GameEventListener gameEventListener;
    private Pacman pacman;
    private ArrayList<Ghost> ghosts;
    boolean inGame;
    private Timer timer;
    private boolean pacmanStart;
    private long startTime;
    private long portalTime;
    private int level;
    private boolean munch;
    private int consecutiveGhosts;
    private JLabel pointsLabel;

    public GamePanel(GameMainFrame frame, int level, int points){
        this.frame = frame;
        initializeVariables(level,points);
        initializeLayout();
    }

    private void initializeVariables(int level, int points) {
        CoordManager.populateMaze();
        this.level = level;
        System.out.println(level);
        this.inGame = true;
        this.pacmanStart = false;
        this.timer = new Timer(Constants.GAME_SPEED,new GameLoop(this));
        this.timer.start();
        this.munch = true;
        SoundPlayer.stopAll();
        SoundPlayer.playMusic(GAME_START);
        this.startTime = this.portalTime = System.currentTimeMillis();
        this.pacman = new Pacman();
        pacman.setPoints(points);
        this.ghosts = new ArrayList<>();
        Pinky pinky = new Pinky(this.pacman);
        Blinky blinky = new Blinky(this.pacman);
        this.ghosts.add(pinky);
        this.ghosts.add(blinky);
        this.pointsLabel = new JLabel("Points: "+points);
        pointsLabel.setBounds(10,440,100,20);
        add(pointsLabel);
    }

    private void restartLevel(){
        this.level++;
        CoordManager.populateMaze();
        System.out.println(level);
        this.inGame = true;
        this.munch = true;
        SoundPlayer.playMusic(GAME_START);
        this.startTime = System.currentTimeMillis();
        this.portalTime = System.currentTimeMillis();
        this.pacman.returnToSpawnPoint();
        for(Ghost ghost : this.ghosts) {
            ghost.returnToSpawnPoint(this.level);
        }
        timer.start();
    }

    private void initializeLayout() {
        this.gameEventListener = new GameEventListener(this);
        addKeyListener(this.gameEventListener);
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

    private void doDrawing(Graphics g){
        if(inGame){
            drawPoints();
            drawPills(g);
            drawPowerPills(g);
            drawPortals(g);
            drawPacman(g);
            drawGhosts(g);
        } else{
            if(timer.isRunning()){
                timer.stop();
            }
        }
        //Metodo che si assicura che tutto si sia aggiornato
        Toolkit.getDefaultToolkit().sync();
    }
    private void drawPoints(){
        pointsLabel.setText("Points: "+this.pacman.getPoints());
    }

    private void drawPortals(Graphics g) {
        Portal bluePortal = CoordManager.maze.getBluePortal();
        Portal redPortal = CoordManager.maze.getRedPortal();
        g.drawImage(bluePortal.getImage(), bluePortal.getX(), bluePortal.getY(), this);
        g.drawImage(redPortal.getImage(), redPortal.getX(), redPortal.getY(), this);
        //System.out.println("Le coordinate del rosso sono: "+bluePortal.getOther().getX()+" e "+bluePortal.getOther().getY());
        //System.out.println("Le coordinate del blu sono: "+redPortal.getOther().getX()+" e "+redPortal.getOther().getY());
        if(System.currentTimeMillis() >= (this.portalTime + 400)){
            if(CoordManager.checkCollision(pacman,bluePortal)){
                teleport(pacman,bluePortal);
            }else if(CoordManager.checkCollision(pacman,redPortal)){
                teleport(pacman,redPortal);
            }else{
                for(Ghost ghost : this.ghosts) {
                    if (CoordManager.checkCollision(ghost, bluePortal)) {
                        teleport(ghost, bluePortal);
                    } else if (CoordManager.checkCollision(ghost, redPortal)) {
                        teleport(ghost, redPortal);
                    }
                }
            }
        }
    }

    private void teleport(Sprite a, Portal p){
        a.setX(p.getOther().getX());
        a.setY(p.getOther().getY());
        if(p.getColor().equals("BLUE")){
            SoundPlayer.playEffect(BLUE_PORTAL_SOUND);
        }else{
            SoundPlayer.playEffect(RED_PORTAL_SOUND);
        }
        this.portalTime = System.currentTimeMillis();
    }

    private void drawPowerPills(Graphics g) {
        for(int i = 0; i< CoordManager.maze.getPowerPillsNum(); i++){
            PowerPill pp = CoordManager.maze.getPowerPill(i);
            // rimuovere le pill direttamente dall'ArrayList causava una fastidiosa intermittenza delle altre
            if(CoordManager.checkCollision(pacman,pp)){
                if(!pp.isDead()){
                    CoordManager.maze.removeAlivePowerPill();
                    this.pacman.addPoints(pp.getPoints());
                    this.consecutiveGhosts = 0;
                    for(Ghost ghost : this.ghosts) {
                        if (ghost.getState() != EATEN) {
                            ghost.becomeFrightened();
                            System.out.println("Passo a frightened");
                        }
                    }
                }
                pp.setDead(true);
            } else if(!pp.isDead()){
                g.drawImage(pp.getImage(), pp.getX(), pp.getY(), this);
            }
        }
    }

    private void drawPills(Graphics g) {
        for(int i = 0; i< CoordManager.maze.getPillsNum(); i++){
            Pill p = CoordManager.maze.getPill(i);
            // rimuovere le pill direttamente dall'ArrayList causava una fastidiosa intermittenza delle altre
            if(CoordManager.checkCollision(pacman,p)){
                if(!p.isDead()){
                    CoordManager.maze.removeAlivePill();
                    this.pacman.addPoints(p.getPoints());
                    if(munch){
                        SoundPlayer.playEffect(MUNCH_1);
                        munch = false;
                    } else {
                        SoundPlayer.playEffect(MUNCH_2);
                        munch = true;
                    }
                }
                p.setDead(true);
            } else if(!p.isDead()){
                g.drawImage(p.getImage(), p.getX(), p.getY(), this);
            }
        }
    }

    private void drawPacman(Graphics g) {
        g.drawImage(pacman.getImage(), pacman.getX(), pacman.getY(), this);
    }

    private void drawGhosts(Graphics g) {
        for(Ghost ghost : this.ghosts){
            g.drawImage(ghost.getImage(), ghost.getX(), ghost.getY(), this);
            if(CoordManager.checkCollision(pacman,ghost)){
                if(ghost.getState() == FRIGHTENED){
                    ghost.becomeEaten();
                    SoundPlayer.playEffect(EAT_GHOST);
                    this.pacman.addPoints(ghost.getPoints() * (2^this.consecutiveGhosts));
                    this.consecutiveGhosts++;
                }
            }
        }
    }

    public void doOneLoop() {
        update();
        //System.out.println("Update");
        repaint();
        //System.out.println("Repaint");
    }

    private void update() {
        if(this.pacmanStart && this.inGame){
            boolean frightened = false;
            boolean eaten = false;
            this.pacman.move();
            for(Ghost ghost : this.ghosts) {
                ghost.move();
                if(ghost.getState() == FRIGHTENED){
                    frightened = true;
                }
                if(ghost.getState() == EATEN){
                    eaten = true;
                }
            }
            SoundPlayer.playBackgroundMusic(frightened,eaten);
            if(CoordManager.maze.getAlivePills() == 0){
                SoundPlayer.stopAll();
                endGame();
            }
        }else{
            long test = System.currentTimeMillis();
            for(int i = 0; i< CoordManager.maze.getPillsNum(); i++){
                CoordManager.maze.getPill(i).setDead(false);
            }
            if(test >= (this.startTime + 4*1000)) { //multiply by 1000 to get milliseconds
                SoundPlayer.stopMusic(STARTUP);
                this.pacmanStart=true;
                for(Ghost ghost : this.ghosts) {
                    ghost.getTimer().start();
                }
            }
        }
    }

    private void endGame(){
        System.out.println("fine livello");
        this.pacmanStart = false;
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        restartLevel();
    }

    public void keyPressed(KeyEvent e) {
        if(inGame){
            this.pacman.keyPressed(e);
            int keyPressed = e.getKeyCode();
            if(keyPressed == VK_ENTER){
                if(timer.isRunning()){
                    timer.stop();
                } else {
                    timer.start();
                }
            }
        }
    }

    public void keyReleased(KeyEvent e) {

    }
}
