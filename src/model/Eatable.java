package model;

public class Eatable extends Sprite {

    private int points;

    public Eatable(int x, int y, int w, int h, int points) {
        this.points = points;
        setX(x);
        setY(y);
        setW(w);
        setH(h);
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
