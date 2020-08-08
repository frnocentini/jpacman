package sound;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;

public class SoundClip {

    private Sound name;
    private Clip clip;
    private AudioInputStream ais;

    public SoundClip(Sound name, Clip clip, AudioInputStream ais) {
        this.name = name;
        this.clip = clip;
        this.ais = ais;
    }

    public Sound getName() {
        return name;
    }

    public void setName(Sound name) {
        this.name = name;
    }

    public Clip getClip() {
        return clip;
    }

    public void setClip(Clip clip) {
        this.clip = clip;
    }

    public AudioInputStream getAis() {
        return ais;
    }

    public void setAis(AudioInputStream ais) {
        this.ais = ais;
    }
}