package frameManagers;

import sprites.Direction;

import javax.swing.ImageIcon;
import java.util.ArrayList;

public class FrameManager {

    protected ArrayList<ImageIcon> up;      // Frame dell'animazione rivolta verso l'alto
    protected ArrayList<ImageIcon> down;    // il basso
    protected ArrayList<ImageIcon> left;    // la sinistra
    protected ArrayList<ImageIcon> right;   // la destra
    protected int index;                    // Indice che permette di capire qual è il prossimo frame da restituire
    protected final int SIZE;               // Dimensione degli ArrayList
    protected boolean forward;              // Booleana che indica se eseguire l'animazione al dritto o al rovescio
    protected int delay;                    // Contatore che implementa un ritardo nell'incrmento o
                                            // il decremento dell'index
    protected final int DELAY_LIMIT;        // Massimo ritardo

    public FrameManager(ArrayList<ImageIcon> up, ArrayList<ImageIcon> down, ArrayList<ImageIcon> left, ArrayList<ImageIcon> right, int index, int delayLimit) {
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
        this.SIZE = up.size();
        this.index = index;
        this.DELAY_LIMIT = delayLimit;
        this.delay = 0;
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
        delay++;
        if(delay == this.DELAY_LIMIT) {
            if(forward){
                index++;
            }else{
                index--;
            }
            delay = 0;
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
