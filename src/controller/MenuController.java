package controller;

import constants.Constants;

import java.io.File;

public class MenuController {

    private int mazesNumber; // Numero dei labirinti disponibili nella omonima directory

    public MenuController() {
        this.mazesNumber = loadMazes();
    }

    // Data la directory dei labirinti, generiamo la sua lista dei file e restituiamo la lunghezza, ovvero
    // il numero di file (e quindi di labirinti)
    private int loadMazes() {
        return new File(Constants.MAZES_DIR).list().length;
    }

    // Creiamo l'array di Stringhe per popolare la ComboBox di selezione labirinto
    public String[] populateMazeStrings() {
        String[] mazeStrings = new String[this.mazesNumber];
        for(int i=0;i<this.mazesNumber;i++){
            mazeStrings[i] = new String(""+(i+1));
        }
        return mazeStrings;
    }

}
