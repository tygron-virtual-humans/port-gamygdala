package decayfunction;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Test linear DecayFunction.
 */
public class LinearDecayTest {

  @Test
  public void testDecay() {

    LinearDecay ld = new LinearDecay(.5);

    // Test after one second (decay is .5 per second ==> .5 decay)
    double oneSec = ld.decay(100, 1000);
    assertEquals(99.5, oneSec, 10E-15);

    // Test after 100 seconds (decay is .5 per second ==> 50 decay)
    double hundredSec = ld.decay(100, 100000);
    assertEquals(50, hundredSec, 10E-15);

    LinearDecay ld2 = new LinearDecay(.22);

    // Test after 100 seconds with different decay and
    // different initial value (decay is .22 per second ==> 22 decay)
    double diffDecay = ld2.decay(222, 100000);
    assertEquals(200, diffDecay, 10E-15);

  }

}
