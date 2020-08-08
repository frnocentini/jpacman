package model;

import utility.Coordinate;
import utility.Direction;

import java.awt.*;

public class Sprite {

    private Image image;
    private boolean dead;
    protected int x;
    protected int y;
    protected int w;
    protected int h;
    protected int dx;
    protected int dy;
    protected Direction dir;
    protected int points;
    protected Coordinate spawnPoint;

    public Sprite(int x, int y, int w, int h, int points) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.dead = false;
        this.points = points;
    }

    public Sprite() {
        this.dead = false;
    }

    public void die() {
        this.dead = true;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDx() {
        return dx;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public int getDy() {
        return dy;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public Direction getDir() {
        return dir;
    }

    public void setDir(Direction dir) {
        this.dir = dir;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

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
