package agent;

import decayfunction.DecayFunction;
import decayfunction.LinearDecay;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by svenpopping on 19/06/15.
 */
public class AgentRelationsTest {

    Relation relation;
    AgentRelations agentRelations;
    Agent agent;

    @Before
    public void setUp() throws Exception {
        agentRelations = new AgentRelations();
        relation = mock(Relation.class);
        agent = mock(Agent.class);

        agentRelations.updateRelation(agent, 0.5);

        when(agent.toString()).thenReturn("agent");
    }

    @After
    public void tearDown() throws Exception {
        agentRelations = null;
        relation = null;
        agent = null;
    }

    @Test
    public void testUpdateRelation() throws Exception {
        assertEquals(agent, agentRelations.get(0).getAgent());
    }

    @Test
    public void testHasRelationWith() throws Exception {
        Agent otherAgent = mock(Agent.class);
        assertFalse(agentRelations.hasRelationWith(otherAgent));

        agentRelations.updateRelation(otherAgent, 10);
        assertTrue(agentRelations.hasRelationWith(otherAgent));
    }

    @Test
    public void testGetRelation() throws Exception {
        Agent otherAgent = mock(Agent.class);
        assertNull(agentRelations.getRelation(otherAgent));

        agentRelations.updateRelation(otherAgent, 10);
        assertNotNull(agentRelations.getRelation(otherAgent));
        assertEquals(otherAgent, agentRelations.getRelation(otherAgent).getAgent());
        assertEquals(10d, agentRelations.getRelation(otherAgent).getLike(), 10E-15);
    }

    @Test
    public void testDecay() throws Exception {
        DecayFunction function = new LinearDecay(0.5);
        agentRelations.decay(function, 1000);
    }

    @Test
    public void testGetRelationsString() throws Exception {
        assertEquals(" for agent", agentRelations.getRelationsString(agent));
    }

    @Test
    public void testEvaluateSocialEmotion() throws Exception {

    }

    @Test
    public void testUpdateEmotionAsCausalAgent() throws Exception {

    }

    @Test
    public void testUpdateEmotionAsAffectedAgent() throws Exception {

    }
}