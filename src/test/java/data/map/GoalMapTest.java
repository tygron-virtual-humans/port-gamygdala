package data.map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import data.Goal;

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
    public void testPut() throws Exception {
        assertEquals(null, goalMap.put(testGoal.getName(), testGoal));

        assertTrue(goalMap.hasGoal(testGoal));
    }
}
