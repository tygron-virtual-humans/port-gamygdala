package agent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import data.Emotion;
import decayfunction.DecayFunction;

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
        assertEquals(0, rel.like, 10E-15);
        assertNotNull(rel.emotionList);
    }

    @Test
    public void testAddEmotion1() {

        Emotion emo = new Emotion("TestEmotion", 10);

        rel.addEmotion(emo);
        assertTrue(rel.emotionList.contains(emo));
    }

    @Test
    public void testAddEmotion2() {

        Emotion e1 = new Emotion("TestEmotion", 10);
        Emotion e2 = new Emotion("TestEmotion", 20);

        rel.addEmotion(e1);
        rel.addEmotion(e2);

        assertEquals(1, rel.emotionList.size());

        Emotion e3 = rel.emotionList.get(0);
        assertEquals("TestEmotion", e3.name);
        assertEquals(30, e3.intensity, 10E-15);

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
        verify(df).decay(e1.intensity, 1000);

        // Emotions are copied, so first retrieve the new emotion,
        // and then check the new intensity value

        // Check copy on keep
        assertNotEquals(59d, e1.intensity, 10E-15);

        // Check new emotion value
        Emotion e2 = rel.emotionList.get(0);
        assertEquals(59d, e2.intensity, 10E-15);
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
        assertEquals(0, rel.emotionList.size());

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
    public void testGetSetAgent() {

        Agent a2 = new Agent("Agent2");
        assertEquals(agent, rel.getAgent());

        rel.setAgent(a2);
        assertEquals(a2, rel.getAgent());

    }

    @Test
    public void testGetSetLike() {

        assertEquals(0, rel.getLike(), 10E-15);

        rel.setLike(59d);
        assertEquals(59d, rel.getLike(), 10E-15);
    }

    @Test
    public void testGetSetEmotionList() {

        ArrayList<Emotion> el = new ArrayList<Emotion>();
        el.add(new Emotion("", 0d));

        assertNotEquals(el, rel.getEmotionList());

        rel.setEmotionList(el);
        assertEquals(el, rel.getEmotionList());

    }

    @Test
    public void testToString() {

        String expected = "<Relation[causalAgent=" + agent + ", like=" + rel.like + "]>";
        assertEquals(expected, rel.toString());

    }

}
