package ifit;

import org.junit.Assert;
import org.junit.Test;

public class TreadmillTest extends Assert
{

  @Test
  public void shouldSetSpeedAndIncline() throws Exception
  {
    Treadmill treadmill = new Treadmill();
    treadmill.set(1.5f, 1.0f);    
  }
}
