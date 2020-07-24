import ui.GameMainFrame;

import java.awt.*;

public class ProvaPacman {

    public static void main(String[] args){

        EventQueue.invokeLater( () -> {
            new GameMainFrame();
        });

    }
}
