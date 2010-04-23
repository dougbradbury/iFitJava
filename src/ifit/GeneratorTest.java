package ifit;

import org.junit.*;

import javax.sound.sampled.*;
import java.util.Arrays;

public class GeneratorTest extends Assert
{
  private static final int speedStart = 1;
  private static final int inclineStart = 11;
  private static final int sumStart = 21;

  @Test
  public void shouldSetSpeedAndIncline() throws Exception
  {
    Generator generator = new Generator(1.0f, 10.0f);
    int[] allOnes = new int[64];
    Arrays.fill(allOnes, 1);    
    generator.writeModulationOf(allOnes);
    Player.play(generator);
  }

  @Test
  public void shouldBinaryModulate() throws Exception
  {
    int[] signal = Generator.binaryModulator(0.0f, 0.0f);
    assertEquals(0, signal[0]);

    checkZeroByteAt(signal, speedStart);
    checkZeroByteAt(signal, inclineStart);
    checkZeroByteAt(signal, sumStart);

    assertEquals(0, signal[31]);
  }

  @Test
  public void shouldBinaryModulateNonZero() throws Exception
  {
    int[] signal = Generator.binaryModulator(0.8f, 3.2f);
    assertEquals(0, signal[0]);

    int i = 1;
    assertEquals(0, signal[i + 0]);
    assertEquals(1, signal[i + 1]);

    assertEquals(0, signal[i + 2]);
    assertEquals(0, signal[i + 3]);
    assertEquals(0, signal[i + 4]);
    assertEquals(1, signal[i + 5]);
    assertEquals(0, signal[i + 6]);
    assertEquals(0, signal[i + 7]);
    assertEquals(0, signal[i + 8]);
    assertEquals(0, signal[i + 9]);


    i = inclineStart;
    assertEquals(0, signal[i + 0]);
    assertEquals(1, signal[i + 1]);

    assertEquals(0, signal[i + 2]);
    assertEquals(0, signal[i + 3]);
    assertEquals(0, signal[i + 4]);
    assertEquals(0, signal[i + 5]);
    assertEquals(0, signal[i + 6]);
    assertEquals(1, signal[i + 7]);
    assertEquals(0, signal[i + 8]);
    assertEquals(0, signal[i + 9]);

    i = sumStart;
    assertEquals(0, signal[i + 0]);
    assertEquals(1, signal[i + 1]);

    assertEquals(0, signal[i + 2]);
    assertEquals(0, signal[i + 3]);
    assertEquals(0, signal[i + 4]);
    assertEquals(1, signal[i + 5]);
    assertEquals(0, signal[i + 6]);
    assertEquals(1, signal[i + 7]);
    assertEquals(0, signal[i + 8]);
    assertEquals(0, signal[i + 9]);


    assertEquals(0, signal[31]);
  }

  private void checkZeroByteAt(int[] signal, int i)
  {
    assertEquals(0, signal[i + 0]);
    assertEquals(1, signal[i + 1]);
    assertEquals(0, signal[i + 2]);
    assertEquals(0, signal[i + 3]);
    assertEquals(0, signal[i + 4]);
    assertEquals(0, signal[i + 5]);
    assertEquals(0, signal[i + 6]);
    assertEquals(0, signal[i + 7]);
    assertEquals(0, signal[i + 8]);
    assertEquals(0, signal[i + 9]);
  }
}
