package model;

import constants.Constants;
import image.Image;
import image.ImageFactory;
import utility.CoordManager;
import utility.Coordinate;

import javax.swing.*;

import static utility.Direction.*;

public class Blinky extends Ghost{

    public Blinky(Pacman p){
        super(p);
        initialize();
    }

    private void initialize(){
        ImageIcon imageIcon = ImageFactory.createImage(Image.BLINKY);
        setImage(imageIcon.getImage());

        Coordinate start = CoordManager.getObjCoord('1');

        setX(start.getX());
        setY(start.getY());

        this.spawnPoint = new Coordinate(start.getX(), start.getY());

        dir = UP;
    }

    @Override
    public void setTarget() {
        this.target.setX(this.pacman.getX());
        this.target.setY(this.pacman.getY());
    }
}
