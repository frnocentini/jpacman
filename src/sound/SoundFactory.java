package sound;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import static constants.Constants.*;
import static sound.Sound.*;

public class SoundFactory {

    public Clip chooseSound(Sound sound){
        
        Clip audio = null;

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
        return audio;
    }

    private Clip createStream(String audioFilename){
        Clip audio = null;
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(audioFilename).getAbsoluteFile());
            audio = AudioSystem.getClip();
            audio.open(audioInputStream);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
        return audio;
    }

    public void playSound(Clip c){
        c.start();
    }

    public void stopSound(Clip c){
        if (c.isOpen()) {
            c.stop();
            c.flush();
            c.close();
        }
    }

    public void loopSound(Clip c){
         c.loop(Clip.LOOP_CONTINUOUSLY);
    }

}
