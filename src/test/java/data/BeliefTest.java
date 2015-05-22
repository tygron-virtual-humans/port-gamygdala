package data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import exception.GoalCongruenceMapException;

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
    public void setUp() throws GoalCongruenceMapException {
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
    
    @Test(expected=GoalCongruenceMapException.class)
    public void testException() throws GoalCongruenceMapException {
        
        goalCongruence.add(.59);
        Belief belief_exception = new Belief(.8, new Agent(""), affectedGoals, goalCongruence, true);

    }

    @Test
    public void testToString() {
        assertEquals("<Belief[CausalAgent = <Agent[TestAgent]>, likelihood = 0.8, incremental = true]>", belief.toString());
    }
}
