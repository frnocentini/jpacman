package sound;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import static constants.Constants.*;
import static sound.Sound.*;

public class SoundFactory {

    public SoundClip chooseSound(Sound sound){
        // Abbina ad ogni elemento dell'ENUM Sound un oggetto SoundClip
        SoundClip audio = null;

        switch(sound){
            case CREDIT:
                audio = createStream(CREDIT_URL);
                break;
            case DEATH:
                audio = createStream(DEATH_URL);
                break;
            case EAT_FRUIT:
                audio = createStream(EAT_FRUIT_URL);
                break;
            case EAT_GHOST:
                audio = createStream(EAT_GHOST_URL);
                break;
            case PAUSE_SOUND:
                audio = createStream(PAUSE_SOUND_URL);
                break;
            case GAME_START:
                audio = createStream(GAME_START_URL);
                break;
            case MUNCH_1:
                audio = createStream(MUNCH_1_URL);
                break;
            case MUNCH_2:
                audio = createStream(MUNCH_2_URL);
                break;
            case FRIGHT_SOUND:
                audio = createStream(FRIGHT_SOUND_URL);
                break;
            case EATEN_SOUND:
                audio = createStream(EATEN_SOUND_URL);
                break;
            case SIREN_1:
                audio = createStream(SIREN_1_URL);
                break;
            case SIREN_2:
                audio = createStream(SIREN_2_URL);
                break;
            case SIREN_3:
                audio = createStream(SIREN_3_URL);
                break;
            case SIREN_4:
                audio = createStream(SIREN_4_URL);
                break;
            case SIREN_5:
                audio = createStream(SIREN_5_URL);
                break;
            case STARTUP:
                audio = createStream(STARTUP_URL);
                break;
            case BLUE_PORTAL_SOUND:
                audio = createStream(BLUE_PORTAL_SOUND_URL);
                break;
            case RED_PORTAL_SOUND:
                audio = createStream(RED_PORTAL_SOUND_URL);
                break;
        }
        audio.setName(sound);
        return audio;
    }

    private SoundClip createStream(String audioFilename){
        // Dato un file audio ne crea Stream e Clip e li inserisce in un SoundClip
        SoundClip audio = null;
        try {
            // IDE
            //AudioInputStream ais = AudioSystem.getAudioInputStream(new File(audioFilename).getAbsoluteFile());
            // JAR
            AudioInputStream ais = AudioSystem.getAudioInputStream(ClassLoader.getSystemResource(audioFilename));
            DataLine.Info info = new DataLine.Info(Clip.class, ais.getFormat());
            Clip c = (Clip) AudioSystem.getLine(info);
            audio = new SoundClip(null,c,ais);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return audio;
    }

    public void playSound(SoundClip sc){
        // Se sta già venendo riprodotto lo fermo
        stopSound(sc);
        Clip c = sc.getClip();
        try {
            // Apriamo la Clip nel suo Stream
            c.open(sc.getAis());
            c.start();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopSound(SoundClip sc){
        Clip c = sc.getClip();
        // Se sta già venendo riprodotto
        if (c.isOpen()) {
            c.close();
            try {
                // Chiude anche lo Stream
                sc.getAis().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void loopSound(SoundClip sc){
        Clip c = sc.getClip();
        try {
            c.open(sc.getAis());
            c.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
