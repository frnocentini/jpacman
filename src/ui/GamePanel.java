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

    public GamePanel(GameMainFrame frame, int level){
        this.frame = frame;
        initializeVariables(level);
        initializeLayout();
    }

    private void initializeVariables(int level) {
        this.level = level;
        System.out.println(level);
        this.inGame = true;
        this.timer = new Timer(Constants.GAME_SPEED,new GameLoop(this));
        this.timer.start();
        this.munch = true;
        SoundPlayer.playMusic(GAME_START);
        this.pacman = new Pacman();
        this.ghosts = new ArrayList<>();
        Blinky blinky = new Blinky(this.pacman);
        Pinky pinky = new Pinky(this.pacman);
        this.ghosts.add(blinky);
        this.ghosts.add(pinky);
        this.pacmanStart = false;
        this.startTime = this.portalTime = System.currentTimeMillis();
    }

    private void initializeLayout() {
        this.gameEventListener = new GameEventListener(this);
        addKeyListener(this.gameEventListener);
        setFocusable(true);
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

    private void drawPortals(Graphics g) {
        Portal bluePortal = CoordManager.maze.getBluePortal();
        Portal redPortal = CoordManager.maze.getRedPortal();
        g.drawImage(bluePortal.getImage(), bluePortal.getX(), bluePortal.getY(), this);
        g.drawImage(redPortal.getImage(), redPortal.getX(), redPortal.getY(), this);
        //System.out.println("Le coordinate del rosso sono: "+bluePortal.getOther().getX()+" e "+bluePortal.getOther().getY());
        //System.out.println("Le coordinate del blu sono: "+redPortal.getOther().getX()+" e "+redPortal.getOther().getY());
        if(System.currentTimeMillis() >= (this.portalTime + 300)){
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
                    for(Ghost ghost : this.ghosts) {
                        if (ghost.getState() != EATEN) {
                            ghost.setState(FRIGHTENED);
                            ghost.setFrightTime();
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
            if(CoordManager.maze.getAlivePills() == 0){
                this.inGame = false;
                frame.getContentPane().removeAll();
                this.gameEventListener = null;
                System.gc();
                System.out.println("fine livello");
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                frame.dispose();
                frame.initializeLayout();
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
        if(this.pacmanStart){
            this.pacman.move();
            for(Ghost ghost : this.ghosts) {
                ghost.move();
            }
        }else{
            long test = System.currentTimeMillis();
            for(int i = 0; i< CoordManager.maze.getPillsNum(); i++){
                CoordManager.maze.getPill(i).setDead(false);
            }
            if(test >= (this.startTime + 4*1000)) { //multiply by 1000 to get milliseconds
                this.pacmanStart=true;
                for(Ghost ghost : this.ghosts) {
                    ghost.getTimer().start();
                }
            }
        }
    }

    public void keyPressed(KeyEvent e) {
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

    public void keyReleased(KeyEvent e) {

    }
}
