package sprites;

import structure.Coordinate;

import java.awt.Image;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public abstract class Sprite {

    protected Image image;
    protected boolean dead;
    protected int x;
    protected int y;
    protected int w;
    protected int h;
    protected int dx;
    protected int dy;
    protected Direction dir;
    protected int points;

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

    public boolean checkCollision(Sprite a) {
        // Find the bounds of the rectangle intersection
        Rectangle2D ar = new Rectangle2D.Double(a.getX()+a.getW()/4, a.getY()+a.getH()/4, 3 * a.getW() / 4, 3 * a.getH() / 4);
        Rectangle2D br = new Rectangle2D.Double(this.getX()+this.getW()/4, this.getY()+this.getH()/4, 3 * this.getW() / 4, 3 * this.getH() / 4);
        if(ar.intersects(br)){
            return true;
        }
        return false;
    }

    public boolean checkCircleCollision(Coordinate co, double length){
        Rectangle2D ar = new Rectangle2D.Double(this.getX()+this.getW()/4, this.getY()+this.getH()/4, 3 * this.getW() / 4, 3 * this.getH() / 4);
        Ellipse2D e = new Ellipse2D.Double(co.getX(),co.getY(),length,length);
        if(e.intersects(ar)){
            return true;
        }
        return false;
    }

    public boolean checkCircleCollision(int x, int y, double length){
        return checkCircleCollision(new Coordinate(x,y),length);
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
}
