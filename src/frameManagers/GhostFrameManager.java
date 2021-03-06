package frameManagers;

import sprites.Direction;

import javax.swing.ImageIcon;
import java.util.ArrayList;

public class GhostFrameManager extends FrameManager {

    private ArrayList<ImageIcon> frightened;    // Frame del fantasma spaventato
    private ImageIcon eatenUp;                  // Immagini del fantasma mangiato
    private ImageIcon eatenDown;
    private ImageIcon eatenLeft;
    private ImageIcon eatenRight;
    private boolean frightenedForward;          // Attributi analoghi a quelli classici ma per l'animazione spaventata
    private int frightenedIndex;
    private final int FRIGHTENED_SIZE;
    private int frightenedDelay;
    private final int FRIGHTENED_DELAY_LIMIT;

    public GhostFrameManager(ArrayList<ImageIcon> up, ArrayList<ImageIcon> down, ArrayList<ImageIcon> left, ArrayList<ImageIcon> right, int delayLimit, ArrayList<ImageIcon> frightened, ImageIcon eatenUp, ImageIcon eatenDown, ImageIcon eatenLeft, ImageIcon eatenRight, int frightenedDelayLimit) {
        super(up, down, left, right, 0,delayLimit);
        this.frightened = frightened;
        this.eatenUp = eatenUp;
        this.eatenDown = eatenDown;
        this.eatenLeft = eatenLeft;
        this.eatenRight = eatenRight;
        this.frightenedIndex = 0;
        this.frightenedForward = true;
        this.FRIGHTENED_DELAY_LIMIT = frightenedDelayLimit;
        this.frightenedDelay = 0;
        this.FRIGHTENED_SIZE = frightened.size();
    }

    public ImageIcon getNextFrameFrightened(boolean timeOut){
        //timeOut indica che va usata la seconda metà dell'ArrayList (fantasmi intermittenti)
        // Portiamo l'index tra i limit consentiti (tra 0 e la metà se timeOut == false)
        if(!timeOut && this.frightenedIndex > (this.FRIGHTENED_SIZE/2)-1){
            this.frightenedIndex = (this.FRIGHTENED_SIZE/2)-1;
            this.frightenedForward = false;
        }
        if(this.frightenedIndex < 0){
            this.frightenedIndex = 0;
            this.frightenedForward = true;
        }
        ImageIcon frame = frightened.get(this.frightenedIndex);
        frightenedDelay++;
        if(frightenedDelay == this.DELAY_LIMIT) {
            if(frightenedForward){
                frightenedIndex++;
            }else{
                frightenedIndex--;
            }
            frightenedDelay = 0;
        }
        // L'index è in grado di raggiungere la seconda metà solo se timeOut == true
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
