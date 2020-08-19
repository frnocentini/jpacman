package structure;

import constants.Constants;
import image.Image;
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

    public Maze(char[][] maze) {
        this.maze = new char[21][19];
        for (int i = 0; i< maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                this.maze[i][j] = maze[i][j];
            }
        }
        fruitList = new ArrayList<>();
        fruitList.add(ImageFactory.createImage(Image.FRUIT0));
        fruitList.add(ImageFactory.createImage(Image.FRUIT1));
        fruitList.add(ImageFactory.createImage(Image.FRUIT2));
        fruitList.add(ImageFactory.createImage(Image.FRUIT3));
        fruitList.add(ImageFactory.createImage(Image.FRUIT4));
        fruitList.add(ImageFactory.createImage(Image.FRUIT5));
        fruitList.add(ImageFactory.createImage(Image.FRUIT6));
        fruitList.add(ImageFactory.createImage(Image.FRUIT7));
    }

    public void initializeMaze(){
        this.alivePills=0;
        this.alivePowerPills=0;
        this.pills = new ArrayList<>();
        this.powerPills = new ArrayList<>();
    }

    public void chooseFruit(int level){
        int i = ((level-1) % fruitList.size());
        fruit = new Fruit(Constants.FRUIT_POINTS[i],fruitList.get(i));
    }

    public void setGameStart(){
        gameStart = System.currentTimeMillis();
    }

    public Fruit getFruit(){
        if(System.currentTimeMillis() >= gameStart + 15000 && System.currentTimeMillis() <= gameStart + 21000 && !fruit.isDead()){
            return fruit;
        }
        return null;
    }

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
