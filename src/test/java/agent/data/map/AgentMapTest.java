package agent.data.map;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import agent.Agent;

/**
 * Tests for AgentMap.
 */
public class AgentMapTest {

    AgentMap agentMap;
    Agent agent;

    /**
     * Setup: Create a AgentMap and a Mock of Agent.
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        agentMap = new AgentMap();
        agent = mock(Agent.class);

        when(agent.getName()).thenReturn("TestAgent");
        agentMap.put(agent.getName(), agent);
    }

    /**
     * Teardown.
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
        agent = null;
        agentMap = null;
    }

    /**
     * GetAgentByName when name exist.
     * @throws Exception
     */
    @Test
    public void testGetAgentByName() throws Exception {
        assertEquals(agent, agentMap.getAgentByName("TestAgent"));
    }

    /**
     * GetAgentByName when name doesn't exist.
     * @throws Exception
     */
    @Test
    public void testGetAgentByNameFalse() throws Exception {
        assertEquals(null, agentMap.getAgentByName("TestAgent1"));
    }

    /**
     * ToStringAllEmotions with mock of Agent.
     * @throws Exception
     */
    @Test
    public void testToStringAllEmotions() throws Exception {
        assertEquals("null null", agentMap.toStringAllEmotions(true));
    }

    /**
     * Add Agent to the Map twice.
     * @throws Exception
     */
    @Test
    public void testPut() throws Exception {
        assertEquals(null, agentMap.put(agent.getName(), agent));
    }
}
