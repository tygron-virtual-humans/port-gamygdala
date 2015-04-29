import static org.junit.Assert.*;

import org.junit.Test;


public class EmotionTest {

	@Test
	public void testEmotion() {
		
		Emotion e = new Emotion("Emotion", .5);
		assertEquals("Emotion", e.name);
		assertEquals(.5, e.intensity, 10E-15);
		
	}
	
	@Test
	public void testToString() {
		Emotion e = new Emotion("Test", .75);
		assertEquals("<Emotion[Test, 0.75]>", e.toString());
	}
	
	@Test
	public void testEquals() {
		Emotion e, e2;
		
		e = new Emotion("Test", .75);
		e2 = new Emotion("Test", .75);
		assertEquals(e, e2);
		
		e = new Emotion("Test", 0);
		e2 = new Emotion("Test", .75);
		assertNotEquals(e, e2);
		
		e = new Emotion("Emotion", 0);
		e2 = new Emotion("Test", .75);
		assertNotEquals(e, e2);
		
		e = new Emotion("Emotion", .75);
		e2 = new Emotion("Test", .75);
		assertNotEquals(e, e2);
		
		String s = new String("Test");
		e2 = new Emotion("Test", .75);
		assertFalse(e2.equals(s));
	}

}
