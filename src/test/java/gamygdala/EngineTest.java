package gamygdala;

import agent.Agent;
import data.Belief;
import data.Goal;
import data.map.AgentMap;
import data.map.GamygdalaMap;
import decayfunction.DecayFunction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class EngineTest {

    Engine engine;
    Gamygdala gamygdala;

    @Before
    public void setUp() throws Exception {
        gamygdala = mock(Gamygdala.class);
        engine = Engine.getInstance();
        engine.setGamygdala(gamygdala);
    }

    @After
    public void tearDown() throws Exception {
        gamygdala = null;
        engine = null;
    }

    @Test
    public void testGetInstance() throws Exception {
        // Create "new" Engine instance
        Engine engine2 = Engine.getInstance();

        // Verify that it's the same object
        assertTrue(engine.equals(engine2));
    }

    @Test
    public void testCreateAgent() throws Exception {
        // Create Agent
        when(gamygdala.createAgent("TestAgent")).thenReturn(new Agent("TestAgent"));
        Agent newAgent = engine.createAgent("TestAgent");

        // Check name of Agent
        assertEquals(new Agent("TestAgent"), newAgent);
    }



    @Test
    public void testCreateGoalForAgent() throws Exception {
        // Mock Gamygdala
        Gamygdala mock = mock(Gamygdala.class);
        engine.setGamygdala(mock);

        engine.createGoalForAgent(new Agent("Mario"), "Save Peace", .51, true);
        verify(mock).createGoalForAgent(new Agent("Mario"), "Save Peace", .51, true);
    }

    @Test
    public void testCreateRelation() throws Exception {
        Gamygdala mock = mock(Gamygdala.class);
        engine.setGamygdala(mock);

        Agent source = mock(Agent.class);
        Agent target = new Agent("Target");

        engine.createRelation(source, target, .59);
        verify(mock).createRelation(source, target, .59);
    }

    @Test
    public void testDecayAll() throws Exception {
        engine.decayAll();

        verify(gamygdala).decayAll(any(Long.class), any(Long.class));
    }

    @Test
    public void testAppraise() throws Exception {
        Belief belief = mock(Belief.class);

        // No agent specified
        engine.appraise(belief);
        verify(gamygdala).appraise(belief, null);

        // With agent
        Agent testAgent = new Agent("TestAgent");
        engine.appraise(belief, testAgent);
        verify(gamygdala).appraise(belief, testAgent);
    }

    @Test
    public void testAppraise1() throws Exception {

    }

    @Test
    public void testSetGain() throws Exception {
        // Create GamygdalaMap with one Agent
        AgentMap map = new AgentMap();
        Agent agent = mock(Agent.class);
        map.put(agent.getName(), agent);

        // Edge cases
        assertFalse(engine.setGain(-1));
        assertFalse(engine.setGain(21));
        assertFalse(engine.setGain(0));

        // Test set gain
        assertTrue(engine.setGain(20));
        assertTrue(engine.setGain(1));
    }

    @Test
    public void testSetDecay() throws Exception {
        DecayFunction df = mock(DecayFunction.class);

        // Check DecayFunction null
        engine.setDecay(59d, null);
        verify(gamygdala, times(1)).setDecayFactor(59d);
        verify(gamygdala, times(0)).setDecayFunction(df);

        // Check DecayFunction not null
        engine.setDecay(10d, df);
        verify(gamygdala).setDecayFactor(10d);
        verify(gamygdala).setDecayFunction(df);
    }

    @Test
    public void testGetGamygdala() throws Exception {
        Engine e = mock(Engine.class);
        Gamygdala gam = new Gamygdala();

        when(e.getGamygdala()).thenReturn(gam);
        assertEquals(gam, e.getGamygdala());
    }

    @Test
    public void testSetGamygdala() throws Exception {
        assertEquals(gamygdala, engine.getGamygdala());

        Gamygdala gam = new Gamygdala();
        assertNotEquals(engine.getGamygdala(), gam);

        engine.setGamygdala(gam);
        assertEquals(gam, engine.getGamygdala());
    }

    @Test
    public void testResetWithoutInstance() throws Exception {
        Engine e = Engine.getInstance();
        assertNotEquals(e, Engine.resetEngine());
    }
}