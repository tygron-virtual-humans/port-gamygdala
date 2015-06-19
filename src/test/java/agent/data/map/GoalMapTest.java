package agent.data.map;

import agent.data.Goal;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for GoalMap.
 */
public class GoalMapTest {

    GoalMap goalMap;
    Goal testGoal;

    @Before
    public void setUp() throws Exception {
        testGoal = new Goal("TestGoal", 0.5, true);
        goalMap = new GoalMap();
    }

    @After
    public void tearDown() throws Exception {
        goalMap = null;
    }

    @Test
    public void testAddGoal() throws Exception {
        assertTrue(goalMap.addGoal(testGoal));
        assertTrue(goalMap.hasGoal(testGoal));

        assertFalse(goalMap.addGoal(testGoal));
    }

    @Test
    public void testRemoveGoal() throws Exception {
        goalMap.addGoal(testGoal);

        assertTrue(goalMap.removeGoal(testGoal));

        assertFalse(goalMap.removeGoal(testGoal));
    }

    @Test
    public void testHasGoal() throws Exception {
        goalMap.addGoal(testGoal);

        assertTrue(goalMap.hasGoal(testGoal));

        assertFalse(goalMap.hasGoal(null));
    }

    @Test
    public void testGetGoalByName() throws Exception {
        goalMap.addGoal(testGoal);

        assertEquals(testGoal, goalMap.getGoalByName("TestGoal"));
    }

    @Test
    public void testGetGoalByNameGoalNotInMap() throws Exception{
        goalMap.addGoal(testGoal);

        assertEquals(null, goalMap.getGoalByName("TestGoalNameNotInMap"));
    }

    @Test
    public void testPut() throws Exception {
        assertEquals(null, goalMap.put(testGoal.getName(), testGoal));

        assertTrue(goalMap.hasGoal(testGoal));
    }

    @Test
    public void testPutAddingGoalASecondTime() throws Exception {
        goalMap.put(testGoal.getName(), testGoal);
        assertEquals(null, goalMap.put(testGoal.getName(), testGoal));

        assertTrue(goalMap.hasGoal(testGoal));
    }
}
