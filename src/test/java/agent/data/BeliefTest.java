package agent.data;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import agent.Agent;
import exception.GoalCongruenceMapException;

/**
 * Tests for Belief.
 */
public class BeliefTest {

    Belief belief;
    Agent agent;
    Goal goal;
    ArrayList<Goal> goalArrayList;
    ArrayList<Double> goalCongruence;

    @Before
    public void setUp() throws Exception {

        agent = mock(Agent.class);
        goal = mock(Goal.class);

        when(agent.toString()).thenReturn("agent");

        goalArrayList = new ArrayList<Goal>();
        goalCongruence = new ArrayList<Double>();

        goalArrayList.add(goal);
        goalCongruence.add(.5);

        try {
            belief = new Belief(.8, agent, goalArrayList, goalCongruence, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() throws Exception {
        agent = null;
        belief = null;
    }

    @Test (expected = GoalCongruenceMapException.class)
    public void testExceptionInConstructor() throws Exception {
        ArrayList<Goal> arrayList = new ArrayList<Goal>();
        belief = new Belief(.8, agent, arrayList, goalCongruence, true);
    }

    @Test
    public void testGetLikelihood() throws Exception {
        assertEquals(.8, belief.getLikelihood(), 1E-15);
    }

    @Test
    public void testGetGoalCongruenceMap() throws Exception {
        HashMap<Goal, Double> map = new HashMap<Goal, Double>();
        map.put(goalArrayList.get(0), Math.min(1, Math.max(-1, .5)));

        assertEquals(map, belief.getGoalCongruenceMap());
    }

    @Test
    public void testIsIncremental() throws Exception {
        assertEquals(true, belief.isIncremental());
    }

    @Test
    public void testGetCausalAgent() throws Exception {
        assertEquals(agent, belief.getCausalAgent());
    }

    @Test
    public void testToString() throws Exception {
        assertEquals("<Belief[CausalAgent = agent, likelihood = 0.8, incremental = true]>", belief.toString());
    }
}
