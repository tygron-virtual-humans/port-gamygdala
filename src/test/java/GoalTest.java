import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GoalTest {

	Goal g;

	@Before
	public void setUp() throws Exception {
		g = new Goal("TestGoal", .5, false);
	}

	@After
	public void tearDown() throws Exception {
		g = null;
	}

	@Test
	public void testGoalGoal() {
		
		// Check regular constructor
		
		assertEquals("TestGoal", g.name);
		assertEquals(.5, g.utility, 10E-15);
		assertEquals(false, g.isMaintenanceGoal);
		
		// Check Goal copy constructor
		Goal g2 = new Goal(g);
		assertEquals("TestGoal", g2.name);
		assertEquals(.5, g2.utility, 10E-15);
		assertEquals(false, g2.isMaintenanceGoal);
		
	}

	@Test
	public void testEqualsObject() {
		
		Goal g2 = new Goal("NotTheSameGoal", 2, true);
		assertFalse(g.equals(g2));
		
		Goal g3 = new Goal(g);
		assertTrue(g.equals(g3));
		
		String s1 = "ThisIsObviouslyNotAGoal";
		assertFalse(g.equals(s1));
		
	}

}
