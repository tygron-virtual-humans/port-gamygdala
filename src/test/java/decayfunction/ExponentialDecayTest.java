package decayfunction;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Test exponential DecayFunction.
 */
public class ExponentialDecayTest {

    @Test
    public void testDecay_1() {

        ExponentialDecay ed = new ExponentialDecay(.5);

        // Test after one second (decay is .5 ==> 50 decay)
        double oneSec = ed.decay(100, 1000);
        assertEquals(50, oneSec, 10E-15);

    }

    @Test
    public void testDecay_2() {

        ExponentialDecay ed = new ExponentialDecay(.5);

        // Test after 3 seconds (decay is .5 per second ==> 87.5 decay)
        double threeSec = ed.decay(100, 3000);
        assertEquals(12.5, threeSec, 10E-15);

    }

    @Test
    public void testDecay_3() {
        ExponentialDecay ed2 = new ExponentialDecay(.8);

        // Test after 10 seconds with different decay and initial value (decay
        // is .8 ==> 22 decay)
        double diffDecay = ed2.decay(222, 10000);
        assertEquals(23.837068492800014, diffDecay, 10E-15);

    }

    @Test
    public void testDecay_4() {
        ExponentialDecay ed2 = new ExponentialDecay(.8);

        // Test a decay value which is not a multiple of 1000.
        double diffDecay = ed2.decay(100, 59);
        assertNotEquals(100, diffDecay, 10E-15);
        assertEquals(98.69208161873428, diffDecay, 10E-15);
    }

}
