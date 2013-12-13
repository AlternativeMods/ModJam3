package jkmau5.modjam.radiomod;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * No description given
 *
 * @author jk-5
 */
public class AudioTest {

    public static void main(String args[]){
        try {
            Clip clip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(AudioTest.class.getResourceAsStream("/test.wav"));
            clip.open(inputStream);
            clip.start();
            Thread.sleep(1000000);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
