package ui;

import callbacks.GameEventListener;
import constants.Constants;
import image.Image;
import image.ImageFactory;
import model.Pacman;
import utility.CoordManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class BGPanel extends JPanel {

    public BGPanel(GridLayout gridLayout, int level){
        super(gridLayout);
        initializeLayout();
        initializeMaze(level);
    }

    private void initializeLayout() {
        setFocusable(true);
        setPreferredSize(new Dimension(Constants.BOARD_WIDTH * Constants.SCALE, Constants.BOARD_HEIGHT * Constants.SCALE));
    }

    private void initializeMaze(int level) {
        CoordManager.createMaze(level);
        ImageIcon empty = ImageFactory.createImage(Image.EMPTY);
        ImageIcon wall = ImageFactory.createImage(Image.WALL);
        for (int i=0;i<CoordManager.maze.length;i++){
            for(int j=0;j<CoordManager.maze[0].length;j++){
                JLabel label = new JLabel();
                this.add(label);
                if(CoordManager.maze[i][j]=='W') {
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
