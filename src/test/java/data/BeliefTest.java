package data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import agent.Agent;

/**
 * Unit tests for Belief.
 */
public class BeliefTest {
    Agent a;
    Goal goal;
    Belief belief;
    ArrayList<Goal> affectedGoals;
    ArrayList<Double> goalCongruence;

    @Before
    public void setUp() {
        a = new Agent("TestAgent");
        goal = new Goal("TestGoal", .2, true);

        affectedGoals = new ArrayList<Goal>();
        goalCongruence = new ArrayList<Double>();

        affectedGoals.add(goal);
        goalCongruence.add(.5);

        belief = new Belief(.8, a, affectedGoals, goalCongruence, true);
    }

    @After
    public void breakDown() {
        a = null;
        belief = null;
    }

    @Test
    public void testBelief() {
        assertTrue(belief.isIncremental());
        assertEquals(.8, belief.getLikelihood(), 10E-15);
        assertEquals(a, belief.getCausalAgent());

        HashMap<Goal, Double> goalCongruenceTestMap = new HashMap<Goal, Double>();
        goalCongruenceTestMap.put(goal, .5);

        assertEquals(goalCongruenceTestMap, belief.getGoalCongruenceMap());
    }

    @Test
    public void testSetters() {
        belief.setIsIncremental(false);
        assertFalse(belief.isIncremental());

        belief.setLikelihood(3.5);
        assertEquals(1.0, belief.getLikelihood(), 10E-15);

        Agent newAgent = new Agent("TestAgent2");
        belief.setCausalAgent(newAgent);
        assertEquals(newAgent, belief.getCausalAgent());

        HashMap<Goal, Double> newGoalCongruenceMap = new HashMap<Goal, Double>();
        newGoalCongruenceMap.put(new Goal("TestGoal2", 0.5, false), 0.2);
        belief.setGoalCongruenceMap(newGoalCongruenceMap);
        assertEquals(newGoalCongruenceMap, belief.getGoalCongruenceMap());
    }

    @Test
    public void testToString() {
        assertEquals("<Belief[CausalAgent = <Agent[TestAgent]>, likelihood = 0.8, incremental = true]>", belief.toString());
    }
}
