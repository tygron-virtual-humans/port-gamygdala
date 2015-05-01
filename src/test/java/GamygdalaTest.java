import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GamygdalaTest {

	Gamygdala g;

	@Before
	public void setUp() {
		g = new Gamygdala();
	}

	@After
	public void cleanUp() {
		g = null;
	}

	@Test
	public void testGamygdala() {

		assertNotNull(g.agents);
		assertNotNull(g.goals);
		assertNotNull(g.decayFunction);

	}

	@Test
	public void testCreateAgent() {

		// Start empty
		assertTrue(g.agents.size() == 0);

		// Add Agents
		g.createAgent("testAgent");

		// Has added agent?
		assertTrue(g.agents.size() == 1);

		// Verify agent exists
		assertNotNull(g.agents.get("testAgent"));

	}

	@Test
	public void testRegisterAgent() {
		Agent a = new Agent("RegisteredAgent");
		g.registerAgent(a);
		assertNotNull(g.agents.get("RegisteredAgent"));
		assertEquals(a, g.agents.get("RegisteredAgent"));
	}

	@Test
	public void testCreateGoalForAgentAgentStringDouble() {
		Agent a = new Agent("TestAgent");
		g.registerAgent(a);
		g.createGoalForAgent(a, "TestGoal", 1);
		assertFalse(g.agents.get("TestAgent").goals.get("TestGoal").isMaintenanceGoal);
	}

	@Test
	public void testCreateGoalForAgentAgentStringDoubleBoolean() {

		Agent a = new Agent("TestAgent");

		// Can't add goal for agent that does not exist.
		assertNull(g.createGoalForAgent(a, "", 0, true));
		g.registerAgent(a);

		// Goal does not already exist
		Goal target = new Goal("TestGoal", .5, true);
		Goal test = g.createGoalForAgent(a, "TestGoal", .5, true);
		assertEquals(target, test);

	}

	@Test
	public void testCreateGoalForAgentAgentStringDoubleBoolean2() {

		Agent a = new Agent("TestAgent");
		g.registerAgent(a);

		// Register three goals with the same name (first one original, other two with different maintenanceGoal attr but same name)
		Goal test1 = g.createGoalForAgent(a, "TestGoal", .5, false);
		Goal test2 = g.createGoalForAgent(a, "TestGoal", .7, false);
		Goal test3 = g.createGoalForAgent(a, "TestGoal", .7, true);

		// Verify the original Goal is returned when a goal with a similar name is added, with the isMaintenanceGoal attribute of the last instance.
		assertEquals(test1, test2);
		assertNotEquals(test2, test3);
		assertNotEquals(test1, test3);

		// Verify he goal stored with it's name is the first goal
		assertEquals(test1, g.goals.get("TestGoal"));

	}

//	@Test
//	public void testCreateRelation() {
//		fail("Not yet implemented");
//	}

	@Test
	public void testGetAgentByName() {
		
		assertNull(g.getAgentByName("TestAgent"));
		g.createAgent("TestAgent");
		assertNotNull(g.getAgentByName("TestAgent"));
		
	}

	@Test
	public void testGetGoalByName() {
		
		assertNull(g.getGoalByName("TestGoal"));
		g.registerGoal(new Goal("TestGoal", 1, false));
		assertNotNull(g.getGoalByName("TestGoal"));
		assertTrue(g.goals.size() == 1);
		g.registerGoal(new Goal("TestGoal", 1, false));
		assertTrue(g.goals.size() == 1);
	}

//	@Test
//	public void testGetMillisPassed() {
//		fail("Not yet implemented");
//	}

}
