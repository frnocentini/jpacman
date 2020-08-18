package loops;

import logic.GameLogic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameLoop implements ActionListener {

    private GameLogic gameLogic;

    public GameLoop(GameLogic gameLogic){
        this.gameLogic = gameLogic;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        this.gameLogic.doOneLoop();
    }
}
