package model;

import constants.Constants;
import image.Image;
import image.ImageFactory;
import image.ImageSet;
import utility.CoordManager;
import utility.Coordinate;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import static utility.Direction.*;

public class Pacman extends Character {

    private ImageSet imageSet;
    private Timer timer;
    private int keyPressed;
    private int lives;

    public Pacman(){
        initialize();
    }

    private void initialize(){

        addImageSet();

        dir = LEFT;
        ImageIcon imageIcon = this.imageSet.getNextFrame(dir);
        setImage(imageIcon.getImage());

        this.spawnPoint = CoordManager.getObjCoord('S');

        setX(this.spawnPoint.getX());
        setY(this.spawnPoint.getY());
        setW(Constants.PACMAN_WIDTH);
        setH(Constants.PACMAN_HEIGHT);

        this.timer = new Timer(1,new PacmanLoop(this));

        dx = -Constants.PACMAN_SPEED;
        dy = 0;
    }

    @Override
    public void move() {
        if(CoordManager.checkEmpty(x,y,dir)){
            x += dx;
            y += dy;
            if(dx != 0 || dy != 0){
                ImageIcon imageIcon = this.imageSet.getNextFrame(dir);
                setImage(imageIcon.getImage());
            }
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
    public void addImageSet() {
        ArrayList<ImageIcon> up = new ArrayList<>();
        ArrayList<ImageIcon> down = new ArrayList<>();
        ArrayList<ImageIcon> left = new ArrayList<>();
        ArrayList<ImageIcon> right = new ArrayList<>();
        ImageIcon a0 = ImageFactory.createImage(Image.PACMAN_A0);
        up.add(a0);
        up.add(ImageFactory.createImage(Image.PACMAN_U1));
        up.add(ImageFactory.createImage(Image.PACMAN_U2));
        down.add(a0);
        down.add(ImageFactory.createImage(Image.PACMAN_D1));
        down.add(ImageFactory.createImage(Image.PACMAN_D2));
        left.add(a0);
        left.add(ImageFactory.createImage(Image.PACMAN_L1));
        left.add(ImageFactory.createImage(Image.PACMAN_L2));
        right.add(a0);
        right.add(ImageFactory.createImage(Image.PACMAN_R1));
        right.add(ImageFactory.createImage(Image.PACMAN_R2));
        this.imageSet = new ImageSet(up,down,left,right,1);
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
                break;
        }
    }

    public void doOneLoop() {
        if(timer.isRunning()){
            changeLoop();
        }
    }

    public void addPoints(int points){
        this.points += points;
    }

    @Override
    public void returnToSpawnPoint(){
        super.returnToSpawnPoint();
        this.dir = LEFT;
        ImageIcon imageIcon = this.imageSet.getFrameAt(1,dir);
        setImage(imageIcon.getImage());
    }
}
