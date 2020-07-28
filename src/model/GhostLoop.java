package model;

import constants.Constants;
import ui.GameMainFrame;
import utility.State;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static utility.State.CHASE;
import static utility.State.SCATTER;

public class GhostLoop implements ActionListener {

    private Ghost ghost;
    private long[] times;
    private int counter;
    private long startTime;

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
        this.ghost.setState(CHASE);
        this.counter = -1;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        if(counter == -1){
            counter++;
            this.startTime = System.currentTimeMillis();
        } else if(counter < 7) {
            if (System.currentTimeMillis() >= startTime+times[counter]) {
                counter++;
                this.startTime = System.currentTimeMillis();
                if (this.ghost.getState() == CHASE){
                    System.out.println("Passo a scatter");
                    this.ghost.setState(SCATTER);
                } else {
                    System.out.println("Passo a chase");
                    this.ghost.setState(CHASE);
                }
            }
        } else if (counter == 7){
            System.out.println("Passo a chase");
            this.ghost.setState(CHASE);
            counter++;
        }
    }
}