package sprites;

import image.ImageList;
import loops.PacmanLoop;
import constants.Constants;
import image.ImageFactory;
import frameManagers.PacmanFrameManager;
import structure.MazeManager;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import static sprites.Direction.*;

public class Pacman extends Character {

    private PacmanFrameManager frameManager;    // Oggetto che si occupa nel fornire l'immagine a Pacman
                                                // per creare un effetto di animazione
    private Timer timer;                        // Timer che permette di cambiare direzione in quella
                                                // richiesta dal giocatore non appena possibile
    private int keyCode;                        // Ultima direzione richiesta dal giocatore
    private int lives;                          // Numero delle vite rimanenti

    public Pacman(){
        initialize();
    }

    private void initialize(){
        // Creiamo e aggiungiamo le immagini al FrameManager
        addFrameManager();
        // Gli impostiamo la prima immagine
        dir = LEFT;
        ImageIcon imageIcon = this.frameManager.getNextFrame(dir);
        setImage(imageIcon.getImage());
        // Assegnamo ilpunto di generazione
        this.spawnPoint = MazeManager.getObjCoord('S');

        setX(this.spawnPoint.getX());
        setY(this.spawnPoint.getY());
        setW(Constants.PACMAN_WIDTH);
        setH(Constants.PACMAN_HEIGHT);
        // Timer chiama ogni millisecondo PacmanLoop, necessario al cambio di direzione
        this.timer = new Timer(1,new PacmanLoop(this));

        dx = -Constants.PACMAN_SPEED;
        dy = 0;
    }

    @Override
    public void move() { //Richiamato dal GameLoop ad ogni update()
        // Se Pacman è morto si avvia l'animazione della morte ad ogni chiamata del move()
        if(!this.dead){
            // Se può andare avanti, lo fa
            if(MazeManager.checkEmpty(this.x,this.y,this.dir)) {
                x += dx;
                y += dy;
                // Cambio il frame dell'animazione
                ImageIcon imageIcon = this.frameManager.getNextFrame(dir);
                setImage(imageIcon.getImage());
            }
        } else {
            ImageIcon imageIcon = this.frameManager.getNextFrameDeath();
            setImage(imageIcon.getImage());
        }
        if(x<0){
            x = 0;
        }
        if(x>= Constants.BOARD_WIDTH- Constants.PACMAN_WIDTH){
            x = Constants.BOARD_WIDTH- Constants.PACMAN_WIDTH;
        }
        if(y<0){
            y = 0;
        }
        if(y>= Constants.BOARD_HEIGHT- Constants.PACMAN_HEIGHT){
            y = Constants.BOARD_HEIGHT- Constants.PACMAN_HEIGHT;
        }
    }

    @Override
    public void addFrameManager() {
        ArrayList<ImageIcon> up = new ArrayList<>();
        ArrayList<ImageIcon> down = new ArrayList<>();
        ArrayList<ImageIcon> left = new ArrayList<>();
        ArrayList<ImageIcon> right = new ArrayList<>();
        ArrayList<ImageIcon> death = new ArrayList<>();
        ImageIcon a0 = ImageFactory.createImage(ImageList.PACMAN_A0);
        up.add(a0);
        up.add(ImageFactory.createImage(ImageList.PACMAN_U1));
        up.add(ImageFactory.createImage(ImageList.PACMAN_U2));
        down.add(a0);
        down.add(ImageFactory.createImage(ImageList.PACMAN_D1));
        down.add(ImageFactory.createImage(ImageList.PACMAN_D2));
        left.add(a0);
        left.add(ImageFactory.createImage(ImageList.PACMAN_L1));
        left.add(ImageFactory.createImage(ImageList.PACMAN_L2));
        right.add(a0);
        right.add(ImageFactory.createImage(ImageList.PACMAN_R1));
        right.add(ImageFactory.createImage(ImageList.PACMAN_R2));
        death.add(ImageFactory.createImage(ImageList.PACMAN_DEATH0));
        death.add(ImageFactory.createImage(ImageList.PACMAN_DEATH1));
        death.add(ImageFactory.createImage(ImageList.PACMAN_DEATH2));
        death.add(ImageFactory.createImage(ImageList.PACMAN_DEATH3));
        death.add(ImageFactory.createImage(ImageList.PACMAN_DEATH4));
        death.add(ImageFactory.createImage(ImageList.PACMAN_DEATH5));
        death.add(ImageFactory.createImage(ImageList.PACMAN_DEATH6));
        death.add(ImageFactory.createImage(ImageList.PACMAN_DEATH7));
        death.add(ImageFactory.createImage(ImageList.PACMAN_DEATH8));
        death.add(ImageFactory.createImage(ImageList.PACMAN_DEATH9));
        death.add(ImageFactory.createImage(ImageList.PACMAN_DEATH10));
        this.frameManager = new PacmanFrameManager(up,down,left,right,1,4,this,death,7);
    }

    public void keyPressed(KeyEvent e) {
        this.keyCode = e.getKeyCode();
        // Il timer per cambiare direzione viene avviato
        if(timer.isRunning()){
            timer.stop();
        }
        timer.start();
    }

    public void doOneLoop() {
        if(timer.isRunning()){
            changeLoop();
        }
    }

    public void changeLoop(){
        int speed = Constants.PACMAN_SPEED;
        switch (this.keyCode) {
            case KeyEvent.VK_UP:
                // Se la direzione non è quella attuale o la sua inversa devo controllare che io possa
                // cambiare direzione (le mie coordinate sono multiplo della dimensione dei blocchi)
                if(dir != UP && dir != DOWN){
                    if(!MazeManager.canIMove(x,y) || !MazeManager.checkEmpty(x,y,UP)){
                        break;
                    }
                }
                // Cambio le velocità
                dy = -speed;
                dx = 0;
                setDir(UP);
                if(timer.isRunning()){
                    timer.stop();
                }
                break;
            case KeyEvent.VK_DOWN:
                if(dir != UP && dir != DOWN){
                    if(!MazeManager.canIMove(x,y) || !MazeManager.checkEmpty(x,y,DOWN)){
                        break;
                    }
                }
                dy = speed;
                dx = 0;
                setDir(DOWN);
                if(timer.isRunning()){
                    timer.stop();
                }
                break;
            case KeyEvent.VK_RIGHT:
                if(dir != RIGHT && dir != LEFT){
                    if(!MazeManager.canIMove(x,y) || !MazeManager.checkEmpty(x,y,RIGHT)){
                        break;
                    }
                }
                dx = speed;
                dy = 0;
                setDir(RIGHT);
                if(timer.isRunning()){
                    timer.stop();
                }
                break;
            case KeyEvent.VK_LEFT:
                if(dir != RIGHT && dir != LEFT){
                    if(!MazeManager.canIMove(x,y)|| !MazeManager.checkEmpty(x,y,LEFT)){
                        break;
                    }
                }
                dx = -speed;
                dy = 0;
                setDir(LEFT);
                if(timer.isRunning()){
                    timer.stop();
                }
                break;
        }
    }

    public void addPoints(int points){
        this.points += points;
    }

    @Override
    public void returnToSpawnPoint(){
        super.returnToSpawnPoint();
        this.dir = LEFT;
        dx = -Constants.PACMAN_SPEED;
        dy = 0;
        ImageIcon imageIcon = this.frameManager.getFrameAt(1,dir);
        setImage(imageIcon.getImage());
    }

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void increaseLives(){
        this.lives++;
    }

    public void decreaseLives(){
        this.lives--;
    }
}
