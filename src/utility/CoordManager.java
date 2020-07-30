package utility;

import constants.Constants;
import model.Maze;
import model.Pacman;
import model.Pill;
import model.Sprite;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.*;

public class CoordManager {

    public static Maze maze;

    public static void createMaze(int level) {
        char[][] inputMaze = new char[21][19];
        try {
            FileReader lvlFile = new FileReader(new File("mazes/maze"+level+".txt"));
            BufferedReader br = new BufferedReader(lvlFile);  //Creation of BufferedReader object
            int c,i,j;
            c = i = j = 0;
            while((c = br.read()) != -1){
                char character = (char) c;          //converting integer to char
                if(character == '\n'){
                    i++;
                    j = 0;
                }else{
                    if(character != '\r'){
                        inputMaze[i][j] = character;
                        j++;
                    }
                }
            }
            CoordManager.maze = new Maze(inputMaze);
        } catch (FileNotFoundException e) {
            System.err.println("Il file del livello non è stato trovato");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Errore nella lettura dei file su disco");
            e.printStackTrace();
        }
    }

    public static Coordinate getObjCoord(char c){
        Coordinate co = new Coordinate(-1,-1);
        for (int i=0;i<CoordManager.maze.getMazeHeight();i++){
            for(int j=0;j<CoordManager.maze.getMazeWidth();j++){
                if(CoordManager.maze.getMazeValue(i,j) == c){
                    co = convertCoords(j,i);
                    return co;
                }
            }
        }
        return co;
    }

    public static Coordinate convertCoords(int x, int y) {
        return new Coordinate(x * Constants.BLOCK_DIM,y * Constants.BLOCK_DIM);
    }

    public static char whichBlock(int x, int y) {
        //System.out.println(x+" "+y);
        return CoordManager.maze.getMazeValue(y / Constants.BLOCK_DIM,x / Constants.BLOCK_DIM);
    }

    public static boolean canIMove(int x, int y){
        return canIMove(new Coordinate(x,y));
    }

    public static boolean canIMove(Coordinate co){
        if((co.getX() % 20) == 0 && (co.getY() % 20) == 0){
            return true;
        }
        return false;
    }

    public static boolean checkEmpty(int x, int y, Direction d){
        return checkEmpty(new Coordinate(x,y),d);
    }

    public static boolean checkEmpty(Coordinate co, Direction d){
        //System.out.println(d);
        switch(d){
            case UP:
                if(whichBlock(co.getX(),co.getY()-1) != 'W')
                    return true;
                break;
            case DOWN:
                if(whichBlock(co.getX(),co.getY()+Constants.BLOCK_DIM) != 'W')
                    return true;
                break;
            case LEFT:
                if(whichBlock(co.getX()-1,co.getY()) != 'W')
                    return true;
                break;
            case RIGHT:
                if(whichBlock(co.getX()+Constants.BLOCK_DIM,co.getY()) != 'W')
                    return true;
                break;
        }
        return false;
    }

    public static boolean checkCollision(Sprite a, Sprite b) {
        // Find the bounds of the rectangle intersection
        Rectangle2D ar = new Rectangle2D.Double(a.getX(), a.getY(), a.getW(), a.getH());
        Rectangle2D br = new Rectangle2D.Double(b.getX(), b.getY(), b.getW(), b.getH());
        if(ar.intersects(br)){
            return true;
        }
        return false;
    }
}