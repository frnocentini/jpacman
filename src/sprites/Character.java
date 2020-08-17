package sprites;

import utility.Coordinate;

public abstract class Character extends Sprite{

    protected Coordinate spawnPoint;

    public abstract void move();

    public abstract void addImageSet();

    public Coordinate getSpawnPoint() {
        return spawnPoint;
    }

    public void setSpawnPoint(Coordinate spawnPoint) {
        this.spawnPoint = spawnPoint;
    }

    public void returnToSpawnPoint() {
        this.x = this.spawnPoint.getX();
        this.y = this.spawnPoint.getY();
        this.dx = 0;
        this.dy = 0;
    }

}
