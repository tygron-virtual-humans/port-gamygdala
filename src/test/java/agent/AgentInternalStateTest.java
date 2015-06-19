package agent;

import agent.data.Emotion;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

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
        ais.updateEmotionalState(emo);

    }

    @After
    public void tearDown() {
        ais = null;
    }

    @Test
    public void testUpdateEmotionalState() {

        Emotion newEmo = new Emotion("OtherEmotion", 2);

        // Add new emotion
        ais.updateEmotionalState(newEmo);

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
        ais.updateEmotionalState(update);

        // Check intensity
        assertEquals(3d, ais.get(0).getIntensity(), 10E-15);

    }

    @Test
    public void testGetEmotionalStateGain() {

        // Check empty gain factor
        AgentInternalState test = ais.getEmotionalState(1d);

        assertEquals(.5, test.get(0).getIntensity(), 10E-15);

    }

    @Test
    public void testGetEmotionalStateNoGain() {

        // Check empty gain factor
        AgentInternalState test = ais.getEmotionalState(null);
        assertEquals(ais, test);

    }

    @Test
    public void testPrintEmotionalState() {
        
        System.out.println(ais.getEmotionalStateString(null));
        
        String expectedNoGain = "TestEmotion: 1.0, ";
        String expectedGain = "TestEmotion: 0.5, ";
        
        assertEquals(expectedNoGain, ais.getEmotionalStateString(null));
        assertEquals(expectedGain, ais.getEmotionalStateString(1d));
    }

}
