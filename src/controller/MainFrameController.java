package controller;

import constants.Constants;
import ui.GameMainFrame;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class MainFrameController {

    private GameMainFrame frame;

    public MainFrameController(GameMainFrame frame) {
        this.frame = frame;
    }

    public void registerFont(){
        Font f = null;
        try {
            f = Font.createFont(Font.TRUETYPE_FONT, new File(Constants.ARMA_FONT));
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(f);
    }

    public int readHighScore(){
        int score = 0;
        // Leggiamo il file dell'highscore
        File f = new File(Constants.HIGHSCORES);
        try {
            Scanner sc = new Scanner(f);
            score = sc.nextInt();
        } catch (FileNotFoundException e) {
            // Se non esiste lo creiamo con il valore "0" al suo interno
            try {
                f.createNewFile();
                PrintWriter pw = new PrintWriter(f);
                pw.print(0);
                pw.flush();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        return score;
    }

    public void writeHighScore(int points){
        if(points > this.frame.getHighScore()) {
            this.frame.setHighScore(points);
            PrintWriter pw = null;
            try {
                pw = new PrintWriter(new File(Constants.HIGHSCORES));
                pw.print(points);
                pw.flush();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
