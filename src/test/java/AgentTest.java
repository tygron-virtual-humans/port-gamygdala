import static org.junit.Assert.*;

import org.junit.Test;

public class AgentTest {

	@Test
	public void testAgent() {
		Agent a = new Agent("TestAgent");

		// Verify name
		assertEquals("TestAgent", a.name);

		// Verify maps / collections instantiated
		assertNotNull(a.goals);
		assertNotNull(a.currentRelations);
		assertNotNull(a.internalState);

		// Check initial gain
		assertEquals(1, a.gain, 10E-15);
	}

	@Test
	public void testAddRemoveHasGoal() {
		Agent a = new Agent("TestAgent");
		Goal g = new Goal("TestGoal", 1, false);

		// Clean start
		assertFalse(a.hasGoal(g));

		// Add / Remove goals
		a.addGoal(g);
		assertTrue(a.hasGoal(g));
		a.removeGoal(g);
		assertFalse(a.hasGoal(g));

	}

	@Test
	public void testAddRemoveHasNullGoal() {
		Agent a = new Agent("TestAgent");
		Goal g2 = null;

		// Assert that no NPE's are thrown
		assertFalse(a.hasGoal(g2));
		assertFalse(a.addGoal(g2));
		assertFalse(a.removeGoal(g2));
	}

	@Test
	public void testAddRemoveExistingGoal() {
		Agent a = new Agent("TestAgent");
		Goal g1 = new Goal("TestGoal", 1, false);
		Goal g2 = new Goal("TestGoal", 1, false);

		// Clean start
		assertFalse(a.hasGoal(g1));
		assertFalse(a.hasGoal(g2));

		// Add goal1, then verify goal2 cannot be added because it's the same
		a.addGoal(g1);
		assertFalse(a.addGoal(g2));

		// Verify no goals remain
		a.removeGoal(g1);
		assertFalse(a.hasGoal(g1));
		assertFalse(a.hasGoal(g2));

	}

	@Test
	public void testGetGoalByName() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetGain() {
		fail("Not yet implemented");
	}

	@Test
	public void testAppraise() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateEmotionalState() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetEmotionalState() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetPADState() {
		fail("Not yet implemented");
	}

	@Test
	public void testPrintEmotionalState() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateRelation() {

		// Verify new relation is added
		Agent a = new Agent("TestAgent");
		a.updateRelation("TestName", 1);
		a.updateRelation("TestName", 2);

		assertTrue(a.hasRelationWith("TestName"));

		assertNotNull(a.getRelation("TestName"));
		assertEquals(2, a.getRelation("TestName").like, 10E-15);

	}

	@Test
	public void testAddGetHasRelation() {

		// Check NPE's
		Agent a = new Agent("TestAgent");
		assertNull(a.getRelation(null));

		// Verify new relation is added
		assertFalse(a.hasRelationWith("TestName"));
		Relation r = new Relation("TestName", 1);
		a.updateRelation("TestName", 1);
		assertEquals(r, a.getRelation("TestName"));
		assertTrue(a.hasRelationWith("TestName"));

	}

	@Test
	public void testPrintRelations() {
		fail("Not yet implemented");
	}

	@Test
	public void testDecay() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetSetGamygdalaInstance() {

		Agent a = new Agent("TestAgent");
		assertNull(a.getGamygdalaInstance());

		Gamygdala g = new Gamygdala();
		a.setGamygdalaInstance(g);

		assertEquals(g, a.getGamygdalaInstance());

	}

}
