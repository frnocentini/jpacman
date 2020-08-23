package logic;

import constants.Constants;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MenuLogic {

    private int mazesNumber; // Numero dei labirinti disponibili nella omonima directory
    private int count;

    public MenuLogic() {
        this.mazesNumber = loadMazes();
    }

    // Data la directory dei labirinti, generiamo la sua lista dei file e restituiamo la lunghezza, ovvero
    // il numero di file (e quindi di labirinti)
    private int loadMazes() {
        // IDE
        //return new File(Constants.MAZES_DIR).list().length;
        // JAR
        return new File(System.getProperty("user.dir"),"mazes").list().length;
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
