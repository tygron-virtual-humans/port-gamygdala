package gamygdala;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import agent.Agent;
import data.Goal;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GamygdalaTest {

  Gamygdala gamyg;

  @Before
  public void setUp() {
    gamyg = new Gamygdala();
  }

  @After
  public void cleanUp() {
    gamyg = null;
  }

  @Test
  public void testGamygdala() {

    assertNotNull(gamyg.agents);
    assertNotNull(gamyg.goals);
    assertNotNull(gamyg.decayFunction);

  }

  @Test
  public void testCreateAgent() {

    // Start empty
    assertTrue(gamyg.agents.size() == 0);

    // Add Agents
    gamyg.createAgent("testAgent");

    // Has added agent?
    assertTrue(gamyg.agents.size() == 1);

    // Verify agent exists
    assertNotNull(gamyg.agents.get("testAgent"));

  }

  @Test
  public void testRegisterAgent() {
    Agent agent = new Agent("RegisteredAgent");
    gamyg.registerAgent(agent);
    assertNotNull(gamyg.agents.get("RegisteredAgent"));
    assertEquals(agent, gamyg.agents.get("RegisteredAgent"));
  }

  // @Test
  // public void testCreateGoalForAgentAgentStringDouble() {
  // fail("Not yet implemented");
  // }

  @Test
  public void testCreateGoalForAgentAgentStringDoubleBoolean() {

    Agent agent = new Agent("TestAgent");

    // Can't add goal for agent that does not exist.
    assertNull(gamyg.createGoalForAgent(agent, "", 0, true));
    gamyg.registerAgent(agent);

    // Goal does not already exist
    Goal target = new Goal("TestGoal", .5, true);
    Goal test = gamyg.createGoalForAgent(agent, "TestGoal", .5, true);
    assertEquals(target, test);

  }

  @Test
  public void testCreateGoalForAgentAgentStringDoubleBoolean2() {

    Agent agent = new Agent("TestAgent");
    gamyg.registerAgent(agent);

    // Register three goals with the same name
    // (first one original, other two with different maintenanceGoal attr but same name)
    Goal test1 = gamyg.createGoalForAgent(agent, "TestGoal", .5, false);
    Goal test2 = gamyg.createGoalForAgent(agent, "TestGoal", .7, false);
    Goal test3 = gamyg.createGoalForAgent(agent, "TestGoal", .7, true);

    // Verify the original Goal is returned when a goal with a similar name is added,
    // with the isMaintenanceGoal attribute of the last instance.
    assertEquals(test1, test2);
    assertNotEquals(test2, test3);
    assertNotEquals(test1, test3);

    // Verify he goal stored with it's name is the first goal
    assertEquals(test1, gamyg.goals.get("TestGoal"));

  }

  // @Test
  // public void testCreateRelation() {
  // fail("Not yet implemented");
  // }

  @Test
  public void testGetAgentByName() {

    assertNull(gamyg.getAgentByName("TestAgent"));
    gamyg.createAgent("TestAgent");
    assertNotNull(gamyg.getAgentByName("TestAgent"));

  }

  @Test
  public void testGetGoalByName() {

    assertNull(gamyg.getGoalByName("TestGoal"));
    gamyg.registerGoal(new Goal("TestGoal", 1, false));
    assertNotNull(gamyg.getGoalByName("TestGoal"));

  }

  // @Test
  // public void testGetMillisPassed() {
  // fail("Not yet implemented");
  // }
  //
  // @Test
  // public void testDebug() {
  // fail("Not yet implemented");
  // }

}
