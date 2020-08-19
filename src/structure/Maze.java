package structure;

import constants.Constants;
import image.ImageList;
import image.ImageFactory;
import sprites.Fruit;
import sprites.Pill;
import sprites.Portal;
import sprites.PowerPill;

import javax.swing.*;
import java.util.ArrayList;

public class Maze {

    private char[][] maze;                          // Matrice di char che rappresenta il labirinto
    private ArrayList<Pill> pills;                  // ArrayList che contiene gli oggetti pillole
    private ArrayList<PowerPill> powerPills;        // ArrayList che contiene gli oggetti powerpill
    private int alivePills;                         // Numero delle pillole rimaste da mangiare
    private int alivePowerPills;                    // Numero delle powerpill rimaste da mangiare
    private Portal bluePortal;                      // Oggetto portale blu
    private Portal redPortal;                       // Oggetto Portale rooso
    private ArrayList<ImageIcon> fruitList;         // ArrayList di immagini della frutta (una per livello)
    private Fruit fruit;                            // Oggetto frutto
    private long gameStart;                         // Orario di inizio movimento dei personaggi
    private long pausedTime;                        // Tempo passato mentre il gioco era in pausa
    private long pauseClock;                        // Istante in cui il gioco è stato messo in pausa l'ultima volta

    public Maze(char[][] maze) {
        this.maze = new char[21][19];
        for (int i = 0; i< maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                this.maze[i][j] = maze[i][j];
            }
        }
        fruitList = new ArrayList<>();
        fruitList.add(ImageFactory.createImage(ImageList.FRUIT0));
        fruitList.add(ImageFactory.createImage(ImageList.FRUIT1));
        fruitList.add(ImageFactory.createImage(ImageList.FRUIT2));
        fruitList.add(ImageFactory.createImage(ImageList.FRUIT3));
        fruitList.add(ImageFactory.createImage(ImageList.FRUIT4));
        fruitList.add(ImageFactory.createImage(ImageList.FRUIT5));
        fruitList.add(ImageFactory.createImage(ImageList.FRUIT6));
        fruitList.add(ImageFactory.createImage(ImageList.FRUIT7));
    }

    // Riporta tutte le Pill e le PowerPill a zero
    public void initializeMaze(){
        this.alivePills=0;
        this.alivePowerPills=0;
        this.pills = new ArrayList<>();
        this.powerPills = new ArrayList<>();
    }

    // A seconda del livello crea il frutto da far apparire
    public void chooseFruit(int level){
        int i = ((level-1) % fruitList.size());
        this.fruit = new Fruit(Constants.FRUIT_POINTS[i],fruitList.get(i));
    }

    // Avvia il timer per far apparire e sparire la frutta
    public void setGameStart(){
        this.gameStart = System.currentTimeMillis();
        this.pausedTime = 0;
    }

    public void pause() {
        this.pauseClock = System.currentTimeMillis();
    }

    public void resume() {
        this.pausedTime += System.currentTimeMillis()-this.pauseClock;
    }

    // Restituisce il frutto da stampare al GameLogic se il tempo è compreso tra gli intervalli e
    // non è già stato mangiato
    public Fruit getFruit(){
        if(System.currentTimeMillis() >= gameStart + 15000 + this.pausedTime && System.currentTimeMillis() <= gameStart + 21000 + this.pausedTime && !fruit.isDead()){
            return fruit;
        }
        return null;
    }

    // Aggiunge una Pill al labirinto
    public void addPill(int x, int y){
        pills.add(new Pill(x,y, Constants.PILL_WIDTH, Constants.PILL_HEIGHT ,Constants.PILL_POINTS));
        alivePills++;
    }

    public Pill getPill(int i){
        return pills.get(i);
    }

    public void removeAlivePill(){
        alivePills--;
    }

    public int getPillsNum(){
        return pills.size();
    }

    public int getAlivePills() {
        return alivePills;
    }

    public void setAlivePills(int alivePills) {
        this.alivePills = alivePills;
    }

    // Aggiunge una PowerPill al labirinto
    public void addPowerPill(int x, int y){
        powerPills.add(new PowerPill(x,y, Constants.POWERPILL_WIDTH, Constants.POWERPILL_HEIGHT ,Constants.POWERPILL_POINTS));
        alivePowerPills++;
    }

    public PowerPill getPowerPill(int i){
        return powerPills.get(i);
    }

    public void removeAlivePowerPill(){
        alivePowerPills--;
    }

    public int getPowerPillsNum(){
        return powerPills.size();
    }

    public int getAlivePowerPills() {
        return alivePowerPills;
    }

    public void setAlivePowerPills(int alivePowerPills) {
        this.alivePowerPills = alivePowerPills;
    }

    public char getMazeValue(int i, int j) {
        return maze[i][j];
    }

    public int getMazeWidth() {
        return maze[0].length;
    }

    public int getMazeHeight() {
        return maze.length;
    }

    public Portal getBluePortal() {
        return bluePortal;
    }

    public void setBluePortal(Portal bluePortal) {
        this.bluePortal = bluePortal;
    }

    public Portal getRedPortal() {
        return redPortal;
    }

    public void setRedPortal(Portal redPortal) {
        this.redPortal = redPortal;
    }
}
