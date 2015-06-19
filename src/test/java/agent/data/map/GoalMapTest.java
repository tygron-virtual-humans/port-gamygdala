package agent.data.map;

import agent.data.Goal;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GoalMapTest {

    GoalMap goalMap;
    Goal goal;

    /**
     * Setup: Create a AgentMap and a Mock of Agent.
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        goalMap = new GoalMap();
        goal = mock(Goal.class);

        when(goal.getName()).thenReturn("TestGoal");
        goalMap.put(goal.getName(), goal);
    }

    /**
     * Teardown.
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
        goal = null;
        goalMap = null;
    }

    /**
     * Remove Goal when it is in the GoalMap
     * @throws Exception
     */
    @Test
    public void testRemoveGoal() throws Exception {
        assertEquals(true, goalMap.removeGoal(goal));
    }

    /**
     * Remove Goal when it isn't in the GoalMap
     * @throws Exception
     */
    @Test
    public void testRemoveGoalFalse() throws Exception {
        goalMap.removeGoal(goal);
        assertEquals(false, goalMap.removeGoal(goal));
    }

    /**
     * HasGoal when GoalMap has the Goal
     * @throws Exception
     */
    @Test
    public void testHasGoal() throws Exception {
        assertEquals(true, goalMap.hasGoal(goal));
    }

    /**
     * HasGoal when GoalMap hasn't the Goal
     * @throws Exception
     */
    @Test
    public void testHasGoalFalse() throws Exception {
        assertEquals(false, goalMap.hasGoal(mock(Goal.class)));
    }

    /**
     * Get Goal when Name exists
     * @throws Exception
     */
    @Test
    public void testGetGoalByName() throws Exception {
        assertEquals(goal, goalMap.getGoalByName("TestGoal"));
    }

    /**
     * Get Goal when Name doens't exists.
     * @throws Exception
     */
    @Test
    public void testGetGoalByNameFalse() throws Exception {
        assertEquals(null, goalMap.getGoalByName("TestAgent"));
    }

    /**
     * Add null to Map
     * @throws Exception
     */
    @Test
    public void testPutNull() throws Exception {
        assertEquals(null, goalMap.put(goal.getName(), null));
    }

    /**
     * Add Goal to the Map twice
     * @throws Exception
     */
    @Test
    public void testPut() throws Exception {
        assertEquals(null, goalMap.put(goal.getName(), goal));
    }
}