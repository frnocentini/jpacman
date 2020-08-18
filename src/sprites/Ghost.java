package sprites;

import loops.GhostLoop;
import constants.Constants;
import frameManagers.GhostFrameManager;
import structure.MazeManager;
import structure.Coordinate;

import javax.swing.*;

import java.util.HashMap;
import java.util.Random;

import static sprites.Direction.*;
import static sprites.Direction.UP;
import static sprites.State.*;

public abstract class Ghost extends Character {

    protected GhostFrameManager frameManager;
    protected State state;
    protected Coordinate target;
    protected Coordinate scatterTarget;
    protected Pacman pacman;
    protected GhostLoop ghostLoop;
    protected long frightTime;
    protected Timer timer;
    protected long pausedTime;
    protected long pauseClock;

    public Ghost(Pacman pacman) {
        setW(Constants.GHOST_WIDTH);
        setH(Constants.GHOST_HEIGHT);
        ghostLoop = new GhostLoop(this,1);
        this.points = Constants.GHOST_POINTS;
        this.pausedTime = 0;
        this.timer = new Timer(1,ghostLoop);
        this.pacman = pacman;
        this.target = new Coordinate(0,0);
    }

    @Override
    public void move(){
        int speed = Constants.GHOST_SPEED;
        setTarget();
        ImageIcon imageIcon = null;
        switch(this.state){
            case CHASE:
            case SCATTER:
                x -= x%(Constants.GHOST_SPEED);
                y -= y%(Constants.GHOST_SPEED);
                imageIcon = this.frameManager.getNextFrame(dir);
                setImage(imageIcon.getImage());
                break;
            case FRIGHTENED:
                x -= x%(Constants.GHOST_SPEED);
                y -= y%(Constants.GHOST_SPEED);
                if(System.currentTimeMillis() > this.frightTime + 6000 + this.pausedTime){
                    imageIcon = this.frameManager.getNextFrameFrightened(true);
                    setImage(imageIcon.getImage());
                } else {
                    imageIcon = this.frameManager.getNextFrameFrightened(false);
                    setImage(imageIcon.getImage());
                }
                break;
            case EATEN:
                speed  = Constants.GHOST_SPEED_EATEN;
                x -= x%(Constants.GHOST_SPEED_EATEN);
                y -= y%(Constants.GHOST_SPEED_EATEN);
                imageIcon = this.frameManager.getNextFrameEaten(dir);
                setImage(imageIcon.getImage());
                break;
        }
        if(MazeManager.canIMove(x,y)){
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

    public void changeToRandDir() {
        boolean trapped = false;
        boolean check = false;
        Direction dir = null;
        while(!check){
            int pick = new Random().nextInt(Direction.values().length);
            dir = Direction.values()[pick];
            check = MazeManager.checkEmpty(this.x,this.y,dir);
            switch(dir){
                case UP:
                    trapped = !MazeManager.checkEmpty(this.x,this.y,DOWN) && !MazeManager.checkEmpty(this.x,this.y,RIGHT) && !MazeManager.checkEmpty(this.x,this.y,LEFT);
                    if(this.dir == DOWN && !trapped){
                        check = false;
                    }
                    break;
                case DOWN:
                    trapped = !MazeManager.checkEmpty(this.x,this.y,UP) && !MazeManager.checkEmpty(this.x,this.y,RIGHT) && !MazeManager.checkEmpty(this.x,this.y,LEFT);
                    if(this.dir == UP && !trapped){
                        check = false;
                    }
                    break;
                case LEFT:
                    trapped = !MazeManager.checkEmpty(this.x,this.y,UP) && !MazeManager.checkEmpty(this.x,this.y,DOWN) && !MazeManager.checkEmpty(this.x,this.y,RIGHT);
                    if(this.dir == RIGHT && !trapped){
                        check = false;
                    }
                    break;
                case RIGHT:
                    trapped = !MazeManager.checkEmpty(this.x,this.y,UP) && !MazeManager.checkEmpty(this.x,this.y,LEFT) && !MazeManager.checkEmpty(this.x,this.y,DOWN);
                    if(this.dir == LEFT && !trapped){
                        check = false;
                    }
                    break;
            }
        }
        this.dir = dir;
    }

    public void changeDir() {
        HashMap<Direction, Boolean> dirs = new HashMap<Direction, Boolean>();
        for (Direction dir : Direction.values()) {
            dirs.put(dir, MazeManager.checkEmpty(x,y,dir));
        }
        switch(this.dir){
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
                break;
            case RIGHT:
                if(dirs.get(UP) || dirs.get(DOWN) || dirs.get(RIGHT)){
                    dirs.remove(LEFT);
                    dirs.put(LEFT,false);
                }
                break;
        }
        int minValue, tempDist;
        Direction tempDir = null;
        minValue = Integer.MAX_VALUE;
        for (Direction dir : Direction.values()) {
            if(dirs.get(dir)){
                tempDist = calcDist(dir);
                if(tempDist < minValue){
                    minValue = tempDist;
                    tempDir = dir;
                }
            }
        }
        this.dir = tempDir;
    }

    public int calcDist(Direction dir) {
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
        int diffx = Math.abs(x-target.getX())^2;
        int diffy = Math.abs(y-target.getY())^2;
        return diffx+diffy;
    }

    public void setTarget() {
        switch(this.state){
            case CHASE:
                setChaseTarget();
                break;
            case SCATTER:
                this.target.setX(this.scatterTarget.getX());
                this.target.setY(this.scatterTarget.getY());
                break;
            case EATEN:
                Coordinate start = MazeManager.getObjCoord('1');
                this.target.setX(start.getX());
                this.target.setY(start.getY());
                break;
            case FRIGHTENED:
                break;
        }
    }

    public void becomeEaten(){
        this.state = EATEN;
    }

    public abstract void setChaseTarget();

    public void becomeFrightened(){
        if(this.state != FRIGHTENED){
            switch(this.dir){
                case UP:
                    if(MazeManager.checkEmpty(x,y,DOWN)){
                        this.dir = DOWN;
                    }
                    break;
                case RIGHT:
                    if(MazeManager.checkEmpty(x,y,LEFT)){
                        this.dir = LEFT;
                    }
                    break;
                case DOWN:
                    if(MazeManager.checkEmpty(x,y,UP)){
                        this.dir = UP;
                    }
                    break;
                case LEFT:
                    if(MazeManager.checkEmpty(x,y,RIGHT)){
                        this.dir = RIGHT;
                    }
                    break;
            }
        }
        ghostLoop.resetTimeLost();
        setFrightTime();
        this.pausedTime = 0;
        this.state = FRIGHTENED;
    }

    public void returnToSpawnPoint(int level){
        super.returnToSpawnPoint();
        this.timer.stop();
        this.ghostLoop = null;
        this.ghostLoop = new GhostLoop(this,level);
        this.timer = new Timer(1,ghostLoop);
        ImageIcon imageIcon = this.frameManager.getFrameAt(0,dir);
        setImage(imageIcon.getImage());
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Coordinate getTarget() {
        return target;
    }

    public void setTarget(Coordinate target) {
        this.target = target;
    }

    public Coordinate getScatterTarget() {
        return scatterTarget;
    }

    public void setScatterTarget(Coordinate scatterTarget) {
        this.scatterTarget = scatterTarget;
    }

    public Pacman getPacman() {
        return pacman;
    }

    public void setPacman(Pacman pacman) {
        this.pacman = pacman;
    }

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public long getFrightTime() {
        return frightTime;
    }

    public void setFrightTime() {
        this.frightTime = System.currentTimeMillis();
    }

    public long getPausedTime() {
        return pausedTime;
    }

    public void setPausedTime(long pausedTime) {
        this.pausedTime = pausedTime;
    }

    public void pause() {
        this.pauseClock = System.currentTimeMillis();
    }

    public void resume() {
        this.pausedTime += System.currentTimeMillis()-this.pauseClock;
    }
}
