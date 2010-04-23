package ifit;

import org.junit.Assert;
import org.junit.Test;

public class TreadmillTest extends Assert
{

  @Test
  public void shouldSetSpeedAndIncline() throws Exception
  {
    Treadmill treadmill = new Treadmill();
    treadmill.set(0.0f, 1.0f);    
  }
}
