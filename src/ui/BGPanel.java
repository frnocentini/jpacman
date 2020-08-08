package ui;

import callbacks.GameEventListener;
import constants.Constants;
import image.Image;
import image.ImageFactory;
import model.Pacman;
import model.Portal;
import utility.CoordManager;
import utility.Coordinate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class BGPanel extends JPanel {

    public BGPanel(GridLayout gridLayout, int mazeNum){
        super(gridLayout);
        initializeLayout();
        initializeMaze(mazeNum);
    }

    private void initializeLayout() {
        setFocusable(true);
        setPreferredSize(new Dimension(Constants.BOARD_WIDTH * Constants.SCALE, Constants.BOARD_HEIGHT * Constants.SCALE));
    }

    private void initializeMaze(int mazeNum) {
        CoordManager.createMaze(mazeNum);
        ImageIcon empty = ImageFactory.createImage(Image.EMPTY);
        ImageIcon wall = ImageFactory.createImage(Image.WALL);
        for (int i=0;i<CoordManager.maze.getMazeHeight();i++){
            for(int j=0;j<CoordManager.maze.getMazeWidth();j++){
                JLabel label = new JLabel();
                this.add(label);
                if(CoordManager.maze.getMazeValue(i,j)=='W') {
                    label.setIcon(wall);
                }else{
                    label.setIcon(empty);
                }
            }
        }
    }


    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
    }
}
