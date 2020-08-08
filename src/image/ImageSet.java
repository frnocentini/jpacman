package image;

import utility.Direction;

import javax.swing.*;
import java.util.ArrayList;

public class ImageSet {

    private ArrayList<ImageIcon> up;
    private ArrayList<ImageIcon> down;
    private ArrayList<ImageIcon> left;
    private ArrayList<ImageIcon> right;
    private int index;
    private final int SIZE;
    private boolean forward;

    public ImageSet(ArrayList<ImageIcon> up, ArrayList<ImageIcon> down, ArrayList<ImageIcon> left, ArrayList<ImageIcon> right, int index) {
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
        this.SIZE = up.size();
        this.index = index;
        this.forward = true;
    }

    public ImageIcon getNextFrame(Direction dir){
        ArrayList<ImageIcon> movement = null;
        switch(dir){
            case UP:
                movement = this.up;
                break;
            case RIGHT:
                movement = this.right;
                break;
            case DOWN:
                movement = this.down;
                break;
            case LEFT:
                movement = this.left;
                break;
        }
        ImageIcon frame = movement.get(index);
        if(forward){
            index++;
        }else{
            index--;
        }
        if(index == this.SIZE-1){
            forward = false;
        } else if (index == 0) {
            forward = true;
        }
        return frame;
    }

    public ImageIcon getFrameAt(int i, Direction dir) {
        ArrayList<ImageIcon> movement = null;
        switch(dir){
            case UP:
                movement = this.up;
                break;
            case RIGHT:
                movement = this.right;
                break;
            case DOWN:
                movement = this.down;
                break;
            case LEFT:
                movement = this.left;
                break;
        }
        return movement.get(i);
    }
}
