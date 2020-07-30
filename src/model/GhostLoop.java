package model;

import constants.Constants;
import ui.GameMainFrame;
import utility.State;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static utility.State.*;

public class GhostLoop implements ActionListener {

    private Ghost ghost;
    private long[] times;
    private int counter;
    private long startTime;
    private long timeLost;
    private State backupState;

    public GhostLoop(Ghost ghost){
        this.ghost = ghost;
        this.times = new long[7];
        if(GameMainFrame.level == 1){
            for(int i=0;i<7;i++){
                this.times[i] = Constants.FIRST_TIMES[i];
            }
        } else if (GameMainFrame.level < 5){
            for(int i=0;i<7;i++){
                this.times[i] = Constants.SECOND_TIMES[i];
            }
        } else {
            for(int i=0;i<7;i++){
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
                if (System.currentTimeMillis() >= startTime + times[counter]) {
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
            ghost.setFrightenedImage();
            long temp = System.currentTimeMillis()-ghost.getFrightTime();
            this.startTime += temp - this.timeLost;
            this.timeLost = temp;
            System.out.println(temp);
            if(System.currentTimeMillis() >= ghost.getFrightTime()+8000){
                System.out.println("Esco da frightened con backupState: "+this.backupState);
                this.ghost.setState(this.backupState);
                ghost.resetImage();
            }
        }
    }

    public void resetTimeLost(){
        this.timeLost = 0;
    }
}