package engine;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class SoundEffect {

    public static final String BASE_PATH = "Audio/";
    public static enum Volume {
        MUTE, LOW, MEDIUM, HIGH
    }

    public Volume volume = Volume.HIGH;
    private Clip clip;

    public SoundEffect(String filename) {
        try {
            URL url = this.getClass().getClassLoader().getResource(BASE_PATH + filename);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public final void setVolume(Volume volume) {
        this.volume = volume;
    }

    public final void playWithoutInterrupt() {
        play(false, false);
    }

    public final void play() {
        play(false, true);
    }

    public final void loop() {
        play(true, false);
    }

    private void play(boolean loop, boolean allowInterrupt) {
        if (volume != Volume.MUTE) {
            if (clip.isRunning() && allowInterrupt) {
                clip.stop();
            } else if (clip.isRunning()) {
                return;
            }

            clip.setFramePosition(0);
            clip.start();
            if (loop) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
        }
    }

    public final boolean isRunning() {
        return clip.isRunning();
    }

    public final void stop() {
        clip.stop();
        clip.setFramePosition(0);
    }

    public final void mute() {
        volume = Volume.MUTE;
    }
}