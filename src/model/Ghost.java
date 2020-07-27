package model;

import constants.Constants;
import image.Image;
import image.ImageFactory;
import utility.CoordManager;
import utility.Coordinate;
import utility.Direction;
import utility.State;

import javax.swing.*;

import java.util.HashMap;

import static utility.Direction.*;
import static utility.Direction.UP;
import static utility.State.CHASE;

public abstract class Ghost extends Sprite {

    State state;
    Coordinate target;
    Coordinate spawnPoint;
    Pacman pacman;

    public Ghost(Pacman pacman) {
        setW(Constants.GHOST_WIDTH);
        setH(Constants.GHOST_HEIGHT);
        this.state = CHASE;
        this.pacman = pacman;
        this.target = new Coordinate(0,0);
    }

    public void move(){
        int speed = Constants.GHOST_SPEED;
        setTarget();
        if(CoordManager.canIMove(x,y)){
            changeDir();
        }
        switch(dir){
            case UP:
                y -= speed;
                break;
            case DOWN:
                y += speed;
                break;
            case LEFT:
                x -= speed;
                break;
            case RIGHT:
                x += speed;
                break;
        }
    }

    private void changeDir() {
        HashMap<Direction, Boolean> dirs = new HashMap<Direction, Boolean>();
        for (Direction dir : Direction.values()) {
            dirs.put(dir,CoordManager.checkEmpty(x,y,dir));
        }
        switch(dir){
            case UP:
                dirs.remove(DOWN);
                dirs.put(DOWN,false);
                break;
            case DOWN:
                dirs.remove(UP);
                dirs.put(UP,false);
                break;
            case LEFT:
                dirs.remove(RIGHT);
                dirs.put(RIGHT,false);
                break;
            case RIGHT:
                dirs.remove(LEFT);
                dirs.put(LEFT,false);
                break;
        }
        int maxValue, tempDist;
        maxValue = 0;
        for (Direction dir : Direction.values()) {
            if(dirs.get(dir)){
                tempDist = calcDist(dir);
                if(tempDist > maxValue){
                    maxValue = tempDist;
                    this.dir = dir;
                }
            }
        }
    }

    private int calcDist(Direction dir) {
        int x = this.x;
        int y = this.y;
        switch(dir){
            case UP:
                y -= Constants.BLOCK_DIM;
                break;
            case DOWN:
                y += Constants.BLOCK_DIM;
                break;
            case LEFT:
                x -= Constants.BLOCK_DIM;
                break;
            case RIGHT:
                x += Constants.BLOCK_DIM;
                break;
        }
        return (Math.abs(x-target.getX())^2+Math.abs(y-target.getY())^2);
    }

    public abstract void setTarget();
}
