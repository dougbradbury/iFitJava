package ifit;

import javax.sound.sampled.AudioFormat;

public class Generator
{
  private int[] signal;
  private int writeIndex;

  final static int BYTE = 8;
  final static int SYMBOLS = 32;
  final static double TWOPI = 6.2831853;
  final static double AMPLITUDE = 32767.0;
  final static double FREQUENCY = 2000.0;
  final static float SAMPLERATE = 44100.0f;
  final static double ANGLE = (TWOPI * FREQUENCY);
  final static double DURATION = (4.0 / FREQUENCY * (float) SYMBOLS);
  final static int SAMPLESPERSIGNAL = (int) (DURATION * SAMPLERATE);

  public Generator()
  {
    signal = new int[SAMPLESPERSIGNAL * 5];
  }

  public static AudioFormat getAudioFormat()
  {
    return new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, SAMPLERATE, 16, 2, 4, SAMPLERATE, true);
  }

  public void generateSignal(float incline, float speed)
  {
    writeTwice(Modulator.binaryModulator(incline, speed));
  }

  public void generateStop()
  {
    writeTwice(Modulator.modulationOf(0xFC, 0xFC));
  }

  public byte[] getByteSignal()
  {
    byte[] bytes = new byte[signal.length*4];
    int byteIndex = 0;
    for (int i : signal)
    {
      bytes[byteIndex++] = bigEnd(i);
      bytes[byteIndex++] = littleEnd(i);
      bytes[byteIndex++] = bigEnd(i);
      bytes[byteIndex++] = littleEnd(i);
    }
    return bytes;
  }

  private byte littleEnd(int i)
  {
    return as_byte(i);
  }

  private byte bigEnd(int i)
  {
    return as_byte((i >> 8));
  }

  private byte as_byte(int i)
  {
    return (byte) (i & 0xFF);
  }

  private void writeTwice(int[] signal)
  {
    writeIndex = 0;
    writeZeros(SAMPLESPERSIGNAL / 4);
    writeModulatedSignal(signal);
    writeZeros(SAMPLESPERSIGNAL / 2);
    writeModulatedSignal(signal);
    writeZeros(SAMPLESPERSIGNAL / 4);
  }

  private void writeZeros(int howMAny)
  {
    int t;
    for(t = 0; t < howMAny; t++)
      signal[writeIndex++] = 0;
  }

  public void writeModulatedSignal(int[] moulatedSignal)
  {
    int t;
    for(int sample : moulatedSignal)
    {
      for(t = 0; t < (SAMPLESPERSIGNAL / SYMBOLS); t++)
      {
        this.signal[writeIndex++] = (int) (sample * AMPLITUDE * Math.sin((ANGLE * (float) t) / SAMPLERATE));
      }
    }
  }
}