package gamygdala;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import agent.Agent;
import data.Belief;
import data.Goal;
import exception.GoalCongruenceMapException;

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
        Belief belief = new Belief(-1, agent2, affectedGoals, goalCongruences, false);

        assertTrue(engine.appraise(belief, agent1));
        
    }

}
