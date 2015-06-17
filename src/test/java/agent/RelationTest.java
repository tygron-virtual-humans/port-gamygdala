package agent;

import data.Emotion;
import decayfunction.DecayFunction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Test Relation class.
 */
public class RelationTest {

    private Agent agent;
    private Relation rel;

    @Before
    public void setUp() {
        agent = new Agent("TestAgent");
        rel = new Relation(agent, 0);
    }

    @After
    public void cleanUp() {
        rel = null;
    }

    @Test
    public void testRelation() {

        assertEquals(agent, rel.getAgent());
        assertEquals(0, rel.getLike(), 10E-15);
        assertNotNull(rel.getEmotions());
    }

    @Test
    public void testAddEmotion1() {

        Emotion emo = new Emotion("TestEmotion", 10);

        rel.addEmotion(emo);
        assertTrue(rel.getEmotions().contains(emo));
    }

    @Test
    public void testAddEmotion2() {

        Emotion e1 = new Emotion("TestEmotion", 10);
        Emotion e2 = new Emotion("TestEmotion", 20);

        rel.addEmotion(e1);
        rel.addEmotion(e2);

        assertEquals(1, rel.getEmotions().size());

        Emotion e3 = rel.getEmotions().get(0);
        assertEquals("TestEmotion", e3.getName());
        assertEquals(30, e3.getIntensity(), 10E-15);

    }

    @Test
    public void testDecay() {

        // Add emotion to relation
        Emotion e1 = new Emotion("TestEmotion", 10);
        rel.addEmotion(e1);

        // Mock DecayFunction
        DecayFunction df = mock(DecayFunction.class);
        when(df.decay(any(Double.class), any(Long.class))).thenReturn(59d);

        // Decay emotions (1sec passed)
        rel.decay(df, 1000);

        // Verify the DecayFunction has been invoked
        verify(df).decay(e1.getIntensity(), 1000);

        // Emotions are copied, so first retrieve the new emotion,
        // and then check the new intensity value

        // Check copy on keep
        assertNotEquals(59d, e1.getIntensity(), 10E-15);

        // Check new emotion value
        Emotion e2 = rel.getEmotions().get(0);
        assertEquals(59d, e2.getIntensity(), 10E-15);
    }

    @Test
    public void testDecayRemove() {

        // Add emotion to relation
        Emotion e1 = new Emotion("TestEmotion", 10);
        rel.addEmotion(e1);

        // Mock DecayFunction
        DecayFunction df = mock(DecayFunction.class);
        when(df.decay(any(Double.class), any(Long.class))).thenReturn(-.59);

        // Decay emotions (1sec passed)
        rel.decay(df, 1000);

        // Verify the emotion has gone
        assertEquals(0, rel.getEmotions().size());

    }

    @Test
    public void testEquals() {

        // Same Agent object
        Relation equalRelation = new Relation(agent, 0);
        assertTrue(rel.equals(equalRelation));

        // Similar Agent objects (but not equal)
        Agent similarAgent = new Agent("TestAgent");
        Relation similarRelation = new Relation(similarAgent, 0);
        assertFalse(rel.equals(similarRelation));

        // Null
        assertFalse(rel.equals(null));
    }

    @Test
    public void testToString() {

        String expected = "<Relation[causalAgent=" + agent + ", like=" + rel.getLike() + "]>";
        assertEquals(expected, rel.toString());

    }

}
