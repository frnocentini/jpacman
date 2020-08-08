package model;

import constants.Constants;
import image.Image;
import image.ImageFactory;
import utility.CoordManager;
import utility.Coordinate;

import javax.swing.*;

import static utility.Direction.*;

public class Pinky extends Ghost{

    public Pinky(Pacman p){
        super(p);
        initialize();
    }

    private void initialize(){
        resetImage();

        Coordinate start = CoordManager.getObjCoord('2');

        setX(start.getX());
        setY(start.getY());

        this.spawnPoint = new Coordinate(start.getX(), start.getY());
        this.scatterTarget = new Coordinate(0,0);

        dir = DOWN;
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

    @Override
    public void resetImage() {
        ImageIcon imageIcon = ImageFactory.createImage(Image.PINKY);
        setImage(imageIcon.getImage());
    }

    @Override
    public void addImageSet() {

    }
}
