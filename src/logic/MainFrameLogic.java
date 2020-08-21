package logic;

import constants.Constants;
import ui.GameMainFrame;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class MainFrameLogic {

    private GameMainFrame frame;

    public MainFrameLogic(GameMainFrame frame) {
        this.frame = frame;
    }

    public void registerFont(){
        Font f = null;
        Font fa = null;
        try {
            // IDE
            //f = Font.createFont(Font.TRUETYPE_FONT, new File(Constants.ARMA_FONT));
            //fa = Font.createFont(Font.TRUETYPE_FONT, new File(Constants.ARROWS_FONT));
            // JAR
            f = Font.createFont(Font.TRUETYPE_FONT, Thread.currentThread().getContextClassLoader().getResourceAsStream(Constants.ARMA_FONT));
            fa = Font.createFont(Font.TRUETYPE_FONT, Thread.currentThread().getContextClassLoader().getResourceAsStream(Constants.ARROWS_FONT));
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(f);
        ge.registerFont(fa);
    }

    public int readHighScore(){
        int score = 0;
        // Leggiamo il file dell'highscore
        // IDE
        //File f = new File(Constants.HIGHSCORES);
        // JAR
        File f = new File(System.getProperty("user.dir"),"highScore.txt");
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
        // IDE
        //File f = new File(Constants.HIGHSCORES);
        // JAR
        File f = new File(System.getProperty("user.dir"),"highScore.txt");
        if(points > this.frame.getHighScore()) {
            this.frame.setHighScore(points);
            PrintWriter pw = null;
            try {
                pw = new PrintWriter(f);
                pw.print(points);
                pw.flush();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
