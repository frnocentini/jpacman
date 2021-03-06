package frameManagers;

import sprites.Pacman;

import javax.swing.ImageIcon;
import java.util.ArrayList;

public class PacmanFrameManager extends FrameManager {

    private Pacman pacman;                  // Riferimento a pacman, ci servirà per reimpostarlo come vivo una volta
                                            // finita l'animazione
    private ArrayList<ImageIcon> death;     // Frame dell'animazione della morte
    private int deathIndex;                 // Stessi attributi del FrameManager classico ma per la morte
    private int deathDelay;
    private final int DEATH_SIZE;
    private final int DEATH_DELAY_LIMIT;

    public PacmanFrameManager(ArrayList<ImageIcon> up, ArrayList<ImageIcon> down, ArrayList<ImageIcon> left, ArrayList<ImageIcon> right, int index, int delayLimit, Pacman pacman, ArrayList<ImageIcon> death, int deathDelayLimit) {
        super(up, down, left, right, index, delayLimit);
        this.pacman = pacman;
        this.death = death;
        this.deathIndex = 0;
        this.DEATH_SIZE = death.size();
        this.DEATH_DELAY_LIMIT = deathDelayLimit;
        this.deathDelay = 0;
    }

    // Restituisce i frame necessari per l'animazione della morte
    public ImageIcon getNextFrameDeath(){
        ImageIcon frame = this.death.get(deathIndex);
        deathDelay++;
        if(deathDelay == DEATH_DELAY_LIMIT){
            deathIndex++;
            deathDelay = 0;
        }
        // A fine animazione imposta il Pacman come vivo
        if(deathIndex == this.DEATH_SIZE){
            this.pacman.setDead(false);
            this.deathIndex = 0;
        }
        return frame;
    }
}
