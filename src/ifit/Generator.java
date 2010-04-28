package ifit;

import javax.sound.sampled.AudioFormat;

public class Generator
{
  private int[] signal;
  private float incline;
  private float speed;
  private int writeIndex;

  private static int bit(int bit, int value)
  {
    return ((value >> bit) & 1);
  }

  final static int BYTE = 8;
  final static int SYMBOLS = 32;
  final static double TWOPI = 6.2831853;
  final static double AMPLITUDE = 32767.0;
  final static double FREQUENCY = 2000.0;
  final static float SAMPLERATE = 44100.0f;
  final static double ANGLE = (TWOPI * FREQUENCY);
  final static double DURATION = (4.0 / FREQUENCY * (float) SYMBOLS);
  final static int SAMPLESPERSIGNAL = (int) (DURATION * SAMPLERATE);

  public Generator(float incline, float speed)
  {
    this.incline = incline;
    this.speed = speed;
    signal = new int[SAMPLESPERSIGNAL * 5];
  }

  public AudioFormat getAudioFormat()
  {
    return new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, SAMPLERATE, 16, 2, 4, SAMPLERATE, true);
  }

  public byte[] getByteSignal()
  {
    byte[] bytes = new byte[signal.length*4];
    int byteIndex = 0;
    for (int i : signal)
    {
      bytes[byteIndex++] = (byte) ((i >> 8) & 0xFF);
      bytes[byteIndex++] = (byte) (i & 0xFF);
      bytes[byteIndex++] = (byte) ((i >> 8) & 0xFF);
      bytes[byteIndex++] = (byte) (i & 0xFF);
    }
    return bytes;
  }


  public static int[] binaryModulator(float speed, float incline)
  {
    int intSpeed = (int) (speed * 10.0);
    int intIncline = (int) (incline * 10.0);
    int check = intSpeed + intIncline;

    int[] sigp = new int[2 + 3 * (BYTE + 2)];
    int i = 0;

    sigp[i++] = 0;

    i = writeByte(intIncline, sigp, i);
    i = writeByte(intSpeed, sigp, i);
    i = writeByte(check, sigp, i);

    sigp[i] = 0;

    return sigp;
  }

  private static int writeByte(int intspeed, int[] sigp, int i)
  {
    sigp[i++] = 0;
    sigp[i++] = 1;

    for(int bit = 0; bit < BYTE; bit++) // speed byte
    {
      sigp[i] = bit(bit, intspeed);
      i++;
    }
    return i;
  }


  public void generateSignal()
  {
    writeIndex = 0;
    int[] binarySignal = binaryModulator(incline, speed);

    writeZeros(SAMPLESPERSIGNAL / 4);
    writeModulationOf(binarySignal);
    writeZeros(SAMPLESPERSIGNAL / 2);
    writeModulationOf(binarySignal);
    writeZeros(SAMPLESPERSIGNAL / 4);
  }

  private void writeZeros(int howMAny)
  {
    int t;
    for(t = 0; t < howMAny; t++)
      signal[writeIndex++] = 0;
  }

  public void writeModulationOf(int[] binarySignal)
  {
    int t;
    for(int sample : binarySignal)
    {
      for(t = 0; t < (SAMPLESPERSIGNAL / SYMBOLS); t++)
      {
        signal[writeIndex++] = (int) (sample * AMPLITUDE * Math.sin((ANGLE * (float) t) / (float) (SAMPLERATE)));
      }
    }
  }
}