import javax.sound.sampled.*;
import java.io.*;

public class Sonido {

    private String ruta;
    private AudioInputStream audioStream;
    private Clip clip;
    private long timer = 0;

    public Sonido(String ruta) {
        this.ruta = ruta;

        try {
            audioStream = AudioSystem.getAudioInputStream(new File(ruta).getAbsoluteFile());
            clip = AudioSystem.getClip();
        } catch (Exception e) {
        }
    }

    public void play() {
        try {
            clip.open(audioStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
        }
    }// end play

    public void pause() {
            timer = clip.getMicrosecondPosition();
            clip.stop();
    }// end pause

    public void resume() {
        clip.close();
        try {
            audioStream = AudioSystem.getAudioInputStream(new File(ruta).getAbsoluteFile());
            play();
            clip.setMicrosecondPosition(timer);
        } catch (Exception e) {
        }
    }// end resume

    public void stopAlto() {
        try {
            timer = 0L; // reset timer to 0 long
            clip.stop();
            clip.close();
        } catch (Exception e) {
        }
    }// end stop

    public void SpeedUp(){
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(20.0f);
    }
}
