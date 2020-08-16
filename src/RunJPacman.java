import ui.GameMainFrame;

import java.awt.*;

public class RunJPacman {

    public static void main(String[] args){

        // Gestisce in maniera parallela il nostro codice e la componente grafica dell'applicazione
        EventQueue.invokeLater( () -> {
            new GameMainFrame();
        });
        

    }
}
