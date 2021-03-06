package structure;

public class Coordinate {
    private int x;
    private int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
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

    public boolean equals(Coordinate c){
        if(this.x == c.getX() && this.y == c.getY()){
            return true;
        }
        return false;
    }

    public boolean equals(int x, int y){
        return equals(new Coordinate(x,y));
    }
}
