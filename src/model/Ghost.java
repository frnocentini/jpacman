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
import java.util.Random;

import static utility.Direction.*;
import static utility.Direction.UP;
import static utility.State.CHASE;
import static utility.State.FRIGHTENED;

public abstract class Ghost extends Sprite {

    State state;
    Coordinate target;
    Coordinate scatterTarget;
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
            if(this.state != FRIGHTENED){
                changeDir();
            } else {
                changeToRandDir();
            }
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

    private void changeToRandDir() {
        boolean trapped = false;
        boolean check = false;
        Direction dir = null;
        while(!check){
            int pick = new Random().nextInt(Direction.values().length);
            dir = Direction.values()[pick];
            check = CoordManager.checkEmpty(this.x,this.y,dir);
            switch(dir){
                case UP:
                    trapped = !CoordManager.checkEmpty(this.x,this.y,DOWN) && !CoordManager.checkEmpty(this.x,this.y,RIGHT) && !CoordManager.checkEmpty(this.x,this.y,LEFT);
                    if(this.dir == DOWN){
                        check = false;
                    }
                    break;
                case DOWN:
                    trapped = !CoordManager.checkEmpty(this.x,this.y,UP) && !CoordManager.checkEmpty(this.x,this.y,RIGHT) && !CoordManager.checkEmpty(this.x,this.y,LEFT);
                    if(this.dir == UP && !trapped){
                        check = false;
                    }
                    break;
                case LEFT:
                    trapped = !CoordManager.checkEmpty(this.x,this.y,UP) && !CoordManager.checkEmpty(this.x,this.y,DOWN) && !CoordManager.checkEmpty(this.x,this.y,RIGHT);
                    if(this.dir == RIGHT && !trapped){
                        check = false;
                    }
                    break;
                case RIGHT:
                    trapped = !CoordManager.checkEmpty(this.x,this.y,UP) && !CoordManager.checkEmpty(this.x,this.y,LEFT) && !CoordManager.checkEmpty(this.x,this.y,DOWN);
                    if(this.dir == LEFT && !trapped){
                        check = false;
                    }
                    break;
            }
        }
        this.dir = dir;
    }

    private void changeDir() {
        HashMap<Direction, Boolean> dirs = new HashMap<Direction, Boolean>();
        for (Direction dir : Direction.values()) {
            dirs.put(dir,CoordManager.checkEmpty(x,y,dir));
        }
        switch(dir){
            case UP:
                if(dirs.get(UP) || dirs.get(LEFT) || dirs.get(RIGHT)){
                    dirs.remove(DOWN);
                    dirs.put(DOWN,false);
                }
                break;
            case DOWN:
                if(dirs.get(DOWN) || dirs.get(LEFT) || dirs.get(RIGHT)){
                    dirs.remove(UP);
                    dirs.put(UP,false);
                }
                break;
            case LEFT:
                if(dirs.get(UP) || dirs.get(DOWN) || dirs.get(LEFT)){
                    dirs.remove(RIGHT);
                    dirs.put(RIGHT,false);
                }
            case RIGHT:
                if(dirs.get(UP) || dirs.get(DOWN) || dirs.get(RIGHT)){
                    dirs.remove(LEFT);
                    dirs.put(LEFT,false);
                }
        }
        int maxValue, tempDist;
        maxValue = Integer.MAX_VALUE;
        for (Direction dir : Direction.values()) {
            if(dirs.get(dir)){
                tempDist = calcDist(dir);
                if(tempDist < maxValue){
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
        System.out.println("x: "+ (Math.abs(x-target.getX())));
        System.out.println("y: "+ (Math.abs(y-target.getY())));
        return (Math.abs(x-target.getX())+Math.abs(y-target.getY()));
    }

    private void setTarget() {
        switch(this.state){
            case CHASE:
                setChaseTarget();
                break;
            case SCATTER:
                this.target = this.scatterTarget;
                break;
            case EATEN:
                this.target = this.spawnPoint;
                break;
        }
    }

    public abstract void setChaseTarget();
}
