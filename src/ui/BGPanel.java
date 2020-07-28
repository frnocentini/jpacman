package ui;

import callbacks.GameEventListener;
import constants.Constants;
import image.Image;
import image.ImageFactory;
import model.Pacman;
import utility.CoordManager;
import utility.Coordinate;

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
        for (int i=0;i<CoordManager.maze.getMazeHeight();i++){
            for(int j=0;j<CoordManager.maze.getMazeWidth();j++){
                JLabel label = new JLabel();
                this.add(label);
                if(CoordManager.maze.getMazeValue(i,j)=='W') {
                    label.setIcon(wall);
                }else{
                    label.setIcon(empty);
                }
                Coordinate co = CoordManager.convertCoords(j+1,i+1);
                switch(CoordManager.maze.getMazeValue(i,j)){
                    case 'O':
                        int x = co.getX()-Constants.BLOCK_DIM/2 - Constants.PILL_WIDTH/2;
                        int y = co.getY()-Constants.BLOCK_DIM/2 - Constants.PILL_HEIGHT/2;
                        CoordManager.maze.addPill(x,y);
                        break;
                    case 'P':
                        CoordManager.maze.addPill(co.getX(),co.getY());
                        break;
                }
            }
        }
    }


    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
    }
}
