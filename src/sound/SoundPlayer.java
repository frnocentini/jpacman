package sound;

import javax.sound.sampled.Clip;
import java.util.HashMap;

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

    public static void stopMusic(Sound sound) {
        Clip c = library.get(sound);
        sf.stopSound(c);
        library.remove(sound);
    }
}
