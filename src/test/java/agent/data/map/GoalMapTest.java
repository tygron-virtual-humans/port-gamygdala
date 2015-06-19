package agent.data.map;

import agent.data.Goal;
import junit.framework.TestCase;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by svenpopping on 19/06/15.
 */
public class GoalMapTest extends TestCase {

    GoalMap goalMap;
    Goal goal;

    /**
     * Setup: Create a AgentMap and a Mock of Agent.
     * @throws Exception
     */
    public void setUp() throws Exception {
        super.setUp();
        goalMap = new GoalMap();
        goal = mock(Goal.class);

        when(goal.getName()).thenReturn("TestGoal");
        goalMap.put(goal.getName(), goal);
    }

    /**
     * Teardown.
     * @throws Exception
     */
    public void tearDown() throws Exception {
        goal = null;
        goalMap = null;
    }

    /**
     * Remove Goal when it is in the GoalMap
     * @throws Exception
     */
    public void testRemoveGoal() throws Exception {
        assertEquals(true, goalMap.removeGoal(goal));
    }

    /**
     * Remove Goal when it isn't in the GoalMap
     * @throws Exception
     */
    public void testRemoveGoalFalse() throws Exception {
        goalMap.removeGoal(goal);
        assertEquals(false, goalMap.removeGoal(goal));
    }

    /**
     * HasGoal when GoalMap has the Goal
     * @throws Exception
     */
    public void testHasGoal() throws Exception {
        assertEquals(true, goalMap.hasGoal(goal));
    }

    /**
     * HasGoal when GoalMap hasn't the Goal
     * @throws Exception
     */
    public void testHasGoalFalse() throws Exception {
        assertEquals(false, goalMap.hasGoal(mock(Goal.class)));
    }

    /**
     * Get Goal when Name exists
     * @throws Exception
     */
    public void testGetGoalByName() throws Exception {
        assertEquals(goal, goalMap.getGoalByName("TestGoal"));
    }

    /**
     * Get Goal when Name doens't exists.
     * @throws Exception
     */
    public void testGetGoalByNameFalse() throws Exception {
        assertEquals(null, goalMap.getGoalByName("TestAgent"));
    }

    /**
     * Add null to Map
     * @throws Exception
     */
    public void testPutNull() throws Exception {
        assertEquals(null, goalMap.put(goal.getName(), null));
    }

    /**
     * Add Goal to the Map twice
     * @throws Exception
     */
    public void testPut() throws Exception {
        assertEquals(null, goalMap.put(goal.getName(), goal));
    }
}