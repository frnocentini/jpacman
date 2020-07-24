package ui;

import callbacks.GameEventListener;
import constants.Constants;
import model.Pacman;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class GamePanel extends JPanel {

    private Pacman pacman;
    boolean inGame;
    private Timer timer;

    public GamePanel(){
        initializeVariables();
        initializeLayout();
    }

    private void initializeVariables() {
        this.inGame = true;
        this.timer = new Timer(Constants.GAME_SPEED,new GameLoop(this));
        this.timer.start();
        this.pacman = new Pacman();
    }

    private void initializeLayout() {
        addKeyListener(new GameEventListener(this));
        setFocusable(true);
        setPreferredSize(new Dimension(Constants.BOARD_WIDTH * Constants.SCALE, Constants.BOARD_HEIGHT * Constants.SCALE));
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        doDrawing(g);
    }

    private void doDrawing(Graphics g){
        if(inGame){
            drawPacman(g);
        } else{
            if(timer.isRunning()){
                timer.stop();
            }
        }
        //Metodo che si assicura che tutto si sia aggiornato
        Toolkit.getDefaultToolkit().sync();
    }

    private void drawPacman(Graphics g) {
        g.drawImage(pacman.getImage(), pacman.getX(), pacman.getY(), this);
    }

    public void doOneLoop() {
        update();
        //System.out.println("Update");
        repaint();
        //System.out.println("Repaint");
    }

    private void update() {
        this.pacman.move();
    }

    public void keyPressed(KeyEvent e) {
        this.pacman.keyPressed(e);
    }

    public void keyReleased(KeyEvent e) {
        //this.pacman.keyReleased(e);
    }
}
