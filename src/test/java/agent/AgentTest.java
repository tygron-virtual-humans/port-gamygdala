package agent;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import agent.data.Emotion;
import agent.data.Goal;

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
        assertEquals("TestAgent", agent.getName());

        // Verify maps / collections instantiated
        assertNotNull(agent.getGoals());
        assertNotNull(agent.getCurrentRelations());
        assertNotNull(agent.getInternalState());

        // Check initial gain
        assertEquals(1, agent.getGain(), 10E-15);
    }

    @Test
    public void testAddRemoveHasGoal() {

        Goal goal = new Goal("TestGoal", 1, false);

        // Clean start
        assertFalse(agent.hasGoal(goal));

        // Add / Remove goals
        agent.getGoals().put(goal.getName(), goal);
        assertTrue(agent.hasGoal(goal));
        agent.removeGoal(goal);
        assertFalse(agent.hasGoal(goal));
    }

    @Test
    public void testAddRemoveHasNullGoal() {

        Goal g2 = null;

        // Assert that no NPE's are thrown
        assertFalse(agent.hasGoal(g2));

        agent.getGoals().put(null, g2);
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
        agent.getGoals().put(g1.getName(), g1);
        assertEquals(null, agent.getGoals().put(g1.getName(), g1));

        // Verify no goals remain
        agent.removeGoal(g1);
        assertFalse(agent.hasGoal(g1));
        assertFalse(agent.hasGoal(g2));

    }

    @Test
    public void testGetGoalByName() {

        Goal goal = new Goal("TestGoal", 1, true);
        agent.getGoals().put(goal.getName(), goal);

        assertNotNull(agent.getGoals().get(goal.getName()));
        assertEquals(goal, agent.getGoals().get(goal.getName()));

        assertEquals(goal, agent.getGoalByName("TestGoal"));

    }

    @Test
    public void testSetGain() {
        // Verify default gain is set
        assertEquals(Agent.DEFAULT_GAIN, agent.getGain(), 10E-15);

        // Set gain to allowed value and verify
        agent.setGain(2);
        assertEquals(2, agent.getGain(), 10E-15);

        // Set gain to disallowed value and verify the gain has not been changed
        agent.setGain(0);
        agent.setGain(20.01);
        assertEquals(2, agent.getGain(), 10E-15);
    }

    @Test
    public void testUpdateEmotionalState() {
        // Update emotional state
        AgentInternalState agentInternalState = mock(AgentInternalState.class);
        agent.setInternalState(agentInternalState);

        Emotion emotion = mock(Emotion.class);

        agent.getInternalState().updateState(emotion);
        verify(agentInternalState).updateState(emotion);
    }

    @Test
    public void testGetEmotionalState() {
        // Get emotional state
        AgentInternalState agentInternalState = mock(AgentInternalState.class);
        agent.setInternalState(agentInternalState);

        agent.getInternalState().getState(null);

        // Verify the right method is called on AgentInternalState.
        verify(agentInternalState).getState(null);

    }

    @Test
    public void testToStringEmotionalState() {
        // GAIN = FALSE

        // Print emotional state
        assertEquals("TestAgent feels ", agent.toStringEmotionalState(false));

        // GAIN = TRUE

        // Print emotional state (gain = true)
        assertEquals("TestAgent feels ", agent.toStringEmotionalState(true));

    }

    @Test
    public void testUpdateRelation() {

        // Mock currentRelations
        AgentRelations relations = mock(AgentRelations.class);
        agent.setCurrentRelations(relations);

        // Invalid relation factor
        agent.updateRelation(mock(Agent.class), 100);

        verifyZeroInteractions(relations);

        // Correct relation factor
        agent.updateRelation(mock(Agent.class), -1);

        // Verify iteraction with currentRelations
        verify(relations, times(1)).updateRelation(any(Agent.class), any(Double.class));

    }

    @Test
    public void testHasRelationWith() {
        AgentRelations agentRelations = mock(AgentRelations.class);
        agent.setCurrentRelations(agentRelations);

        // Invalid relation factor
        Agent agent1 = mock(Agent.class);
        agent.hasRelationWith(agent1);

        // Verify iteraction with currentRelations
        verify(agentRelations).hasRelationWith(agent1);

    }

    @Test
    public void testAgentActionsEmptyCausalAgent() {
        assertNull(agent.agentActions(agent, null, 0));
    }

    @Test
    public void testAgentActionsCaseOne_NoRelation() {

        Agent causalAgent = new Agent("CausalAgent");

        // CASE 1

        Emotion result = agent.agentActions(agent, causalAgent, .25);
        assertEquals(new Emotion("gratitude", .25), result);
    }

    @Test
    public void testAgentActionsCaseOne_HasRelation() {

        Agent causalAgent = new Agent("CausalAgent");

        // Add relation
        agent.updateRelation(causalAgent, 1);

        // CASE 1
        Emotion result = agent.agentActions(agent, causalAgent, .25);
        Emotion expected = new Emotion("gratitude", .25);
        assertEquals(expected, result);

        // Verify added to relation
        assertEquals(expected, agent.getCurrentRelations().get(0).getEmotions().get(0));
    }

    @Test
    public void testAgentActionsCaseTwo() {
        assertEquals(null, agent.agentActions(agent, agent, .25));
    }


    @Test
    public void testAgentActionsCaseThree_NoRelation() {
        Agent causalAgent = new Agent("CausalAgent");

        // CASE 3
        assertNull(agent.agentActions(causalAgent, agent, .25));
    }

    @Test
    public void testAgentActionsCaseThree_HasRelation() {

        Agent causalAgent = new Agent("CausalAgent");

        // Add relation
        agent.updateRelation(causalAgent, 1);

        // CASE 3
        Emotion result = agent.agentActions(causalAgent, agent, -.25);
        Emotion expected = new Emotion("remorse", .25);
        assertEquals(expected, result);

        // Verify added to relation
        assertEquals(expected, agent.getCurrentRelations().get(0).getEmotions().get(0));
    }


    @Test
    public void testEvaluateSocialEmotion() {

        Relation relation = mock(Relation.class);

        // Test four emotions
        when(relation.getLike()).thenReturn(0d);
        assertEquals("happy-for", agent.getCurrentRelations().evaluateSocialEmotion(relation, 1).getName());

        when(relation.getLike()).thenReturn(-.1d);
        assertEquals("resentment", agent.getCurrentRelations().evaluateSocialEmotion(relation, 1).getName());

        when(relation.getLike()).thenReturn(0d);
        assertEquals("pity", agent.getCurrentRelations().evaluateSocialEmotion(relation, -1).getName());

        when(relation.getLike()).thenReturn(-.1d);
        assertEquals("gloating", agent.getCurrentRelations().evaluateSocialEmotion(relation, -1).getName());

    }

    @Test
    public void testToString() {
        assertEquals("<Agent[TestAgent, 1.0]>", agent.toString());
    }


    @Test
    public void testEquals() throws Exception {
        // Equals with null
        assertFalse(agent.equals(null));

        // Equals with different Object
        assertFalse(agent.equals(new Object()));

        // Equals with Double
        agent.setGain(0.5);
        Agent expected = new Agent("TestAgent");
        expected.setGain(0.51);
        assertFalse(agent.equals(expected));

        // Equals with different goals
        agent.getGoals().put("Save Peace", new Goal("Save Peace", 0.5, true));
        Agent goals = new Agent("TestAgent");

        agent.getGoals().put("Kill mario", new Goal("Kill mario", 0.4, false));
        assertFalse(agent.equals(goals));
    }

}
