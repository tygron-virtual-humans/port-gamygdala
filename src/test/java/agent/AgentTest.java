package agent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import data.Goal;
import gamygdala.Gamygdala;

/**
 * Test Agent class.
 */
public class AgentTest {

  Agent agent;

  @Before
  public void setUp() {
    agent = new Agent("TestAgent");
  }

  @After
  public void cleanUp() {
    agent = null;
  }

  @Test
  public void testAgent() {

    // Verify name
    assertEquals("TestAgent", agent.name);

    // Verify maps / collections instantiated
    assertNotNull(agent.goals);
    assertNotNull(agent.currentRelations);
    assertNotNull(agent.internalState);

    // Check initial gain
    assertEquals(1, agent.gain, 10E-15);
  }

  @Test
  public void testAddRemoveHasGoal() {

    Goal goal = new Goal("TestGoal", 1, false);

    // Clean start
    assertFalse(agent.hasGoal(goal));

    // Add / Remove goals
    agent.addGoal(goal);
    assertTrue(agent.hasGoal(goal));
    agent.removeGoal(goal);
    assertFalse(agent.hasGoal(goal));

  }

  @Test
  public void testAddRemoveHasNullGoal() {

    Goal g2 = null;

    // Assert that no NPE's are thrown
    assertFalse(agent.hasGoal(g2));
    assertFalse(agent.addGoal(g2));
    assertFalse(agent.removeGoal(g2));
  }

  @Test
  public void testAddRemoveExistingGoal() {

    Goal g1 = new Goal("TestGoal", 1, false);
    Goal g2 = new Goal("TestGoal", 1, false);

    // Clean start
    assertFalse(agent.hasGoal(g1));
    assertFalse(agent.hasGoal(g2));

    // Add goal1, then verify goal2 cannot be added because it's the same
    agent.addGoal(g1);
    assertFalse(agent.addGoal(g2));

    // Verify no goals remain
    agent.removeGoal(g1);
    assertFalse(agent.hasGoal(g1));
    assertFalse(agent.hasGoal(g2));

  }

  @Test
  public void testGetGoalByName() {

    Goal goal = new Goal("TestGoal", 1, true);
    agent.addGoal(goal);

    assertNotNull(agent.goals.get(goal.getName()));
    assertEquals(goal, agent.goals.get(goal.getName()));

  }

  @Test
  public void testSetGain() {

    // Verify default gain is set
    assertEquals(Agent.DEFAULT_GAIN, agent.gain, 10E-15);

    // Set gain to allowed value and verify
    agent.setGain(2);
    assertEquals(2, agent.gain, 10E-15);

    // Set gain to disallowed value and verify the gain has not been changed
    agent.setGain(0);
    agent.setGain(20.01);
    assertEquals(2, agent.gain, 10E-15);

  }

  // @Test
  // public void testAppraise() {
  // fail("Not yet implemented");
  // }
  //
  // @Test
  // public void testUpdateEmotionalState() {
  // fail("Not yet implemented");
  // }
  //
  // @Test
  // public void testGetEmotionalState() {
  // fail("Not yet implemented");
  // }
  //
  // @Test
  // public void testGetPADState() {
  // fail("Not yet implemented");
  // }
  //
  // @Test
  // public void testPrintEmotionalState() {
  // fail("Not yet implemented");
  // }

  @Test
  public void testUpdateRelation() {

    // Verify new relation is added
    Agent agent = new Agent("TestAgent");
    agent.updateRelation(new Agent("TestName"), 0.5);

    assertTrue(agent.hasRelationWith(new Agent("TestName")));

    assertNotNull(agent.getRelation(new Agent("TestName")));
    assertEquals(2, agent.getRelation(new Agent("TestName")).like, 10E-15);

  }

  @Test
  public void testAddGetHasRelation() {

    // Check NPE's
    assertNull(agent.getRelation(null));

    // Verify new relation is added
    assertFalse(agent.hasRelationWith(new Agent("TestName")));
    Relation rel = new Relation(new Agent("TestName"), 1);
    agent.updateRelation(new Agent("TestName"), 1);
    assertEquals(rel, agent.getRelation(new Agent("TestName")));
    assertTrue(agent.hasRelationWith(new Agent("TestName")));

  }

  // @Test
  // public void testPrintRelations() {
  // fail("Not yet implemented");
  // }
  //
  // @Test
  // public void testDecay() {
  // fail("Not yet implemented");
  // }

//  @Test
//  public void testGetSetGamygdalaInstance() {
//
//    assertNull(agent.getGamygdalaInstance());
//
//    Gamygdala gamyg = new Gamygdala();
//    agent.setGamygdalaInstance(gamyg);
//
//    assertEquals(gamyg, agent.getGamygdalaInstance());
//
//  }

  @Test
  public void testToString() {

    assertEquals("<Agent[TestAgent]>", agent.toString());

  }

}
