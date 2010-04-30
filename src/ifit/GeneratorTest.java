package ifit;

import org.junit.*;

import java.util.Arrays;

public class GeneratorTest extends Assert
{

  @Test
  public void shouldPlayPureTone() throws Exception
  {
    Generator generator = new Generator();
    int[] allOnes = new int[64];
    Arrays.fill(allOnes, 1);
    generator.writeModulatedSignal(allOnes);
    Player player = new Player(Generator.getAudioFormat());
    player.play(generator);
    player.stop();
  }



}
