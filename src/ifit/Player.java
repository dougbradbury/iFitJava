package ifit;

import javax.sound.sampled.*;

public class Player
{
  private SourceDataLine line;

  public Player(AudioFormat format)
  {
    DataLine.Info lineInfo = new DataLine.Info(SourceDataLine.class, format);

    if(!AudioSystem.isLineSupported(lineInfo))
    {
      System.out.println("Line Not supported");
    }

    try
    {
      line = (SourceDataLine) AudioSystem.getLine(lineInfo);
      line.open(format);
      line.start();
    }
    catch(LineUnavailableException ex)
    {
      System.out.println("Line Unavailable: " + ex.getMessage());
    }
  }

  public void play(Generator generator)
  {
    byte[] pcmSignal = generator.getByteSignal();
    int written = line.write(pcmSignal, 0, pcmSignal.length);
    if(written != pcmSignal.length)
    {
      System.out.println("all data not written");
    }
  }

  public void stop()
  {
    line.drain();
    line.stop();
    line.close();
  }
}
