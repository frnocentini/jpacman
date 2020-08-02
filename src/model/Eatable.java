package model;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Eatable extends Sprite {

    private int points;
    private Clip audio;

    public Eatable(int x, int y, int w, int h, int points, String audioFilename) {
        this.points = points;
        setX(x);
        setY(y);
        setW(w);
        setH(h);
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(audioFilename).getAbsoluteFile());
            this.audio = AudioSystem.getClip();
            this.audio.open(audioInputStream);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }

    public void makeSound(){
        this.audio.start();
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
