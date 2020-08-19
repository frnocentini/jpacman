package sprites;

import constants.Constants;
import frameManagers.GhostFrameManager;
import image.ImageList;
import image.ImageFactory;
import structure.MazeManager;
import structure.Coordinate;

import javax.swing.*;
import java.util.ArrayList;

import static sprites.Direction.LEFT;

public class Inky extends Ghost{

    private Blinky blinky;

    public Inky(Pacman p, Blinky blinky){
        super(p);
        this.blinky = blinky;
        initialize();
    }

    private void initialize(){
        addFrameManager();

        dir = LEFT;
        ImageIcon imageIcon = this.frameManager.getNextFrame(dir);
        setImage(imageIcon.getImage());

        Coordinate start = MazeManager.getObjCoord('3');

        setX(start.getX());
        setY(start.getY());

        this.spawnPoint = new Coordinate(start.getX(), start.getY());
        this.scatterTarget = new Coordinate(Constants.BOARD_WIDTH,Constants.BOARD_HEIGHT);
    }

    @Override
    public void addFrameManager() {
        ArrayList<ImageIcon> up = new ArrayList<>();
        ArrayList<ImageIcon> down = new ArrayList<>();
        ArrayList<ImageIcon> left = new ArrayList<>();
        ArrayList<ImageIcon> right = new ArrayList<>();
        ArrayList<ImageIcon> frightened = new ArrayList<>();
        up.add(ImageFactory.createImage(ImageList.INKY_U0));
        up.add(ImageFactory.createImage(ImageList.INKY_U1));
        down.add(ImageFactory.createImage(ImageList.INKY_D0));
        down.add(ImageFactory.createImage(ImageList.INKY_D1));
        left.add(ImageFactory.createImage(ImageList.INKY_L0));
        left.add(ImageFactory.createImage(ImageList.INKY_L1));
        right.add(ImageFactory.createImage(ImageList.INKY_R0));
        right.add(ImageFactory.createImage(ImageList.INKY_R1));
        frightened.add(ImageFactory.createImage(ImageList.FRIGHTENED0));
        frightened.add(ImageFactory.createImage(ImageList.FRIGHTENED1));
        frightened.add(ImageFactory.createImage(ImageList.FRIGHTENED2));
        frightened.add(ImageFactory.createImage(ImageList.FRIGHTENED3));
        ImageIcon eatenUp = ImageFactory.createImage(ImageList.EATEN_U);
        ImageIcon eatenDown = ImageFactory.createImage(ImageList.EATEN_D);
        ImageIcon eatenLeft = ImageFactory.createImage(ImageList.EATEN_L);
        ImageIcon eatenRight = ImageFactory.createImage(ImageList.EATEN_R);
        this.frameManager = new GhostFrameManager(up,down,left,right,4,frightened,eatenUp,eatenDown,eatenLeft,eatenRight,8);
    }

    @Override
    public void setChaseTarget() {
        int xRIF = this.pacman.getX();
        int yRIF = this.pacman.getY();
        switch(this.pacman.getDir()){
            case UP:
                yRIF -= 2*Constants.BLOCK_DIM;
                break;
            case DOWN:
                yRIF += 2*Constants.BLOCK_DIM;
                break;
            case LEFT:
                xRIF -= 2*Constants.BLOCK_DIM;
                break;
            case RIGHT:
                xRIF += 2*Constants.BLOCK_DIM;
                break;
        }
        if(xRIF < 0){
            xRIF = 0;
        }
        if(yRIF < 0){
            yRIF = 0;
        }
        if(xRIF > Constants.BOARD_WIDTH){
            xRIF = Constants.BOARD_WIDTH;
        }
        if(yRIF > Constants.BOARD_HEIGHT){
            yRIF = Constants.BOARD_HEIGHT;
        }

        int xTemp = this.blinky.getX() - xRIF;
        int yTemp = this.blinky.getY() - yRIF;

        xTemp *= -1;
        yTemp *= -1;

        int x = xTemp + xRIF;
        int y = yTemp + yRIF;

        if(x < 0){
            x = 0;
        }
        if(y < 0){
            y = 0;
        }
        if(x > Constants.BOARD_WIDTH){
            x = Constants.BOARD_WIDTH;
        }
        if(y > Constants.BOARD_HEIGHT){
            y = Constants.BOARD_HEIGHT;
        }

        this.target.setX(x);
        this.target.setY(y);
    }
}
