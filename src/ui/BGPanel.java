package ui;

import constants.Constants;
import image.Image;
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

    private void initializeMaze(int mazeNum) {
        // Richiamiamo il metodo statico che trasforma il file del labirinto in un labirinto nel nostro gioco
        MazeManager.createMaze(mazeNum);
        // Creiamo le ImageIcon del pavimento e del muro
        ImageIcon empty = ImageFactory.createImage(Image.EMPTY);
        ImageIcon wall = ImageFactory.createImage(Image.WALL);
        // Creo per ogni cella della matrice una JLabel e inseirsco un muro o un pavimento
        for (int i = 0; i< MazeManager.getMaze().getMazeHeight(); i++){
            for(int j = 0; j< MazeManager.getMaze().getMazeWidth(); j++){
                JLabel label = new JLabel();
                this.add(label);
                if(MazeManager.getMaze().getMazeValue(i,j)=='W') {
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
