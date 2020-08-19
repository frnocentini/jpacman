package ui;

import constants.Constants;
import image.ImageList;
import image.ImageFactory;
import structure.MazeManager;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;

public class BGPanel extends JPanel {

    public BGPanel(GridLayout gridLayout, int mazeNum){
        // Uno dei costruttori di JPanel accetta un oggetto Layout
        super(gridLayout);
        setPreferredSize(new Dimension(Constants.BOARD_WIDTH * Constants.SCALE, Constants.BOARD_HEIGHT * Constants.SCALE));
        initializeMaze(mazeNum);
    }

    public void initializeMaze(int mazeNum) {
        // Richiamiamo il metodo statico che trasforma il file del labirinto in un labirinto nel nostro gioco
        char[][] maze = MazeManager.createMaze(mazeNum);
        // Creiamo le ImageIcon del pavimento e del muro
        ImageIcon empty = ImageFactory.createImage(ImageList.EMPTY);
        ImageIcon wall = ImageFactory.createImage(ImageList.WALL);
        // Creo per ogni cella della matrice una JLabel e inseirsco un muro o un pavimento
        for (int i = 0; i< maze.length; i++){
            for(int j = 0; j< maze[0].length; j++){
                JLabel label = new JLabel();
                this.add(label);
                if(maze[i][j] == 'W') {
                    label.setIcon(wall);
                }else{
                    label.setIcon(empty);
                }
            }
        }
    }


    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
    }
}
