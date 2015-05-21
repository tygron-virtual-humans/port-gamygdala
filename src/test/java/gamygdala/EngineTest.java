package gamygdala;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import agent.Agent;
import data.Belief;
import data.Goal;
import data.map.GamygdalaMap;
import decayfunction.DecayFunction;

/**
 * Tests for Engine class.
 */
public class EngineTest {

    Engine engine;
    Gamygdala gamygdala;

    @Before
    public void setUp() {
        gamygdala = mock(Gamygdala.class);
        engine = Engine.getInstance();
        engine.setGamygdala(gamygdala);
    }

    @After
    public void tearDown() {
        gamygdala = null;
        engine = null;
    }

    @Test
    public void testSingleton() {
        
        // Create "new" Engine instance
        Engine engine2 = Engine.getInstance();
        
        // Verify that it's the same object
        assertTrue(engine == engine2);
        
    }
    
    @Test
    public void testCreateAgent() {

        // Mock GamygdalaMap
        GamygdalaMap map = mock(GamygdalaMap.class);
        when(gamygdala.getGamygdalaMap()).thenReturn(map);

        // Create Agent
        Agent newAgent = engine.createAgent("TestAgent");
        verify(map).registerAgent(any(Agent.class));

        // Check name of Agent
        assertEquals("TestAgent", newAgent.name);
    }

    @Test
    public void testCreateGoalForAgent() {

        // Mock GamygdalaMap
        GamygdalaMap map = mock(GamygdalaMap.class);
        when(gamygdala.getGamygdalaMap()).thenReturn(map);

        // Create Goal
        Agent newAgent = mock(Agent.class);
        Goal newGoal = engine.createGoalForAgent(newAgent, "TestGoal", .59, false);

        // Check Agent and GamygdalaMap interactions
        verify(newAgent).addGoal(any(Goal.class));
        verify(map).registerGoal(any(Goal.class));

        // Check properties of Goal
        assertEquals("TestGoal", newGoal.getName());
        assertEquals(.59, newGoal.getUtility(), 10E-15);
        assertEquals(false, newGoal.isMaintenanceGoal());

    }

    @Test
    public void testCreateRelation() {

        Agent source = mock(Agent.class);
        Agent target = new Agent("Target");

        engine.createRelation(source, target, .59);

        verify(source).updateRelation(target, .59);

    }

    @Test
    public void testDecayAll() {

        engine.decayAll();

        verify(gamygdala).decayAll(any(Long.class), any(Long.class));

    }

    @Test
    public void testAppraise() {

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
    public void testSetGain() {

        // Create GamygdalaMap with one Agent
        GamygdalaMap map = new GamygdalaMap();
        Agent agent = mock(Agent.class);
        map.registerAgent(agent);
        when(gamygdala.getGamygdalaMap()).thenReturn(map);

        // Edge cases
        assertFalse(engine.setGain(-1));
        assertFalse(engine.setGain(21));
        assertFalse(engine.setGain(0));

        // Test set gain
        assertTrue(engine.setGain(20));
        verify(agent).setGain(20);

    }

    @Test
    public void testSetDecay() {
        
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
    public void testGetSetGamygdala() {
        
        assertEquals(gamygdala, engine.getGamygdala());
        
        Gamygdala gam = new Gamygdala();
        assertNotEquals(engine.getGamygdala(), gam);
        
        engine.setGamygdala(gam);
        assertEquals(gam, engine.getGamygdala());
        
    }
}
