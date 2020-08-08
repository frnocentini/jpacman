package sound;

import javax.sound.sampled.Clip;

public class SoundClip {

    private Sound name;
    private Clip clip;

    public SoundClip(Sound name, Clip clip) {
        this.name = name;
        this.clip = clip;
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
}