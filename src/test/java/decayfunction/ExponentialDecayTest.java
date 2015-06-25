package decayfunction;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for ExponentialDecay.
 */
public class ExponentialDecayTest {

    DecayFunction decayFunction;

    @Before
    public void setUp() throws Exception {
        decayFunction = new ExponentialDecay(0.4);
    }

    @After
    public void tearDown() throws Exception {
        decayFunction = null;
    }

    @Test
    public void testGetDecayFactor() throws Exception {
        assertEquals(0.4, decayFunction.getDecayFactor(), 1E-15);
    }

    @Test
    public void testSetDecayFactor() throws Exception {
        decayFunction.setDecayFactor(0.6);

        assertEquals(0.6, decayFunction.getDecayFactor(), 1E-15);
    }

    @Test
    public void testDecay() throws Exception {
        assertEquals(4.0, decayFunction.decay(10, 1000), 1E-15);
    }

    @Test
    public void testDecayLater() throws Exception {
        assertEquals(1.6, decayFunction.decay(10, 2000), 1E-15);
    }
}
