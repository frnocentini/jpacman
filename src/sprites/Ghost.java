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

    protected GhostFrameManager frameManager;   // FrameManager del Ghost
    protected State state;                      // Stato del fantasma (CHASE / SCATTER / FRIGHTENED / EATEN)
    protected Coordinate target;                // Coordinata verso cui andare nello spostamento
    protected Coordinate scatterTarget;         // Coordinata da usare come target nella modalità Scatter
    protected Pacman pacman;                    // Riferimento a Pacman necessario per il calcolo del Chase Target
    protected GhostLoop ghostLoop;              // Con timer definisce le caratteristiche temporali dei
                                                // fantasmi (es. passaggio scatter / target)
    protected Timer timer;
    protected long frightTime;                  // Ultimo istante in cui pacman ha mangiato una powerpill
    protected long pausedTime;                  // Tempo passato mentre il gioco era in pausa
    protected long pauseClock;                  // Istante in cui il gioco è stato messo in pausa l'ultima volta

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
        // Impostiamo il nuovo target, a seconda dello spostamento di Pacman o dello stato
        setTarget();
        ImageIcon imageIcon;
        switch(this.state){
            case CHASE:
            case SCATTER:
                x -= x%speed;
                y -= y%speed;
                imageIcon = this.frameManager.getNextFrame(dir);
                setImage(imageIcon.getImage());
                break;
            case FRIGHTENED:
                x -= x%speed;
                y -= y%speed;
                // Se sono passati 6 secondi mostriamo l'intermittenza del fantasma
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
                x -= x%speed;
                y -= y%speed;
                imageIcon = this.frameManager.getNextFrameEaten(dir);
                setImage(imageIcon.getImage());
                break;
        }
        // Se si trova a coordinate multiple della dimensione del blocco (= 20)
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
        HashMap<Direction, Boolean> dirs = this.getAvailableDirs();
        boolean check;
        Direction dirPick;
        // Finché la direzione non è disponibile non esco dal ciclo
        do{
            int pick = new Random().nextInt(Direction.values().length);
            dirPick = Direction.values()[pick];
            check = dirs.get(dirPick);
        }while(!check);
        this.dir = dirPick;
    }

    public void changeDir() {
        HashMap<Direction, Boolean> dirs = this.getAvailableDirs();
        // Controlliamo quale, tra le direzioni disponibili, è la più vicina in linea d'aria
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

    public HashMap<Direction, Boolean> getAvailableDirs(){
        HashMap<Direction, Boolean> dirs = new HashMap<Direction, Boolean>();
        for (Direction dir : Direction.values()) {
            dirs.put(dir, MazeManager.checkEmpty(this.x,this.y,dir));
            // etichetta: DIREZIONE - valore: BOOLEANA
        }
        // Controlliamo che non sia in trappola, in caso escludiamo la possibilità di dietrofront
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
        return dirs;
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
                // Metodo astratto in Ghost in quanto si implementa in modo dierso per ogni fantasma
                setChaseTarget();
                break;
            case SCATTER:
                this.target.setX(this.scatterTarget.getX());
                this.target.setY(this.scatterTarget.getY());
                break;
            case EATEN:
                // Il target è lo SpawnPoint di Blinky
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
            this.state = FRIGHTENED;
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
