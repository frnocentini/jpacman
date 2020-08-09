package model;

import constants.Constants;
import image.GhostImageSet;
import image.Image;
import image.ImageFactory;
import utility.CoordManager;
import utility.Coordinate;

import javax.swing.*;

import java.util.ArrayList;

import static utility.Direction.*;

public class Pinky extends Ghost{

    public Pinky(Pacman p){
        super(p);
        initialize();
    }

    private void initialize(){
        addImageSet();

        dir = DOWN;
        ImageIcon imageIcon = this.imageSet.getNextFrame(dir);
        setImage(imageIcon.getImage());

        Coordinate start = CoordManager.getObjCoord('2');

        setX(start.getX());
        setY(start.getY());

        this.spawnPoint = new Coordinate(start.getX(), start.getY());
        this.scatterTarget = new Coordinate(0,0);
    }

    @Override
    public void addImageSet() {
        ArrayList<ImageIcon> up = new ArrayList<>();
        ArrayList<ImageIcon> down = new ArrayList<>();
        ArrayList<ImageIcon> left = new ArrayList<>();
        ArrayList<ImageIcon> right = new ArrayList<>();
        ArrayList<ImageIcon> frightened = new ArrayList<>();
        up.add(ImageFactory.createImage(Image.PINKY_U1));
        up.add(ImageFactory.createImage(Image.PINKY_U2));
        down.add(ImageFactory.createImage(Image.PINKY_D1));
        down.add(ImageFactory.createImage(Image.PINKY_D2));
        left.add(ImageFactory.createImage(Image.PINKY_L1));
        left.add(ImageFactory.createImage(Image.PINKY_L2));
        right.add(ImageFactory.createImage(Image.PINKY_R1));
        right.add(ImageFactory.createImage(Image.PINKY_R2));
        frightened.add(ImageFactory.createImage(Image.FRIGHTENED0));
        frightened.add(ImageFactory.createImage(Image.FRIGHTENED1));
        frightened.add(ImageFactory.createImage(Image.FRIGHTENED2));
        frightened.add(ImageFactory.createImage(Image.FRIGHTENED3));
        ImageIcon eatenUp = ImageFactory.createImage(Image.EATEN_U);
        ImageIcon eatenDown = ImageFactory.createImage(Image.EATEN_D);
        ImageIcon eatenLeft = ImageFactory.createImage(Image.EATEN_L);
        ImageIcon eatenRight = ImageFactory.createImage(Image.EATEN_R);
        this.imageSet = new GhostImageSet(up,down,left,right,frightened,eatenUp,eatenDown,eatenLeft,eatenRight);
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
