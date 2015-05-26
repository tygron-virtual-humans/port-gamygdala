package data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import agent.Agent;
import sun.jvm.hotspot.runtime.ConstructionException;

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


        try {
            belief = new Belief(.8, a, affectedGoals, goalCongruence, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    public void testToString() {
        assertEquals("<Belief[CausalAgent = <Agent[TestAgent]>, likelihood = 0.8, incremental = true]>", belief.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructionEngineDebug() {
        new Belief(.8, a, affectedGoals, new ArrayList<Double>(){{ add(.8); add(.7); }}, true);
    }
}
