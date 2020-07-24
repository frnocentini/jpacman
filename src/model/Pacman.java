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

    public Pacman(){
        initialize();
    }

    private void initialize(){
        ImageIcon imageIcon = ImageFactory.createImage(Image.PACMAN);
        setImage(imageIcon.getImage());

        Coordinate start = CoordManager.getObjCoord('P');

        dir = RIGHT;

        setX(start.getX());
        setY(start.getY());
    }

    @Override
    public void move() {
        /*if(CoordManager.checkWall(x,y,dir)){

        }*/
        x += dx;
        y += dy;
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
        int key = e.getKeyCode();
        int speed = Constants.PACMAN_SPEED;
        switch (key) {
            case KeyEvent.VK_UP:
                dy = -speed;
                dx = 0;
                setDir(UP);
                break;
            case KeyEvent.VK_DOWN:
                dy = speed;
                dx = 0;
                setDir(DOWN);
                break;
            case KeyEvent.VK_RIGHT:
                dx = speed;
                dy = 0;
                setDir(RIGHT);
                break;
            case KeyEvent.VK_LEFT:
                dx = -speed;
                dy = 0;
                setDir(LEFT);
                break;
        }
    }

    /*public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_DOWN:
                dy = 0;
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_LEFT:
                dx = 0;
                break;
        }
    }*/
}
