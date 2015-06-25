package agent;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import agent.data.Emotion;
import decayfunction.LinearDecay;

/**
 * Tests for Relation.
 */
public class RelationTest {

    Relation relation;
    Agent agent;
    Agent target;

    @Before
    public void setUp() throws Exception {
        target = mock(Agent.class);
        relation = new Relation(target, 0.5);

        when(target.toString()).thenReturn("agent");
    }

    @After
    public void tearDown() throws Exception {
        target = null;
        relation = null;
    }

    @Test
    public void testGetAgent() throws Exception {
        assertEquals(target, relation.getAgent());
    }

    @Test
    public void testGetLike() throws Exception {
        assertEquals(0.5, relation.getLike(), 1E-15);
    }

    @Test
    public void testSetLike() throws Exception {
        assertEquals(0.5, relation.getLike(), 1E-15);

        relation.setLike(0.4);
        assertEquals(0.4, relation.getLike(), 1E-15);
    }

    @Test
    public void testGetEmotions() throws Exception {
        assertEquals(new ArrayList<Emotion>(), relation.getEmotions());
    }

    @SuppressWarnings("serial")
    @Test
    public void testAddEmotion() throws Exception {
        Emotion emotion = new Emotion("henk", .5);

        relation.addEmotion(emotion);
        List<Emotion> list = new ArrayList<Emotion>(){{ add(new Emotion("henk", 0.5)); }};
        assertEquals(list, relation.getEmotions());
    }

    @SuppressWarnings("serial")
    @Test
    public void testAddEmotionTwice() throws Exception {
        Emotion emotion = new Emotion("henk", .5);
        relation.addEmotion(emotion);
        relation.addEmotion(emotion);

        List<Emotion> list = new ArrayList<Emotion>(){{ add(new Emotion("henk", 1.0)); }};
        assertEquals(list, relation.getEmotions());
    }

    @SuppressWarnings("serial")
    @Test
    public void testDecay() throws Exception {
        Emotion emotion = new Emotion("henk", .5);
        relation.addEmotion(emotion);

        relation.decay(new LinearDecay(0.3), 1000);
        List<Emotion> list = new ArrayList<Emotion>(){{ add(new Emotion("henk", .2)); }};
        assertEquals(list, relation.getEmotions());
    }

    @Test
    public void testDecayTwice() throws Exception {
        Emotion emotion = new Emotion("henk", .5);
        relation.addEmotion(emotion);

        relation.decay(new LinearDecay(0.3), 2000);
        List<Emotion> list = new ArrayList<Emotion>();
        assertEquals(list, relation.getEmotions());
    }

    @Test
    public void testGetRelationString() throws Exception {
        Emotion emotion = new Emotion("henk", .5);
        relation.addEmotion(emotion);

        assertEquals("henk(0.5)", relation.getRelationString());
    }

    @Test
    public void testEquals() throws Exception {
        // Other = null
        assertEquals(false, relation.equals(null));

        // Other.name is different
        relation.setLike(0.2);
        assertEquals(false, relation.equals(new Relation(target, 0.5)));

        // Other.utility is different
        relation.addEmotion(new Emotion("pizza", 0.5));
        assertEquals(false, relation.equals(new Relation(target, 0.5)));

        // Other is this
        assertEquals(true, relation.equals(relation));
    }

    @Test
    public void testToString() throws Exception {
        assertEquals("<Relation[causalAgent=agent, like=0.5]>", relation.toString());
    }
}
