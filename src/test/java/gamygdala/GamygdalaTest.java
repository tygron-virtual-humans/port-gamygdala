package gamygdala;

import agent.Agent;
import data.Belief;
import data.Goal;
import decayfunction.DecayFunction;
import decayfunction.ExponentialDecay;
import exception.GoalCongruenceMapException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Tests for the core Gamygdala functionality.
 */
public class GamygdalaTest {

    private Gamygdala engine;

    @Before
    public void setUp() {
        engine = new Gamygdala();
    }

    @After
    public void tearDown() {
        engine = null;
    }

    @Test
    public void testGamygdala() {

        // Verify that constructor properly initializes attributes
        assertNotNull(engine.getGamygdalaMap());
        assertEquals(.8, engine.getDecayFactor(), 10E-15);
        assertNotNull(engine.getDecayFunction());

    }

    @Test
    public void testAppraiseAssertions() {

        // Empty Belief
        assertFalse(engine.appraise(null, mock(Agent.class)));

        // No Goals
        assertTrue(engine.appraise(mock(Belief.class), mock(Agent.class)));
    }

    @Test
    public void testAppraiseAffectedAgent() throws GoalCongruenceMapException {

        // Set-up environment (two agents with one goal)
        Agent agent1 = new Agent("TestAgent_1");
        Agent agent2 = new Agent("TestAgent_2");
        engine.getGamygdalaMap().registerAgent(agent1);
        engine.getGamygdalaMap().registerAgent(agent1);

        Goal goal = new Goal("TestGoal", .59, false);
        engine.getGamygdalaMap().registerGoal(goal);
        agent1.addGoal(goal);
        agent2.addGoal(goal);
        
        // Create a belief
        ArrayList<Goal> affectedGoals = new ArrayList<Goal>(1);
        affectedGoals.add(goal);
        ArrayList<Double> goalCongruences = new ArrayList<Double>(1);
        goalCongruences.add(2d);
        Belief belief = null;
        try {
            belief = new Belief(-1, agent2, affectedGoals, goalCongruences, false);
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertTrue(engine.appraise(belief, agent1));
        
    }

    @Test
    public void testSetDecayFunction() throws Exception {
        DecayFunction decayFunction = new ExponentialDecay(0.5);
        engine.setDecayFunction(decayFunction);

        assertEquals(decayFunction, engine.getDecayFunction());
    }

    @Test
    public void testSetDecayFactor() throws Exception {
        Double decayFactor = 0.3;
        engine.setDecayFactor(decayFactor);

        assertEquals(decayFactor, engine.getDecayFactor(), 0.0001);
    }

    @Test
    public void testDecayAll() throws Exception {

    }

    @Test
    public void testGetAgentMap() throws Exception {

    }

    @Test
    public void testGetGoalMap() throws Exception {

    }

    @Test
    public void testDebug() throws Exception {

    }

    @Test
    public void testPrintAllEmotions() throws Exception {

    }

    @Test
    public void testSetAgentsGain() throws Exception {

    }

    @Test
    public void testCreateAgent() throws Exception {

    }

    @Test
    public void testCreateGoalForAgent() throws Exception {

    }

    @Test
    public void testCreateRelation() throws Exception {

    }
}
