package decayfunction;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test DecayFunction.
 */
public class DecayFunctionTest {

  @Test
  public void testGetSetDecayFactor() {

    // Create dummy DecayFunction
    DecayFunction df = new DecayFunction(.9) {

      @Override
      public double decay(double initial, long millisPassed) {
        return 0;
      }
    };

    // Test get decay
    assertEquals(.9, df.getDecayFactor(), 10E-15);

    // Test set / get decay
    df.setDecayFactor(.22);
    assertEquals(.22, df.getDecayFactor(), 10E-15);

  }

}
