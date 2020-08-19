package sprites;

import constants.Constants;
import frameManagers.GhostFrameManager;
import image.ImageList;
import image.ImageFactory;
import structure.MazeManager;
import structure.Coordinate;

import javax.swing.*;
import java.util.ArrayList;

import static sprites.Direction.RIGHT;

public class Clyde extends Ghost{

    public Clyde(Pacman p){
        super(p);
        initialize();
    }

    private void initialize(){
        addFrameManager();

        dir = RIGHT;
        ImageIcon imageIcon = this.frameManager.getNextFrame(dir);
        setImage(imageIcon.getImage());

        Coordinate start = MazeManager.getObjCoord('4');

        setX(start.getX());
        setY(start.getY());

        this.spawnPoint = new Coordinate(start.getX(), start.getY());
        this.scatterTarget = new Coordinate(0,Constants.BOARD_HEIGHT);
    }

    @Override
    public void addFrameManager() {
        ArrayList<ImageIcon> up = new ArrayList<>();
        ArrayList<ImageIcon> down = new ArrayList<>();
        ArrayList<ImageIcon> left = new ArrayList<>();
        ArrayList<ImageIcon> right = new ArrayList<>();
        ArrayList<ImageIcon> frightened = new ArrayList<>();
        up.add(ImageFactory.createImage(ImageList.CLYDE_U0));
        up.add(ImageFactory.createImage(ImageList.CLYDE_U1));
        down.add(ImageFactory.createImage(ImageList.CLYDE_D0));
        down.add(ImageFactory.createImage(ImageList.CLYDE_D1));
        left.add(ImageFactory.createImage(ImageList.CLYDE_L0));
        left.add(ImageFactory.createImage(ImageList.CLYDE_L1));
        right.add(ImageFactory.createImage(ImageList.CLYDE_R0));
        right.add(ImageFactory.createImage(ImageList.CLYDE_R1));
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
        if(MazeManager.checkCircleCollision(this,this.pacman.getX(),this.pacman.getY(),8*Constants.BLOCK_DIM)){
            this.target.setX(this.scatterTarget.getX());
            this.target.setY(this.scatterTarget.getX());
        }else{
            this.target.setX(this.pacman.getX());
            this.target.setY(this.pacman.getY());
        }
    }
}
