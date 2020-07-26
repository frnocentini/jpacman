package model;

import constants.Constants;
import utility.Coordinate;

import java.util.ArrayList;

public class Maze {

    private char[][] maze;
    private ArrayList<Pill> pills;
    private int alivePills;

    public Maze(char[][] maze) {
        this.alivePills=0;
        this.pills = new ArrayList<>();
        this.maze = new char[21][19];
        for (int i = 0; i< maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                this.maze[i][j] = maze[i][j];
            }
        }
    }

    public void addPill(int x, int y){
        pills.add(new Pill(x,y, Constants.PILL_WIDTH, Constants.PILL_HEIGHT ,100,""));
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

    public char getMazeValue(int i, int j) {
        return maze[i][j];
    }

    public int getMazeWidth() {
        return maze[0].length;
    }

    public int getMazeHeight() {
        return maze.length;
    }

    public int getAlivePills() {
        return alivePills;
    }

    public void setAlivePills(int alivePills) {
        this.alivePills = alivePills;
    }
}
