package sound;

import utility.CoordManager;

import javax.sound.sampled.Clip;
import java.util.HashMap;

import static sound.Sound.*;
import static sound.Sound.SIREN_5;

public class SoundPlayer {

    private static SoundFactory sf;
    private static boolean muteEffects;
    private static boolean muteMusic;
    private static HashMap<Sound, Clip> library;

    public static void initialize(){
        SoundPlayer.sf = new SoundFactory();
        SoundPlayer.muteEffects = false;
        SoundPlayer.muteMusic = false;
        library = new HashMap<>();
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
            library.put(sound,c);
        }
    }

    public static void loopEffect(Sound sound) {
        if(!muteEffects && !isPlaying(sound)){
            stopAll();
            Clip c = sf.chooseSound(sound);
            sf.loopSound(c);
            library.put(sound,c);
        }
    }

    public static void stopMusic(Sound sound) {
        if(isPlaying(sound)) {
            Clip c = library.get(sound);
            sf.stopSound(c);
            library.remove(sound);
        }
    }

    private static boolean isPlaying(Sound sound){
        if(library.containsKey(sound)) {
            return true;
        }
        return false;
    }

    public static void stopAll(){
        for(HashMap.Entry<Sound, Clip> entry : library.entrySet()) {
            Sound sound = entry.getKey();
            Clip c = entry.getValue();

            sf.stopSound(c);
            library.remove(sound);
        }
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
        } else if(CoordManager.maze.getAlivePills() == 0){
            SoundPlayer.stopAll();
        }
    }
}
