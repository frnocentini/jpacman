package model;

import utility.Coordinate;
import utility.State;

import static utility.State.CHASE;

public abstract class Ghost extends Sprite {

    State state;
    Coordinate target;
    Coordinate spawnPoint;

    public Ghost(Coordinate spawnPoint) {
        this.state = CHASE;
        this.spawnPoint = spawnPoint;
    }

    public void move(){

    }

    public abstract void setTarget();
}
