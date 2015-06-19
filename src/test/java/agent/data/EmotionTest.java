package agent.data;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Test Emotion class.
 */
public class EmotionTest {

    ArrayList<String> stringArray;

    @Before
    public void setUp() {
        stringArray = null;
    }

    @Test
    public void testDetermineEmotionsHope() {
        ArrayList<String> expected = new ArrayList<String>() {{
            add("hope");
        }};
        stringArray = Emotion.determineEmotions(-.1, -.1, 0.1);
        assertEquals(expected, stringArray);
    }

    @Test
    public void testDetermineEmotionsFear() {
        ArrayList<String> expected = new ArrayList<String>() {{
            add("fear");
        }};
        stringArray = Emotion.determineEmotions(-.1, .0, 0.1);
        assertEquals(expected, stringArray);
    }

    @Test
    public void testDetermineEmotionsSatisfactionJoy() {
        ArrayList<String> expected = new ArrayList<String>() {{
            add("satisfaction");
            add("joy");
        }};
        stringArray = Emotion.determineEmotions(.6, 0.49, 1.d);
        assertEquals(expected, stringArray);
    }

    @Test
    public void testDetermineEmotionsFearConfirmedDistress() {
        ArrayList<String> expected = new ArrayList<String>() {{
            add("fear-confirmed");
            add("distress");
        }};
        stringArray = Emotion.determineEmotions(-.1, 0.49, 1.d);
        assertEquals(expected, stringArray);
    }

    @Test
    public void testDetermineEmotionsDisappointmentDistress() {
        ArrayList<String> expected = new ArrayList<String>() {{
            add("disappointment");
            add("distress");
        }};
        stringArray = Emotion.determineEmotions(0.1, 0.51, 0.d);
        assertEquals(expected, stringArray);
    }

    @Test
    public void testDetermineEmotionsReliefJoy() {
        ArrayList<String> expected = new ArrayList<String>() {{
            add("relief");
            add("joy");
        }};
        stringArray = Emotion.determineEmotions(-.1, 0.51, 0.d);
        assertEquals(expected, stringArray);
    }

    @Test
    public void testToString() {
        Emotion emo = new Emotion("Test", .75);
        assertEquals("<Emotion[Test, 0.75]>", emo.toString());
    }

    @Test
    public void testEquals() {
        Emotion emo1;
        Emotion emo2;

        emo1 = new Emotion("Test", .75);
        emo2 = new Emotion("Test", .75);
        assertEquals(emo1, emo2);

        emo1 = new Emotion("Test", 0);
        emo2 = new Emotion("Test", .75);
        assertNotEquals(emo1, emo2);

        emo1 = new Emotion("Emotion", 0);
        emo2 = new Emotion("Test", .75);
        assertNotEquals(emo1, emo2);

        emo1 = new Emotion("Emotion", .75);
        emo2 = new Emotion("Test", .75);
        assertNotEquals(emo1, emo2);

        Object object = new Object();
        emo2 = new Emotion("Test", .75);
        assertNotEquals(emo2, object);
    }

}
