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

    private Ghost ghost;        // Riferimento al fantasma
    private long[] times;       // Array dei tempi di Chase / Scatter
    private int counter;        // Indice progressivo dell'array qui sopra
    private long startTime;     // Ultimo istante in cui è cambiato lo stato da Chase o Scatter
    private long timeLost;      // Tempo passato in stato di Frightened / Eaten
    private State backupState;  // Ultimo stato prima di entrare in Frightened

    public GhostLoop(Ghost ghost, int level){
        this.ghost = ghost;
        this.times = new long[7];
        // A seconda del livello prendiamo l'arry dei tempi corretto
        if(level == 1){
            //System.out.println("Primo liv.");
            for(int i=0;i<7;i++){
                this.times[i] = Constants.FIRST_TIMES[i];
            }
        } else if (level < 5){
            //System.out.println("secondo liv.");
            for(int i=0;i<7;i++){
                this.times[i] = Constants.SECOND_TIMES[i];
            }
        } else {
            for(int i=0;i<7;i++){
                //System.out.println("quinto liv.");
                this.times[i] = Constants.FIFTH_TIMES[i];
            }
        }
        // Impostiamo il primo stato come scatter
        this.ghost.setState(SCATTER);
        this.backupState = SCATTER;
        this.counter = -1;
        this.timeLost = 0;
    }

    // Metodo chiamato per la prima volta quando avviamo il timer dopo che sono passati i primi 4 secondi
    @Override
    public void actionPerformed(ActionEvent arg0) {
        if(ghost.getState() == SCATTER || ghost.getState() == CHASE) {
            if (counter == -1) {
                counter++;
                // Inizia il conteggio del tempo passato in Scatter
                this.startTime = System.currentTimeMillis();
            } else if (counter < 7) {
                // Controlliamo che sia passato il tempo dello stato corrente, aggiungiamo inoltre
                // il tempo di pausa che, in caso, provvederemo ad azzerare subito
                if (System.currentTimeMillis() >= startTime + times[counter] + this.ghost.getPausedTime()) {
                    this.ghost.setPausedTime(0);
                    // Passiamo allo stato successivo
                    counter++;
                    this.startTime = System.currentTimeMillis();
                    if (this.ghost.getState() == CHASE) {
                        //System.out.println("Passo a scatter");
                        this.ghost.setState(SCATTER);
                        this.backupState = SCATTER;
                    } else {
                        //System.out.println("Passo a chase");
                        this.ghost.setState(CHASE);
                        this.backupState = CHASE;
                    }
                }
            }
        } else{
            // Aggiungiamo progressivamente allo startTime il tempo perso in Frightened o Eaten
            long temp = System.currentTimeMillis()-ghost.getFrightTime();
            // Tolgo il tempo che ho aggiunto al ciclo precedente per aggiungere quello appena calcolato
            this.startTime += temp - this.timeLost;
            this.timeLost = temp;
            if(this.ghost.getState() == FRIGHTENED){
                // Controllo se sono passati 8 secondi di gioco per uscire dallo stato di Frightened
                if(System.currentTimeMillis() >= ghost.getFrightTime()+8000  + this.ghost.getPausedTime()){
                    this.ghost.setPausedTime(0);
                    this.ghost.setState(this.backupState);
                }
            } else if (this.ghost.getState() == EATEN){
                // Controllo quando le coordinate del fantasma e quelle dello SpawnPoint di Blinky coincidono
                Coordinate sp = MazeManager.getObjCoord('1');
                Coordinate co = new Coordinate(this.ghost.getX(),this.ghost.getY());
                if(sp.equals(co)){
                    this.ghost.setPausedTime(0);
                    this.ghost.setState(this.backupState);
                }
            }
        }
    }

    public void resetTimeLost(){
        this.timeLost = 0;
    }
}