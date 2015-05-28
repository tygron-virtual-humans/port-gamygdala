package agent;

import data.Emotion;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class MapPadTest {

    MapPad mapPad;

    @Before
    public void setUp() {
        mapPad = new MapPad();
    }

    @After
    public void breakUp() {
        mapPad = null;
    }

    @Test
    public void testGetPadState() throws Exception {
        ArrayList<Emotion> emotions = new ArrayList<Emotion>();
        Emotion emotion = new Emotion("joy", 0.5);
        emotions.add(emotion);

        double[] expected = new double[]{0.38, 0.24, 0.175};
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], mapPad.getPadState(emotions, 0.1, false)[i], 0.001);
        }
    }

    @Test
    public void testGetPadStateWithGain() throws Exception {
        ArrayList<Emotion> emotions = new ArrayList<Emotion>();
        Emotion emotion = new Emotion("remorse", 0.5);
        emotions.add(emotion);

        double[] expected = new double[]{-.0277, .0138, -.0167};
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], mapPad.getPadState(emotions, 0.1, true)[i], 0.001);
        }
    }
}