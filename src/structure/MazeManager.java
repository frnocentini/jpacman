package structure;

import constants.Constants;
import sprites.Direction;
import sprites.Portal;
import sprites.Sprite;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class MazeManager {

    private static Maze maze; // Oggetto che corrisponde alla matrice di char del Maze

    // Crea la matrice di char corrispondente al labirinto
    public static void createMaze(int mazeNum) {
        char[][] inputMaze = new char[21][19];
        try {
            // IDE
            FileReader lvlFile = new FileReader(new File(Constants.MAZES_DIR+"maze"+mazeNum+".txt"));
            BufferedReader br = new BufferedReader(lvlFile);
            // JAR
            //BufferedReader br = new BufferedReader(new InputStreamReader(MazeManager.class.getClassLoader().getResourceAsStream("resources/mazes/maze"+mazeNum+".txt")));
            int c,i,j;
            i = j = 0;
            while((c = br.read()) != -1){
                // converto l'integer in un char
                char character = (char) c;
                if(character == '\n'){
                    i++;
                    j = 0;
                }else if(character != '\r'){
                    // La newline è \n\r perciò controllo solo \n e ignoro \r
                    inputMaze[i][j] = character;
                    j++;
                }
            }
            MazeManager.maze = new Maze(inputMaze); // Crea l'attributo Maze
        } catch (FileNotFoundException e) {
            System.err.println("Il file del livello non è stato trovato");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Errore nella lettura dei file su disco");
            e.printStackTrace();
        }
    }

    public static void populateMaze() {
        MazeManager.maze.initializeMaze();
        Portal bluePortal = null;
        Portal redPortal = null;
        for (int i = 0; i< MazeManager.maze.getMazeHeight(); i++){
            for(int j = 0; j< MazeManager.maze.getMazeWidth(); j++){
                Coordinate co = MazeManager.convertCoords(j,i);
                switch(MazeManager.maze.getMazeValue(i,j)){
                    case 'O':
                        int x = co.getX()+Constants.BLOCK_DIM/2 - Constants.PILL_WIDTH/2;
                        int y = co.getY()+Constants.BLOCK_DIM/2 - Constants.PILL_HEIGHT/2;
                        MazeManager.maze.addPill(x,y);
                        break;
                    case 'P':
                        MazeManager.maze.addPowerPill(co.getX(),co.getY());
                        break;
                    case 'B':
                        bluePortal = new Portal(co.getX(),co.getY(), 20, 20, "BLUE");
                        break;
                    case 'R':
                        redPortal = new Portal(co.getX(),co.getY(), 20, 20, "RED");
                        break;
                }
            }
        }
        bluePortal.setOther(redPortal);
        redPortal.setOther(bluePortal);
        MazeManager.maze.setBluePortal(bluePortal);
        MazeManager.maze.setRedPortal(redPortal);
    }

    public static Coordinate getObjCoord(char c){
        Coordinate co = new Coordinate(-1,-1);
        for (int i = 0; i< MazeManager.maze.getMazeHeight(); i++){
            for(int j = 0; j< MazeManager.maze.getMazeWidth(); j++){
                if(MazeManager.maze.getMazeValue(i,j) == c){
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
        return MazeManager.maze.getMazeValue(y / Constants.BLOCK_DIM,x / Constants.BLOCK_DIM);
    }

    public static boolean canIMove(int x, int y){
        return canIMove(new Coordinate(x,y));
    }

    public static boolean canIMove(Coordinate co){
        if((co.getX() % Constants.BLOCK_DIM) == 0 && (co.getY() % Constants.BLOCK_DIM) == 0){
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
        Rectangle2D ar = new Rectangle2D.Double(a.getX()+a.getW()/4, a.getY()+a.getH()/4, 3 * a.getW() / 4, 3 * a.getH() / 4);
        Rectangle2D br = new Rectangle2D.Double(b.getX()+a.getW()/4, b.getY()+a.getH()/4, 3 * b.getW() / 4, 3 * b.getH() / 4);
        if(ar.intersects(br)){
            return true;
        }
        return false;
    }

    public static boolean checkCircleCollision(Sprite a, Coordinate co, double length){
        Rectangle2D ar = new Rectangle2D.Double(a.getX()+a.getW()/4, a.getY()+a.getH()/4, 3 * a.getW() / 4, 3 * a.getH() / 4);
        Ellipse2D e = new Ellipse2D.Double(co.getX(),co.getY(),length,length);
        if(e.intersects(ar)){
            return true;
        }
        return false;
    }

    public static boolean checkCircleCollision(Sprite a, int x, int y, double length){
        return checkCircleCollision(a,new Coordinate(x,y),length);
    }

    public static Maze getMaze() {
        return maze;
    }
}