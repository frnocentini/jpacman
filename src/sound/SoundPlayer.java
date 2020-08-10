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
            SoundClip sc = sf.chooseSound(sound);
            sf.playSound(sc);
        }
    }

    public static void playMusic(Sound sound) {
        if(!muteMusic){
            SoundClip sc = sf.chooseSound(sound);
            sf.playSound(sc);
            library.add(new SoundClip(sound,sc.getClip(),sc.getAis()));
        }
    }

    public static void loopEffect(Sound sound) {
        int index = isPlaying(sound);
        if(!muteEffects && index == -1){
            if(isPlaying(GAME_START) == -1){
                stopAll();
            }
            SoundClip sc = sf.chooseSound(sound);
            sf.loopSound(sc);
            library.add(new SoundClip(sound,sc.getClip(),sc.getAis()));
        }
    }

    public static void stopMusic(Sound sound) {
        int index = isPlaying(sound);
        if(index > -1) {
            SoundClip sc = library.get(index);
            sf.stopSound(sc);
            library.remove(index);
        }
    }

    private static int isPlaying(Sound sound){
        for(int i=0;i<library.size();i++){
            SoundClip sc = library.get(i);
            if(sc.getName() == sound){
                return i;
            }
        }
        return -1;
    }

    public static void stopAll(){
        for(SoundClip sc : library){
            sf.stopSound(sc);
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
        } else if (CoordManager.maze.getAlivePills() == 0){
            SoundPlayer.stopAll();
        }
    }

    public static void removeMusic(Sound sound) {
        for(int i=0;i<library.size();i++){
            SoundClip sc = library.get(i);
            if(sc.getName() == sound){
               library.remove(i);
            }
        }
    }
}
