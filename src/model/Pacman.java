package model;

import constants.Constants;
import image.Image;
import image.ImageFactory;
import utility.CoordManager;
import utility.Coordinate;

import javax.swing.*;
import java.awt.event.KeyEvent;

import static utility.Direction.*;

public class Pacman extends Sprite {

    private Timer timer;
    private int keyPressed;

    public Pacman(){
        initialize();
    }

    private void initialize(){
        ImageIcon imageIcon = ImageFactory.createImage(Image.PACMAN_LEFT);
        setImage(imageIcon.getImage());

        Coordinate start = CoordManager.getObjCoord('S');

        setX(start.getX());
        setY(start.getY());
        setW(Constants.PACMAN_WIDTH);
        setH(Constants.PACMAN_HEIGHT);

        this.timer = new Timer(1,new PacmanLoop(this));

        dir = LEFT;

        dx = -Constants.PACMAN_SPEED;
        dy = 0;
    }

    public void move() {
        if(CoordManager.checkEmpty(x,y,dir)){
            x += dx;
            y += dy;
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

    public void keyPressed(KeyEvent e) {
        this.keyPressed = e.getKeyCode();
        if(timer.isRunning()){
            timer.stop();
        }
        timer.start();
    }

    public void changeLoop(){
        int speed = Constants.PACMAN_SPEED;
        ImageIcon imageIcon = null;
        switch (this.keyPressed) {
            case KeyEvent.VK_UP:
                if(dir != UP && dir != DOWN){
                    if(!CoordManager.canIMove(x,y) || !CoordManager.checkEmpty(x,y,UP)){
                        break;
                    }
                }
                dy = -speed;
                dx = 0;
                setDir(UP);
                imageIcon = ImageFactory.createImage(Image.PACMAN_UP);
                setImage(imageIcon.getImage());
                break;
            case KeyEvent.VK_DOWN:
                if(dir != UP && dir != DOWN){
                    if(!CoordManager.canIMove(x,y) || !CoordManager.checkEmpty(x,y,DOWN)){
                        break;
                    }
                }
                dy = speed;
                dx = 0;
                setDir(DOWN);
                imageIcon = ImageFactory.createImage(Image.PACMAN_DOWN);
                setImage(imageIcon.getImage());
                break;
            case KeyEvent.VK_RIGHT:
                if(dir != RIGHT && dir != LEFT){
                    if(!CoordManager.canIMove(x,y) || !CoordManager.checkEmpty(x,y,RIGHT)){
                        break;
                    }
                }
                dx = speed;
                dy = 0;
                setDir(RIGHT);
                imageIcon = ImageFactory.createImage(Image.PACMAN_RIGHT);
                setImage(imageIcon.getImage());
                break;
            case KeyEvent.VK_LEFT:
                if(dir != RIGHT && dir != LEFT){
                    if(!CoordManager.canIMove(x,y)|| !CoordManager.checkEmpty(x,y,LEFT)){
                        break;
                    }
                }
                dx = -speed;
                dy = 0;
                setDir(LEFT);
                imageIcon = ImageFactory.createImage(Image.PACMAN_LEFT);
                setImage(imageIcon.getImage());
                break;
        }
    }

    public void doOneLoop() {
        if(timer.isRunning()){
            changeLoop();
        }
    }
}
