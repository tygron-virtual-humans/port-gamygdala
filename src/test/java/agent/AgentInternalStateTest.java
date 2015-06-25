package agent;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import agent.data.Emotion;

/**
 * Tests for AgentInternalState.
 */
public class AgentInternalStateTest {

    AgentInternalState ais;

    @Before
    public void setUp() {
        ais = new AgentInternalState();

        // Add initial emotion
        Emotion emo = new Emotion("TestEmotion", 1);
        ais.updateState(emo);

    }

    @After
    public void tearDown() {
        ais = null;
    }

    @Test
    public void testUpdateEmotionalState() {

        Emotion newEmo = new Emotion("OtherEmotion", 2);

        // Add new emotion
        ais.updateState(newEmo);

        // Check intensities
        assertEquals(1d, ais.get(0).getIntensity(), 10E-15);
        assertEquals(2d, ais.get(1).getIntensity(), 10E-15);

    }

    @Test
    public void testUpdateEmotionalStateDuplicate() {

        Emotion update = new Emotion("TestEmotion", 2);

        // Check initial intensity
        assertEquals(1d, ais.get(0).getIntensity(), 10E-15);

        // Add new emotion with same name, but new intensity
        ais.updateState(update);

        // Check intensity
        assertEquals(3d, ais.get(0).getIntensity(), 10E-15);

    }

    @Test
    public void testGetEmotionalStateGain() {

        // Check empty gain factor
        AgentInternalState test = ais.getState(1d);

        assertEquals(.5, test.get(0).getIntensity(), 10E-15);

    }

    @Test
    public void testGetEmotionalStateNoGain() {

        // Check empty gain factor
        AgentInternalState test = ais.getState(null);
        assertEquals(ais, test);

    }

    @Test
    public void testPrintEmotionalState() {

        System.out.println(ais.toString(null));

        String expectedNoGain = "TestEmotion: 1.0, ";
        String expectedGain = "TestEmotion: 0.5, ";

        assertEquals(expectedNoGain, ais.toString(null));
        assertEquals(expectedGain, ais.toString(1d));
    }

}
