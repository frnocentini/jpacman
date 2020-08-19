package sprites;

import constants.Constants;
import frameManagers.GhostFrameManager;
import image.ImageList;
import image.ImageFactory;
import structure.MazeManager;
import structure.Coordinate;

import javax.swing.*;

import java.util.ArrayList;

import static sprites.Direction.*;

public class Pinky extends Ghost{

    public Pinky(Pacman p){
        super(p);
        initialize();
    }

    private void initialize(){
        addFrameManager();

        dir = DOWN;
        ImageIcon imageIcon = this.frameManager.getNextFrame(dir);
        setImage(imageIcon.getImage());

        Coordinate start = MazeManager.getObjCoord('2');

        setX(start.getX());
        setY(start.getY());

        this.spawnPoint = new Coordinate(start.getX(), start.getY());
        this.scatterTarget = new Coordinate(0,0);
    }

    @Override
    public void addFrameManager() {
        ArrayList<ImageIcon> up = new ArrayList<>();
        ArrayList<ImageIcon> down = new ArrayList<>();
        ArrayList<ImageIcon> left = new ArrayList<>();
        ArrayList<ImageIcon> right = new ArrayList<>();
        ArrayList<ImageIcon> frightened = new ArrayList<>();
        up.add(ImageFactory.createImage(ImageList.PINKY_U0));
        up.add(ImageFactory.createImage(ImageList.PINKY_U1));
        down.add(ImageFactory.createImage(ImageList.PINKY_D0));
        down.add(ImageFactory.createImage(ImageList.PINKY_D1));
        left.add(ImageFactory.createImage(ImageList.PINKY_L0));
        left.add(ImageFactory.createImage(ImageList.PINKY_L1));
        right.add(ImageFactory.createImage(ImageList.PINKY_R0));
        right.add(ImageFactory.createImage(ImageList.PINKY_R1));
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
        int x = this.pacman.getX();
        int y = this.pacman.getY();
        switch(this.pacman.getDir()){
            case UP:
                y -= 4*Constants.BLOCK_DIM;
                break;
            case DOWN:
                y += 4*Constants.BLOCK_DIM;
                break;
            case LEFT:
                x -= 4*Constants.BLOCK_DIM;
                break;
            case RIGHT:
                x += 4*Constants.BLOCK_DIM;
                break;
        }
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
