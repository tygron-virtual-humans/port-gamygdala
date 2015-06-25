package agent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import agent.data.Emotion;

/**
 * Tests for Pleasure-Arousal-Dominance Map.
 */
public class PADMapTest {

    PADMap padMap;

    @Before
    public void setUp() throws Exception {
        padMap = PADMap.getInstance();
    }

    @After
    public void tearDown() throws Exception {
        padMap = null;
    }

    @Test
    public void testGetPadState() throws Exception {
        List<Emotion> emotions = new ArrayList<Emotion>();
        emotions.add(new Emotion("disappointment", 1));

        double[] pad = PADMap.getPadState(emotions, 0.9);
        double[] expected = new double[] {
                -0.3544222078760491,
                -0.11894273127753305,
                -0.20697858842188738
        };

        for (int i = 0; i < pad.length; i++) {
            assertEquals(expected[i], pad[i], 10E-15d);
        }
    }

    @Test
    public void testGetPadStateAgent() throws Exception {
        AgentInternalState emotions = new AgentInternalState();
        emotions.add(new Emotion("disappointment", 1));

        Agent agent = mock(Agent.class);
        AgentInternalState agentInternal = mock(AgentInternalState.class);

        when(agent.getInternalState()).thenReturn(agentInternal);
        when(agentInternal.getState(0.9)).thenReturn(emotions);


        double[] pad = PADMap.getPadState(agent, 0.9);
        double[] expected = new double[] {
                -0.3544222078760491,
                -0.11894273127753305,
                -0.20697858842188738
        };

        for (int i = 0; i < pad.length; i++) {
            assertEquals(expected[i], pad[i], 10E-15);
        }
    }

    @Test
    public void testGetInstance() throws Exception {
        assertNotNull(PADMap.getInstance());

        assertTrue(padMap.equals(PADMap.getInstance()));
    }

    @Test
    public void testDetermineEmotionNull() {
        List<Emotion> emotionList = PADMap.determineEmotions(0.0, 0, 0);

        assertTrue(emotionList == null);
    }

    @Test
    public void testDetermineEmotionElse() {
        List<Emotion> emotionList = PADMap.determineEmotions(0.0, 0.0, 1.2);

        assertTrue(emotionList == null);
    }

    @Test
    public void testDetermineEmotionOne() {
        List<Emotion> emotionList = PADMap.determineEmotions(1, 1, 0);

        List<Emotion> expected = new ArrayList<Emotion>();
        expected.add(new Emotion("disappointment", 1));
        expected.add(new Emotion("distress", 1));

        assertTrue(expected.equals(emotionList));
    }

    @Test
    public void testDetermineEmotionZero() {
        List<Emotion> emotionList = PADMap.determineEmotions(1, 1, 0);

        List<Emotion> expected = new ArrayList<Emotion>();
        expected.add(new Emotion("disappointment", 1));
        expected.add(new Emotion("distress", 1));

        assertTrue(expected.equals(emotionList));
    }

    @Test
    public void testDetermineEmotionBetween() {
        List<Emotion> emotionList = PADMap.determineEmotions(1, 1, 0.5);

        List<Emotion> expected = new ArrayList<Emotion>();
        expected.add(new Emotion("hope", 1));

        assertTrue(expected.equals(emotionList));
    }
}
