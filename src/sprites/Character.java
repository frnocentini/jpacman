package sprites;

import structure.Coordinate;

public abstract class Character extends Sprite{

    protected Coordinate spawnPoint; // Punto di generazione del personaggio

    public abstract void move();

    public abstract void addFrameManager();

    public Coordinate getSpawnPoint() {
        return spawnPoint;
    }

    public void setSpawnPoint(Coordinate spawnPoint) {
        this.spawnPoint = spawnPoint;
    }

    // Rimanda il personaggio al punto di nascita
    public void returnToSpawnPoint() {
        this.x = this.spawnPoint.getX();
        this.y = this.spawnPoint.getY();
    }

}
