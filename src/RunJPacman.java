import ui.GameMainFrame;

import java.awt.*;

public class RunJPacman {

    public static void main(String[] args){

        EventQueue.invokeLater( () -> {
            new GameMainFrame();
        });
        

    }
}
