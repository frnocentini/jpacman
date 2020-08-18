package loops;

import constants.Constants;
import sprites.Ghost;
import structure.MazeManager;
import structure.Coordinate;
import sprites.State;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static sprites.State.*;

public class GhostLoop implements ActionListener {

    private Ghost ghost;
    private long[] times;
    private int counter;
    private long startTime;
    private long timeLost;
    private State backupState;

    public GhostLoop(Ghost ghost, int level){
        this.ghost = ghost;
        this.times = new long[7];
        if(level == 1){
            System.out.println("Primo liv.");
            for(int i=0;i<7;i++){
                this.times[i] = Constants.FIRST_TIMES[i];
            }
        } else if (level < 5){
            System.out.println("secondo liv.");
            for(int i=0;i<7;i++){
                this.times[i] = Constants.SECOND_TIMES[i];
            }
        } else {
            for(int i=0;i<7;i++){
                System.out.println("quinto liv.");
                this.times[i] = Constants.FIFTH_TIMES[i];
            }
        }
        this.ghost.setState(SCATTER);
        this.backupState = SCATTER;
        this.counter = -1;
        this.timeLost = 0;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        if(ghost.getState() == SCATTER || ghost.getState() == CHASE) {
            if (counter == -1) {
                counter++;
                this.startTime = System.currentTimeMillis();
            } else if (counter < 7) {
                if (System.currentTimeMillis() >= startTime + times[counter] + this.ghost.getPausedTime()) {
                    this.ghost.setPausedTime(0);
                    counter++;
                    this.startTime = System.currentTimeMillis();
                    if (this.ghost.getState() == CHASE) {
                        System.out.println("Passo a scatter");
                        this.ghost.setState(SCATTER);
                        this.backupState = SCATTER;
                    } else {
                        System.out.println("Passo a chase");
                        this.ghost.setState(CHASE);
                        this.backupState = CHASE;
                    }
                }
            }
        } else if(this.ghost.getState() == FRIGHTENED){
            long temp = System.currentTimeMillis()-ghost.getFrightTime();
            this.startTime += temp - this.timeLost;
            this.timeLost = temp;
            //System.out.println("l'orario è "+System.currentTimeMillis()+" deve essere maggiore di "+(ghost.getFrightTime()+8000)+" + "+this.ghost.getPausedTime());
            if(System.currentTimeMillis() >= ghost.getFrightTime()+8000  + this.ghost.getPausedTime()){
                this.ghost.setPausedTime(0);
                System.out.println("Esco da frightened con backupState: "+this.backupState);
                this.ghost.setState(this.backupState);
            }
        } else if (this.ghost.getState() == EATEN){
            Coordinate sp = MazeManager.getObjCoord('1');
            Coordinate co = new Coordinate(this.ghost.getX(),this.ghost.getY());
            if(sp.equals(co)){
                this.ghost.setState(this.backupState);
            }
        }
    }

    public void resetTimeLost(){
        this.timeLost = 0;
    }
}