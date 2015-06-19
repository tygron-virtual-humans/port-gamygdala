package agent;

import agent.data.Emotion;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Tests for PADMap.
 */
public class PADMapTest {

    PADMap pad;

    @Before
    public void setUp() {
        pad = PADMap.getInstance();
    }

    @After
    public void tearDown() {
        pad = null;
    }

    @Test
    public void testGetInstance() {

        assertNotNull(pad);
        assertNotNull(PADMap.getInstance());

    }

    @Test
    public void testInitialSetup() {

        // Since there's no constructor, verify that the initial emotions are
        // added to the PADMap at the appropriate time.
        assertNotNull(PADMap.getMapPad());

        // 16 initial emotions
        assertEquals(16, PADMap.getMapPad().size());

    }

    @Test
    public void testSingleton() {

        PADMap otherPad = PADMap.getInstance();

        assertNotNull(otherPad);
        assertTrue(pad == otherPad);

    }

    @Test
    public void testThreadSafe() {

        // TODO(Sven): Test thread safety.

    }

    @Test
    public void testGetPadState() {

        double[] padState;
        ArrayList<Emotion> emotions = new ArrayList<Emotion>();

        // No emotions, so no result on PAD scale.
        padState = PADMap.getPadState(emotions, null);
        assertEquals(0, padState[0], 10E-15);
        assertEquals(0, padState[1], 10E-15);
        assertEquals(0, padState[2], 10E-15);

        // Add hope and gratitude
        emotions.add(new Emotion("hope", 1.59));
        emotions.add(new Emotion("gratitude", 1.59));

        padState = PADMap.getPadState(emotions, null);

        // Check calculations
        assertEquals(1.8285, padState[0], 10E-15);
        assertEquals(0.6201, padState[1], 10E-15);
        assertEquals(-0.1113, padState[2], 10E-15);

    }

    @Test
    public void testGetPadStateGain() {

        // TODO(anyone): Find out how gain works for the PAD map and write test.

    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetPadStateAgentDoubleNull() {

        // Test NPE
        Agent agent = null;
        PADMap.getPadState(agent, 59.2);

    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testGetPadStateArrayListDoubleNull() {

        // Test NPE
        ArrayList<Emotion> emotions = null;
        PADMap.getPadState(emotions, 59.2);

    }

    @Test
    public void testGetPadStateAgentDouble() {

        Agent agent = mock(Agent.class);
        when(agent.getEmotionalState(any(Double.class))).thenReturn(new AgentInternalState());
        PADMap.getPadState(agent, 59.2);

        verify(agent).getEmotionalState(59.2);

    }

    @Test
    public void testGetPadStateNormal() throws Exception {
        ArrayList<Emotion> emotions = new ArrayList<Emotion>();
        Emotion emotion = new Emotion("joy", 0.5);
        emotions.add(emotion);

        double[] expected = new double[]{0.38, 0.24, 0.175};
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], PADMap.getPadState(emotions, null)[i], 0.001);
        }
    }

    @Test
    public void testGetPadStateWithGain() throws Exception {
        ArrayList<Emotion> emotions = new ArrayList<Emotion>();
        Emotion emotion = new Emotion("remorse", 0.5);
        emotions.add(emotion);

        double[] expected = new double[]{-.0277, .0138, -.0167};
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], PADMap.getPadState(emotions, 0.1)[i], 0.001);
        }
    }

}
