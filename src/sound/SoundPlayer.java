package sound;

import utility.CoordManager;

import javax.sound.sampled.Clip;
import java.util.ArrayList;
import java.util.HashMap;

import static sound.Sound.*;

public class SoundPlayer {

    private static SoundFactory sf;
    private static boolean muteEffects;
    private static boolean muteMusic;
    private static ArrayList<SoundClip> library;

    public static void initialize(){
        SoundPlayer.sf = new SoundFactory();
        SoundPlayer.muteEffects = false;
        SoundPlayer.muteMusic = false;
        library = new ArrayList<>();
    }

    public static void playEffect(Sound sound){
        if(!muteEffects){
            Clip c = sf.chooseSound(sound);
            sf.playSound(c);
        }
    }

    public static void playMusic(Sound sound) {
        if(!muteMusic){
            Clip c = sf.chooseSound(sound);
            sf.playSound(c);
            library.add(new SoundClip(sound,c));
        }
    }

    public static void loopEffect(Sound sound) {
        int index = isPlaying(sound);
        if(!muteEffects && index == -1){
            if(sound != SIREN_1){
                stopAll();
            }
            Clip c = sf.chooseSound(sound);
            sf.loopSound(c);
            library.add(new SoundClip(sound,c));
        }
    }

    public static void stopMusic(Sound sound) {
        int index = isPlaying(sound);
        if(index > -1) {
            Clip c = library.get(index).getClip();
            sf.stopSound(c);
            library.remove(index);
        }
    }

    private static int isPlaying(Sound sound){
        for(int i=0;i<library.size();i++){
            SoundClip sc = library.get(i);
            if(sc.getName().equals(sound)){
                return i;
            }
        }
        return -1;
    }

    public static void stopAll(){
        for(SoundClip sc : library){
            Clip c = sc.getClip();
            sf.stopSound(c);
        }
        library.clear();
    }

    public static void playBackgroundMusic(boolean frightened, boolean eaten) {
        if(eaten){
            SoundPlayer.loopEffect(EATEN_SOUND);
        } else if (frightened){
            SoundPlayer.loopEffect(FRIGHT_SOUND);
        }else if(CoordManager.maze.getAlivePills() > CoordManager.maze.getPillsNum() * 4/5){
            SoundPlayer.loopEffect(SIREN_1);
        } else if (CoordManager.maze.getAlivePills() > CoordManager.maze.getPillsNum() * 3/5) {
            SoundPlayer.loopEffect(SIREN_2);
        } else if (CoordManager.maze.getAlivePills() > CoordManager.maze.getPillsNum() * 2/5) {
            SoundPlayer.loopEffect(SIREN_3);
        } else if (CoordManager.maze.getAlivePills() > CoordManager.maze.getPillsNum() / 5) {
            SoundPlayer.loopEffect(SIREN_4);
        } else if (CoordManager.maze.getAlivePills() > 0) {
            SoundPlayer.loopEffect(SIREN_5);
        }
    }
}
