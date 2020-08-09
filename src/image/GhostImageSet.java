package image;

import utility.Direction;

import javax.swing.*;
import java.util.ArrayList;

public class GhostImageSet extends ImageSet{

    protected ArrayList<ImageIcon> frightened;
    protected ImageIcon eatenUp;
    protected ImageIcon eatenDown;
    protected ImageIcon eatenLeft;
    protected ImageIcon eatenRight;
    protected boolean frightenedForward;
    protected int frightenedIndex;
    protected final int FRIGHTENED_SIZE;

    public GhostImageSet(ArrayList<ImageIcon> up, ArrayList<ImageIcon> down, ArrayList<ImageIcon> left, ArrayList<ImageIcon> right, ArrayList<ImageIcon> frightened, ImageIcon eatenUp, ImageIcon eatenDown, ImageIcon eatenLeft, ImageIcon eatenRight) {
        super(up, down, left, right, 0);
        this.frightened = frightened;
        this.eatenUp = eatenUp;
        this.eatenDown = eatenDown;
        this.eatenLeft = eatenLeft;
        this.eatenRight = eatenRight;
        this.frightenedIndex = 0;
        this.frightenedForward = true;
        this.FRIGHTENED_SIZE = frightened.size();
    }

    public ImageIcon getNextFrameFrightened(boolean timeOut){
        if(!timeOut && this.frightenedIndex > (this.FRIGHTENED_SIZE/2)-1){
            this.frightenedIndex = (this.FRIGHTENED_SIZE/2)-1;
            this.frightenedForward = false;
        }
        if(this.frightenedIndex < 0){
            this.frightenedIndex = 0;
            this.frightenedForward = true;
        }
        ImageIcon frame = frightened.get(this.frightenedIndex);
        if(frightenedForward){
            frightenedIndex++;
        }else{
            frightenedIndex--;
        }
        if(timeOut){
            if(frightenedIndex == this.FRIGHTENED_SIZE-1){
                frightenedForward = false;
            }
        } else {
            if(frightenedIndex == (this.FRIGHTENED_SIZE/2)-1){
                frightenedForward = false;
            }
        }
        if (frightenedIndex == 0) {
            frightenedForward = true;
        }
        return frame;
    }

    public ImageIcon getNextFrameEaten(Direction dir){
        switch(dir){
            case UP:
                return this.eatenUp;
            case RIGHT:
                return this.eatenRight;
            case DOWN:
                return this.eatenDown;
            case LEFT:
                return this.eatenLeft;
        }
        return null;
    }

}
