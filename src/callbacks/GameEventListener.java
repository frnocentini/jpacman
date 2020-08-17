package callbacks;

import ui.GamePanel;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

// Ascolta gli eventi della tastiera e richiama il GamePanel di conseguenza
public class GameEventListener extends KeyAdapter {

    private GamePanel gamePanel;

    public GameEventListener(GamePanel board){
        this.gamePanel = board;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        this.gamePanel.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        this.gamePanel.keyReleased(e);
    }
}
