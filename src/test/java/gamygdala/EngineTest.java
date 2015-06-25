package gamygdala;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import agent.Agent;
import agent.data.Belief;
import agent.data.Goal;

/**
 * Tests for Engine.
 */
public class EngineTest {

    Engine engine;
    Gamygdala gamygdala;

    @Before
    public void setUp() throws Exception {
        engine = Engine.getInstance();
        gamygdala = mock(Gamygdala.class);

        engine.setGamygdala(gamygdala);
    }

    @After
    public void tearDown() throws Exception {
        engine = Engine.resetEngine();
    }

    @Test
    public void testGetGamygdala() throws Exception {
        assertEquals(gamygdala, engine.getGamygdala());
    }

    @Test
    public void testSetGamygdala() throws Exception {
        assertEquals(gamygdala, engine.getGamygdala());

        engine.setGamygdala(mock(Gamygdala.class));
        assertNotEquals(gamygdala, engine.getGamygdala());
    }

    @Test
    public void testGetInstance() throws Exception {
        assertEquals(engine, Engine.getInstance());
    }

    @Test
    public void testResetEngine() throws Exception {
        assertNotEquals(engine, Engine.resetEngine());
    }

    @Test
    public void testCreateAgent() throws Exception {
        Agent mockAgent = mock(Agent.class);
        when(gamygdala.createAgent("stan")).thenReturn(mockAgent);

        assertEquals(mockAgent, engine.createAgent("stan"));
    }

    @Test
    public void testCreateGoalForAgent() throws Exception {
        Agent mockAgent = mock(Agent.class);
        Goal mockGoal = mock(Goal.class);
        when(gamygdala.createGoalForAgent(mockAgent, "save", 0.5, false)).thenReturn(mockGoal);

        assertEquals(mockGoal, engine.createGoalForAgent(mockAgent, "save", 0.5, false));
    }

    @Test
    public void testCreateRelation() throws Exception {
        Agent mockAgent = mock(Agent.class);

        engine.createRelation(mockAgent, mockAgent, 0.2);
        verify(gamygdala, times(1)).createRelation(mockAgent, mockAgent, 0.2);
    }

    @Test
    public void testDecayAll() throws Exception {
        engine.decayAll();
        verify(gamygdala, times(1)).decayAll(any(long.class), any(long.class));
    }

    @Test
    public void testAppraise() throws Exception {
        Belief belief = mock(Belief.class);

        engine.appraise(belief);
        verify(gamygdala, times(1)).appraise(belief, null);
    }

    @Test
    public void testAppraise1() throws Exception {
        Belief belief = mock(Belief.class);
        Agent mockAgent = mock(Agent.class);

        engine.appraise(belief, mockAgent);
        verify(gamygdala, times(1)).appraise(belief, mockAgent);
    }

    @Test
    public void testSetGain() throws Exception {
        assertTrue(engine.setGain(20));

        assertTrue(engine.setGain(0.1));

        assertFalse(engine.setGain(20.1));

        assertFalse(engine.setGain(-.1));
    }
    
}
