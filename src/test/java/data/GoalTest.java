package data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GoalTest {

  Goal goal;

  @Before
  public void setUp() throws Exception {
    goal = new Goal("TestGoal", .5, false);
  }

  @After
  public void tearDown() throws Exception {
    goal = null;
  }

  @Test
  public void testGoalGoal() {

    // Check regular constructor

    assertEquals("TestGoal", goal.name);
    assertEquals(.5, goal.utility, 10E-15);
    assertEquals(false, goal.isMaintenanceGoal);

    // Check Goal copy constructor
    Goal g2 = new Goal(goal);
    assertEquals("TestGoal", g2.name);
    assertEquals(.5, g2.utility, 10E-15);
    assertEquals(false, g2.isMaintenanceGoal);

  }

  @Test
  public void testEqualsObject() {

    Goal g2 = new Goal("NotTheSameGoal", 2, true);
    assertFalse(goal.equals(g2));

    Goal g3 = new Goal(goal);
    assertTrue(goal.equals(g3));

    String s1 = "ThisIsObviouslyNotAGoal";
    assertFalse(goal.equals(s1));

  }

}