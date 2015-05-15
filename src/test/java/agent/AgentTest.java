package agent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import data.Emotion;
import data.Goal;

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

        assertEquals(goal, agent.getGoalByName("TestGoal"));

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

    @Test
    public void testUpdateEmotionalState() {

        // Mock internalstate
        agent.internalState = mock(AgentInternalState.class);

        // Update emotional state
        agent.updateEmotionalState(mock(Emotion.class));

        // Verify the right method is called on AgentInternalState.
        verify(agent.internalState).updateEmotionalState(any(Emotion.class));

    }

    @Test
    public void testGetEmotionalState() {

        // Mock internalstate
        agent.internalState = mock(AgentInternalState.class);

        // Get emotional state
        agent.getEmotionalState(null);

        // Verify the right method is called on AgentInternalState.
        verify(agent.internalState).getEmotionalState(null);

    }

    @Test
    public void testPrintEmotionalState() {

        // Mock internalstate
        agent.internalState = mock(AgentInternalState.class);

        // GAIN = FALSE

        // Print emotional state
        agent.printEmotionalState(false);

        // Verify the right method is called on AgentInternalState.
        verify(agent.internalState).printEmotionalState(null);

        // GAIN = TRUE

        // Print emotional state (gain = true)
        agent.printEmotionalState(true);

        // Verify the right method is called on AgentInternalState.
        verify(agent.internalState).printEmotionalState(agent.gain);

    }

    @Test
    public void testUpdateRelation() {

        // Mock currentRelations
        agent.currentRelations = mock(AgentRelations.class);

        // Invalid relation factor
        agent.updateRelation(mock(Agent.class), 100);

        verifyZeroInteractions(agent.currentRelations);

        // Correct relation factor
        agent.updateRelation(mock(Agent.class), -1);

        // Verify iteraction with currentRelations
        verify(agent.currentRelations).updateRelation(any(Agent.class), any(Double.class));

    }

    @Test
    public void testHasRelationWith() {

        // Mock currentRelations
        agent.currentRelations = mock(AgentRelations.class);

        // Invalid relation factor
        agent.hasRelationWith(mock(Agent.class));

        // Verify iteraction with currentRelations
        verify(agent.currentRelations).hasRelationWith(any(Agent.class));

    }

    @Test
    public void testGetRelation() {

        // Mock currentRelations
        agent.currentRelations = mock(AgentRelations.class);

        // Invalid relation factor
        agent.getRelation(mock(Agent.class));

        // Verify iteraction with currentRelations
        verify(agent.currentRelations).getRelation(any(Agent.class));

    }

    @Test
    public void testPrintRelations() {

        // Mock currentRelations
        agent.currentRelations = mock(AgentRelations.class);

        // Invalid relation factor
        agent.printRelations(mock(Agent.class));

        // Verify iteraction with currentRelations
        verify(agent.currentRelations).printRelations(any(Agent.class));

    }

    @Test
    public void testAgentActionsEmptyCausalAgent() {

        // Check for empty causal Agent
        Emotion result = agent.agentActions(agent, null, 0);
        assertNull(result);

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
        assertEquals(expected, agent.currentRelations.get(0).emotionList.get(0));
    }
    
    @Test
    public void testAgentActionsCaseTwo() {

        // CASE 2

        Emotion result = agent.agentActions(agent, agent, .25);
        assertNull(result);
    }
    
    @Test
    public void testAgentActionsCaseThree_NoRelation() {

        Agent causalAgent = new Agent("CausalAgent");

        // CASE 3

        Emotion result = agent.agentActions(causalAgent, agent, .25);
        assertEquals(new Emotion(null, 0.0), result);
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
        assertEquals(expected, agent.currentRelations.get(0).emotionList.get(0));
    }
    

    @Test
    public void testEvaluateSocialEmotion() {
        
        Relation relation = mock(Relation.class);
        
        // Test four emotions
        when(relation.getLike()).thenReturn(1d);
        assertEquals("happy-for", agent.evaluateSocialEmotion(1, relation).name);
        
        when(relation.getLike()).thenReturn(-1d);
        assertEquals("resentment", agent.evaluateSocialEmotion(1, relation).name);
        
        when(relation.getLike()).thenReturn(1d);
        assertEquals("pity", agent.evaluateSocialEmotion(-1, relation).name);
        
        when(relation.getLike()).thenReturn(-1d);
        assertEquals("gloating", agent.evaluateSocialEmotion(-1, relation).name);
        
    }

    @Test
    public void testDecay() {
        // TODO()
    }

    @Test
    public void testToString() {
        assertEquals("<Agent[TestAgent]>", agent.toString());
    }

}
