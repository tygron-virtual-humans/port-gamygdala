package agent;

import data.Emotion;
import data.Goal;
import decayfunction.DecayFunction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

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
        verify(agent.internalState).getEmotionalStateString(null);

        // GAIN = TRUE

        // Print emotional state (gain = true)
        agent.printEmotionalState(true);

        // Verify the right method is called on AgentInternalState.
        verify(agent.internalState).getEmotionalStateString(agent.gain);

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
        verify(agent.currentRelations).getRelationsString(any(Agent.class));

    }

    @Test(expected = AssertionError.class)
    public void testAgentActionsEmptyCausalAgent() {
        agent.agentActions(agent, null, 0);
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
        assertEquals(expected, agent.currentRelations.get(0).getEmotions().get(0));
    }

    @Test
    public void testAgentActionsCaseTwo() {
        assertEquals(null, agent.agentActions(agent, agent, .25));
    }


    @Test(expected = AssertionError.class)
    public void testAgentActionsCaseThree_NoRelation() {

        Agent causalAgent = new Agent("CausalAgent");

        // CASE 3
        agent.agentActions(causalAgent, agent, .25);
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
        assertEquals(expected, agent.currentRelations.get(0).getEmotions().get(0));
    }
    

    @Test
    public void testEvaluateSocialEmotion() {
        
        Relation relation = mock(Relation.class);
        
        // Test four emotions
        when(relation.getLike()).thenReturn(1d);
        assertEquals("happy-for", agent.evaluateSocialEmotion(1, relation).getName());
        
        when(relation.getLike()).thenReturn(-1d);
        assertEquals("resentment", agent.evaluateSocialEmotion(1, relation).getName());
        
        when(relation.getLike()).thenReturn(1d);
        assertEquals("pity", agent.evaluateSocialEmotion(-1, relation).getName());
        
        when(relation.getLike()).thenReturn(-1d);
        assertEquals("gloating", agent.evaluateSocialEmotion(-1, relation).getName());
        
    }

    @Test
    public void testDecayInternalEmotion() {
        DecayFunction decayFunction = mock(DecayFunction.class);
        agent.updateEmotionalState(mock(Emotion.class));

        agent.decay(decayFunction, 1);

        verify(decayFunction, times(1)).decay(0.0, 1);
    }

    @Test
    public void testDecayInternalEmotionTwo() {
        DecayFunction decayFunction = mock(DecayFunction.class);
        when(decayFunction.decay(0.6, 1000)).thenReturn(0.2);

        agent.updateEmotionalState(new Emotion("name", 0.6));
        agent.decay(decayFunction, 1000);
        verify(decayFunction, times(1)).decay(0.6, 1000);

        agent.decay(decayFunction, 2000);
        verify(decayFunction, times(1)).decay(0.2, 2000);

        ArrayList<Emotion> expected = new ArrayList<Emotion>(){{ add(new Emotion("name", 0.0)); }};
        assertEquals(expected, agent.getEmotionalState(0.2));
    }

//    @Test
//    public void testDecayRelationState() {
//        DecayFunction decayFunction = mock(DecayFunction.class);
//        Relation relation = mock(Relation.class);
//
//        when(decayFunction.decay(0.6, 1000)).thenReturn(0.4);
//        agent.decay(decayFunction, 1000);
//
//        agent.updateRelation(mock(Agent.class), 0.5);
//
//        when(agent.updateRelation(mock(Agent.class), 0.5)).thenReturn(relation);
//        verify(agent.currentRelations.get(0), times(1)).decay(decayFunction, 1000);
//    }

    @Test
    public void testToString() {
        assertEquals("<Agent[TestAgent]>", agent.toString());
    }

}
