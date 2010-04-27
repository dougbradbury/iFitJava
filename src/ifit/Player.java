package ifit;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class Player {
    private SourceDataLine line;

    public void play(Generator generator) {
        DataLine.Info lineInfo = new DataLine.Info(SourceDataLine.class, generator.getAudioFormat());

        if (!AudioSystem.isLineSupported(lineInfo)) {
            System.out.println("Line Not supported");
        }

        try {
            line = (SourceDataLine) AudioSystem.getLine(lineInfo);
            line.open(generator.getAudioFormat());
            line.start();
        }
        catch (LineUnavailableException ex) {
            System.out.println("Line Unavailable: " + ex.getMessage());
        }        byte[] pcmSignal = generator.getByteSignal();
        int written = line.write(pcmSignal, 0, pcmSignal.length);
        if (written != pcmSignal.length) {
            System.out.println("all data not written");
        }
        line.drain();
    }

    public void stop() {
        line.stop();
        line.close();
    }
}
