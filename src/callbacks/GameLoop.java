package callbacks;

import controller.GameController;
import ui.GamePanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameLoop implements ActionListener {

    private GameController gameController;

    public GameLoop(GameController gameController){
        this.gameController = gameController;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        this.gameController.doOneLoop();
    }
}
