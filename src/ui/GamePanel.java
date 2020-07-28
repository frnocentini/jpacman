package ui;

import callbacks.GameEventListener;
import constants.Constants;
import model.Blinky;
import model.Pacman;
import model.Pill;
import utility.CoordManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class GamePanel extends JPanel {

    private GameMainFrame frame;
    private GameEventListener gameEventListener;
    private Pacman pacman;
    private Blinky blinky;
    boolean inGame;
    private Timer timer;
    private boolean pacmanStart;
    private long startTime;
    private int level;

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
        this.pacman = new Pacman();
        this.blinky = new Blinky(this.pacman);
        this.pacmanStart = false;
        this.startTime = System.currentTimeMillis();
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
            drawBlinky(g);
            drawPacman(g);
        } else{
            if(timer.isRunning()){
                timer.stop();
            }
        }
        //Metodo che si assicura che tutto si sia aggiornato
        Toolkit.getDefaultToolkit().sync();
    }

    private void drawPills(Graphics g) {
        for(int i = 0; i< CoordManager.maze.getPillsNum(); i++){
            Pill p = CoordManager.maze.getPill(i);
            // rimuovere le pill direttamente dall'ArrayList causava una fastidiosa intermittenza delle altre
            if(CoordManager.checkCollision(pacman,p)){
                if(!p.isDead()){
                    CoordManager.maze.removeAlivePill();
                }
                p.setDead(true);
            } else if(!p.isDead()){
                g.drawImage(p.getImage(), p.getX(), p.getY(), this);
            }
            if(CoordManager.maze.getAlivePills() == 150){
                this.inGame = false;
                frame.getContentPane().removeAll();
                this.gameEventListener = null;
                System.gc();
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

    private void drawBlinky(Graphics g) {
        g.drawImage(blinky.getImage(), blinky.getX(), blinky.getY(), this);
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
            this.blinky.move();
        }else{
            long test = System.currentTimeMillis();
            for(int i = 0; i< CoordManager.maze.getPillsNum(); i++){
                CoordManager.maze.getPill(i).setDead(false);
            }
            if(test >= (this.startTime + 3*1000)) { //multiply by 1000 to get milliseconds
                this.pacmanStart=true;
            }
        }
    }

    public void keyPressed(KeyEvent e) {
        this.pacman.keyPressed(e);
    }

    public void keyReleased(KeyEvent e) {

    }
}
