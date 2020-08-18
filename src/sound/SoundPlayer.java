package sound;

import constants.Constants;

import java.util.ArrayList;

import static sound.Sound.*;

public class SoundPlayer {

    private static SoundFactory sf; // Oggetto della classe che gestisce i file audio e i loro stream
    private static boolean muteEffects; // true = effetti sonori mutati
    private static boolean muteMusic; // true = musica mutata
    private static ArrayList<SoundClip> library; // ArrayList che contiene il riferimento ai suoni che stanno
                                                // venendo riprodotti (non viene sfruttato nel nostro caso)

    public static void initialize(){
        SoundPlayer.sf = new SoundFactory();
        // Inizializiamo (a falso) le variabili che mutano gli effetti e la musica
        SoundPlayer.muteEffects = Constants.EFFECTS_STATE;
        SoundPlayer.muteMusic = Constants.MUSIC_STATE;
        library = new ArrayList<>();
    }

    public static void playEffect(Sound sound){
        if(!muteEffects){
            // Creiamo una SoundClip
            SoundClip sc = sf.chooseSound(sound);
            // Apre la clip in SoundFactory
            sf.playSound(sc);
        }
    }

    public static void playMusic(Sound sound) {
        if(!muteMusic){
            // Fermiamo le musiche e gli effetti loopati prima di riprodurre la nuova
            stopAll();
            SoundClip sc = sf.chooseSound(sound);
            sf.playSound(sc);
            // Aggiungiamola all'ArrayList library
            library.add(new SoundClip(sound,sc.getClip(),sc.getAis()));
        }
    }

    public static void loopEffect(Sound sound) {
        // Controlliamo che il suono non stia già venendo riprodotto
        int index = isPlaying(sound);
        if(!muteEffects && index == -1){
            // Se è appena iniziata la partita non c'è bisogno di fermare tutti i suoni
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
        // Se la clip è in riproduzione la fermiamo
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
        for(int i=0;i<library.size();i++){
            sf.stopSound(library.get(i));
            library.remove(i);
        }
    }

    public static void removeMusic(Sound sound) {
        // Rimuoviamo dall'ArrayList musiche già ferme
        for(int i=0;i<library.size();i++){
            SoundClip sc = library.get(i);
            if(sc.getName() == sound){
               library.remove(i);
            }
        }
    }

    public static boolean isMuteEffects() {
        return muteEffects;
    }

    public static void setMuteEffects(boolean muteEffects) {
        SoundPlayer.muteEffects = muteEffects;
    }

    public static boolean isMuteMusic() {
        return muteMusic;
    }

    public static void setMuteMusic(boolean muteMusic) {
        SoundPlayer.muteMusic = muteMusic;
    }
}
