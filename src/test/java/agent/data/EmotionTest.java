package agent.data;

import static junit.framework.TestCase.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for Emotion.
 */
public class EmotionTest {

    Emotion emotion;

    @Before
    public void setUp() {
        emotion = new Emotion("happy", 0.8);
    }

    @Test
    public void testGetName() throws Exception {
        assertEquals("happy", emotion.getName());
    }

    @Test
    public void testSetName() throws Exception {
        assertEquals("happy", emotion.getName());

        emotion.setName("sad");
        assertEquals("sad", emotion.getName());
    }

    @Test
    public void testGetIntensity() throws Exception {
        assertEquals(0.8, emotion.getIntensity());
    }

    @Test
    public void testSetIntensity() throws Exception {
        assertEquals(0.8, emotion.getIntensity());

        emotion.setIntensity(0.4);
        assertEquals(0.4, emotion.getIntensity());
    }

    @Test
    public void testToString() throws Exception {
        assertEquals("<Emotion[happy, 0.8]>", emotion.toString());
    }

    @Test
    public void testHashCode() throws Exception {
        assertEquals(1567851811, emotion.hashCode());
    }

    @Test
    public void testEquals() throws Exception {
        assertEquals(false, emotion.equals(null));

        assertEquals(false, emotion.equals(new Emotion("happy", 0.7)));

        assertEquals(false, emotion.equals(new Emotion("sad", 0.8)));

        assertEquals(true, emotion.equals(new Emotion("happy", 0.8)));

        assertEquals(true, emotion.equals(emotion));
    }
}
