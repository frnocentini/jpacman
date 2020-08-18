package sprites;

import constants.Constants;
import frameManagers.GhostFrameManager;
import image.Image;
import image.ImageFactory;
import structure.MazeManager;
import structure.Coordinate;

import javax.swing.*;

import java.util.ArrayList;

import static sprites.Direction.*;

public class Blinky extends Ghost{

    public Blinky(Pacman p){
        super(p);
        initialize();
    }

    private void initialize(){
        addFrameManager();

        dir = UP;
        ImageIcon imageIcon = this.frameManager.getNextFrame(dir);
        setImage(imageIcon.getImage());

        Coordinate start = MazeManager.getObjCoord('1');

        setX(start.getX());
        setY(start.getY());

        this.spawnPoint = new Coordinate(start.getX(), start.getY());
        this.scatterTarget = new Coordinate(Constants.BOARD_WIDTH,0);

    }

    @Override
    public void addFrameManager() {
        ArrayList<ImageIcon> up = new ArrayList<>();
        ArrayList<ImageIcon> down = new ArrayList<>();
        ArrayList<ImageIcon> left = new ArrayList<>();
        ArrayList<ImageIcon> right = new ArrayList<>();
        ArrayList<ImageIcon> frightened = new ArrayList<>();
        up.add(ImageFactory.createImage(Image.BLINKY_U0));
        up.add(ImageFactory.createImage(Image.BLINKY_U1));
        down.add(ImageFactory.createImage(Image.BLINKY_D0));
        down.add(ImageFactory.createImage(Image.BLINKY_D1));
        left.add(ImageFactory.createImage(Image.BLINKY_L0));
        left.add(ImageFactory.createImage(Image.BLINKY_L1));
        right.add(ImageFactory.createImage(Image.BLINKY_R0));
        right.add(ImageFactory.createImage(Image.BLINKY_R1));
        frightened.add(ImageFactory.createImage(Image.FRIGHTENED0));
        frightened.add(ImageFactory.createImage(Image.FRIGHTENED1));
        frightened.add(ImageFactory.createImage(Image.FRIGHTENED0));
        frightened.add(ImageFactory.createImage(Image.FRIGHTENED1));
        frightened.add(ImageFactory.createImage(Image.FRIGHTENED2));
        frightened.add(ImageFactory.createImage(Image.FRIGHTENED3));
        frightened.add(ImageFactory.createImage(Image.FRIGHTENED2));
        frightened.add(ImageFactory.createImage(Image.FRIGHTENED3));
        ImageIcon eatenUp = ImageFactory.createImage(Image.EATEN_U);
        ImageIcon eatenDown = ImageFactory.createImage(Image.EATEN_D);
        ImageIcon eatenLeft = ImageFactory.createImage(Image.EATEN_L);
        ImageIcon eatenRight = ImageFactory.createImage(Image.EATEN_R);
        this.frameManager = new GhostFrameManager(up,down,left,right,4,frightened,eatenUp,eatenDown,eatenLeft,eatenRight,4);
    }

    @Override
    public void setChaseTarget() {
        this.target.setX(this.pacman.getX());
        this.target.setY(this.pacman.getY());
    }

}
