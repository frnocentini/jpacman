package loops;

import sprites.Pacman;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PacmanLoop implements ActionListener {

    private Pacman pacman;

    public PacmanLoop(Pacman pacman){
        this.pacman = pacman;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        this.pacman.doOneLoop();
    }
}