import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ExponentialDecayTest {

	@Test
	public void testDecay() {

		ExponentialDecay ed = new ExponentialDecay(.5);

		// Test after one second (decay is .5 ==> 50 decay)
		double oneSec = ed.decay(100, 1000);
		assertEquals(50, oneSec, 10E-15);

		// Test after 3 seconds (decay is .5 per second ==> 87.5 decay)
		double threeSec = ed.decay(100, 3000);
		assertEquals(12.5, threeSec, 10E-15);

		ExponentialDecay ed2 = new ExponentialDecay(.8);
		
		// Test after 10 seconds with different decay and different initial value (decay is .8 ==> 22 decay)
		double diffDecay = ed2.decay(222, 10000);
		assertEquals(23.837068492800014, diffDecay, 10E-15);

	}

}
