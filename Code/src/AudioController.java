import sun.audio.AudioStream;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

class AudioController implements LineListener {
    boolean playCompleted = false;
    GamePanel gp;

    public AudioController(GamePanel gp) {
        this.gp = gp;
    }

    public void play(String key){
        if (!gp.mute) {
            try {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(new BufferedInputStream(getClass().getResourceAsStream("/audio/"+key+".wav")));

                AudioFormat format = audioStream.getFormat();

                DataLine.Info info = new DataLine.Info(Clip.class, format);

                Clip audioClip = (Clip) AudioSystem.getLine(info);

                audioClip.addLineListener(this);

                audioClip.open(audioStream);

                audioClip.start();

                playCompleted = false;

                while (!playCompleted) {
                    // wait for the playback completes
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }

                audioClip.stop();
                audioClip.close();
             /*   try { audioStream.reset(); }
                catch (Exception e) { e.printStackTrace(); }*/

            } catch (LineUnavailableException ex) {
                System.out.println("Audio line for playing back is unavailable.");
                ex.printStackTrace();
            } catch (IOException ex) {
                System.out.println("Error playing the audio file.");
                ex.printStackTrace();
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void update(LineEvent event) {
        LineEvent.Type type = event.getType();
        if (type == LineEvent.Type.STOP) {
            playCompleted = true;
        }
    }
}