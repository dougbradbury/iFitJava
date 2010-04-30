package ifit;

public class Modulator
{
  final static int BYTE = 8;

  public static int[] binaryModulator(float speed, float incline)
  {
    int intSpeed = (int) (speed * 10.0);
    int intIncline = (int) (incline * 10.0);
    return modulationOf(intSpeed, intIncline);
  }

  static int[] modulationOf(int intSpeed, int intIncline)
  {
    int[] sigp = new int[2 + 3 * (Generator.BYTE + 2)];
    int i = 0;

    int check = intSpeed + intIncline;

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

  private static int bit(int bit, int value)
  {
    return ((value >> bit) & 1);
  }


}