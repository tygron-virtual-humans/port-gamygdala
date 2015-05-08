package gamygdala;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import agent.Agent;

/**
 * Test main Gamygdala engine.
 */
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

    assertNotNull(gamyg.gamydgalaMap.getAgentMap());
    assertNotNull(gamyg.gamydgalaMap.getGoalMap());
    assertNotNull(gamyg.decayFunction);

  }

//  @Test
//  public void testCreateAgent() {
//
//    // Start empty
//    assertTrue(gamyg.gamydgalaMap.getAgentMap().size() == 0);
//
//    // Add Agents
//    gamyg.createAgent(new Agent("testAgent"));
//
//    // Has added agent?
//    assertTrue(gamyg.gamydgalaMap.getAgentMap().size() == 1);
//
//    // Verify agent exists
//    assertNotNull(gamyg.gamydgalaMap.getAgentMap().get("testAgent"));
//
//  }

  @Test
  public void testRegisterAgent() {
    Agent agent = new Agent("RegisteredAgent");
    gamyg.registerAgent(agent);
    assertNotNull(gamyg.gamydgalaMap.getAgentMap().get("RegisteredAgent"));
    assertEquals(agent, gamyg.gamydgalaMap.getAgentMap().get("RegisteredAgent"));
  }

  // @Test
  // public void testCreateGoalForAgentAgentStringDouble() {
  // fail("Not yet implemented");
  // }

//  @Test
//  public void testCreateGoalForAgentAgentStringDoubleBoolean() {
//
//    Agent agent = new Agent("TestAgent");
//
//    // Can't add goal for agent that does not exist.
//    assertNull(gamyg.createGoalForAgent(agent, "", 0, true));
//    gamyg.registerAgent(agent);
//
//    // Goal does not already exist
//    Goal target = new Goal("TestGoal", .5, true);
//    Goal test = gamyg.createGoalForAgent(agent, "TestGoal", .5, true);
//    assertEquals(target, test);
//
//  }

//  @Test
//  public void testCreateGoalForAgentAgentStringDoubleBoolean2() {
//
//    Agent agent = new Agent("TestAgent");
//    gamyg.registerAgent(agent);
//
//    // Register three goals with the same name
//    // (first one original, other two with different maintenanceGoal attr but same name)
//    Goal test1 = gamyg.createGoalForAgent(agent, "TestGoal", .5, false);
//    Goal test2 = gamyg.createGoalForAgent(agent, "TestGoal", .7, false);
//    Goal test3 = gamyg.createGoalForAgent(agent, "TestGoal", .7, true);
//
//    // Verify the original Goal is returned when a goal with a similar name is added,
//    // with the isMaintenanceGoal attribute of the last instance.
//    assertEquals(test1, test2);
//    assertNotEquals(test2, test3);
//    assertNotEquals(test1, test3);
//
//    // Verify he goal stored with it's name is the first goal
//    assertEquals(test1, gamyg.gamydgalaMap.getGoalMap().get("TestGoal"));
//
//  }

  // @Test
  // public void testCreateRelation() {
  // fail("Not yet implemented");
  // }

//  @Test
//  public void testGetAgentByName() {
//
//    assertNull(gamyg.getAgentByName("TestAgent"));
//    gamyg.createAgent("TestAgent");
//    assertNotNull(gamyg.getAgentByName("TestAgent"));
//
//  }

//  @Test
//  public void testGetGoalByName() {
//
//    assertNull(gamyg.getGoalByName("TestGoal"));
//    gamyg.registerGoal(new Goal("TestGoal", 1, false));
//    assertNotNull(gamyg.getGoalByName("TestGoal"));
//
//  }

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
