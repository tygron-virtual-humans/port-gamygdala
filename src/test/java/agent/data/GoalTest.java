package agent.data;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static junit.framework.TestCase.assertEquals;

/**
 * Created by svenpopping on 19/06/15.
 */
public class GoalTest {

    Goal goal;

    @Before
    public void setUp() throws Exception {
        goal = new Goal("TestGoal", 0.33, false);
    }

    @After
    public void tearDown() throws Exception {
        goal = null;
    }

    @Test
    public void testConstructorWithGoal() {
        goal.setLikelihood(0.2);
        Goal construct = new Goal(goal);

        assertEquals(true, goal.equals(construct));
    }

    @Test
    public void testGetName() throws Exception {
        assertEquals("TestGoal", goal.getName());
    }

    @Test
    public void testSetName() throws Exception {
        assertEquals("TestGoal", goal.getName());

        goal.setName("Goally");
        assertEquals("Goally", goal.getName());
    }

    @Test
    public void testGetUtility() throws Exception {
        assertEquals(0.33, goal.getUtility(), 1E-15);
    }

    @Test
    public void testSetUtility() throws Exception {
        assertEquals(0.33, goal.getUtility(), 1E-15);

        goal.setUtility(0.331);
        assertEquals(0.331, goal.getUtility(), 1E-15);
    }

    @Test
    public void testGetLikelihood() throws Exception {
        assertEquals(0.5, goal.getLikelihood(), 1E-15);
    }

    @Test
    public void testSetLikelihood() throws Exception {
        assertEquals(0.5, goal.getLikelihood(), 1E-15);

        goal.setLikelihood(0.51);
        assertEquals(0.51, goal.getLikelihood(), 1E-15);
    }

    @Test
    public void testIsMaintenanceGoal() throws Exception {
        assertEquals(false, goal.isMaintenanceGoal());
    }

    @Test
    public void testSetMaintenanceGoal() throws Exception {
        assertEquals(false, goal.isMaintenanceGoal());

        goal.setMaintenanceGoal(true);
        assertEquals(true, goal.isMaintenanceGoal());
    }

    @Test
    public void testToString() throws Exception {
        assertEquals("<Goal[TestGoal, 0.5, false]>", goal.toString());
    }

    @Test
    public void testEquals() throws Exception {
        // Other = null
        assertEquals(false, goal.equals(null));

        // Other.name is different
        assertEquals(false, goal.equals(new Goal("TestGoal1", 0.33, false)));

        // Other.utility is different
        assertEquals(false, goal.equals(new Goal("TestGoal", 0.4, false)));

        // Other.isMaintainable is different
        assertEquals(false, goal.equals(new Goal("TestGoal", 0.33, true)));

        // Other.likelihood is different
        Goal other = new Goal("TestGoal", 0.33, true);
        other.setLikelihood(0.4);
        assertEquals(false, goal.equals(other));


        // Other is the same
        assertEquals(true, goal.equals(new Goal("TestGoal", 0.33, false)));

        // Other is this
        assertEquals(true, goal.equals(goal));
    }

    @Test
    public void testHashCode() throws Exception {
        assertEquals(-323148574, goal.hashCode());
    }
}