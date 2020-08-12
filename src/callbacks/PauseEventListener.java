package callbacks;

import ui.GamePanel;
import ui.PausePanel;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PauseEventListener extends KeyAdapter {

    private PausePanel board;

    public PauseEventListener(PausePanel board){
        this.board = board;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        this.board.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //
    }
}
